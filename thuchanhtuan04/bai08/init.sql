-- Tạo cơ sở dữ liệu mới
CREATE DATABASE smile_edu;

-- Kết nối vào database vừa tạo
\c smile_edu;

-- Tạo bảng lưu trữ thông tin người dùng
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) DEFAULT 'student',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Chèn sẵn một dòng dữ liệu mẫu
INSERT INTO users (username, email, role) 
VALUES ('admin_teacher', 'admin@smileedu.com', 'teacher');