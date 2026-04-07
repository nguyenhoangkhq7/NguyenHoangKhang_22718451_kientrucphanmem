# 🚀 Quick Start - ELK Stack

## 📝 Lệnh Nhanh

### Khởi Động

```powershell
cd phan3\bai07
docker-compose up -d
```

### Truy Cập

- **Kibana**: http://localhost:5601
  - Username: `elastic`
  - Password: `elastic_password_123`

### Kiểm Tra Status

```powershell
# Xem containers
docker-compose ps

# Xem logs
docker-compose logs -f

# Health check Elasticsearch
curl -u elastic:elastic_password_123 http://localhost:9200/_cluster/health
```

## 🧪 Test API từ PowerShell

```powershell
# Define credentials
$username = "elastic"
$password = "elastic_password_123"
$credentials = "$($username):$($password)"
$encodedCredentials = [Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes($credentials))
$headers = @{
    "Authorization" = "Basic $encodedCredentials"
    "Content-Type" = "application/json"
}

# 1️⃣ Cluster health
Invoke-WebRequest -Uri "http://localhost:9200/_cluster/health" -Headers $headers | ForEach-Object { $_.Content | ConvertFrom-Json }

# 2️⃣ Node info
Invoke-WebRequest -Uri "http://localhost:9200/" -Headers $headers | ForEach-Object { $_.Content | ConvertFrom-Json }

# 3️⃣ Create index
$body = '{}'
Invoke-WebRequest -Method PUT -Uri "http://localhost:9200/test-index" -Headers $headers -Body $body

# 4️⃣ Add document
$doc = @{
    name = "Test"
    timestamp = Get-Date -Format "O"
} | ConvertTo-Json
Invoke-WebRequest -Method POST -Uri "http://localhost:9200/test-index/_doc" -Headers $headers -Body $doc

# 5️⃣ Search
Invoke-WebRequest -Uri "http://localhost:9200/test-index/_search" -Headers $headers | ForEach-Object { $_.Content | ConvertFrom-Json }

# 6️⃣ List indices
Invoke-WebRequest -Uri "http://localhost:9200/_cat/indices?format=json" -Headers $headers | ForEach-Object { $_.Content | ConvertFrom-Json }
```

## 📊 Kibana UI Steps

### 1️⃣ Khám Phá Data

1. Mở http://localhost:5601
2. Đăng nhập: elastic / elastic_password_123
3. Vào **Discover**
4. Chọn index (nếu có)

### 2️⃣ Tạo Visualization

1. Vào **Visualize**
2. Click **Create visualization**
3. Chọn type (Bar, Line, Pie, v.v.)
4. Chọn index
5. Configure visualization

### 3️⃣ Tạo Dashboard

1. Vào **Dashboards**
2. Click **Create dashboard**
3. **Edit** dashboard
4. Add visualizations
5. **Save** dashboard

## 🛑 Dừng

```powershell
# Dừng containers
docker-compose down

# Dừng và xóa volumes
docker-compose down -v
```

## 🔧 Thay Đổi Credentials

```bash
# 1. Edit .env
# Thay ELASTIC_PASSWORD=new_password

# 2. Xóa volume cũ
docker-compose down -v

# 3. Khởi động lại
docker-compose up -d
```

## ⚡ Port Reference

| Service            | Port | URL                   |
| ------------------ | ---- | --------------------- |
| Elasticsearch      | 9200 | http://localhost:9200 |
| Elasticsearch Node | 9300 | localhost:9300        |
| Kibana             | 5601 | http://localhost:5601 |

## 📈 Popular Kibana Visualizations

- **Metrics**: Count, Average, MAX, MIN
- **Area Chart**: Trending data
- **Bar Chart**: Categorical data
- **Pie Chart**: Part-to-whole relationships
- **Line Chart**: Time series
- **Gauge**: Performance metrics
- **Map**: Geographical data
- **Table**: Detailed records

## 🎯 Next Steps

- Index logs từ ứng dụng
- Setup Beats agents (Filebeat, Metricbeat)
- Configure Logstash pipelines
- Create custom dashboards
- Setup alerts
