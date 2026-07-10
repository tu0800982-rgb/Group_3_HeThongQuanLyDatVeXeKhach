# MO TA CONG VIEC: DEV 3 - DATA ARCHITECT (MODEL & REPOSITORY)

## 1. Vai tro va muc tieu
Xay dung nen tang du lieu cho toan bo he thong. Dinh nghia cac class thuc the va hien thuc hoa co che doc/ghi du lieu xuong cac file JSON dong vai tro nhu he quan tri database.

## 2. Checklist cong viec cu the
- [ ] Khai bao abstract class `User` va cac lop con `Customer`, `Staff` theo đúng tính kế thừa.
- [ ] Khai bao lop `BusTrip`, `Seat`, `Ticket`, `Booking` áp dụng tính đóng gói (private thuộc tính, getter/setter).
- [ ] Xây dựng tiện ích `JsonUtils` sử dụng thư viện Gson để đọc/ghi dữ liệu giữa file JSON sang List đối tượng Java va nguoc lai.
- [ ] Tao tien ich `IdGenerator` de tu dong sinh cac chuoi ID duy nhat cho ve xe va phieu dat.
- [ ] Viet cac lop Repository: `BusTripRepository`, `TicketRepository`, `UserRepository`, `BookingRepository` chua cac ham nhu `findAll()`, `findById()`, `save()`.
- [ ] Thiet lap thu muc `data/` chua san cac file `.json` co it nhat 3 chuyen xe va thong tin ghe de lam database mock.

## 3. Business rule lien quan
* Tat ca cac thuoc tinh cua thuc the phai de pham vi truy cap la `private`.
* Du lieu ghi xuong file JSON luon phai dam bao giu toan ven cau truc mang `[]`.

## 4. Input, Output va Moi quan he phu thuoc
* **Input:** Request doc tu tang tren hoac doi tuong Java can luu xuong.
* **Output:** Chuoi JSON luu trong file vat ly hoac doi tuong Java duoc anh xa len.
* **Phu thuoc vao:** Khong phu thuoc vao ai. Day la phan viec tien quyet can hoan thanh dau tien.
* **Ai phu thuoc vao minh:** Ca doi Backend (Dev 2, Dev 4, Dev 5) deu phai import san pham cua ban de code.

## 5. Goi y cach tu kiem tra (Self-test)
* Viet mot code block chay thu o ham Main goi `BusTripRepository.findAll()`, in ket qua ra terminal xem he thong co load dung danh sach cac ghe o file JSON len hay khong. Thu save mot thuc the moi va mo file JSON ra kiem tra xem du lieu co ghi de thanh cong khong.