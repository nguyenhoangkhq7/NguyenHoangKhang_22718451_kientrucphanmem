const express = require("express");
const mysql = require("mysql2");
const app = express();

const dbConfig = {
  host: "mysql-db",
  user: "user",
  password: "password",
  database: "test_db",
};

let connection;
let isReconnecting = false;

function handleDisconnect() {
  if (isReconnecting) return;

  // Tạo kết nối mới
  connection = mysql.createConnection(dbConfig);

  connection.connect((err) => {
    if (err) {
      console.log("Chưa kết nối được MySQL, đang thử lại sau 5 giây...");
      triggerReconnect();
    } else {
      console.log("✅ Đã kết nối MySQL thành công!");
      isReconnecting = false;
    }
  });

  // Quan trọng: Bắt lỗi nếu kết nối bị ngắt giữa chừng sau khi đã chạy
  connection.on("error", (err) => {
    console.log("Mất kết nối Database hoặc lỗi kết nối...");
    if (
      err.code === "PROTOCOL_CONNECTION_LOST" ||
      err.code === "ECONNREFUSED" ||
      err.code === "PROTOCOL_ENQUEUE_AFTER_FATAL_ERROR"
    ) {
      triggerReconnect();
    } else {
      console.error("Lỗi DB khác:", err.message);
      triggerReconnect();
    }
  });
}

function triggerReconnect() {
  if (isReconnecting) return;
  isReconnecting = true;

  // Đóng kết nối cũ nếu còn
  if (connection) {
    connection.destroy(); // Sử dụng destroy để ngắt ngay lập tức
  }

  setTimeout(() => {
    isReconnecting = false;
    handleDisconnect();
  }, 5000);
}

// Bắt đầu quá trình kết nối
handleDisconnect();

app.get("/", (req, res) => {
  // Kiểm tra nếu chưa có kết nối thì báo lỗi nhẹ nhàng thay vì sập app
  if (!connection || connection.state === "disconnected") {
    return res.status(503).send("Database đang khởi động, vui lòng đợi...");
  }

  connection.query(
    'SELECT "Kết nối thành công!" AS message',
    (err, results) => {
      if (err) return res.status(500).send(err.message);
      res.send(results[0].message);
    },
  );
});

app.listen(3000, () => console.log("🚀 Server chạy trên cổng 3000"));
