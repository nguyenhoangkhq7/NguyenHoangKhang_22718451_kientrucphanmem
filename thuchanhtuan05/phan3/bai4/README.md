# Prometheus + Grafana Monitoring Stack

## Hướng dẫn sử dụng

### Khởi động stack

```bash
docker-compose up -d
```

### Truy cập các dịch vụ

- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (admin/admin)
- **cAdvisor**: http://localhost:8080
- **Node Exporter**: http://localhost:9100

### Cấu hình Grafana

1. Đăng nhập vào Grafana (admin/admin)
2. Vào **Configuration > Data Sources**
3. Thêm datasource Prometheus mới:
   - Name: Prometheus
   - URL: http://prometheus:9090
   - Click "Save & Test"

4. Cấu hình Dashboard:
   - Vào **+** > **Import Dashboard**
   - Sử dụng Dashboard ID từ Grafana Dashboard library
   - Ví dụ:
     - 1860 (Node Exporter Full)
     - 893 (Docker container metrics)
     - 3662 (Prometheus)

### Các metrics được thu thập

- **Prometheus**: Metrics của chính Prometheus
- **cAdvisor**: Metrics của Docker containers (CPU, Memory, Network, Disk)
- **Node Exporter**: System metrics (CPU, Memory, Disk, Network)

### Dừng stack

```bash
docker-compose down
```

### Dừng và xóa dữ liệu

```bash
docker-compose down -v
```
