# Bus Ticket Booking Management System

Ứng dụng quản lý đặt vé xe khách tuyến **Hà Nội ↔ Hải Phòng**, gồm frontend HTML/CSS/JavaScript thuần và backend Java 17 Spring Boot. Dữ liệu chỉ được lưu trong bộ nhớ bằng `ArrayList`; không dùng database, JPA hay ORM.

## Cấu trúc

```text
frontend/                         # Giao diện tĩnh, dùng Fetch API
  index.html                       # Trang chủ
  trips.html, seats.html           # Tìm chuyến và chọn ghế
  booking.html, payment.html       # Luồng đặt vé/thanh toán
  success.html, history.html       # Vé thành công và tra cứu
  dashboard.html                   # Dashboard nhân viên
backend/                           # Spring Boot REST API
  src/main/java/com/busbooking/
    controller/ service/ repository/ model/ dto/
    enums/ exception/ utils/ config/
```

## Yêu cầu

- Java 17
- Maven 3.9+
- Một static web server cho thư mục `frontend` (ví dụ Live Server trên VS Code)

## Chạy dự án

1. Chạy backend từ thư mục `backend`:

   ```bash
   mvn spring-boot:run
   ```

   API chạy tại `http://localhost:8080`.

2. Mở thư mục `frontend` bằng static web server tại `http://localhost:5500`.
3. Truy cập `http://localhost:5500/index.html`.

> CORS đã cho phép `http://localhost:5500` và `http://127.0.0.1:5500`.

## Dữ liệu khởi tạo

Khi backend khởi động, hệ thống tạo 6 chuyến xe, mỗi chuyến 45 ghế; 20 khách hàng; 10 booking/ticket và 5 thanh toán thành công. Các chuyến được đặt từ ngày kế tiếp thời điểm chạy ứng dụng để luôn có thể thử luồng booking.

Mã khách hàng mẫu để tra cứu lịch sử: `CUS-001` đến `CUS-020`.

## API

| Method | Endpoint | Chức năng |
|---|---|---|
| GET | `/api/trips` | Danh sách chuyến xe |
| GET | `/api/trips/search` | Tìm chuyến theo điểm đi/đến, ngày, ghế, giá, loại xe |
| GET | `/api/trips/{tripId}` | Chi tiết chuyến |
| GET | `/api/trips/{tripId}/seats` | Sơ đồ ghế |
| POST | `/api/bookings` | Tạo booking |
| GET | `/api/bookings/{bookingId}` | Chi tiết booking |
| GET | `/api/bookings/customer/{customerId}` | Lịch sử booking |
| PUT | `/api/bookings/{bookingId}/cancel` | Hủy booking và giải phóng ghế |
| POST | `/api/payments` | Thanh toán booking |
| GET | `/api/payments/{paymentId}` | Chi tiết thanh toán |
| GET | `/api/staff/dashboard` | Chỉ số dashboard |
| GET | `/api/staff/report` | Báo cáo tổng hợp |

Mọi API trả về định dạng:

```json
{"success": true, "message": "...", "data": {}}
```

## Kiểm thử

Chạy:

```bash
cd C:\Users\LEGION\Group_3_HeThongQuanLyDatVeXeKhach\backend
& "D:\apache-maven-3.9.16-bin\bin\mvn.cmd" spring-boot:run
```

Test tích hợp kiểm tra danh sách chuyến, 45 ghế/chuyến, dashboard và luồng booking → payment.