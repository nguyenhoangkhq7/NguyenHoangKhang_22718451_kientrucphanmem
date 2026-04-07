# CI/CD Pipeline với Docker Compose

Bài tập mô phỏng pipeline development/testing/production sử dụng Docker Compose.

## 🏗️ Cấu trúc Dự án

```
phan3/bai6/
├── app/
│   ├── app.py                 # Flask application
│   ├── requirements.txt       # Python dependencies
│   ├── Dockerfile             # Production image
│   ├── Dockerfile.test        # Test image
│   └── tests/
│       └── test_app.py        # Unit tests
├── nginx/
│   ├── Dockerfile             # Nginx image
│   └── nginx.conf             # Nginx configuration
├── docker-compose-dev.yml     # Development environment
├── docker-compose-prod.yml    # Production environment
└── README.md                  # This file
```

## 📋 Các Môi Trường

### Development Mode (docker-compose-dev.yml)

**Services:**

- **app**: Flask app với code mounted từ host (hot reload)
  - Port: 5000
  - Volume: `./app:/app` (code thay đổi tức lập cập nhật)
- **tests**: Unit tests chạy mỗi 10 giây
  - Tự động chạy tests khi code thay đổi
  - Hiển thị coverage report

**Điểm đặc biệt:**

- Code được mount từ host → hot reload khi sửa code
- Tests chạy tự động
- Phù hợp cho development

### Production Mode (docker-compose-prod.yml)

**Services:**

- **app**: Flask app (không mount code)
  - Expose port 5000 (không public)
- **nginx**: Reverse proxy
  - Port: 80 (public)
  - Proxy requests tới app

**Điểc đặc biệt:**

- App chạy trong container được build sẵn
- Nginx làm reverse proxy
- Phù hợp cho production

## 🚀 Cách Sử Dụng

### 1️⃣ Khởi động Development Environment

```bash
# Vào thư mục bai6
cd phan3\bai6

# Khởi động dev stack (app + tests)
docker-compose -f docker-compose-dev.yml up -d

# Xem logs
docker-compose -f docker-compose-dev.yml logs -f

# Dùng lệnh ngắn (cần set .env hoặc alias)
# hoặc sử dụng file mặc định nếu đặt tên là docker-compose.yml
```

**Truy cập:**

- App: http://localhost:5000
- API test: http://localhost:5000/api/add/2/3

**Thay đổi code** và nhận xét:

- App sẽ reload tự động (Flask debug mode)
- Tests chạy lại mỗi 10 giây

```bash
# Dừng dev stack
docker-compose -f docker-compose-dev.yml down
```

### 2️⃣ Khởi động Production Environment

```bash
# Khởi động prod stack (app + nginx)
docker-compose -f docker-compose-prod.yml up -d

# Xem logs
docker-compose -f docker-compose-prod.yml logs -f
```

**Truy cập:**

- App via Nginx: http://localhost (port 80)
- API test: http://localhost/api/multiply/4/5

```bash
# Dừng prod stack
docker-compose -f docker-compose-prod.yml down
```

## 🧪 API Endpoints

| Endpoint                | Method | Mô tả         | Ví dụ                                       |
| ----------------------- | ------ | ------------- | ------------------------------------------- |
| `/`                     | GET    | Hello message | curl http://localhost:5000/                 |
| `/health`               | GET    | Health check  | curl http://localhost:5000/health           |
| `/api/add/<a>/<b>`      | GET    | Cộng 2 số     | curl http://localhost:5000/api/add/5/3      |
| `/api/multiply/<a>/<b>` | GET    | Nhân 2 số     | curl http://localhost:5000/api/multiply/4/5 |

## 🧬 Unit Tests

```bash
# Chạy tests thủ công
docker-compose -f docker-compose-dev.yml exec tests pytest -v

# Xem coverage report
docker-compose -f docker-compose-dev.yml exec tests pytest --cov=. --cov-report=html
```

**Các test case:**

- `test_index()` - Kiểm tra endpoint /
- `test_health()` - Kiểm tra health check
- `test_add()` - Kiểm tra endpoint /api/add/
- `test_multiply()` - Kiểm tra endpoint /api/multiply/
- `test_add_negative()` - Kiểm tra số âm
- `test_multiply_zero()` - Kiểm tra số 0

## 📊 So Sánh Dev vs Prod

| Tính năng       | Dev            | Prod             |
| --------------- | -------------- | ---------------- |
| Code mount      | ✅ Hot reload  | ❌ Build static  |
| Tests           | ✅ Auto run    | ❌ không         |
| Debug           | ✅ Flask debug | ❌ Tắt           |
| Port app public | ✅ :5000       | ❌ Internal      |
| Nginx           | ❌ không       | ✅ Reverse proxy |
| Auto restart    | ❌ không       | ✅ Có            |

## 🔧 Thêm Dependency

Nếu thêm package Python:

```bash
# 1️⃣ Cập nhật requirements.txt
echo "new_package==1.0.0" >> app/requirements.txt

# 2️⃣ Rebuild images
docker-compose -f docker-compose-dev.yml build
docker compose -f docker-compose-prod.yml build

# 3️⃣ Khởi động lại
docker-compose -f docker-compose-dev.yml up -d
```

## 🛑 Dừng Toàn Bộ

```bash
# Dừng dev
docker-compose -f docker-compose-dev.yml down

# Dừng prod
docker-compose -f docker-compose-prod.yml down

# Dừng và xóa volumes
docker-compose -f docker-compose-dev.yml down -v
docker-compose -f docker-compose-prod.yml down -v
```

## 🎯 Workflow Thực Tế

### Development Cycle

1. Sửa code ở `/app/app.py` hoặc `/app/tests/test_app.py`
2. Container sẽ auto-reload code (Flask debug mode)
3. Tests chạy lại mỗi 10 giây
4. Kiểm tra coverage report

### Deployment Flow

1. Dev team tests ở dev environment
2. Khi sẵn sàng, build production image
3. Deploy bằng docker-compose-prod.yml
4. Nginx làm load balancer/reverse proxy

## 📝 Ghi Chú

- **docker-compose-dev.yml**: Chỉ có app + tests, ports:5000 công khai
- **docker-compose-prod.yml**: Có app (nội bộ) + nginx (port 80 công khai)
- Sử dụng `-f` flag để chỉ định file compose khác nhau
- Tests tự động khởi động khi app sẵn sàng
