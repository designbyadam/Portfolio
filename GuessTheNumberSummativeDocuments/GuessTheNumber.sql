drop database if exists GuessTheNumber;
create database GuessTheNumber;

use GuessTheNumber;

create table games(
gameId int primary key auto_increment,
isGameOver boolean not null default 0,
targetNum int not null
);

create table rounds(
roundId int primary key auto_increment,
roundNumber int not null,
guessNumber int not null,
gameId int not null,
exactCorrect int,
partialCorrect int,
timeStamp datetime,
foreign key (gameId) references games(gameId));

drop database if exists GuessTheNumberTest;
create database GuessTheNumberTest;

use GuessTheNumberTest;

create table games(
gameId int primary key auto_increment,
isGameOver boolean not null default 0,
targetNum int not null
);

create table rounds(
roundId int primary key,
roundNumber int not null,
guessNumber int not null,
gameId int not null,
exactCorrect int,
partialCorrect int,
timeStamp datetime,
foreign key (gameId) references games(gameId));

drop table rounds;
