
CREATE DATABASE IF NOT EXISTS `sample` DEFAULT CHARSET 'utf8';

USE `sample`;

CREATE TABLE `sample`.`sm_user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) NOT NULL,
  `password` VARCHAR(128) NOT NULL,
  `is_enabled` TINYINT NULL DEFAULT 1,
  `register_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'user table structure definition';

CREATE TABLE `sample`.`sm_role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  `description` VARCHAR(255) NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
COMMENT = 'role table structure definition';

CREATE TABLE `sample`.`sm_authority` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  `path` VARCHAR(32) NOT NULL,
  `description` VARCHAR(255) NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
COMMENT = 'authority table structure definition';

CREATE TABLE `sample`.`sm_user_role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `role_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`))
COMMENT = 'user role associated table structure definition';

CREATE TABLE `sample`.`sm_role_authority` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` INT UNSIGNED NOT NULL,
  `authority_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`))
COMMENT = 'role authority associated table structure definition.';





