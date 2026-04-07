# Django + Celery + Redis

Triển khai Django application với Celery worker và Redis làm message broker cho async task processing.

## 🏗️ Cấu Trúc Dự Án

```
phan3/bai8/
├── app/
│   ├── config/
│   │   ├── __init__.py
│   │   ├── celery.py           # Celery configuration
│   │   ├── settings.py         # Django settings
│   │   ├── urls.py             # URL routing
│   │   └── wsgi.py             # WSGI application
│   ├── tasks/
│   │   ├── __init__.py
│   │   ├── models.py           # Task model
│   │   ├── tasks.py            # Celery tasks
│   │   ├── views.py            # API views
│   │   ├── serializers.py      # DRF serializers
│   │   └── apps.py             # App config
│   ├── manage.py               # Django management
│   ├── requirements.txt        # Python dependencies
│   ├── Dockerfile              # Django & Celery image
│   └── init.sh                 # Initialization script
├── docker-compose.yaml         # Orchestration
└── README.md                   # This file
```

## 📋 Services

### Redis

- **Image**: redis:7-alpine
- **Port**: 6379
- **Volume**: redis_data:/data
- **Chức năng**: Message broker cho Celery + Result backend
- **Persistence**: Enabled (AOF)

### Django App

- **Port**: 8000
- **Framework**: Django 4.2.7 + Django REST Framework
- **Server**: Gunicorn (2 workers)
- **Volume**: ./app (code mount)
- **Environment**:
  - CELERY_BROKER_URL: redis://redis:6379/0
  - CELERY_RESULT_BACKEND: redis://redis:6379/1

### Celery Worker

- **Command**: celery -A config worker
- **Concurrency**: 4 workers
- **Message Broker**: Redis
- **Volume**: ./app (code mount)

## 🚀 Khởi Động

### 1️⃣ Chuẩn Bị

```bash
cd phan3\bai8
```

### 2️⃣ Build và Khởi Động

```bash
# Khởi động tất cả services
docker-compose up -d

# Xem logs
docker-compose logs -f

# Kiểm tra status
docker-compose ps
```

### 3️⃣ Khởi Tạo Django Database

```bash
# Chạy migrations
docker-compose exec app python manage.py migrate

# Tạo superuser (nếu cần)
docker-compose exec app python manage.py createsuperuser
# hoặc sử dụng script init.sh
docker-compose exec app bash init.sh
```

## 📱 Truy Cập

- **Django API**: http://localhost:8000/api/
- **Task Status**: http://localhost:8000/api/status/<task_id>/
- **Admin Panel**: http://localhost:8000/admin/

## 🧪 API Endpoints

### Create Tasks

#### 1️⃣ Add Numbers

```bash
curl -X POST http://localhost:8000/api/tasks/add/ \
  -H "Content-Type: application/json" \
  -d '{"a": 5, "b": 3}'
```

**Response:**

```json
{
  "task_id": 1,
  "celery_task_id": "abc123def456",
  "status": "pending"
}
```

#### 2️⃣ Multiply Numbers

```bash
curl -X POST http://localhost:8000/api/tasks/multiply/ \
  -H "Content-Type: application/json" \
  -d '{"a": 4, "b": 5}'
```

#### 3️⃣ Long Running Task

```bash
curl -X POST http://localhost:8000/api/tasks/long_task/ \
  -H "Content-Type: application/json" \
  -d '{"duration": 15}'
```

#### 4️⃣ Send Email (Simulated)

```bash
curl -X POST http://localhost:8000/api/tasks/send_email_task/ \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "subject": "Test", "message": "Hello"}'
```

### Check Task Status

```bash
curl http://localhost:8000/api/status/<celery_task_id>/
```

**Response:**

```json
{
  "task_id": "abc123def456",
  "state": "SUCCESS",
  "current": 100,
  "total": 100,
  "status": "Completed",
  "result": {
    "result": 8,
    "a": 5,
    "b": 3
  }
}
```

## 🔧 PowerShell Examples

```powershell
# 1️⃣ Create Add Task
$response = Invoke-WebRequest -Method POST `
  -Uri "http://localhost:8000/api/tasks/add/" `
  -ContentType "application/json" `
  -Body '{"a": 10, "b": 20}' | Select-Object -ExpandProperty Content | ConvertFrom-Json

