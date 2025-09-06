-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 23, 2025 at 04:52 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `attendance_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `att_log_tbl`
--

CREATE TABLE `att_log_tbl` (
  `att_log_id` int(11) NOT NULL,
  `ev_id` int(11) NOT NULL,
  `am_in` time DEFAULT NULL,
  `am_out` time DEFAULT NULL,
  `pm_in` time DEFAULT NULL,
  `pm_out` time DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `stud_sch_id` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `att_log_tbl`
--

INSERT INTO `att_log_tbl` (`att_log_id`, `ev_id`, `am_in`, `am_out`, `pm_in`, `pm_out`, `created_at`, `updated_at`, `stud_sch_id`) VALUES
(3, 2, '09:00:00', NULL, NULL, NULL, '2025-07-01 01:00:00', '2025-05-16 10:23:04', 'S019'),
(4, 2, '09:10:00', '12:00:00', NULL, NULL, '2025-07-01 01:10:00', '2025-07-01 04:00:00', 'S020'),
(5, 3, '13:00:00', '17:00:00', NULL, NULL, '2025-07-15 05:00:00', '2025-07-15 09:00:00', 'S021'),
(6, 3, '13:10:00', '17:00:00', NULL, NULL, '2025-07-15 05:10:00', '2025-07-15 09:00:00', 'S022'),
(7, 4, '08:00:00', NULL, NULL, NULL, '2025-08-05 00:00:00', '2025-05-16 10:23:04', 'S023'),
(8, 5, '10:00:00', '12:00:00', NULL, NULL, '2025-08-10 02:00:00', '2025-08-10 04:00:00', 'S024'),
(9, 6, NULL, NULL, '13:00:00', '16:00:00', '2025-08-15 05:00:00', '2025-08-15 08:00:00', 'S025'),
(10, 7, '09:00:00', '17:00:00', NULL, NULL, '2025-08-20 01:00:00', '2025-08-20 09:00:00', 'S026'),
(11, 7, '09:30:00', '16:45:00', NULL, NULL, '2025-08-20 01:30:00', '2025-08-20 08:45:00', 'S027'),
(12, 8, NULL, NULL, '13:15:00', '15:15:00', '2025-09-01 05:15:00', '2025-09-01 07:15:00', 'S028'),
(13, 8, NULL, NULL, '13:30:00', '15:30:00', '2025-09-01 05:30:00', '2025-09-01 07:30:00', 'S029'),
(14, 9, '08:00:00', '09:30:00', NULL, NULL, '2025-09-05 00:00:00', '2025-09-05 01:30:00', 'S030'),
(15, 10, '14:00:00', NULL, NULL, NULL, '2025-09-10 06:00:00', '2025-05-16 10:23:04', 'S031'),
(16, 3, '13:00:00', NULL, NULL, NULL, '2025-07-15 05:00:00', '2025-05-16 10:23:04', 'S032'),
(17, 6, '10:00:00', '12:30:00', NULL, NULL, '2025-08-15 02:00:00', '2025-08-15 04:30:00', 'S033'),
(18, 2, NULL, NULL, '14:00:00', '17:00:00', '2025-07-01 06:00:00', '2025-07-01 09:00:00', 'S034'),
(19, 4, '08:30:00', '09:30:00', NULL, NULL, '2025-08-05 00:30:00', '2025-08-05 01:30:00', 'S035'),
(20, 9, '09:00:00', '10:30:00', NULL, NULL, '2025-09-05 01:00:00', '2025-09-05 02:30:00', 'S036'),
(21, 9, '09:15:00', '10:45:00', NULL, NULL, '2025-09-05 01:15:00', '2025-09-05 02:45:00', 'S037'),
(22, 1, NULL, NULL, '14:00:00', '16:00:00', '2025-06-10 06:00:00', '2025-06-10 08:00:00', 'S038'),
(23, 5, NULL, NULL, '13:00:00', '15:00:00', '2025-08-10 05:00:00', '2025-08-10 07:00:00', 'S039'),
(24, 10, '15:00:00', NULL, NULL, NULL, '2025-09-10 07:00:00', '2025-05-16 10:23:04', 'S040'),
(25, 10, '15:10:00', '17:00:00', NULL, NULL, '2025-09-10 07:10:00', '2025-09-10 09:00:00', 'S041'),
(26, 8, NULL, NULL, '13:00:00', '14:30:00', '2025-09-01 05:00:00', '2025-09-01 06:30:00', 'S042'),
(27, 7, '08:45:00', '17:15:00', NULL, NULL, '2025-08-20 00:45:00', '2025-08-20 09:15:00', 'S043'),
(28, 6, '09:00:00', '12:00:00', NULL, NULL, '2025-08-15 01:00:00', '2025-08-15 04:00:00', 'S044'),
(29, 5, '08:00:00', NULL, NULL, NULL, '2025-08-10 00:00:00', '2025-05-16 10:23:04', 'S045'),
(30, 4, '08:00:00', '09:00:00', NULL, NULL, '2025-08-05 00:00:00', '2025-08-05 01:00:00', 'S046'),
(31, 4, '08:00:00', '09:00:00', NULL, NULL, '2025-08-05 00:00:00', '2025-08-05 01:00:00', 'S046'),
(32, 4, '08:10:00', NULL, NULL, NULL, '2025-08-05 00:10:00', '2025-05-16 10:26:19', 'S047'),
(33, 4, NULL, NULL, '13:00:00', '14:00:00', '2025-08-05 05:00:00', '2025-08-05 06:00:00', 'S048'),
(34, 5, '10:00:00', '12:00:00', NULL, NULL, '2025-08-10 02:00:00', '2025-08-10 04:00:00', 'S049'),
(35, 5, NULL, NULL, '13:00:00', '15:00:00', '2025-08-10 05:00:00', '2025-08-10 07:00:00', 'S050'),
(36, 6, '10:00:00', '12:00:00', NULL, NULL, '2025-08-15 02:00:00', '2025-08-15 04:00:00', 'S051'),
(37, 6, NULL, NULL, '13:00:00', '16:00:00', '2025-08-15 05:00:00', '2025-08-15 08:00:00', 'S052'),
(38, 6, '09:30:00', '12:30:00', NULL, NULL, '2025-08-15 01:30:00', '2025-08-15 04:30:00', 'S053'),
(39, 7, '09:00:00', '17:00:00', NULL, NULL, '2025-08-20 01:00:00', '2025-08-20 09:00:00', 'S054'),
(40, 7, NULL, NULL, '13:00:00', '17:00:00', '2025-08-20 05:00:00', '2025-08-20 09:00:00', 'S055'),
(41, 8, NULL, NULL, '13:30:00', '15:00:00', '2025-09-01 05:30:00', '2025-09-01 07:00:00', 'S056'),
(42, 8, '08:00:00', '10:00:00', NULL, NULL, '2025-09-01 00:00:00', '2025-09-01 02:00:00', 'S057'),
(43, 9, '08:00:00', '09:30:00', NULL, NULL, '2025-09-05 00:00:00', '2025-09-05 01:30:00', 'S058'),
(44, 9, '08:15:00', '09:45:00', NULL, NULL, '2025-09-05 00:15:00', '2025-09-05 01:45:00', 'S059'),
(45, 10, '14:00:00', '16:00:00', NULL, NULL, '2025-09-10 06:00:00', '2025-09-10 08:00:00', 'S060'),
(46, 10, NULL, NULL, '13:00:00', '15:00:00', '2025-09-10 05:00:00', '2025-09-10 07:00:00', 'S061'),
(47, 1, '08:00:00', '10:00:00', NULL, NULL, '2025-06-10 00:00:00', '2025-06-10 02:00:00', 'S062'),
(48, 2, '09:15:00', '12:00:00', NULL, NULL, '2025-07-01 01:15:00', '2025-07-01 04:00:00', 'S063'),
(49, 3, '13:00:00', '17:00:00', NULL, NULL, '2025-07-15 05:00:00', '2025-07-15 09:00:00', 'S064'),
(50, 4, '08:30:00', '09:30:00', NULL, NULL, '2025-08-05 00:30:00', '2025-08-05 01:30:00', 'S065'),
(51, 5, '10:15:00', '12:00:00', NULL, NULL, '2025-08-10 02:15:00', '2025-08-10 04:00:00', 'S066'),
(52, 6, NULL, NULL, '13:00:00', '15:30:00', '2025-08-15 05:00:00', '2025-08-15 07:30:00', 'S067'),
(53, 7, '09:00:00', '17:00:00', NULL, NULL, '2025-08-20 01:00:00', '2025-08-20 09:00:00', 'S068'),
(54, 8, NULL, NULL, '13:00:00', '15:00:00', '2025-09-01 05:00:00', '2025-09-01 07:00:00', 'S069'),
(55, 9, '08:00:00', '10:00:00', NULL, NULL, '2025-09-05 00:00:00', '2025-09-05 02:00:00', 'S070'),
(56, 10, NULL, NULL, '13:30:00', '15:30:00', '2025-09-10 05:30:00', '2025-09-10 07:30:00', 'S071'),
(57, 1, '08:00:00', '10:00:00', NULL, NULL, '2025-06-10 00:00:00', '2025-06-10 02:00:00', 'S072'),
(58, 1, '08:05:00', '10:05:00', NULL, NULL, '2025-06-10 00:05:00', '2025-06-10 02:05:00', 'S073'),
(59, 2, '09:10:00', '12:10:00', NULL, NULL, '2025-07-01 01:10:00', '2025-07-01 04:10:00', 'S074'),
(60, 3, '13:00:00', '17:00:00', NULL, NULL, '2025-07-15 05:00:00', '2025-07-15 09:00:00', 'S075'),
(124, 1, '08:01:00', '10:01:00', NULL, NULL, '2025-06-10 00:01:00', '2025-06-10 02:01:00', 'S039'),
(226, 2, NULL, NULL, '13:01:00', '16:01:00', '2025-07-01 05:01:00', '2025-07-01 08:01:00', 'S041'),
(227, 2, '09:00:00', '12:00:00', NULL, NULL, '2025-07-01 01:00:00', '2025-07-01 04:00:00', 'S042'),
(228, 3, '13:15:00', '17:00:00', NULL, NULL, '2025-07-15 05:15:00', '2025-07-15 09:00:00', 'S043'),
(229, 3, '13:10:00', NULL, NULL, NULL, '2025-07-15 05:10:00', '2025-05-16 10:26:19', 'S044'),
(230, 3, '13:00:00', '17:00:00', NULL, NULL, '2025-07-15 05:00:00', '2025-07-15 09:00:00', 'S045'),
(232, 1, '15:54:02', NULL, NULL, NULL, '2025-05-19 07:54:02', '2025-05-19 07:54:02', 'S017');

-- --------------------------------------------------------

--
-- Table structure for table `events_tbl`
--

CREATE TABLE `events_tbl` (
  `ev_id` int(11) NOT NULL,
  `ev_title` varchar(255) NOT NULL,
  `ev_desc` text DEFAULT NULL,
  `ev_loc` varchar(255) DEFAULT NULL,
  `ev_dur` varchar(100) DEFAULT NULL,
  `ev_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `events_tbl`
