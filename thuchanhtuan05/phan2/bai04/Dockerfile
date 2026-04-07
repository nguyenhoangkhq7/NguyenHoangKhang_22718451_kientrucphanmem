# 1. Dùng image node có sẵn
FROM node:18-alpine

# 2. Tạo thư mục làm việc trong container
WORKDIR /app

# 3. Copy file package.json và cài đặt thư viện
COPY package*.json ./
RUN npm install

# 4. Copy toàn bộ code vào container
COPY . .

# 5. Mở cổng 3000
EXPOSE 3000

# 6. Lệnh chạy ứng dụng
CMD ["node", "server.js"]