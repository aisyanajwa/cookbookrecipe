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

