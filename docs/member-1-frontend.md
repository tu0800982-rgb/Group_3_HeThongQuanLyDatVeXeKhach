# MO TA CONG VIEC: DEV 1 - FRONTEND MASTER

## 1. Vai tro va muc tieu
Phu trach chinh toan bo tang Frontend cua he thong. Xay dung giao dien tuong tac truc quan, thu thap du lieu bieu mau va goi API xu ly xuong Backend.

## 2. Checklist cong viec cu the
- [ ] Xay dung kien truc thu muc Frontend va cau hinh router cho ung dung.
- [ ] Man hinh 1: Giao dien Trang chủ và form tìm kiếm chuyến xe (Hà Nội – Hải Phòng)..
- [ ] Man hinh 2: Giao dien Sơ đồ ghế của chuyến xe, hiển thị rõ ghế trống, ghế đã đặt và ghế VIP bằng màu sắc khác nhau.
- [ ] Man hinh 3: Form nhập thông tin đặt vé của khách hàng (Họ tên, SĐT, Email, Loại khách).
- [ ] Man hinh 4: Man hinh lựa chọn hình thức thanh toán và xác nhận hóa đơn thanh toán.
- [ ] Man hinh 5: Dashboard dành cho nhân viên, thống kê danh sách vé đã bán.
- [ ] Tích hợp HTTP Client (Fetch API hoặc Axios) để gửi và nhận dữ liệu JSON từ Backend.

## 3. Business rule lien quan o giao dien
* Vo hieu hoa (disabled click) doi voi nhung o ghe co trang thai la `BOOKED`.
* Hien thi dung chenh lech gia giua ghe thuong va ghe VIP khi khach hang thao tac click chon ghe tren sơ do.
* Kiem tra validate dinh dang bieu mau truoc khi gui (Khong bo trong Ho ten, dung format so dien thoai va email).

## 4. Input, Output va Moi quan he phu thuoc
* **Input:** Thao tac click, du lieu text do nguoi dung nhap vao cac o form tren UI.
* **Output:** Object JSON gui di kem HTTP Request den cac endpoint tuong ung cua Backend.
* **Phu thuoc vao:** Dev 4 (Cung cap danh sach URL endpoint va cau truc DTO response chuan).
* **Ai phu thuoc vao minh:** Khong co ai truc tiep ve mat code (nhung can giao dien de ca nhom co the demo du an).

## 5. Goi y cach tu kiem tra (Self-test)
* Thoi gian dau chua co API, tu tao mot hang so object (mock data) ngay tai file component de render giao dien xem so do ghe co hien thi dung mau sac theo trang thai hay khong.
* Kiem tra nut Bam Dat Ve xem co lay dung thong tin chuoi `seatId` va `tripId` da chon ra console hay khong.