-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2026 at 08:57 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cookbook`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `account_id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`account_id`, `email`, `password`, `username`, `role`) VALUES
(1, 'najwa@email.com', 'password123', 'najwa', 'USER'),
(2, 'admin@email.com', 'admin123', 'admin', 'ADMIN'),
(3, 'najwa2484@gmail.com', 'asdfgh', 'aisyanajwa', 'USER'),
(4, 'zua2484@gmail.com', 'asdfgh', 'aisyanajwaa', 'USER'),
(5, 'inaya@gmail.com', '123456', 'inaya', 'USER'),
(6, 'najwa@gmail.com', 'asdfgh', 'najwaaisya', 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `account_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`account_id`) VALUES
(2);

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE `likes` (
  `recipe_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `likes`
--

INSERT INTO `likes` (`recipe_id`, `user_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(5, 5),
(1, 3),
(2, 3),
(1, 6),
(2, 6),
(6, 6);

-- --------------------------------------------------------

--
-- Table structure for table `meal`
--

CREATE TABLE `meal` (
  `id` bigint(20) NOT NULL,
  `meal_plan_mealplan_id` bigint(20) DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `menu` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `meal_plans`
--

CREATE TABLE `meal_plans` (
  `mealplan_id` bigint(20) NOT NULL,
  `recipe_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `day` varchar(255) NOT NULL,
  `menu_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `meal_plans`
--

INSERT INTO `meal_plans` (`mealplan_id`, `recipe_id`, `user_id`, `day`, `menu_name`) VALUES
(1, 1, 3, 'Senin', 'Nasi Goreng Spesial'),
(3, 5, 3, 'Selasa', 'Soto Ayam'),
(4, 2, 1, 'Senin', 'Rendang Daging'),
(5, 1, 1, 'Senin', 'Nasi Goreng Spesial'),
(6, 5, 1, 'Selasa', 'Soto Ayam'),
(7, NULL, 3, 'Senin', 'donat'),
(8, 5, 3, 'Senin', 'Soto Ayam'),
(13, NULL, 6, 'Senin', 'cumi goreng'),
(14, 1, 6, 'Selasa', 'Nasi Goreng Spesial'),
(15, 2, 6, 'Senin', 'Rendang Daging');

-- --------------------------------------------------------

--
-- Table structure for table `recipes`
--

CREATE TABLE `recipes` (
  `like_count` int(11) NOT NULL,
  `servings` int(11) DEFAULT NULL,
  `recipe_id` bigint(20) NOT NULL,
  `uploaded_by` bigint(20) DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `cook_time` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` enum('APPROVED','PENDING','REJECTED') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `recipes`
--

INSERT INTO `recipes` (`like_count`, `servings`, `recipe_id`, `uploaded_by`, `image_url`, `description`, `cook_time`, `name`, `status`) VALUES
(3, 2, 1, 1, '/uploads/nasi_goreng.png', 'Nasi goreng khas Indonesia dengan bumbu spesial yang gurih dan lezat.', '30 Menit', 'Nasi Goreng Spesial', 'APPROVED'),
(3, 6, 2, 1, '/uploads/rendang.png', 'Rendang daging sapi Padang yang empuk dan kaya rempah.', '3 Jam', 'Rendang Daging', 'APPROVED'),
(1, 4, 3, 1, '/uploads/sate_ayam.png', 'Sate ayam khas Madura dengan bumbu kacang yang gurih.', '45 Menit', 'Sate Ayam Madura', 'APPROVED'),
(0, 2, 4, 1, '/uploads/gado_gado.png', 'Gado-gado Jakarta dengan bumbu kacang yang creamy.', '30 Menit', 'Gado-Gado', 'APPROVED'),
(1, 4, 5, 1, '/uploads/soto_ayam.png', 'Soto ayam kuning dengan kuah gurih dan hangat.', '1 Jam', 'Soto Ayam', 'APPROVED'),
(1, 2, 6, 3, '/uploads/9faa9381-495e-4718-bde8-b0503929b7bb.jpeg', 'Cuma bumbuin, tepungin, goreng, langsung jadi udang goreng simple bisa buat bekal.', '30 Menit', 'Udang Goreng Tepung', 'APPROVED'),
(0, 3, 8, 3, 'http://localhost:8081/uploads/0da06dbc-fb55-4efe-b7f7-96a7290a6a5e.png', 'Class Diagram PBO', 'Hari Jumat', 'PBO', 'REJECTED'),
(0, 3, 9, 3, '/uploads/da54911b-1dfa-4332-8859-ea82d0213108.png', 'asd', 'Hari Jumat', 'dwjiasd', 'REJECTED'),
(0, 2, 10, 3, '/uploads/6882d03b-4486-4c25-921b-7136cdb14ae7.png', 'asd', '15 Menit', 'asd', 'PENDING'),
(0, 5, 11, 3, 'http://localhost:8081/uploads/36cf0ebe-624a-49bc-a65d-d7654594463c.jpeg', 'asdasaad', 'dwa', 'blelawaw', 'PENDING'),
(0, 3, 12, 6, 'http://localhost:8081/uploads/a9424aed-ea6d-45c1-b7bb-13b8aaa570d7.jpeg', 'udang', '15 Menit', 'Udang Goreng', 'APPROVED');

-- --------------------------------------------------------

--
-- Table structure for table `recipe_ingredients`
--

CREATE TABLE `recipe_ingredients` (
  `recipe_id` bigint(20) NOT NULL,
  `ingredient_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `recipe_ingredients`
--

INSERT INTO `recipe_ingredients` (`recipe_id`, `ingredient_name`) VALUES
(1, 'Nasi putih 2 piring'),
(1, 'Telur 2 butir'),
(1, 'Kecap manis 2 sdm'),
(1, 'Bawang merah 3 siung'),
(1, 'Bawang putih 2 siung'),
(1, 'Cabai rawit 5 buah'),
(2, 'Daging sapi 500g'),
(2, 'Santan kental 400ml'),
(2, 'Lengkuas 3cm'),
(2, 'Serai 2 batang'),
(2, 'Daun jeruk 5 lembar'),
(2, 'Cabai merah 10 buah'),
(3, 'Daging ayam 300g'),
(3, 'Kacang tanah goreng 100g'),
(3, 'Kecap manis 3 sdm'),
(3, 'Bawang merah 5 siung'),
(3, 'Cabai rawit 5 buah'),
(3, 'Jeruk nipis 1 buah'),
(4, 'Tahu goreng 100g'),
(4, 'Tempe goreng 100g'),
(4, 'Kentang rebus 2 buah'),
(4, 'Kacang panjang rebus 50g'),
(4, 'Tauge rebus 50g'),
(4, 'Telur rebus 2 butir'),
(5, 'Ayam 500g'),
(5, 'Sohun 100g'),
(5, 'Tauge 100g'),
(5, 'Kunyit 3cm'),
(5, 'Daun seledri'),
(5, 'Telur rebus 2 butir'),
(6, 'ad'),
(8, '676767'),
(9, '676767'),
(10, 'ad'),
(11, 'asd'),
(12, 'udang 100 gr'),
(12, 'tepung');

-- --------------------------------------------------------

--
-- Table structure for table `recipe_instructions`
--

CREATE TABLE `recipe_instructions` (
  `recipe_id` bigint(20) NOT NULL,
  `instruction` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `recipe_instructions`
--

INSERT INTO `recipe_instructions` (`recipe_id`, `instruction`) VALUES
(1, '1. Tumis bawang merah dan bawang putih hingga harum.'),
(1, '2. Masukkan telur, orak-arik.'),
(1, '3. Tambahkan nasi, aduk rata.'),
(1, '4. Tuang kecap manis, aduk hingga merata.'),
(1, '5. Sajikan dengan pelengkap.'),
(2, '1. Haluskan bumbu: cabai, bawang merah, bawang putih, lengkuas.'),
(2, '2. Tumis bumbu halus hingga harum.'),
(2, '3. Masukkan daging, aduk rata.'),
(2, '4. Tuang santan, masak dengan api kecil.'),
(2, '5. Aduk sesekali hingga santan kering dan berminyak.'),
(3, '1. Potong ayam dadu, tusuk dengan tusukan sate.'),
(3, '2. Bakar sate sambil dioles bumbu kacang.'),
(3, '3. Buat bumbu kacang: haluskan kacang, cabai, bawang.'),
(3, '4. Tambahkan kecap dan air jeruk nipis.'),
(3, '5. Sajikan sate dengan bumbu kacang dan lontong.'),
(4, '1. Rebus sayuran: kacang panjang, tauge, kentang.'),
(4, '2. Goreng tahu dan tempe.'),
(4, '3. Haluskan bumbu kacang.'),
(4, '4. Tata semua bahan di piring.'),
(4, '5. Siram dengan bumbu kacang, tambahkan kerupuk.'),
(5, '1. Rebus ayam dengan bumbu kuning hingga empuk.'),
(5, '2. Angkat ayam, suwir-suwir dagingnya.'),
(5, '3. Siapkan sohun yang sudah direndam.'),
(5, '4. Tata sohun, tauge, suwiran ayam di mangkuk.'),
(5, '5. Siram dengan kuah panas, taburi bawang goreng.'),
(6, 'qwdsaD'),
(6, 'sadas'),
(6, 'MAMNS'),
(8, '67676767'),
(8, 'najwa'),
(9, 'sadad'),
(10, 'dfsa'),
(11, 'cvx'),
(11, 'asdfdsg'),
(12, 'marinasi udang'),
(12, 'adsasd'),
(12, 'psadkasdk');

-- --------------------------------------------------------

--
-- Table structure for table `saved_categories`
--

CREATE TABLE `saved_categories` (
  `category_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `category_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `saved_categories`
--

INSERT INTO `saved_categories` (`category_id`, `user_id`, `category_name`) VALUES
(1, 1, 'Favorit'),
(2, 3, 'Koleksi'),
(3, 3, 'Favorit'),
(4, 6, 'Koleksi'),
(5, 6, 'masak nanti');

-- --------------------------------------------------------

--
-- Table structure for table `saved_recipes`
--

CREATE TABLE `saved_recipes` (
  `category_id` bigint(20) NOT NULL,
  `recipe_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `saved_recipes`
--

INSERT INTO `saved_recipes` (`category_id`, `recipe_id`) VALUES
(1, 2),
(1, 4),
(3, 5),
(3, 2),
(2, 2),
(4, 1),
(5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `account_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`account_id`) VALUES
(1),
(3),
(4),
(5),
(6);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`account_id`);

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`account_id`);

--
-- Indexes for table `likes`
--
ALTER TABLE `likes`
  ADD KEY `FKojbxucudgxwwp3i9idbdtxkk2` (`recipe_id`),
  ADD KEY `FKnvx9seeqqyy71bij291pwiwrg` (`user_id`);

--
-- Indexes for table `meal`
--
ALTER TABLE `meal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8vtk23guscgfd0d6dxucekfxy` (`meal_plan_mealplan_id`);

--
-- Indexes for table `meal_plans`
--
ALTER TABLE `meal_plans`
  ADD PRIMARY KEY (`mealplan_id`),
  ADD KEY `FK4ujykkwkhu3jx36605vvgvvy7` (`recipe_id`),
  ADD KEY `FK7friea1stnx97lswpyxlb9ln1` (`user_id`);

--
-- Indexes for table `recipes`
--
ALTER TABLE `recipes`
  ADD PRIMARY KEY (`recipe_id`),
  ADD KEY `FKb3jwd9fq206sjkvhdfm6my3va` (`uploaded_by`);

--
-- Indexes for table `recipe_ingredients`
--
ALTER TABLE `recipe_ingredients`
  ADD KEY `FKcqlw8sor5ut10xsuj3jnttkc` (`recipe_id`);

--
-- Indexes for table `recipe_instructions`
--
ALTER TABLE `recipe_instructions`
  ADD KEY `FK7v3emx3mfvngvbwd10x0hx9vg` (`recipe_id`);

--
-- Indexes for table `saved_categories`
--
ALTER TABLE `saved_categories`
  ADD PRIMARY KEY (`category_id`),
  ADD KEY `FKhcfwjumbyxhtjecdmtbn9k1jm` (`user_id`);

--
-- Indexes for table `saved_recipes`
--
ALTER TABLE `saved_recipes`
  ADD KEY `FKaxpryrgf3btjlv2o231c688lm` (`recipe_id`),
  ADD KEY `FKs4fgidfqhi93cbp08th83oy3l` (`category_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`account_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `account_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `meal`
--
ALTER TABLE `meal`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `meal_plans`
--
ALTER TABLE `meal_plans`
  MODIFY `mealplan_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `recipes`
--
ALTER TABLE `recipes`
  MODIFY `recipe_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `saved_categories`
--
ALTER TABLE `saved_categories`
  MODIFY `category_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admins`
--
ALTER TABLE `admins`
  ADD CONSTRAINT `FKji7l5mi8tqv73veb5cd90hupx` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`);

--
-- Constraints for table `likes`
--
ALTER TABLE `likes`
  ADD CONSTRAINT `FKnvx9seeqqyy71bij291pwiwrg` FOREIGN KEY (`user_id`) REFERENCES `users` (`account_id`),
  ADD CONSTRAINT `FKojbxucudgxwwp3i9idbdtxkk2` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`);

--
-- Constraints for table `meal`
--
ALTER TABLE `meal`
  ADD CONSTRAINT `FK8vtk23guscgfd0d6dxucekfxy` FOREIGN KEY (`meal_plan_mealplan_id`) REFERENCES `meal_plans` (`mealplan_id`);

--
-- Constraints for table `meal_plans`
--
ALTER TABLE `meal_plans`
  ADD CONSTRAINT `FK4ujykkwkhu3jx36605vvgvvy7` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`),
  ADD CONSTRAINT `FK7friea1stnx97lswpyxlb9ln1` FOREIGN KEY (`user_id`) REFERENCES `users` (`account_id`);

--
-- Constraints for table `recipes`
--
ALTER TABLE `recipes`
  ADD CONSTRAINT `FKb3jwd9fq206sjkvhdfm6my3va` FOREIGN KEY (`uploaded_by`) REFERENCES `accounts` (`account_id`);

--
-- Constraints for table `recipe_ingredients`
--
ALTER TABLE `recipe_ingredients`
  ADD CONSTRAINT `FKcqlw8sor5ut10xsuj3jnttkc` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`);

--
-- Constraints for table `recipe_instructions`
--
ALTER TABLE `recipe_instructions`
  ADD CONSTRAINT `FK7v3emx3mfvngvbwd10x0hx9vg` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`);

--
-- Constraints for table `saved_categories`
--
ALTER TABLE `saved_categories`
  ADD CONSTRAINT `FKhcfwjumbyxhtjecdmtbn9k1jm` FOREIGN KEY (`user_id`) REFERENCES `users` (`account_id`);

--
-- Constraints for table `saved_recipes`
--
ALTER TABLE `saved_recipes`
  ADD CONSTRAINT `FKaxpryrgf3btjlv2o231c688lm` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`recipe_id`),
  ADD CONSTRAINT `FKs4fgidfqhi93cbp08th83oy3l` FOREIGN KEY (`category_id`) REFERENCES `saved_categories` (`category_id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `FKfm8rm8ks0kgj4fhlmmljkj17x` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
