CREATE DATABASE IF NOT EXISTS db_moviesapp;

USE db_moviesapp;

CREATE TABLE `films` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(150) not null,
    `poster` VARCHAR(200) not null,
    `backdrop` VARCHAR(200) not null,
    `trailer` VARCHAR(200),
    `content` VARCHAR(5000) not null,
    `release` INT not null,
    `type` VARCHAR(20) not null,
    `status` VARCHAR(20),
    `runtime` VARCHAR(20),
    `quality` VARCHAR(20),
    `popular` BOOL default false,
    `rating` DECIMAL(3,1) default 0,
    `viewed` INT default 0
);

create table `roles` (
	`id` int primary key auto_increment,
    `name` varchar(20) not null,
    `permissions` varchar(1000) not null
);

create table `users` (
	`id` int primary key auto_increment,
    `name` varchar(50) not null,
    `email` varchar(60) unique not null,
    `avatar` varchar(200) not null default "https://i.imgur.com/ae7e0eQ.png",
    `password` varchar(200) not null,
    `roleId` int,
    foreign key (`roleId`) references roles(`id`)
);

create table `follows` (
	`userId` int not null,
    `filmId` int not null,
    `date` datetime not null,
    foreign key (`userId`) references users(`id`),
    foreign key (`filmId`) references films(`id`)
);

create table `genres` (
	`id` int primary key auto_increment,
    `name` varchar(20) not null
);

create table `countries` (
	`id` int primary key auto_increment,
    `name` varchar(20) not null
);

create table `episodes` (
	`id` int primary key auto_increment,
    `name` varchar(30) not null,
    `link` varchar(300) not null,
    `filmId` int not null,
    foreign key (`filmId`) references films(`id`)
);

create table `comments` (
	`id` int primary key auto_increment,
    `content` varchar(500) not null,
	`date` datetime not null,
    `userId` int not null,
    `filmId` int not null,
    foreign key (`userId`) references users(`id`),
    foreign key (`filmId`) references films(`id`)
);

create table `notifications` (
	`id` int primary key auto_increment,
    `title` varchar(200) not null,
    `content` varchar(500) not null,
	`date` datetime not null
);

create table `film_genre` (
	`filmId` int not null,
    `genreId` int not null,
    foreign key (`filmId`) references films(`id`),
    foreign key (`genreId`) references genres(`id`)
);

create table `film_country` (
	`filmId` int not null,
    `countryId` int not null,
    foreign key (`filmId`) references films(`id`),
    foreign key (`countryId`) references countries(`id`)
);

create table `user_notification` (
    `read` bool default false,
    `notificationId` int not null,
	`userId` int not null,
    `filmId` int not null,
    foreign key (`userId`) references users(`id`),
    foreign key (`filmId`) references films(`id`),
    foreign key (`notificationId`) references notifications(`id`)
);

CREATE TABLE `otps` ( 
  `email` VARCHAR(250) NOT NULL,
  `otp_code_hashed` VARCHAR(250) NOT NULL,
  `expiry_time` DATETIME NOT NULL DEFAULT (NOW() + INTERVAL 5 MINUTE),
  `is_verified` BOOL NULL DEFAULT false 
);

-- CREATE EVENT delete_expired_otp
-- ON SCHEDULE
--     EVERY 1 DAY
--     STARTS (DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL '02:00:00' HOUR_SECOND)
-- DO
--     DELETE FROM `otps` WHERE expiry_time < NOW();