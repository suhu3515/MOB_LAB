-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 10, 2020 at 03:32 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `moblab`
--

-- --------------------------------------------------------

--
-- Table structure for table `assigned_test`
--

CREATE TABLE `assigned_test` (
  `ass_id` int(11) NOT NULL,
  `testreq_id` int(11) DEFAULT NULL,
  `test_id` int(11) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `fdbk_id` int(11) NOT NULL,
  `tr_id` int(11) NOT NULL,
  `feedback` varchar(50) DEFAULT NULL,
  `rating` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL,
  `mobile` bigint(12) NOT NULL,
  `password` varchar(30) NOT NULL,
  `l_role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`login_id`, `mobile`, `password`, `l_role`) VALUES
(4, 9567105860, 'abcde', 'TESTER'),
(26, 9895989598, '12345', 'TESTER'),
(50, 9605567272, '123443', 'USER'),
(53, 9633058949, '123321', 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `observation`
--

CREATE TABLE `observation` (
  `obs_id` int(11) NOT NULL,
  `subtest_id` int(11) DEFAULT NULL,
  `observ_value` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

CREATE TABLE `results` (
  `res_id` int(11) NOT NULL,
  `asgn_test_id` int(11) NOT NULL,
  `observ_1` int(11) DEFAULT NULL,
  `observ_2` int(11) DEFAULT NULL,
  `observ_3` int(11) DEFAULT NULL,
  `observ_4` int(11) DEFAULT NULL,
  `observ_5` int(11) DEFAULT NULL,
  `observ_6` int(11) DEFAULT NULL,
  `observ_7` int(11) DEFAULT NULL,
  `observ_8` int(11) DEFAULT NULL,
  `observ_9` int(11) DEFAULT NULL,
  `observ_10` int(11) DEFAULT NULL,
  `remarks` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `subtests`
--

CREATE TABLE `subtests` (
  `sub_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `sub_name` varchar(35) NOT NULL,
  `ref_range` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `subtests`
--

INSERT INTO `subtests` (`sub_id`, `test_id`, `sub_name`, `ref_range`) VALUES
(8, 3, 'WBC Count', '100-120'),
(9, 3, 'HBC Count', '100-300');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `test_id` int(11) NOT NULL,
  `test_name` varchar(30) NOT NULL,
  `specimen` varchar(20) NOT NULL,
  `rate` int(11) NOT NULL,
  `ref_range` varchar(30) DEFAULT NULL,
  `est_time` varchar(20) NOT NULL,
  `instr` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`test_id`, `test_name`, `specimen`, `rate`, `ref_range`, `est_time`, `instr`) VALUES
(2, 'Heamoglobin - Hb', 'BLOOD', 100, '100-120', '1 Hour', 'jdskl vfsdznvop:AJBC;oASNDV;osDBvn;OSSnasjbdgsv\r\n\r\ndsf\r\ndsfd\r\nsf\r\nsd\r\nfsd\r\nf\r\ndsf\r\nsd\r\nf\r\nsd\r\nfsd\r\nf\r\nsdf\r\nds\r\nfds'),
(3, 'Total Count', 'BLOOD', 150, '', '1 Hour 15 Minutes', 'fghdsaiokxksd[pfvjkadsop'),
(4, 'Heamoglobin - Hg', 'BLOOD', 200, NULL, '30 mins', NULL),
(9, 'WBC COUNT', 'BLOOD', 200, '150-200', '1 Hour', ''),
(10, 'Sugar ', 'URINE', 200, '', '1 Hour 15 Minutes', '');

-- --------------------------------------------------------

--
-- Table structure for table `test_request`
--

