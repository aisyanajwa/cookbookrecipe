# CookBook – Recipe Management Web Application

CookBook adalah aplikasi web berbasis **Java Spring Boot** dan **React** yang digunakan untuk mengelola, mencari, menyimpan, dan membagikan resep masakan. Aplikasi ini dikembangkan sebagai **Tugas Besar Mata Kuliah Pemrograman Berorientasi Objek (PBO)** dengan menerapkan konsep **Object-Oriented Programming (OOP)** dan arsitektur **Model–View–Controller (MVC)**.

Aplikasi ini mengadopsi konsep **User-Generated Content**, di mana pengguna dapat mengunggah resep secara mandiri, sementara admin berperan dalam melakukan moderasi konten sebelum resep ditampilkan secara publik.

---

## Fitur Utama

### User
- Registrasi dan login
- Pencarian resep berdasarkan nama dan bahan (include & exclude)
- Melihat detail resep yang telah disetujui
- Memberikan like pada resep
- Menyimpan resep ke dalam kategori pribadi
- Mengunggah resep (status awal: pending)
- Menyusun meal plan mingguan (Senin–Minggu)
- Melihat daftar resep yang diunggah sendiri

### Admin
- Melihat resep berstatus pending
- Menyetujui (approve) atau menolak (reject) resep
- Menjaga kualitas dan validitas konten resep

---

## Arsitektur Sistem

Aplikasi CookBook menggunakan arsitektur **Client–Server** berbasis **RESTful API**:

- **Front-End (View)**  
  Dibangun menggunakan React + TypeScript untuk antarmuka pengguna.

- **Back-End (Controller & Model)**  
  Dibangun menggunakan Spring Boot (Java) untuk logika bisnis dan pengelolaan data.

- **Database**  
  Menggunakan MySQL dengan Spring Data JPA dan Hibernate (ORM).

---

## Konsep OOP yang Diterapkan

- **Inheritance**: `User` dan `Admin` merupakan turunan dari kelas abstrak `Account`
- **Abstraction**: Penggunaan abstract class `Account` dan interface `Searchable`
- **Polymorphism**: Controller menggunakan tipe `Account` dan `Searchable`
- **Encapsulation**: Atribut diakses melalui method (getter/setter)

---

## Teknologi yang Digunakan

### Back-End
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL

### Front-End
- React
- TypeScript
- Tailwind CSS
- Vite
- Fetch API

---

## Cara Menjalankan Aplikasi

### 1. Clone Repository

```bash
git clone https://github.com/aisyanajwa/cookbookrecipe.git
cd cookbookrecipe
```

---

### 2. Menjalankan Database (MySQL)

Pastikan MySQL sudah berjalan.

Buat database baru dengan nama:

```sql
CREATE DATABASE cookbook;
```

Import file database yang disediakan:

```bash
mysql -u root -p cookbook < cookbook.sql
```

**Catatan:**
- File `cookbook.sql` berisi struktur tabel dan data awal aplikasi.
- Beberapa data resep memiliki referensi gambar lokal yang tersimpan di komputer pengembang (folder `uploads`), sehingga gambar tersebut tidak akan otomatis muncul jika aplikasi dijalankan di perangkat lain.
- Gambar dapat diunggah ulang melalui fitur upload resep.

---

### 3. Menjalankan Back-End (Spring Boot)

Masuk ke folder backend:

```bash
cd cookbook/cookbook
```

Atur konfigurasi database pada file `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cookbook
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

Jalankan aplikasi:

```bash
./mvnw spring-boot:run
```

Back-End berjalan di:

```
http://localhost:8081
```

---

### 4. Menjalankan Front-End (React)

Masuk ke folder frontend:

```bash
cd Cookbookwebsiteuidesign
```

Install dependency:

```bash
npm install
```

Jalankan aplikasi:

```bash
npm run dev
```

Front-End berjalan di:

```
http://localhost:3000
```
## Screenshot Aplikasi
1. Register/Login
<img width="1919" height="1092" alt="Screenshot 2026-01-09 063052" src="https://github.com/user-attachments/assets/2ce2988e-4af8-4ca7-8091-6cc6ee6170bf" />
<img width="1919" height="1090" alt="Screenshot 2026-01-09 063107" src="https://github.com/user-attachments/assets/052fabf1-4bb6-4ba6-a71c-a664e8af3313" />


2. Tampilan User
<img width="1919" height="1094" alt="Screenshot 2026-01-09 063208" src="https://github.com/user-attachments/assets/b78be988-0383-41e0-9033-218ff0669aee" />
<img width="1919" height="1090" alt="Screenshot 2026-01-09 063244" src="https://github.com/user-attachments/assets/12b2f1ff-343a-4676-9b21-0ef4d9e4fcc2" />
<img width="1919" height="1089" alt="Screenshot 2026-01-09 063722" src="https://github.com/user-attachments/assets/b239da54-d7a2-4126-b670-eb3ae5255385" />
<img width="1919" height="1108" alt="Screenshot 2026-01-09 063636" src="https://github.com/user-attachments/assets/5dff3682-fc52-416e-b40a-740c74b06da4" />
<img width="1919" height="1091" alt="Screenshot 2026-01-09 063624" src="https://github.com/user-attachments/assets/907e2454-b8b0-4655-99fa-ba948624e994" />
<img width="1919" height="1092" alt="Screenshot 2026-01-09 064613" src="https://github.com/user-attachments/assets/0c15e0ff-001d-49da-91c5-f6aa77b2116c" />
<img width="1919" height="1089" alt="Screenshot 2026-01-09 064200" src="https://github.com/user-attachments/assets/64a9aeb1-a063-4302-bca5-7d90c6ea0f59" />
<img width="1919" height="1096" alt="Screenshot 2026-01-09 064141" src="https://github.com/user-attachments/assets/23c0b9d7-cf67-4ab9-bb3f-19045c773692" />
<img width="1919" height="1090" alt="Screenshot 2026-01-09 064102" src="https://github.com/user-attachments/assets/3c1ef091-d6fa-4129-8b93-7bfcab159904" />

3. Tampilan Admin
<img width="1919" height="1092" alt="Screenshot 2026-01-09 064613" src="https://github.com/user-attachments/assets/962f0eaf-3966-400a-a3dc-aad33bf48c26" />
