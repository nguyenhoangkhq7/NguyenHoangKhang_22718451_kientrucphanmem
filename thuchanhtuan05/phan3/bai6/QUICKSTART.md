# Quick Start Commands

## 📝 Chỉ cần copy-paste lệnh này

### Chế độ Development

```powershell
# Khởi động
cd phan3\bai6
docker-compose -f docker-compose-dev.yml up -d

# Xem logs real-time
docker-compose -f docker-compose-dev.yml logs -f tests

# Chạy tests thủ công
docker-compose -f docker-compose-dev.yml exec tests pytest -v

# Dừng
docker-compose -f docker-compose-dev.yml down
```

### Chế độ Production

```powershell
# Khởi động
cd phan3\bai6
docker-compose -f docker-compose-prod.yml up -d

# Xem logs
docker-compose -f docker-compose-prod.yml logs -f

# Kiểm tra
curl http://localhost
curl http://localhost/api/add/10/20

# Dừng
docker-compose -f docker-compose-prod.yml down
```

## 🧪 Test API từ PowerShell

```powershell
# Test development
$response = Invoke-WebRequest -Uri "http://localhost:5000/api/add/5/3" -UseBasicParsing
$response.Content | ConvertFrom-Json

# Test production
$response = Invoke-WebRequest -Uri "http://localhost/api/add/5/3" -UseBasicParsing
$response.Content | ConvertFrom-Json
```

## 🔄 Chuyển đổi giữa Dev và Prod

```powershell
# 1. Dừng dev
docker-compose -f docker-compose-dev.yml down

# 2. Khởi động prod
docker-compose -f docker-compose-prod.yml up -d

# 3. Khi cần back to dev
docker-compose -f docker-compose-prod.yml down
docker-compose -f docker-compose-dev.yml up -d
```

## 📊 Xem Status

```powershell
# Xem containers đang chạy
docker ps

# Xem chi tiết network
docker network ls
docker inspect <network_name>

# Xem logs từ service cụ thể
docker logs <container_name>
```
