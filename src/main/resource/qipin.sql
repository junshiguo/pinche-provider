-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2015-09-21 14:28:19
-- 服务器版本: 5.5.44-0ubuntu0.14.04.1
-- PHP 版本: 5.5.9-1ubuntu4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `qipin`
--

-- --------------------------------------------------------

--
-- 表的结构 `id_counter`
--

CREATE TABLE IF NOT EXISTS `id_counter` (
  `id_key` varchar(45) NOT NULL,
  `id_value` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `id_counter`
--

INSERT INTO `id_counter` (`id_key`, `id_value`) VALUES
('order_id_counter', 0),
('request_id_counter', 0);

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `phone_number` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `gender` tinyint(4) DEFAULT NULL COMMENT '0:male; 1:female; 2:unknown',
  `age` tinyint(3) unsigned DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `nationality` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `job` varchar(20) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`phone_number`, `password`, `gender`, `age`, `name`, `nationality`, `city`, `job`, `photo`) VALUES
('18801731111', '123', 0, 25, 'nike', NULL, NULL, 'er', NULL),
('18801732222', '123', 0, 2, 'nik', NULL, NULL, 'ed', NULL),
('18801735863', '123', 0, 4, 'namenew', NULL, NULL, 'asd', 'http://10.6.4.222:9000/target/resources/images/18801735863.JPG'),
('18801735864', '123', 1, 32, 'gfrh', NULL, NULL, NULL, 'http://10.171.5.28:9000/target/resources/images/default.png'),
('18801735865', '123', 1, 42, '3gqer', NULL, NULL, NULL, 'http://10.171.5.28:9000/target/resources/images/default.png'),
('18817361981', '123', 1, 20, '哈哈', NULL, NULL, '呵呵', 'http://10.6.4.222:9000/target/resources/images/18817361981.JPG'),
('18817361982', '123', 1, 22, 'ddgr', NULL, NULL, NULL, 'http://10.171.5.28:9000/target/resources/images/default.png'),
('18817361983', '123', 0, 12, 'jghd', NULL, NULL, NULL, 'http://10.171.5.28:9000/target/resources/images/default.png'),
('18817361985', '123', 1, 3, '呵呵', NULL, NULL, 'js', NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
