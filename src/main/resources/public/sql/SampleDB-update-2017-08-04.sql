
CREATE TABLE `sample`.`sm_project` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT UNSIGNED NOT NULL,
  `name` VARCHAR(32) NOT NULL,
  `tag` VARCHAR(32) NULL,
  `description` VARCHAR(255) NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `same_project_name_constrains` (`user_id` ASC, `name` ASC))
COMMENT = 'project table structure definition.';

CREATE TABLE `sample`.`sm_virtual_machine` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  `cpu_num` INT NULL,
  `memory_size` INT NULL COMMENT 'in GB',
  `ip_address` VARCHAR(15) NULL,
  `project_id` INT UNSIGNED NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `same_vm_name_constains_under_same_proj` (`name` ASC, `project_id` ASC))
COMMENT = 'none';



