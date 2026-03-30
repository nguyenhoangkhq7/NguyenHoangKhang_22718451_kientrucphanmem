# Sử dụng base image redis bản mới nhất theo yêu cầu
FROM redis:latest

# Copy file redis.conf từ máy tính vào thư mục cấu hình của container
COPY redis.conf /usr/local/etc/redis/redis.conf

# Khai báo cổng 6379 (cổng mặc định của Redis)
EXPOSE 6379

# Ghi đè lệnh khởi chạy mặc định để Redis đọc file cấu hình của chúng ta
CMD ["redis-server", "/usr/local/etc/redis/redis.conf"]