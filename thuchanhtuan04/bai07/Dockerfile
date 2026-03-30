# Sử dụng base image Python (bản slim cho nhẹ)
FROM python:3.9-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Khai báo biến môi trường APP_ENV với giá trị mặc định là development
ENV APP_ENV=development

# Copy file mã nguồn vào container
COPY app.py .

# Lệnh khởi chạy ứng dụng (in kết quả ra terminal)
CMD ["python", "app.py"]