--

INSERT INTO `events_tbl` (`ev_id`, `ev_title`, `ev_desc`, `ev_loc`, `ev_dur`, `ev_date`, `created_at`, `updated_at`) VALUES
(1, 'Library Orientation', 'Intro to library services', 'Main Auditorium', '2 hours', '2025-06-10', '2025-05-09 15:17:28', '2025-05-10 07:12:22'),
(2, 'Book Week Launch', 'National Book Week Opening', 'Library Lobby', '3 hours', '2025-07-01', '2025-05-09 15:17:28', '2025-05-09 15:17:28'),
(3, 'Research Workshop', 'Training on research tools', 'Lab Room A', '4 hours', '2025-07-15', '2025-05-09 15:17:28', '2025-05-10 05:38:39'),
(4, 'Storytelling Hour', 'Story session for kids', 'Children\'s Section', '1 hour', '2025-08-05', '2025-05-10 01:00:00', '2025-05-16 10:22:36'),
(5, 'Digital Literacy Seminar', 'Basics of computer use', 'IT Lab', '2 hours', '2025-08-10', '2025-05-10 01:05:00', '2025-05-16 10:22:36'),
(6, 'Academic Writing', 'APA/MLA workshop', 'Lecture Room 2', '3 hours', '2025-08-15', '2025-05-10 01:10:00', '2025-05-16 10:22:36'),
(7, 'Book Donation Drive', 'Bring a book, share a book', 'Library Entrance', 'Full Day', '2025-08-20', '2025-05-10 01:15:00', '2025-05-16 10:22:36'),
(8, 'Library Quiz Bee', 'Quiz competition', 'Main Hall', '2 hours', '2025-09-01', '2025-05-10 01:20:00', '2025-05-16 10:22:36'),
(9, 'Library Tour', 'Guided tour of resources', 'Entire Library', '1.5 hours', '2025-09-05', '2025-05-10 01:25:00', '2025-05-16 10:22:36'),
(10, 'Thesis Coaching', 'Guidance for thesis writing', 'Study Room B', '2 hours', '2025-09-10', '2025-05-10 01:30:00', '2025-05-16 10:22:36');

