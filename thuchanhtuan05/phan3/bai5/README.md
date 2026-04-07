# Multi-tier Voting App

Ứng dụng voting gồm 5 services: Vote Frontend, Result Backend, Redis cache, Java Worker, và PostgreSQL database.

## Architecture

```
┌─────────────────────────────────────────────┐
│          Vote (Python/Flask)                │ Port 5000
│     - User Interface for voting             │
└──────────────┬──────────────────────────────┘
               │ (stores votes temporarily)
               ▼
┌─────────────────────────────────────────────┐
│     Redis (Cache Layer)                     │ Port 6379
│     - Stores temporary vote counts          │
└──────────────┬──────────────────────────────┘
               │ (processes votes)
               ▼
┌─────────────────────────────────────────────┐
│  Worker (Java - Message Processor)          │
│  - Reads from Redis                         │
│  - Writes to PostgreSQL                     │
└──────────────┬──────────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────────┐
│  PostgreSQL Database                        │ Port 5432
│  - Persistent vote storage                  │
└──────────────┬──────────────────────────────┘
               │ (queries results)
               ▼
┌─────────────────────────────────────────────┐
│   Result (Node.js/Express)                  │ Port 5001
│   - Display voting results in real-time     │
└─────────────────────────────────────────────┘
```

## Services

### 1. Vote (Python Flask)

- **Port**: 5000
- **Chức năng**:
  - UI để người dùng bỏ phiếu
  - Lưu vote tạm vào Redis
  - Hiển thị kết quả real-time

### 2. Result (Node.js Express)

- **Port**: 5001
- **Chức năng**:
  - Truy vấn kết quả từ PostgreSQL
  - Hiển thị biểu đồ kết quả
  - Cập nhật real-time

### 3. Redis

- **Port**: 6379
- **Chức năng**:
  - Cache tạm thời cho votes
  - Giao tiếp giữa vote và worker

### 4. Worker (Java)

- **Chức năng**:
  - Đọc từ Redis định kỳ
  - Ghi vào PostgreSQL
  - Xử lý dữ liệu votes

### 5. PostgreSQL

- **Port**: 5432
- Tài khoản: postgres / postgres
- Database: votes
- **Chức năng**: Lưu trữ dữ liệu votes vĩnh viễn

## Khởi động Stack

```bash
# Vào thư mục bai5
cd phan3\bai5

# Build và chạy tất cả services
docker-compose up -d

# Xem logs
docker-compose logs -f

# Dừng stack
docker-compose down

# Dừng và xóa dữ liệu
docker-compose down -v
```

## Truy cập Ứng dụng

- **Vote (Bỏ phiếu)**: http://localhost:5000
- **Result (Kết quả)**: http://localhost:5001

## Quy trình Bỏ phiếu

1. Người dùng truy cập http://localhost:5000
2. Nhấn "Vote for A" hoặc "Vote for B"
3. Vote được lưu trong Redis ngay lập tức
4. Worker đọc từ Redis và ghi vào PostgreSQL
5. Kết quả được cập nhật trên result app (http://localhost:5001)

## Kiểm tra Status

```bash
# Kiểm tra Redis
docker exec voting_redis redis-cli ping

# Kiểm tra PostgreSQL
docker exec voting_db psql -U postgres -d votes -c "SELECT * FROM votes"

# Kiểm tra Redis votes
docker exec voting_redis redis-cli get vote_a
docker exec voting_redis redis-cli get vote_b

# Xem logs của Worker
docker-compose logs worker
```

## Ports

- 5000: Vote Frontend (Python)
- 5001: Result Backend (Node.js)
- 6379: Redis
- 5432: PostgreSQL

## Lưu ý

- Worker cần kết nối thành công với cả Redis và PostgreSQL
- Nếu Worker lỗi, kiểm tra các environment variables
- Votes được cache tạm trong Redis trước khi ghi vào DB
- Để reset votes: xóa toàn bộ containers với `docker-compose down -v`
