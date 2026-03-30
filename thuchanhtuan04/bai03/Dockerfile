# 1. Sử dụng đúng yêu cầu đề bài
FROM node:18-alpine

# 2. Thư mục làm việc
WORKDIR /app

# 3. Copy file cấu hình và cài đặt package
COPY package*.json ./
RUN npm install

# 4. Copy toàn bộ source code
COPY . .

# 5. Build ứng dụng (Vite sẽ tạo ra thư mục 'dist')
RUN npm run build

# 6. Cài đặt serve để chạy web
RUN npm install -g serve

# 7. Mở cổng 3000
EXPOSE 3000

# 8. Chạy thư mục 'dist' qua serve
CMD ["serve", "-s", "dist", "-l", "3000"]