# ==========================================
# STAGE 1: Build code
# ==========================================
# Sử dụng node:18 (image đầy đủ) làm môi trường build và đặt tên là 'builder'
FROM node:18 AS builder

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy các file quản lý thư viện vào trước
COPY package*.json ./

# Cài đặt TOÀN BỘ thư viện (bao gồm cả devDependencies như nodemon, jest, typescript...)
RUN npm install

# Copy toàn bộ mã nguồn dự án vào container
COPY . .

# Nếu dự án của bạn có bước biên dịch (ví dụ: chuyển TypeScript sang JavaScript),
# bạn sẽ chạy lệnh build tại đây. (Ví dụ: RUN npm run build)


# ==========================================
# STAGE 2: Chạy ứng dụng đã build
# ==========================================
# Sử dụng node:18-alpine (image siêu nhẹ) để chạy ứng dụng thực tế
FROM node:18-alpine

# Thiết lập thư mục làm việc cho stage này
WORKDIR /app

# Copy package.json từ stage 'builder' sang
COPY --from=builder /app/package*.json ./

# Chỉ cài đặt các thư viện cần thiết để chạy production (bỏ qua devDependencies)
# Điều này giúp giảm đáng kể dung lượng của container cuối cùng
RUN npm install --omit=dev

# Copy mã nguồn (hoặc thư mục đã build như /dist, /build) từ stage 'builder' sang
# Giả sử file chạy chính của ứng dụng Express là app.js
COPY --from=builder /app/app.js ./

# Khai báo cổng mà ứng dụng lắng nghe
EXPOSE 3000

# Lệnh khởi chạy ứng dụng
CMD ["node", "app.js"]