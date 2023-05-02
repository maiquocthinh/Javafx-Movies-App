CREATE DATABASE IF NOT EXISTS db_moviesapp;

USE db_moviesapp;

CREATE TABLE `films`
(
    `id`       INT PRIMARY KEY auto_increment,
    `name`     VARCHAR(150) NOT NULL,
    `poster`   VARCHAR(200) NOT NULL,
    `backdrop` VARCHAR(200) NOT NULL,
    `trailer`  VARCHAR(200),
    `content`  VARCHAR(5000) NOT NULL,
    `release`  INT NOT NULL,
    `type`     VARCHAR(20) NOT NULL,
    `status`   VARCHAR(20),
    `runtime`  VARCHAR(20),
    `quality`  VARCHAR(20),
    `popular`  BOOL DEFAULT false,
    `rating`   DECIMAL(3, 1) DEFAULT 0,
    `viewed`   INT DEFAULT 0
);

CREATE TABLE `roles`
(
    `id`          INT PRIMARY KEY auto_increment,
    `name`        VARCHAR(20) NOT NULL,
    `permissions` VARCHAR(1000) NOT NULL
);

CREATE TABLE `users`
(
    `id`       INT PRIMARY KEY auto_increment,
    `name`     VARCHAR(50) NOT NULL,
    `email`    VARCHAR(60) UNIQUE NOT NULL,
    `avatar`   VARCHAR(200) NOT NULL DEFAULT "https://i.imgur.com/ae7e0eq.png",
    `password` VARCHAR(200) NOT NULL,
    `roleId`   INT,
    FOREIGN KEY (`roleId`) REFERENCES roles(`id`)
);

CREATE TABLE `follows`
(
    `userId` INT NOT NULL,
    `filmId` INT NOT NULL,
    `date`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`userId`) REFERENCES users(`id`),
    FOREIGN KEY (`filmId`) REFERENCES films(`id`)
);

CREATE TABLE `genres`
(
    `id`   INT PRIMARY KEY auto_increment,
    `name` VARCHAR(20) NOT NULL
);

CREATE TABLE `countries`
(
    `id`   INT PRIMARY KEY auto_increment,
    `name` VARCHAR(20) NOT NULL
);

CREATE TABLE `episodes`
(
    `id`     INT PRIMARY KEY auto_increment,
    `name`   VARCHAR(30) NOT NULL,
    `link`   VARCHAR(300) NOT NULL,
    `filmId` INT NOT NULL,
    FOREIGN KEY (`filmId`) REFERENCES films(`id`)
);

CREATE TABLE `comments`
(
    `id`      INT PRIMARY KEY auto_increment,
    `content` VARCHAR(500) NOT NULL,
    `date`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `userId`  INT NOT NULL,
    `filmId`  INT NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES users(`id`),
    FOREIGN KEY (`filmId`) REFERENCES films(`id`)
);

CREATE TABLE `notifications`
(
    `id`      INT PRIMARY KEY auto_increment,
    `title`   VARCHAR(200) NOT NULL,
    `content` VARCHAR(500) NOT NULL,
    `date`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `film_genre`
(
    `filmId`  INT NOT NULL,
    `genreId` INT NOT NULL,
    FOREIGN KEY (`filmId`) REFERENCES films(`id`),
    FOREIGN KEY (`genreId`) REFERENCES genres(`id`)
);

CREATE TABLE `film_country`
(
    `filmId`    INT NOT NULL,
    `countryId` INT NOT NULL,
    FOREIGN KEY (`filmId`) REFERENCES films(`id`),
    FOREIGN KEY (`countryId`) REFERENCES countries(`id`)
);

CREATE TABLE `user_notification`
(
    `read`           BOOL DEFAULT false,
    `notificationId` INT NOT NULL,
    `userId`         INT NOT NULL,
    `filmId`         INT NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES users(`id`),
    FOREIGN KEY (`filmId`) REFERENCES films(`id`),
    FOREIGN KEY (`notificationId`) REFERENCES notifications(`id`)
);

CREATE TABLE `otps`
(
    `email`           VARCHAR(250) NOT NULL,
    `otp_code_hashed` VARCHAR(250) NOT NULL,
    `expiry_time`     DATETIME NOT NULL DEFAULT (Now() + INTERVAL 5 minute),
    `is_verified`     BOOL NULL DEFAULT false
);

CREATE TABLE view_log
(
    `filmId` INT NOT NULL,
    `viewed` INT DEFAULT 0,
    `date`   DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (`filmId`) REFERENCES `films`(`id`)
);

-- CREATE EVENT delete_expired_otp
-- ON SCHEDULE
--     EVERY 1 DAY
--     STARTS (DATE_ADD(NOW(), INTERVAL 1 DAY) + INTERVAL '02:00:00' HOUR_SECOND)
-- DO
--     DELETE FROM `otps` WHERE expiry_time < NOW();