-- --------------------------------------------------------

--
-- Table structure for table `student_tbl`
--

CREATE TABLE `student_tbl` (
  `stud_id` int(10) NOT NULL,
  `stud_sch_id` varchar(20) NOT NULL,
  `stud_fname` varchar(50) DEFAULT NULL,
  `stud_lname` varchar(50) DEFAULT NULL,
  `stud_ename` varchar(50) DEFAULT NULL,
  `stud_year_level` varchar(10) DEFAULT NULL,
  `stud_course` varchar(100) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_tbl`
--

INSERT INTO `student_tbl` (`stud_id`, `stud_sch_id`, `stud_fname`, `stud_lname`, `stud_ename`, `stud_year_level`, `stud_course`, `created_at`, `updated_at`) VALUES
(34, 'S017', 'Victor', 'Morales', 'sd', '1', 'BSBA', '2025-05-09 22:25:48', NULL),
(35, 'S018', 'Anna', 'Reyes', '', '2', 'BSIT', '2025-05-09 22:30:00', NULL),
(36, 'S019', 'Carlos', 'Lopez', 'jr', '3', 'BSCS', '2025-05-09 22:32:00', NULL),
(37, 'S020', 'Nina', 'Salazar', '', '4', 'BSBA', '2025-05-09 22:25:48', NULL),
(38, 'S021', 'Leo', 'Francisco', '', '1', 'BSIT', '2025-05-09 22:34:50', NULL),
(39, 'S022', 'Maria', 'Santos', 'lys', '2', 'BSBA', '2025-05-10 09:00:00', NULL),
(40, 'S023', 'James', 'Cruz', '', '3', 'BSIT', '2025-05-10 09:05:00', NULL),
(41, 'S024', 'Isabella', 'Dela Cruz', 'mae', '4', 'BSCS', '2025-05-10 09:10:00', NULL),
(42, 'S025', 'Marco', 'Rivera', '', '1', 'BSBA', '2025-05-10 09:15:00', NULL),
(43, 'S026', 'Sophia', 'Torres', '', '2', 'BSIT', '2025-05-10 09:20:00', NULL),
(44, 'S027', 'Daniel', 'Gonzales', 'jr', '3', 'BSCS', '2025-05-10 09:25:00', NULL),
(45, 'S028', 'Camille', 'Garcia', '', '4', 'BSBA', '2025-05-10 09:30:00', NULL),
(46, 'S029', 'Miguel', 'Flores', '', '1', 'BSIT', '2025-05-10 09:35:00', NULL),
(47, 'S030', 'Julia', 'Mendoza', 'rose', '2', 'BSCS', '2025-05-10 09:40:00', NULL),
(48, 'S031', 'Andrei', 'Domingo', '', '3', 'BSIT', '2025-05-10 09:45:00', NULL),
(49, 'S032', 'Angelica', 'Villanueva', '', '4', 'BSBA', '2025-05-10 09:50:00', NULL),
(50, 'S033', 'Joshua', 'Navarro', 'm', '1', 'BSIT', '2025-05-10 09:55:00', NULL),
(51, 'S034', 'Elaine', 'Gutierrez', '', '2', 'BSCS', '2025-05-10 10:00:00', NULL),
(52, 'S035', 'Mark', 'Ramos', '', '3', 'BSBA', '2025-05-10 10:05:00', NULL),
(53, 'S036', 'Kristine', 'Lim', '', '4', 'BSIT', '2025-05-10 10:10:00', NULL),
(54, 'S037', 'John', 'Tan', 'paul', '1', 'BSCS', '2025-05-10 10:15:00', NULL),
(55, 'S038', 'Bianca', 'Yap', '', '2', 'BSIT', '2025-05-10 10:20:00', NULL),
(56, 'S039', 'Kevin', 'Chua', '', '3', 'BSCS', '2025-05-10 10:25:00', NULL),
(57, 'S040', 'Trisha', 'Sy', '', '4', 'BSBA', '2025-05-10 10:30:00', NULL),
(58, 'S041', 'Alvin', 'Lim', '', '1', 'BSIT', '2025-05-10 10:35:00', NULL),
(59, 'S042', 'Monica', 'Aguilar', '', '2', 'BSCS', '2025-05-10 10:40:00', NULL),
(60, 'S043', 'Enzo', 'Bautista', '', '3', 'BSIT', '2025-05-10 10:45:00', NULL),
(61, 'S044', 'Paula', 'Carreon', 'lee', '4', 'BSBA', '2025-05-10 10:50:00', NULL),
(62, 'S045', 'Vincent', 'Perez', '', '1', 'BSCS', '2025-05-10 10:55:00', NULL),
(63, 'S046', 'Rhea', 'Delos Santos', '', '2', 'BSIT', '2025-05-10 11:00:00', NULL),
(64, '0006', 'fg', 'fg', 'fgd', '1', 'BSIS', '2025-05-19 15:57:37', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_tbl`
--

CREATE TABLE `user_tbl` (
  `user_id` int(10) NOT NULL,
  `user_fname` varchar(50) DEFAULT NULL,
  `user_lname` varchar(50) DEFAULT NULL,
  `user_ename` varchar(50) DEFAULT NULL,
  `user_sex` varchar(10) DEFAULT NULL,
  `user_dob` date DEFAULT NULL,
  `user_username` varchar(50) DEFAULT NULL,
  `user_password` varchar(100) DEFAULT NULL,
  `user_access_level` varchar(20) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_tbl`
--

INSERT INTO `user_tbl` (`user_id`, `user_fname`, `user_lname`, `user_ename`, `user_sex`, `user_dob`, `user_username`, `user_password`, `user_access_level`, `created_at`, `updated_at`) VALUES
(1, 'john russell', 'dalina', '', 'M', '2003-10-27', 'russell', 'russell', 'admin', '0000-00-00 00:00:00', NULL),
(3, 'edrgtf', 'dfg', 'dfg', 'dfg', '2025-05-11', 'ad', 'ad', 'admin', '2025-05-10 16:05:45', NULL),
(4, 'dfddf', 'dd', 'df', 'Male', '2025-05-04', 'off', 'off', 'officer', '2025-05-15 12:42:58', NULL),
(5, 'Mia', 'Lopez', '', 'F', '2004-02-20', 'mia', 'mia123', 'officer', '2025-05-09 10:20:00', NULL),
(6, 'Paolo', 'Torres', '', 'M', '2000-06-15', 'paolo', '123paolo', 'admin', '2025-05-09 10:25:00', NULL),
(7, 'Sophia', 'Garcia', '', 'F', '2003-03-30', 'sophia', 'sopass', 'officer', '2025-05-09 10:30:00', NULL),
(8, 'Marco', 'Chua', '', 'M', '2002-07-07', 'marco', 'marco@1', 'admin', '2025-05-09 10:35:00', NULL),
(9, 'Julia', 'Lim', '', 'F', '2001-04-04', 'julias', 'julie', 'officer', '2025-05-09 10:40:00', NULL),
(10, 'David', 'Tan', '', 'M', '2003-01-01', 'david', 'dtan', 'admin', '2025-05-09 10:45:00', NULL),
(11, 'Angela', 'Evangelista', '', 'F', '2002-09-18', 'angela', 'pass123', 'officer', '2025-05-09 10:05:00', NULL),
(14, 'Lance', 'Reyes', '', 'M', '2001-12-05', 'lance', 'lancepass', 'admin', '2025-05-09 10:15:00', NULL),
(23, 'Conrad', 'Delos Reyes', '', 'M', '2002-11-12', 'conrad', 'pass456', 'officer', '2025-05-09 10:10:00', NULL),
(28, 'Victor', 'Morales', 'sd', NULL, NULL, 'Victor', 'Morales', 'officer', '2025-05-19 15:58:09', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `att_log_tbl`
--
ALTER TABLE `att_log_tbl`
  ADD PRIMARY KEY (`att_log_id`);

--
-- Indexes for table `events_tbl`
--
ALTER TABLE `events_tbl`
  ADD PRIMARY KEY (`ev_id`);

--
-- Indexes for table `student_tbl`
--
ALTER TABLE `student_tbl`
  ADD PRIMARY KEY (`stud_id`),
  ADD UNIQUE KEY `stud_sch_id` (`stud_sch_id`);

--
-- Indexes for table `user_tbl`
--
ALTER TABLE `user_tbl`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_username` (`user_username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `att_log_tbl`
--
ALTER TABLE `att_log_tbl`
  MODIFY `att_log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=234;

--
-- AUTO_INCREMENT for table `events_tbl`
--
ALTER TABLE `events_tbl`
  MODIFY `ev_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `student_tbl`
--
ALTER TABLE `student_tbl`
  MODIFY `stud_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- AUTO_INCREMENT for table `user_tbl`
--
ALTER TABLE `user_tbl`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
