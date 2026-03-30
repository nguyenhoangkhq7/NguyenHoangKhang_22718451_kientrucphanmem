# Sử dụng image PHP 8.2 kèm theo Apache theo yêu cầu
FROM php:8.2-apache

# (Tùy chọn) Bật module rewrite của Apache - rất cần thiết nếu sau này bạn dùng framework như Laravel
RUN a2enmod rewrite

# Khai báo cổng 80 mặc định của Apache
EXPOSE 80