$celeryTaskId = $response.celery_task_id
Write-Host "Task ID: $celeryTaskId"

# 2️⃣ Check Status (wait for completion)
$status = Invoke-WebRequest `
  -Uri "http://localhost:8000/api/status/$celeryTaskId/" | Select-Object -ExpandProperty Content | ConvertFrom-Json

$status | Format-Table -AutoSize

# 3️⃣ Long Running Task
$response = Invoke-WebRequest -Method POST `
  -Uri "http://localhost:8000/api/tasks/long_task/" `
  -ContentType "application/json" `
  -Body '{"duration": 20}' | Select-Object -ExpandProperty Content | ConvertFrom-Json

# 4️⃣ Monitor Progress
$taskId = $response.celery_task_id
for ($i = 0; $i -lt 25; $i++) {
    $status = Invoke-WebRequest `
      -Uri "http://localhost:8000/api/status/$taskId/" | Select-Object -ExpandProperty Content | ConvertFrom-Json

    Write-Host "State: $($status.state) - Current: $($status.current)/$($status.total)"
    Start-Sleep -Seconds 1
}
```

## 📊 Available Celery Tasks

### 1. `add_numbers(a, b)`

- Cộng 2 số
- Delay: ~1 giây

### 2. `multiply_numbers(a, b)`

- Nhân 2 số
- Delay: ~1 giây

### 3. `long_running_task(duration)`

- Task chạy lâu để test progress tracking
- Duration tuneable
- Reports progress mỗi step

### 4. `process_data(task_id, data)`

- Process data từ API
- Lưu kết quả vào Task model
- Database interaction example

### 5. `send_email(email, subject, message)`

- Simulated email sending
- Delay: ~2 giây

## 🔍 Monitoring Celery

### Xem Celery Logs

```bash
# Real-time logs
docker-compose logs -f celery_worker

# Specific worker logs
docker-compose logs celery_worker | grep -i "task"
```

### Redis CLI

```bash
# Kết nối Redis
docker-compose exec redis redis-cli

# Check key patterns
keys *

# Check queue
llen celery

# Get task info
hgetall celery_task_metaz
```

### Celery Flower (Optional)

Flower là web UI để monitor Celery tasks. Để thêm:

```yaml
flower:
  build: ./app
  command: celery -A config flower --port=5555
  ports:
    - "5555:5555"
  depends_on:
    - redis
```

Truy cập: http://localhost:5555

## 🛑 Dừng Stack

```bash
# Dừng containers
docker-compose down

# Dừng và xóa volumes
docker-compose down -v

# Dừng và xóa images
docker-compose down -v --rmi all
```

## 🔄 Hữu Ích Commands

```bash
# View running tasks
docker-compose logs celery_worker

# Scale workers
docker-compose up -d --scale celery_worker=3

# Check Redis queue size
docker-compose exec redis redis-cli llen celery

# Purge all tasks
docker-compose exec redis redis-cli FLUSHDB

# Access Django shell
docker-compose exec app python manage.py shell

# Run Django migrations
docker-compose exec app python manage.py migrate

# Create admin user
docker-compose exec app python manage.py createsuperuser
```

## 🔐 Notes

- Default admin: admin / admin123 (nếu tạo bằng init.sh)
- Mật khẩu trong settings.py chỉ cho development
- Mọi database queries nên sử dụng ORM
- Tasks có timeout 30 minutes
- Results expire sau khi completed

## 📈 Performance Tuning

### Increase Workers

```yaml
celery_worker:
  command: celery -A config worker --concurrency=8
```

### Set Task Timeout

Trong `tasks.py`:

```python
@shared_task(time_limit=600)  # 10 minutes
def long_task():
    pass
```

### Use Task Routing

```python
CELERY_TASK_ROUTES = {
    'tasks.tasks.send_email': {'queue': 'emails'},
    'tasks.tasks.long_running_task': {'queue': 'long_running'},
}
```

## 📚 References

- Django: https://www.djangoproject.com/
- Celery: https://docs.celeryproject.io/
- Redis: https://redis.io/
- Django REST Framework: https://www.django-rest-framework.org/
- Gunicorn: https://gunicorn.org/
