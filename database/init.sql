CREATE DATABASE IF NOT EXISTS db_moviesapp;

USE db_moviesapp;

CREATE TABLE `films` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(150) NOT NULL,
    `poster` VARCHAR(200) NOT NULL,
    `backdrop` VARCHAR(200) NOT NULL,
    `trailer` VARCHAR(200),
    `content` VARCHAR(5000) NOT NULL,
    `release` INT NOT NULL,
    `type` VARCHAR(20) NOT NULL,
    `status` VARCHAR(20),
    `runtime` VARCHAR(20),
    `quality` VARCHAR(20),
    `popular` BOOL DEFAULT false,
    `rating` DECIMAL(3,1) DEFAULT 0,
    `viewed` INT DEFAULT 0
);

CREATE TABLE `roles` (
	`id` INT PRIMARY KEY auto_increment,
    `name` VARCHAR(20) NOT NULL,
    `permissions` VARCHAR(1000) NOT NULL
);

CREATE TABLE `users` (
	`id` INT PRIMARY KEY auto_increment,
    `name` VARCHAR(50) NOT NULL,
    `email` VARCHAR(60) UNIQUE NOT NULL,
    `avatar` VARCHAR(200) NOT NULL DEFAULT "https://i.imgur.com/ae7e0eQ.png",
    `password` VARCHAR(200) NOT NULL,
    `roleId` INT,
    FOREIGN KEY (`roleId`) REFERENCES roles(`id`)
);

create table `follows` (
	`userId` INT NOT NULL,
    `filmId` INT NOT NULL,
    `date` datetime NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES users(`id`),
    FOREIGN KEY (`filmId`) REFERENCES films(`id`)
);

create table `genres` (
	`id` INT primary key auto_increment,
    `name` varchar(20) NOT NULL
);

create table `countries` (
	`id` INT primary key auto_increment,
    `name` varchar(20) NOT NULL
);

create table `episodes` (
	`id` INT primary key auto_increment,
    `name` varchar(30) NOT NULL,
    `link` varchar(300) NOT NULL,
    `filmId` INT NOT NULL,
    FOREIGN KEY (`filmId`) REFERENCES films(`id`)
);

create table `comments` (
	`id` INT primary key auto_increment,
    `content` varchar(500) NOT NULL,
	`date` datetime NOT NULL,
    `userId` INT NOT NULL,
    `filmId` INT NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES users(`id`),
    FOREIGN KEY (`filmId`) REFERENCES films(`id`)
);

create table `notifications` (
	`id` INT primary key auto_increment,
    `title` varchar(200) NOT NULL,
    `content` varchar(500) NOT NULL,
	`date` datetime NOT NULL
);

create table `film_genre` (
	`filmId` INT NOT NULL,
    `genreId` INT NOT NULL,
    FOREIGN KEY (`filmId`) REFERENCES films(`id`),
    FOREIGN KEY (`genreId`) REFERENCES genres(`id`)
);

create table `film_country` (
	`filmId` INT NOT NULL,
    `countryId` INT NOT NULL,
    FOREIGN KEY (`filmId`) REFERENCES films(`id`),
    FOREIGN KEY (`countryId`) REFERENCES countries(`id`)
);

create table `user_notification` (
    `read` bool DEFAULT false,
    `notificationId` INT NOT NULL,
	`userId` INT NOT NULL,
    `filmId` INT NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES users(`id`),
    FOREIGN KEY (`filmId`) REFERENCES films(`id`),
    FOREIGN KEY (`notificationId`) REFERENCES notifications(`id`)
);

CREATE TABLE `otps` (
  `email` VARCHAR(250) NOT NULL,
  `otp_code_hashed` VARCHAR(250) NOT NULL,
  `expiry_time` DATETIME NOT NULL DEFAULT (NOW() + INTERVAL 5 MINUTE),
  `is_verified` BOOL NULL DEFAULT false
);

CREATE TABLE view_log (
  `filmId` INT NOT NULL,
  `viewed` INT DEFAULT 0,
  `date` DATE DEFAULT (CURRENT_DATE),
  FOREIGN KEY (`filmId`) REFERENCES `films`(`id`)
);

-- CREATE EVENT delete_expired_otp
-- ON SCHEDULE
--     EVERY 1 DAY
--     STARTS (DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL '02:00:00' HOUR_SECOND)
-- DO
--     DELETE FROM `otps` WHERE expiry_time < NOW();