-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 10, 2016 at 09:23 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `carrymybag-api`
--

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `qtySmall` int(50) NOT NULL,
  `qtyMed` int(50) NOT NULL,
  `qtyLarge` int(50) NOT NULL,
  `priceSmall` double(255,10) NOT NULL,
  `priceMed` double(255,10) NOT NULL,
  `priceLarge` double(255,10) NOT NULL,
  `pickaddr` varchar(50) NOT NULL,
  `destaddr` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`qtySmall`, `qtyMed`, `qtyLarge`, `priceSmall`, `priceMed`, `priceLarge`, `pickaddr`, `destaddr`) VALUES
(1, 0, 0, 0.0000000000, 0.0000000000, 0.0000000000, '', ''),
(2, 2, 2, 0.0000000000, 0.0000000000, 0.0000000000, '', ''),
(0, 0, 0, 1000.0000000000, 2000.0000000000, 3000.0000000000, '', ''),
(1, 2, 3, 1000.0000000000, 2000.0000000000, 3000.0000000000, '', ''),
(1, 2, 3, 1000.0000000000, 2000.0000000000, 3000.0000000000, '', ''),
(1, 2, 3, 1000.0000000000, 2000.0000000000, 3000.0000000000, '', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
