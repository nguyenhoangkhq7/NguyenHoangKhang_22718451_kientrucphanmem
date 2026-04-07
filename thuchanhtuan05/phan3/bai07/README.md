# ELK Stack (Elasticsearch + Kibana)

Triển khai Elasticsearch và Kibana đơn giản để quản lý logs và visualization.

## 🏗️ Cấu Trúc Dự Án

```
phan3/bai07/
├── docker-compose.yaml
├── .env                    # Credentials (không commit)
├── .env.example           # Template (commit)
└── README.md              # This file
```

## 📋 Services

### Elasticsearch

- **Image**: `docker.elastic.co/elasticsearch/elasticsearch:8.10.0`
- **Port**: 9200 (HTTP API)
- **Port**: 9300 (Node communication)
- **Volume**: `elasticsearch_data:/usr/share/elasticsearch/data`
- **Credentials**: elastic / elastic_password_123
- **Features**:
  - X-Pack Security enabled
  - Single-node cluster
  - Memory limit: 1024MB

### Kibana

- **Image**: `docker.elastic.co/kibana/kibana:8.10.0`
- **Port**: 5601 (Web UI)
- **Connection**: http://elasticsearch:9200
- **Credentials**: elastic / elastic_password_123
- **Features**:
  - Health check trước khi khởi động
  - Tự động kết nối đến Elasticsearch

## 🚀 Khởi Động

### 1️⃣ Chuẩn Bị

```bash
cd phan3\bai07

# (Optional) Thay đổi credentials trong .env
# Mặc định: elastic / elastic_password_123
```

### 2️⃣ Khởi Động Stack

```bash
# Khởi động
docker-compose up -d

# Xem logs
docker-compose logs -f

# Kiểm tra status
docker-compose ps
```

### 3️⃣ Truy Cập

- **Kibana**: http://localhost:5601
  - Username: `elastic`
  - Password: `elastic_password_123`

- **Elasticsearch API**: http://localhost:9200
  - Headers: `Authorization: Basic elastic:elastic_password_123` (Base64 encoded)

## 📝 API Endpoints

### Elasticsearch REST API

```bash
# Health check (không cần auth)
curl http://localhost:9200/_cluster/health

# Health check (có auth)
curl -u elastic:elastic_password_123 http://localhost:9200/_cluster/health

# Get node info
curl -u elastic:elastic_password_123 http://localhost:9200/

# List indices
curl -u elastic:elastic_password_123 http://localhost:9200/_cat/indices

# Create index
curl -X PUT -u elastic:elastic_password_123 http://localhost:9200/my-index

# Add document
curl -X POST -u elastic:elastic_password_123 \
  -H "Content-Type: application/json" \
  -d '{"name":"John","age":30}' \
  http://localhost:9200/my-index/_doc

# Search
curl -X GET -u elastic:elastic_password_123 http://localhost:9200/my-index/_search?pretty
```

### PowerShell Examples

```powershell
# Base64 encode credentials
$credentials = "elastic:elastic_password_123"
$encodedCredentials = [Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes($credentials))

# Health check
$headers = @{
    "Authorization" = "Basic $encodedCredentials"
}
Invoke-WebRequest -Uri "http://localhost:9200/_cluster/health" -Headers $headers | ConvertFrom-Json

# Get node info
Invoke-WebRequest -Uri "http://localhost:9200/" -Headers $headers | ConvertFrom-Json

# Create index
Invoke-WebRequest -Method PUT `
  -Uri "http://localhost:9200/test-index" `
  -Headers $headers

# Add document
$body = @{
    name = "Test Document"
    timestamp = Get-Date -Format "O"
} | ConvertTo-Json

Invoke-WebRequest -Method POST `
  -Uri "http://localhost:9200/test-index/_doc" `
  -Headers $headers `
  -ContentType "application/json" `
  -Body $body
```

## 🎯 Kibana Features

### Discover

- Khám phá và tìm kiếm logs/dữ liệu
- Xem chi tiết từng document
- Lọc theo time range

