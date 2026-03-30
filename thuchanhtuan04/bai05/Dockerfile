# Sử dụng image golang phiên bản alpine (rất nhẹ) làm base image
FROM golang:1.21-alpine

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Copy file mã nguồn main.go vào container
COPY main.go .

# Khởi tạo go module và build ứng dụng thành một file thực thi tên là 'server'
RUN go mod init my-go-app && go build -o server main.go

# Khai báo cổng 8080
EXPOSE 8080

# Lệnh khởi chạy file thực thi vừa được build ra
CMD ["./server"]