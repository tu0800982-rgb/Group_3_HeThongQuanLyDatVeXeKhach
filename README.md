# # HE THONG QUAN LY DAT VE XE KHACH (TUYEN HA NOI - HAI PHONG)

## 1. Gioi thieu de tai
Du an mo phong he thong dat ve va quan ly ve xe khach cho tuyen duong Ha Noi - Hai Phong. System ho tro khach hang tra cuu, chon ghe, dat ve, thanh toan va ho tro nhan vien nha xe thong ke so luong ve da ban trong ngay.

## 2. Kien truc tong quan he thong
He thong duoc xay dung theo mo hinh 3 tang tach biet, ket noi giua Frontend va Backend thong qua HTTP Request/Response (REST API).

┌──────────────────────────────────────────────┐
│                  FRONTEND                    │
│      (Giao dien nguoi dung: React/Angular)   │
└──────────────────────┬───────────────────────┘
│ HTTP Request / Response (JSON)
┌──────────────────────▼───────────────────────┐
│                  BACKEND                     │
│         (Logic nghiep vu: Java Spring Boot)  │
│  ├─ Handler (Tiep nhan & Dieu huong Request) │
│  ├─ Service (Xu ly Business Logic, Da hinh)  │
│  └─ Repository (Anh xa & Doc/Ghi file)       │
└──────────────────────┬───────────────────────┘
│ Local I/O (Gson Library)
┌──────────────────────▼───────────────────────┐
│               MOCK DATABASE                  │
│          (Luu tru du lieu: File JSON)        │
└──────────────────────────────────────────────┘

## 3. Cau truc thu muc Project

project-root/
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   ├── handler/      # Tiep nhan HTTP request
│   │       │   ├── model/        # Dinh nghia cac thuc the va interface
│   │       │   ├── repository/   # Thao tac doc/ghi file JSON
│   │       │   ├── service/      # Cac lop xu ly logic nghiep vu
│   │       │   ├── utils/        # Tien ich chung (JsonUtils, Generator)
│   │       │   └── main/         # Main class chay Spring Boot
│   │       └── resources/
│   └── data/                     # Thu muc chua Mock Database (.json)
├── frontend/                     # Ma nguon giao dien (React/Angular)
└── docs/                         # Tai lieu huong dan va phan cong

## 4. Huong dan cai dat va chay du an

### 4.1 Cai dat moi truong
* Java Development Kit (JDK) phien ban 17 tro len.
* Node.js phien ban LTS moi nhat.
* IDE: IntelliJ IDEA (cho Backend) va Visual Studio Code (cho Frontend).

### 4.2 Khoi chay Backend (Spring Boot)
1. Mo thu muc `backend/` bang IntelliJ IDEA.
2. Cho IDE tai xong cac dependency trong `pom.xml`.
3. Tao thu muc `data/` o thu muc goc cua backend neu chua co, them cac file `trips.json`, `tickets.json`, `users.json`, `bookings.json`.
4. Run class `src/main/java/main/Main.java`.
5. Backend se chay tai dia chi: `http://localhost:8080`.

### 4.3 Khoi chay Frontend
1. Mo thu muc `frontend/` bang Visual Studio Code.
2. Mo Terminal va chay lenh cai dat thu vien: `npm install`.
3. Chay ung dung o che do phat trien: `npm start`.
4. Giao dien se tu dong mo tai dia chi: `http://localhost:3000`.

### 4.4 Kiem tra API nhanh bang cURL
Dung Terminal hoac Postman de kiem tra API tim chuyen xe:
```bash
curl -X GET "http://localhost:8080/api/trips/search?from=HaNoi&to=HaiPhong"