CREATE TABLE `test_request` (
  `tr_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `tester_id` int(11) DEFAULT NULL,
  `pre_loc` text DEFAULT NULL,
  `doc_name` varchar(25) DEFAULT NULL,
  `user_test` text DEFAULT NULL,
  `tr_date` date NOT NULL,
  `pay_stat` int(11) NOT NULL DEFAULT 0,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(35) NOT NULL,
  `dob` date NOT NULL,
  `h_name` varchar(50) NOT NULL,
  `place` varchar(50) NOT NULL,
  `pin` int(7) NOT NULL,
  `mobile` bigint(12) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `location` text DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `dob`, `h_name`, `place`, `pin`, `mobile`, `email`, `location`, `status`) VALUES
(4, 'aslam k', '1998-07-17', 'kallingal house', 'edappal', 679590, 9567105860, 'aslamkedpl12@gmail.com', NULL, 1),
(26, 'ashwini m', '1995-01-10', 'Valathel house', 'mavoor', 679581, 9895989598, 'ashwiniach9020@gmail.com', NULL, 1),
(50, 'mujahid', '1998-12-01', 'karuthankattil', 'niramaruthur', 676109, 9605567272, 'mujahid@gmail.com', 'https://maps.google.com/maps?q=10.7401914,75.97461624', 1),
(53, 'Suhail Ak', '1998-10-28', 'valathel', 'maranchery', 679581, 9633058949, 'suhu3515@gmail.com', 'https://maps.google.com/maps?q=10.73999162,75.97491232', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assigned_test`
--
ALTER TABLE `assigned_test`
  ADD PRIMARY KEY (`ass_id`),
  ADD KEY `fk_tr_at` (`testreq_id`),
  ADD KEY `fk_tr_test` (`test_id`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`fdbk_id`),
  ADD KEY `fk_tr_fd` (`tr_id`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`login_id`);

--
-- Indexes for table `observation`
--
ALTER TABLE `observation`
  ADD PRIMARY KEY (`obs_id`),
  ADD KEY `fk_st_obs` (`subtest_id`);

--
-- Indexes for table `results`
--
ALTER TABLE `results`
  ADD PRIMARY KEY (`res_id`),
  ADD KEY `fk_asgn` (`asgn_test_id`),
  ADD KEY `fk_obs1` (`observ_1`),
  ADD KEY `fk_obs2` (`observ_2`),
  ADD KEY `fk_obs3` (`observ_3`),
  ADD KEY `fk_obs4` (`observ_4`),
  ADD KEY `fk_obs5` (`observ_5`),
  ADD KEY `fk_obs6` (`observ_6`),
  ADD KEY `fk_obs7` (`observ_7`),
  ADD KEY `fk_obs8` (`observ_8`),
  ADD KEY `fk_obs9` (`observ_9`),
  ADD KEY `fk_obs10` (`observ_10`);

--
-- Indexes for table `subtests`
--
ALTER TABLE `subtests`
  ADD PRIMARY KEY (`sub_id`),
  ADD KEY `fk_test` (`test_id`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`test_id`);

--
-- Indexes for table `test_request`
--
ALTER TABLE `test_request`
  ADD PRIMARY KEY (`tr_id`),
  ADD KEY `fk_user_tr` (`user_id`),
  ADD KEY `fk_tester_tr` (`tester_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assigned_test`
--
ALTER TABLE `assigned_test`
  MODIFY `ass_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `fdbk_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `login_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `observation`
--
ALTER TABLE `observation`
  MODIFY `obs_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `results`
--
ALTER TABLE `results`
  MODIFY `res_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `subtests`
--
ALTER TABLE `subtests`
  MODIFY `sub_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `test_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `test_request`
--
ALTER TABLE `test_request`
  MODIFY `tr_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `assigned_test`
--
ALTER TABLE `assigned_test`
  ADD CONSTRAINT `fk_tr_at` FOREIGN KEY (`testreq_id`) REFERENCES `test_request` (`tr_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_tr_test` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`) ON UPDATE CASCADE;

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `fk_tr_fd` FOREIGN KEY (`tr_id`) REFERENCES `test_request` (`tr_id`) ON UPDATE CASCADE;

--
-- Constraints for table `observation`
--
ALTER TABLE `observation`
  ADD CONSTRAINT `fk_st_obs` FOREIGN KEY (`subtest_id`) REFERENCES `subtests` (`sub_id`) ON UPDATE CASCADE;

--
-- Constraints for table `results`
--
ALTER TABLE `results`
  ADD CONSTRAINT `fk_asgn` FOREIGN KEY (`asgn_test_id`) REFERENCES `assigned_test` (`ass_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_obs1` FOREIGN KEY (`observ_1`) REFERENCES `observation` (`obs_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_obs10` FOREIGN KEY (`observ_10`) REFERENCES `observation` (`obs_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_obs2` FOREIGN KEY (`observ_2`) REFERENCES `observation` (`obs_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_obs3` FOREIGN KEY (`observ_3`) REFERENCES `observation` (`obs_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_obs4` FOREIGN KEY (`observ_4`) REFERENCES `observation` (`obs_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_obs5` FOREIGN KEY (`observ_5`) REFERENCES `observation` (`obs_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_obs6` FOREIGN KEY (`observ_6`) REFERENCES `observation` (`obs_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_obs7` FOREIGN KEY (`observ_7`) REFERENCES `observation` (`obs_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_obs8` FOREIGN KEY (`observ_8`) REFERENCES `observation` (`obs_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_obs9` FOREIGN KEY (`observ_9`) REFERENCES `observation` (`obs_id`) ON UPDATE CASCADE;

--
-- Constraints for table `subtests`
--
ALTER TABLE `subtests`
  ADD CONSTRAINT `fk_test` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`) ON UPDATE CASCADE;

--
-- Constraints for table `test_request`
--
ALTER TABLE `test_request`
  ADD CONSTRAINT `fk_tester_tr` FOREIGN KEY (`tester_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_tr` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
