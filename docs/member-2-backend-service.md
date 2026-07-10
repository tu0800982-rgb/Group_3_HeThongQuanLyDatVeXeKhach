# MO TA CONG VIEC: DEV 2 - BACKEND SERVICE LOGIC

## 1. Vai tro va muc tieu
Chiu trach nhiem xay dung loi logic nghiep vu (Tuan tu xu ly) o package `service`. Dam bao cac Business Rule duoc thuc thi nghiem ngat, dieu phoi qua trinh giua cho va thiet lap trang thai thuc the.

## 2. Checklist cong viec cu the
- [ ] Viet lop `BusTripService` xu ly tra cuu danh sach chuyen xe va tim kiem loc theo diem di/den.
- [ ] Viet lop `BookingService` de thuc hien quy trinh dat ve.
- [ ] Cai dat logic kiem tra ton tai cua chuyen xe va ghe xe.
- [ ] Cai dat logic dong bo hoa nham thay doi trang thai ghe tu `AVAILABLE` sang `BOOKED` khi co lenh dat ve thanh cong.
- [ ] Dinh nghia cac Custom Exception cho he thong nhu `TripNotFoundException`, `SeatBookedException`.

## 3. Business rule bat buoc phai xu ly
* không cho phép dat ghe da duoc dat. Neu ghe co trang thai `BOOKED`, lap tuc nem ra `SeatBookedException`.
* không cho phép đặt ghe hoac chuyen xe khong ton tai trong database.
* Goi logic tính tiền từ phan code da hinh cua Dev 5 de cap nhat thuoc tinh `finalPrice` cho thuc the `Ticket`. Gia ve cuoi cung bat buoc phai lon hon 0.

## 4. Input, Output va Moi quan he phu thuoc
* **Input:** Cac tham so ID hoac object DTO truyen tu tang Handler xuong (Vi du: `tripId`, `seatId`, `customerId`).
* **Output:** Cac doi tuong Java entity (`Ticket`, `Booking`, `BusTrip`) hoac nem ra Exception nghiep vu.
* **Phu thuoc vao:** Dev 3 (Can cac lop model va repo de CRUD du lieu JSON), Dev 5 (Can interface giam gia/thanh toan de ap dung vao logic tinh tien).
* **Ai phu thuoc vao minh:** Dev 4 (Handler phai goi Service de thuc hien nghiep vu).

## 5. Goi y cach tu kiem tra (Self-test)
* Viet mot ham main test tam thoi hoac dung Unit Test truyen vao mot `seatId` co san trang thai la `BOOKED` vao ham dat ve de kiem tra xem Service co nem ra dung loi mong muon hay khong.