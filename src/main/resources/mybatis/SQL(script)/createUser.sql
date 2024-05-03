-- root로 로그인한 후 데이터베이스 생성
create database orders default CHARACTER SET UTF8;
show databases;
use mysql;
create user TP@localhost identified by '1234';
GRANT ALL PRIVILEGES ON TP.* TO TP@LOCALHOST;
------------------------------------------------