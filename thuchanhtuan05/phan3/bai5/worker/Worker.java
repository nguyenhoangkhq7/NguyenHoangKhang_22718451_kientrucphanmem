import redis.clients.jedis.Jedis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

public class Worker {
    public static void main(String[] args) {
        Jedis jedis = null;
        Connection connection = null;

        try {
            // Connect to Redis
            String redisHost = System.getenv("REDIS_HOST") != null ? 
                System.getenv("REDIS_HOST") : "redis";
            int redisPort = System.getenv("REDIS_PORT") != null ? 
                Integer.parseInt(System.getenv("REDIS_PORT")) : 6379;
            jedis = new Jedis(redisHost, redisPort);
            jedis.ping();
            System.out.println("Connected to Redis: " + redisHost + ":" + redisPort);

            // Connect to PostgreSQL
            String dbHost = System.getenv("POSTGRES_HOST") != null ? 
                System.getenv("POSTGRES_HOST") : "db";
            String dbPort = System.getenv("POSTGRES_PORT") != null ? 
                System.getenv("POSTGRES_PORT") : "5432";
            String dbName = System.getenv("POSTGRES_DB") != null ? 
                System.getenv("POSTGRES_DB") : "votes";
            String dbUser = System.getenv("POSTGRES_USER") != null ? 
                System.getenv("POSTGRES_USER") : "postgres";
            String dbPassword = System.getenv("POSTGRES_PASSWORD") != null ? 
                System.getenv("POSTGRES_PASSWORD") : "postgres";

            String jdbcUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
            Class.forName("org.postgresql.Driver");

            // Wait for database to be ready
            int maxAttempts = 30;
            int attempt = 0;
            while (attempt < maxAttempts) {
                try {
                    connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
                    System.out.println("Connected to PostgreSQL: " + jdbcUrl);
                    break;
                } catch (Exception e) {
                    attempt++;
                    if (attempt >= maxAttempts) {
                        throw e;
                    }
                    System.out.println("Waiting for database... (" + attempt + "/" + maxAttempts + ")");
                    Thread.sleep(1000);
                }
            }

            // Create votes table if not exists
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS votes (id SERIAL PRIMARY KEY, choice VARCHAR(10))");
            stmt.close();
            System.out.println("Votes table ready");

            // Worker loop - process votes from Redis
            System.out.println("Worker started - listening for votes...");
            while (true) {
                try {
                    // Get vote from Redis
                    String voteA = jedis.get("vote_a");
                    String voteB = jedis.get("vote_b");

                    int countA = voteA != null ? Integer.parseInt(voteA) : 0;
                    int countB = voteB != null ? Integer.parseInt(voteB) : 0;

                    // Get current count from database
                    java.sql.ResultSet rs = stmt.executeQuery(
                        "SELECT choice, COUNT(*) as count FROM votes GROUP BY choice"
                    );
                    int dbCountA = 0;
                    int dbCountB = 0;
                    while (rs.next()) {
                        if (rs.getString("choice").equals("a")) {
                            dbCountA = rs.getInt("count");
                        } else if (rs.getString("choice").equals("b")) {
                            dbCountB = rs.getInt("count");
                        }
                    }
                    rs.close();

                    // Add new votes to database
                    for (int i = dbCountA; i < countA; i++) {
                        stmt.executeUpdate("INSERT INTO votes (choice) VALUES ('a')");
                    }
                    for (int i = dbCountB; i < countB; i++) {
                        stmt.executeUpdate("INSERT INTO votes (choice) VALUES ('b')");
                    }

                    connection.commit();
                    
                    if (countA > dbCountA || countB > dbCountB) {
                        System.out.println(String.format("Processed votes - A: %d, B: %d", countA, countB));
                    }

                    // Sleep before next check
                    Thread.sleep(1000);

                } catch (Exception e) {
                    System.err.println("Error in worker loop: " + e.getMessage());
                    e.printStackTrace();
                    Thread.sleep(5000);
                }
            }

        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } finally {
            try {
                if (connection != null) connection.close();
                if (jedis != null) jedis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
