
######## Download Mysql Latest Image ######
docker pull mysql:latest

Check the link :
https://hub.docker.com/_/mysql

Note: If you do not want to create mysql database on docker, you can directly create on AWS.

####  create Container ###############
docker run --name mysql-inst \
-e MYSQL_ROOT_PASSWORD=welcome1 \
-e MYSQL_DATABASE=starbucks \
-e MYSQL_USER=prod \
-e MYSQL_PASSWORD=welcome1 \
-v /docker_volumes/mysql/database:/var/lib/mysql \
-p 3306:3306 \
-p 33060:33060 \
-d mysql:latest --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

##############################################

####### Connect to Container #####################
docker exec -i -t mysql-inst /bin/bash

############ Create Database and Users ################
mysql -u prod -p starbucks		
CREATE DATABASE starbucks;
USE starbucks;
mysql -u prod -p starbucks
password: welcome1


################## Create Tables #############################
create table users (
   `user_id` varchar(40) not null,
   `user_name` varchar(40) null,
   `created_on` datetime null,
   `mobile_num` varchar(40) not null,
   `email_id` varchar(40) not null,
   `pin` varchar(255) not null,
    primary key (`user_id`),
	unique index `email_id_unique` (`email_id` asc) visible);

create table cards (
  `card_id` varchar(40) not null,
  `user_id` varchar(40) not null,
  `card_num` varchar(9) not null,
  `card_code` varchar(3) not null,
  `created_on` datetime not null,
  primary key (`card_id`),
  constraint `fk_user_1`
    foreign key (user_id)
    references `starbucks`.`users`(user_id));