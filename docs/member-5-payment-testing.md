# MO TA CONG VIEC: DEV 5 - POLYMORPHISM MASTER & QA

## 1. Vai tro va muc tieu
Hien thuc hoa tinh Da hinh (Polymorphism) va Truu tuong (Abstraction) thong qua cac he thong chinh sach thanh toan/chiet khau. Dong thoi dam nhiem vai tro dam bao chat luong (QA), lap kịch ban kiem thu va tong hop cac tai lieu do an.

## 2. Checklist cong viec cu the
- [ ] Khai bao interface `PaymentMethod` va viet cac lop con `CashPayment`, `BankTransferPayment`, `EWalletPayment`.
- [ ] Khai bao interface `DiscountPolicy` va viet cac lop con `NormalCustomerDiscount`, `MemberCustomerDiscount`, `VipCustomerDiscount`.
- [ ] Viet code logic tính giá vé cuối cùng tai day va cung cấp hàm cho Dev 2 gọi tại lớp Service.
- [ ] Thiet lap va thuc thi ma kiem thu (Unit Test hoac Script Test) bao phủ tối thiểu 3 tình huống nghiệp vụ bắt buộc.
- [ ] Vẽ sơ đồ Class Diagram bieu dien moi quan he giua cac thuc the va he thong da hinh.
- [ ] Chuan bi Slide thuyet trinh va viet file bao cao do an tong hop.

## 3. Business rule va cong thuc xu ly
* Cong thuc gia cuoi: `FinalPrice = (BasePrice + PhuPhiGheVip) * (1 - TyLeGiamGia)`. Trong do phu phi ghe VIP phai duoc cau hinh > 0, gia ve luon > 0.
* Khach VIP hoac Khach thanh vien phai duoc giam dung ty le phan tram quy dinh san cua nhom.
* Cap nhat dung trang thai hoa don: Thanh toan thanh cong chuyen thanh `PAID`, that bai giu nguyen `PENDING`.

## 4. Input, Output va Moi quan he phu thuoc
* **Input:** Thong tin gia goc, loai ghe, hang khach hang va hinh thuc thanh toan duoc chon.
* **Output:** Gia tien so thuc sau chiet khau va ket qua trang thai Boolean (true/false) cua giao dich.
* **Phu thuoc vao:** Dev 3 (Can phan lop Model de lam viec voi các thuoc tinh khach hang, ve xe).
* **Ai phu thuoc vao minh:** Dev 2 (Service can cac class tinh toan chiet khau cua ban de hoan thanh logic dat ve).

## 5. Goi y cach tu kiem tra (Self-test)
* Viet san cac test case bang ma nguon truyen doi tuong `Customer` co loai la `VIP` va chon `Seat` co loai `VIP` de xem gia ve cuoi cung tinh ra co khop voi phep tinh tay tren thuc te hay khong.