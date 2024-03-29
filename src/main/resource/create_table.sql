-- MySQL Script generated by MySQL Workbench
-- Mon Sep 21 14:29:02 2015
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
  `gender` TINYINT NULL COMMENT '0:male; 1:female; 2:unknown',
  `age` TINYINT UNSIGNED NULL COMMENT '',
  `name` VARCHAR(20) NULL COMMENT '',
  `nationality` VARCHAR(20) NULL COMMENT '',
  `city` VARCHAR(20) NULL COMMENT '',
  `job` VARCHAR(20) NULL COMMENT '',
  `photo` VARCHAR(255) NULL COMMENT '',
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
  `rating_time` DATETIME NOT NULL COMMENT '',
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
  `user_age` TINYINT UNSIGNED NULL COMMENT '',
  `state` TINYINT NOT NULL COMMENT '',
  `source_X` DOUBLE NULL COMMENT '',
  `source_Y` DOUBLE NULL COMMENT '',
  `source_name` VARCHAR(255) NULL COMMENT '',
  `destination_X` DOUBLE NULL COMMENT '',
  `destination_Y` DOUBLE NULL COMMENT '',
  `destination_name` VARCHAR(255) NULL COMMENT '',
  `leaving_time` DATETIME NULL COMMENT '',
  `exp_gender` TINYINT NULL COMMENT '',
  `exp_age_min` TINYINT UNSIGNED NULL COMMENT '',
  `exp_age_max` TINYINT UNSIGNED NULL COMMENT '',
  `request_time` DATETIME NOT NULL COMMENT '',
  `remain_chance` TINYINT NOT NULL DEFAULT 2 COMMENT '',
  PRIMARY KEY (`request_id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `qipin`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `qipin`.`orders` ;

CREATE TABLE IF NOT EXISTS `qipin`.`orders` (
  `order_id` VARCHAR(25) NOT NULL COMMENT '',
  `request_id1` VARCHAR(25) NOT NULL COMMENT '',
  `request_id2` VARCHAR(25) NOT NULL COMMENT '',
  `user_id1` VARCHAR(20) NOT NULL COMMENT '',
  `user_id2` VARCHAR(20) NOT NULL COMMENT '',
  `order_time` DATETIME NOT NULL COMMENT '',
  `save_percent` DOUBLE NOT NULL COMMENT '',
  `route` VARCHAR(255) NOT NULL COMMENT '',
  `route_names` VARCHAR(255) NOT NULL COMMENT '',
  PRIMARY KEY (`order_id`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `qipin`.`id_counter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `qipin`.`id_counter` ;

CREATE TABLE IF NOT EXISTS `qipin`.`id_counter` (
  `id_key` VARCHAR(45) NOT NULL COMMENT '',
  `id_value` BIGINT NOT NULL DEFAULT 0 COMMENT '',
  PRIMARY KEY (`id_key`)  COMMENT '')
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `qipin`.`payment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `qipin`.`payment` ;

CREATE TABLE IF NOT EXISTS `qipin`.`payment` (
  `request_id` VARCHAR(25) NOT NULL COMMENT '',
  `charge_id` VARCHAR(255) NULL COMMENT '',
  `refund_id` VARCHAR(255) NULL COMMENT '',
  `user_id` VARCHAR(20) NOT NULL COMMENT '',
  `deposit` INT NOT NULL COMMENT '',
  `tip` INT NULL DEFAULT 0 COMMENT '',
  `deduction` INT NULL DEFAULT 0 COMMENT '',
  `state` TINYINT NULL DEFAULT 0 COMMENT '0:wait for paying\n1:paid, set pay_time\n2:wait for refunding,set exp_refund_time\n3:refunded',
  `pay_time` DATETIME NULL COMMENT '',
  `exp_refund_time` DATETIME NULL COMMENT '',
  `refund_time` DATETIME NULL COMMENT '',
  `refund_finish_time` DATETIME NULL COMMENT '',
  PRIMARY KEY (`request_id`)  COMMENT '')
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
