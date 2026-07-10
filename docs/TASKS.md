### File 2: `docs/TASKS.md`

```markdown
# BANG TONG QUAN PHAN CONG CONG VIEC

## 1. Ma tran phan chia cong viec

| Thanh vien | Vai tro chinh | Cong viec chinh | File/Class phu trach 
| :--- | :--- | :--- | :--- | :--- |
| **Dev 1** | Frontend | Xây dựng giao diện (UI), tích hợp API, hiển thị chức năng tìm kiếm và chọn ghế | Toan bo component Frontend 
| **Dev 2** | Backend Service | Core logic dat ve, kiểm tra trùng ghế | BusTripService, BookingService 
| **Dev 3** | Data Architect | Định nghĩa model, utils, repository đọc/ghi JSON | Package model, repository, utils 
| **Dev 4** | API & Integration | Viết Handler, kết nối trực tiếp các endpoint với tầng service | Package handler 
| **Dev 5** | Polymorphism & QA | Cai dat interface da hinh, test case, lam slide/doc | Interface Payment, Discount, Test 

## 2. Timeline theo tuan va moc merge code

### Tuan 1: Thiet lap nen tang du lieu va giao dien tinh
* **Moc 1.1 :** Dev 3 hoan thanh package `model`, `repository` va `utils`. Push code lam nen tang cho Dev 2 va Dev 4. Upload file JSON mau len repo.
* **Moc 1.2 :** Dev 1 hoan thanh khung giao dien tinh (UI) cho man hinh tim kiem va so do ghe bang mock data. Dev 5 hoan thanh dinh nghia cac lop con cho `PaymentMethod` va `DiscountPolicy`.

### Tuan 2: Hoan thien core logic Backend va API
* **Moc 2.1 :** Dev 2 hoan thanh logic xu ly tai cac service. Tich hop cac quy tac kiem tra dieu kien cua ghe va chuyen xe.
* **Moc 2.2 :** Dev 4 hoan thanh viet cac lop handler, mapping endpoint va bat cac exception tu service ném lên. Tien hanh gop code Backend (Merge Code tran backend hoan chinh). Ca nhom test doc lap backend bang Postman.

### Tuan 3: Tich hop he thong va kiem thu
* **Moc 3.1 :** Dev 1 phoi hop cung Dev 4 ket noi goi API thuc te tu Frontend, thay the mock data bang du lieu luu tru o file JSON thuc.
* **Moc 3.2 :** Dev 5 thuc hien test toan dien tren 3 kịch ban nghiep vu, hoan thien Class Diagram, lam Slide va Bao cao cuoi ky. Tong duyet toan bo he thong.