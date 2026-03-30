import os

# Đọc biến môi trường APP_ENV, nếu không tìm thấy sẽ trả về 'Chưa được thiết lập'
app_env = os.getenv('APP_ENV', 'Chưa được thiết lập')

print(f"Xin chào! Môi trường hiện tại (APP_ENV) đang là: {app_env}")