### Visualize

- Tạo biểu đồ từ dữ liệu
- Các loại: Bar, Line, Pie, Gauge, Map, v.v.

### Dashboard

- Kết hợp nhiều visualization
- Share với team
- Auto-refresh data

### Canvas

- Tạo custom presentation
- Thêm real-time data visualization

## 🔧 Cấu Hình

### Thay Đổi Credentials

```bash
# 1. Edit .env
ELASTIC_PASSWORD=new_password_123

# 2. Rebuild elasticsearch container
docker-compose down -v
docker-compose up -d

# 3. Kibana sẽ tự động update credentials
```

### Thay Đổi Memory Limit

Nếu cần tăng memory cho Elasticsearch:

```yaml
# docker-compose.yaml
elasticsearch:
  mem_limit: 2048m # Thay từ 1024m
```

## 📊 Ví Dụ: Index và Query

### Tạo Index với Mapping

```bash
curl -X PUT -u elastic:elastic_password_123 \
  -H "Content-Type: application/json" \
  -d '{
    "mappings": {
      "properties": {
        "timestamp": {
          "type": "date"
        },
        "message": {
          "type": "text"
        },
        "level": {
          "type": "keyword"
        }
      }
    }
  }' \
  http://localhost:9200/logs
```

### Index Documents

```bash
curl -X POST -u elastic:elastic_password_123 \
  -H "Content-Type: application/json" \
  -d '{
    "timestamp": "2026-04-07T10:00:00Z",
    "message": "Application started",
    "level": "INFO"
  }' \
  http://localhost:9200/logs/_doc

curl -X POST -u elastic:elastic_password_123 \
  -H "Content-Type: application/json" \
  -d '{
    "timestamp": "2026-04-07T10:05:00Z",
    "message": "Error processing request",
    "level": "ERROR"
  }' \
  http://localhost:9200/logs/_doc
```

### Search Documents

```bash
# Tất cả documents
curl -X GET -u elastic:elastic_password_123 \
  http://localhost:9200/logs/_search?pretty

# Query theo level
curl -X GET -u elastic:elastic_password_123 \
  -H "Content-Type: application/json" \
  -d '{
    "query": {
      "match": {
        "level": "ERROR"
      }
    }
  }' \
  http://localhost:9200/logs/_search?pretty
```

## 🛑 Dừng Stack

```bash
# Dừng containers (giữ data)
docker-compose down

# Dừng và xóa volumes (xóa tất cả data)
docker-compose down -v

# Dừng và xóa images
docker-compose down -v --rmi all
```

## 🔐 Security Notes

- Credentials được lưu trong `.env` (không commit vào git)
- Mặc định chỉ bật authentication, SSL disabled (phù hợp lab)
- Để production, enable SSL:
  ```yaml
  - xpack.security.http.ssl.enabled=true
  - xpack.security.transport.ssl.enabled=true
  ```

## 📈 Troubleshooting

### Elasticsearch không khởi động

```bash
# Kiểm tra logs
docker-compose logs elasticsearch

# Điều chỉnh memory
# Edit docker-compose.yaml, tăng mem_limit hoặc VM memory
```

### Kibana không kết nối được

```bash
# Kiểm tra kết nối
docker-compose logs kibana

# Reset credentials
docker-compose down -v
docker-compose up -d
```

### Health check failed

```bash
# Chờ Elasticsearch khởi động (có thể mất 1-2 phút)
docker-compose logs elasticsearch

# Manual health check
curl -u elastic:elastic_password_123 http://localhost:9200/_cluster/health
```

## 📚 References

- Elasticsearch Docs: https://www.elastic.co/guide/en/elasticsearch/reference/8.10/
- Kibana Docs: https://www.elastic.co/guide/en/kibana/8.10/
- Docker Images: https://www.docker.elastic.co/

## 🎓 Học Thêm

- Setup log aggregation từ containers khác
- Beats agents (Filebeat, Metricbeat)
- Logstash cho data processing
- Index lifecycle management (ILM)
- Custom dashboards
