-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2011 年 07 月 13 日 16:37
-- 服务器版本: 5.1.41
-- PHP 版本: 5.3.2-1ubuntu4.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `serlib`
--

-- --------------------------------------------------------

--
-- 表的结构 `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `lastlogondate` int(11) DEFAULT NULL,
  `lastlogonip` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`, `lastlogondate`, `lastlogonip`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `jar`
--

CREATE TABLE IF NOT EXISTS `jar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `size` int(11) NOT NULL,
  `project` varchar(45) NOT NULL,
  `project_id` int(11) NOT NULL,
  `buildtime` int(11) NOT NULL,
  `hash` varchar(32) NOT NULL,
  `addtime` int(11) NOT NULL,
  `deltime` int(11) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `jar`
--

INSERT INTO `jar` (`id`, `name`, `size`, `project`, `project_id`, `buildtime`, `hash`, `addtime`, `deltime`, `status`) VALUES
(1, '11', 11, '11', 11, 2222222, '11', 1310545897, NULL, 1);

-- --------------------------------------------------------

--
-- 表的结构 `license`
--

CREATE TABLE IF NOT EXISTS `license` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(80) NOT NULL COMMENT '	',
  `message` text NOT NULL,
  `url` text,
  `addtime` int(11) NOT NULL,
  `deltime` int(11) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `license`
--


-- --------------------------------------------------------

--
-- 表的结构 `project`
--

CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL,
  `home` text NOT NULL,
  `company` varchar(80) DEFAULT NULL,
  `detail` text,
  `license` varchar(80) DEFAULT NULL,
  `license_id` int(11) DEFAULT NULL,
  `addtime` int(11) NOT NULL,
  `deltime` int(11) DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `project`
--

