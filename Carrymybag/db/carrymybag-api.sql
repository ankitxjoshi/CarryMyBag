

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";



--
-- Database: `a8567578_carry`
--

-- --------------------------------------------------------

--
-- Table structure for table `citylist`
--

CREATE TABLE IF NOT EXISTS `citylist` (
  `City_Id` int(50) NOT NULL AUTO_INCREMENT,
  `City_Name` varchar(50) NOT NULL,
  PRIMARY KEY (`City_Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `citylist`
--

INSERT INTO `citylist` (`City_Id`, `City_Name`) VALUES
(1, 'Delhi'),
(2, 'Mumbai');

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
  `destaddr` varchar(50) NOT NULL,
  `userId` varchar(50) NOT NULL,
  `pickUp` date NOT NULL,
  `dropDown` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`qtySmall`, `qtyMed`, `qtyLarge`, `priceSmall`, `priceMed`, `priceLarge`, `pickaddr`, `destaddr`, `userId`, `pickUp`, `dropDown`) VALUES
(1, 2, 3, 1000.0000000000, 2000.0000000000, 3000.0000000000, 'abv', 'abv', 'a', '0000-00-00', '0000-00-00'),
(1, 2, 3, 1000.0000000000, 2000.0000000000, 3000.0000000000, 'ankit', 'joshi', 'a', '0000-00-00', '0000-00-00');

-- --------------------------------------------------------

--
-- Table structure for table `prices`
--

CREATE TABLE IF NOT EXISTS `prices` (
  `Price Id` int(11) NOT NULL AUTO_INCREMENT,
  `From` varchar(50) NOT NULL,
  `To` varchar(50) NOT NULL,
  `Bag Type` varchar(50) NOT NULL,
  `Delivery Type` varchar(50) NOT NULL,
  `Number of days for delivery` int(11) NOT NULL,
  `Price` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`Price Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `prices`
--

INSERT INTO `prices` (`Price Id`, `From`, `To`, `Bag Type`, `Delivery Type`, `Number of days for delivery`, `Price`) VALUES
(1, 'Delhi', 'Mumbai', 'Small', 'One day', 0, 0),
(2, 'Delhi', 'Mumbai', 'Small', 'Fast', 0, 0),
(3, 'Delhi', 'Mumbai', 'Small', 'Standard', 0, 0),
(4, 'Delhi', 'Mumbai', 'Medium', 'One day', 0, 0),
(5, 'Delhi', 'Mumbai', 'Medium', 'Fast', 0, 0),
(6, 'Delhi', 'Mumbai', 'Medium', 'Standard', 0, 0),
(7, 'Delhi', 'Mumbai', 'Large', 'One day', 0, 0),
(8, 'Delhi', 'Mumbai', 'Large', 'Fast', 0, 0),
(9, 'Delhi', 'Mumbai', 'Large', 'Standard', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(23) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `encrypted_password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_id` (`unique_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `unique_id`, `name`, `email`, `encrypted_password`, `salt`, `created_at`, `updated_at`) VALUES
(1, '5748243ded5450.52281475', 'A', 'a', 'YwF+qM9bN2j39IcgI3KlIiHGqE81NWQwNTMwZGI3', '55d0530db7', '2016-05-27 16:11:01', NULL),
(2, '574859fac0eed7.87260639', 'Admin', 'admin@gmail.com', '8rv9I+pReG60pVPIx7OMuc+pW6plZTgzMDc0MWYw', 'ee830741f0', '2016-05-27 20:00:18', NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
