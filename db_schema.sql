create database tweetapp;

use tweetapp;

CREATE TABLE user(
us_first_name varchar(60) not null,
us_last_name varchar(60) ,
us_gender varchar(60) not null,
us_dob date null,
us_email varchar(60),
us_password varchar(60) not null,
us_isactive boolean,
primary key(us_email));

create table post(
id int auto_increment,
message varchar(256) not null,
us_email varchar(60),
us_firstname varchar(60),
date_of_post timestamp DEFAULT CURRENT_TIMESTAMP, 
primary key(id,us_email),
constraint user_tweet_fk foreign key (us_email)
references user (us_email) on delete cascade );
