# Sử dụng image nginx phiên bản mới nhất (latest) theo yêu cầu
FROM nginx:latest

# Copy file index.html từ máy tính của bạn vào thư mục web mặc định của Nginx
COPY index.html /usr/share/nginx/html/

# Khai báo cổng 80 (cổng mặc định của giao thức HTTP mà Nginx sử dụng)
EXPOSE 80

# Lưu ý: Chúng ta không cần thêm lệnh CMD hay ENTRYPOINT ở đây, 
# vì base image nginx:latest đã cấu hình sẵn lệnh khởi chạy server Nginx rồi.