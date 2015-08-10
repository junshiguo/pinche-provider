-- MySQL Script generated by MySQL Workbench
-- Sun Aug  9 19:26:48 2015
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema qipin
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema qipin
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `qipin` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `qipin` ;

-- -----------------------------------------------------
-- Table `qipin`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `qipin`.`user` ;

CREATE TABLE IF NOT EXISTS `qipin`.`user` (
  `phone_number` VARCHAR(20) NOT NULL COMMENT '',
  `password` VARCHAR(20) NOT NULL COMMENT '',
  `gender` TINYINT NULL COMMENT '',
  `age` TINYINT UNSIGNED NULL COMMENT '',
  `name` VARCHAR(20) NULL COMMENT '',
  `nationality` VARCHAR(20) NULL COMMENT '',
  `city` VARCHAR(20) NULL COMMENT '',
  `job` VARCHAR(20) NULL COMMENT '',
  `photo` BLOB NULL COMMENT '',
  PRIMARY KEY (`phone_number`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `qipin`.`rating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `qipin`.`rating` ;

CREATE TABLE IF NOT EXISTS `qipin`.`rating` (
  `user_id` VARCHAR(20) NOT NULL COMMENT 'user.phone_number',
  `order_id` VARCHAR(25) NOT NULL COMMENT '',
  `commentor_id` VARCHAR(20) NOT NULL COMMENT 'user.phone_number',
  `rating` TINYINT NOT NULL COMMENT '',
  `comment` TEXT NULL COMMENT '',
  `rating_time` DATETIME NULL COMMENT '',
  PRIMARY KEY (`user_id`, `order_id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `qipin`.`request`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `qipin`.`request` ;

CREATE TABLE IF NOT EXISTS `qipin`.`request` (
  `request_id` VARCHAR(25) NOT NULL COMMENT '',
  `user_id` VARCHAR(20) NOT NULL COMMENT 'user.phone_number',
  `user_gender` TINYINT NULL COMMENT '',
  `user_age` TINYINT NULL COMMENT '',
  `state` TINYINT NOT NULL COMMENT '0:new request\n1:been handled, not new\n2:handling, waiting for order confirm\n3:match success\n4:normal canceled\n5:canceled after success\n6:been canceled by the other\n7:second handing\n8: two rejection\n9: old request\n5 & 6 trigger returned deposit change',
  `source_X` DOUBLE NULL COMMENT '',
  `source_Y` DOUBLE NULL COMMENT '',
  `source_name` VARCHAR(255) NULL COMMENT '',
  `destination_X` DOUBLE NULL COMMENT '',
  `destination_Y` DOUBLE NULL COMMENT '',
  `destination_name` VARCHAR(255) NULL COMMENT '',
  `leaving_time` DATETIME NULL COMMENT '',
  `exp_gender` TINYINT NULL COMMENT '',
  `exp_age_min` TINYINT NULL COMMENT '',
  `exp_age_max` TINYINT NULL COMMENT '',
  `request_time` DATETIME NULL COMMENT '',
  `remain_chance` TINYINT NULL DEFAULT 2 COMMENT '',
  PRIMARY KEY (`request_id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `qipin`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `qipin`.`orders` ;

CREATE TABLE IF NOT EXISTS `qipin`.`orders` (
  `order_id` VARCHAR(25) NOT NULL COMMENT '',
  `request_id1` VARCHAR(25) NULL COMMENT '',
  `request_id2` VARCHAR(25) NULL COMMENT '',
  `user_id1` VARCHAR(20) NULL COMMENT '',
  `user_id2` VARCHAR(20) NULL COMMENT '',
  `confirmed_user1` TINYINT NULL DEFAULT 0 COMMENT '',
  `confirmed_user2` TINYINT NULL DEFAULT 0 COMMENT '',
  `order_time` DATETIME NULL COMMENT '',
  `save_percent` DOUBLE NULL COMMENT '',
  `route_point1` VARCHAR(50) NULL COMMENT '',
  `route_point2` VARCHAR(50) NULL COMMENT '',
  `route_point3` VARCHAR(50) NULL COMMENT '',
  `route_point4` VARCHAR(50) NULL COMMENT '',
  PRIMARY KEY (`order_id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `qipin`.`request_active`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `qipin`.`request_active` ;

CREATE TABLE IF NOT EXISTS `qipin`.`request_active` (
  `request_id` VARCHAR(25) NOT NULL COMMENT '',
  `user_id` VARCHAR(20) NOT NULL COMMENT 'user.phone_number',
  `user_gender` TINYINT NULL COMMENT '',
  `user_age` TINYINT NULL COMMENT '',
  `state` TINYINT NOT NULL COMMENT '0:new request\n1:been handled, not new\n2:handling, waiting for order confirm\n3:match success\n4:normal canceled\n5:canceled after success\n6:been canceled by the other\n7:second handing\n8: two rejection\n9: old request\n5 & 6 trigger returned deposit change',
  `source_X` DOUBLE NULL COMMENT '',
  `source_Y` DOUBLE NULL COMMENT '',
  `source_name` VARCHAR(255) NULL COMMENT '',
  `destination_X` DOUBLE NULL COMMENT '',
  `destination_Y` DOUBLE NULL COMMENT '',
  `destination_name` VARCHAR(255) NULL COMMENT '',
  `leaving_time` DATETIME NULL COMMENT '',
  `exp_gender` TINYINT NULL COMMENT '',
  `exp_age_min` TINYINT NULL COMMENT '',
  `exp_age_max` TINYINT NULL COMMENT '',
  `request_time` DATETIME NULL COMMENT '',
  `remain_chance` TINYINT NULL DEFAULT 2 COMMENT '',
  PRIMARY KEY (`request_id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `qipin`.`orders_active`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `qipin`.`orders_active` ;

CREATE TABLE IF NOT EXISTS `qipin`.`orders_active` (
  `order_id` VARCHAR(25) NOT NULL COMMENT '',
  `request_id1` VARCHAR(25) NULL COMMENT '',
  `request_id2` VARCHAR(25) NULL COMMENT '',
  `user_id1` VARCHAR(20) NULL COMMENT '',
  `user_id2` VARCHAR(20) NULL COMMENT '',
  `confirmed_user1` TINYINT NULL DEFAULT 0 COMMENT '',
  `confirmed_user2` TINYINT NULL DEFAULT 0 COMMENT '',
  `order_time` DATETIME NULL COMMENT '',
  `save_percent` DOUBLE NULL COMMENT '',
  `route_point1` VARCHAR(50) NULL COMMENT '',
  `route_point2` VARCHAR(50) NULL COMMENT '',
  `route_point3` VARCHAR(50) NULL COMMENT '',
  `route_point4` VARCHAR(50) NULL COMMENT '',
  PRIMARY KEY (`order_id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `qipin`.`id_counter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `qipin`.`id_counter` ;

CREATE TABLE IF NOT EXISTS `qipin`.`id_counter` (
  `id_key` VARCHAR(45) NOT NULL COMMENT '',
  `id_value` BIGINT NULL DEFAULT 0 COMMENT '',
  PRIMARY KEY (`id_key`)  COMMENT '')
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO id_counter VALUES ("order_id_counter", 0);
INSERT INTO id_counter VALUES ("request_id_counter", 0);
