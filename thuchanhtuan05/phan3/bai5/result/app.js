const express = require("express");
const { Pool } = require("pg");
const app = express();

// PostgreSQL configuration
const pool = new Pool({
  user: process.env.POSTGRES_USER || "postgres",
  password: process.env.POSTGRES_PASSWORD || "postgres",
  host: process.env.POSTGRES_HOST || "db",
  port: process.env.POSTGRES_PORT || 5432,
  database: process.env.POSTGRES_DB || "votes",
});

// Middleware
app.use(express.json());
app.use(express.static("public"));

// Routes
app.get("/", (req, res) => {
  res.sendFile(__dirname + "/public/index.html");
});

// Get results from PostgreSQL
app.get("/api/results", async (req, res) => {
  try {
    const query = `
      SELECT choice, COUNT(*) as votes 
      FROM votes 
      GROUP BY choice 
      ORDER BY choice
    `;
    const result = await pool.query(query);

    let resultsObj = {
      a: 0,
      b: 0,
    };

    result.rows.forEach((row) => {
      if (row.choice === "a") {
        resultsObj.a = parseInt(row.votes);
      } else if (row.choice === "b") {
        resultsObj.b = parseInt(row.votes);
      }
    });

    res.json(resultsObj);
  } catch (err) {
    console.error("Error fetching results:", err);
    res.status(500).json({ error: err.message });
  }
});

app.get("/health", (req, res) => {
  res.json({ status: "UP" });
});

const PORT = process.env.PORT || 5001;
app.listen(PORT, () => {
  console.log(`Result app running on port ${PORT}`);
});
