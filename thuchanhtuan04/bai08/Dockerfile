# Sử dụng đúng phiên bản postgres:15 theo yêu cầu
FROM postgres:15

# Copy file script SQL vào thư mục khởi tạo mặc định của PostgreSQL
COPY init.sql /docker-entrypoint-initdb.d/

# Khai báo cổng 5432 (cổng mặc định của Postgres)
EXPOSE 5432