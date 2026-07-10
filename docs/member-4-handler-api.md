# MO TA CONG VIEC: DEV 4 - API & HANDLER INTEGRATION

## 1. Vai tro va muc tieu
Xay dung tang tiep nhan HTTP Request cua he thong. Thiet lap cac API Endpoint chuan REST de Frontend trao doi du lieu va chiu trach nhiem bat exception de dich thanh ma loi HTTP hop le.

## 2. Checklist cong viec cu the
- [ ] Dinh nghia router va endpoint `GET /api/trips` va `GET /api/trips/search`.
- [ ] Dinh nghia endpoint `GET /api/trips/{tripId}/seats` để trả về sơ đồ ghế của chuyến xe.
- [ ] Dinh nghia endpoint `POST /api/bookings` nhan request dat ve tu Frontend.
- [ ] Dinh nghia endpoint `POST /api/payments/{ticketId}` nhan thong tin xu ly xac nhan thanh toan.
- [ ] Dinh nghia endpoint `GET /api/staff/reports` để cung cấp dữ liệu thống kê cho phân hệ nhân viên.
- [ ] Trien khai cau truc `try-catch` cho toàn bộ API Handler nhằm xử lý ngoại lệ và trả về phản hồi lỗi thống nhất.

## 3. Business rule va Quy tac phan hoi ma loi
* Neu Service nem ra loi khong ton tai chuyen/ghe -> Response ve HTTP status `404 Not Found`.
* Neu Service nem ra loi trung ghe, trung lich -> Response ve HTTP status `400 Bad Request`.
* Cau truc JSON tra ve khi co loi bat buoc phai dong nhat: `{ "error": "Noi dung thong bao loi tu service" }`.
* Khong cho phep thanh toan mot ma ve khong ton tai trong he thong.

## 4. Input, Output va Moi quan he phu thuoc
* **Input:** Payload JSON, Query parameters gui tu Frontend qua mang HTTP.
* **Output:** HTTP Response dinh dang JSON va Status Code tieu chuan.
* **Phu thuoc vao:** Dev 2 (Can lop Service de truyen du lieu xu ly nghiep vu).
* **Ai phu thuoc vao minh:** Dev 1 (Frontend can endpoint on dinh cua ban de ghep noi he thong).

## 5. Goi y cach tu kiem tra (Self-test)
* Sau khi code xong mot endpoint, su dung cURL hoac phan mem Postman ban request truc tiep vao dia chi API de xac minh tinh toan ven cua chuoi JSON ket qua, dam bao dung kieu key-value ma Frontend yeu cau.