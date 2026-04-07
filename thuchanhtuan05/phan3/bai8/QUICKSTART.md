# 🚀 Quick Start - Django + Celery + Redis

## 📝 Lệnh Nhanh

### Khởi Động

```bash
cd phan3\bai8
docker-compose up -d
```

### Khởi Tạo Database

```bash
docker-compose exec app python manage.py migrate
docker-compose exec app bash init.sh
```

### Admin Login

- URL: http://localhost:8000/admin/
- Username: `admin`
- Password: `admin123`

## 🧪 Test API

### 1️⃣ Create Add Task

```bash
curl -X POST http://localhost:8000/api/tasks/add/ \
  -H "Content-Type: application/json" \
  -d '{"a": 5, "b": 3}'
```

**Output:**

```json
{
  "task_id": 1,
  "celery_task_id": "abc-123",
  "status": "pending"
}
```

### 2️⃣ Check Status

```bash
curl http://localhost:8000/api/status/abc-123/
```

### 3️⃣ List All Tasks

```bash
curl http://localhost:8000/api/tasks/
```

## 💻 PowerShell Examples

```powershell
# Create task
$resp = Invoke-WebRequest -Method POST `
  -Uri "http://localhost:8000/api/tasks/add/" `
  -ContentType "application/json" `
  -Body '{"a":10,"b":20}' | ConvertFrom-Json

# Check status
$taskId = $resp.celery_task_id
Invoke-WebRequest -Uri "http://localhost:8000/api/status/$taskId/" | ConvertFrom-Json
```

## 📊 Service Status

```bash
# View all containers
docker-compose ps

# View logs
docker-compose logs -f

# View only celery logs
docker-compose logs -f celery_worker

# View redis
docker-compose logs -f redis
```

## 🧪 Available Endpoints

| Endpoint                      | Method | Description          |
| ----------------------------- | ------ | -------------------- |
| `/api/tasks/`                 | GET    | List all tasks       |
| `/api/tasks/add/`             | POST   | Create add task      |
| `/api/tasks/multiply/`        | POST   | Create multiply task |
| `/api/tasks/long_task/`       | POST   | Create long task     |
| `/api/tasks/send_email_task/` | POST   | Create email task    |
| `/api/status/<task_id>/`      | GET    | Check task status    |

## 📝 Sample Payloads

### Add Task

```json
{ "a": 5, "b": 3 }
```

### Multiply Task

```json
{ "a": 4, "b": 5 }
```

### Long Task

```json
{ "duration": 15 }
```

### Email Task

```json
{
  "email": "user@example.com",
  "subject": "Test Subject",
  "message": "Test Message"
}
```

## 🛑 Stop Stack

```bash
# Stop containers
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## ⚡ Common Commands

```bash
# Django shell
docker-compose exec app python manage.py shell

# Run migrations
docker-compose exec app python manage.py migrate

# Make migrations
docker-compose exec app python manage.py makemigrations

# Access Redis
docker-compose exec redis redis-cli

# View Celery worker logs
docker-compose logs celery_worker

# Tail logs live
docker-compose logs -f

# Restart specific service
docker-compose restart celery_worker

# Scale workers
docker-compose up -d --scale celery_worker=3
```

## 🎯 Task States

- `PENDING`: Task waiting to be executed
- `STARTED`: Task started processing
- `PROGRESS`: Task in progress (with updates)
- `SUCCESS`: Task completed successfully
- `FAILURE`: Task failed with error
- `RETRY`: Task retrying after failure

## 🔗 URLs

- **Django API**: http://localhost:8000/api/
- **Admin Panel**: http://localhost:8000/admin/
- **Redis**: localhost:6379 (internal)
- **Celery Worker**: (background, no web interface by default)

## 💡 Tips

1. **Long Running Tasks**: Use duration parameter to test progress
2. **Monitor Progress**: Poll `/api/status/<task_id>/` to see progress
3. **Database**: SQLite (auto-created at ./app/db.sqlite3)
4. **Logs**: Check `docker-compose logs celery_worker` for task execution details
5. **Redis Persistence**: Data saved to docker volume, persists after restart

## 📚 Next Steps

- Explore Celery Flower for better monitoring
- Add task retry logic
- Implement task result expiration
- Add task routing and priority queues
