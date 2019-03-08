create database if not exists GuessTheNumberTest;

use GuessTheNumberTest;

create table games(
gameId int primary key auto_increment,
isGameOver boolean not null default 0,
targetNum int not null
);

create table rounds(
roundId int primary key auto_increment,
guessNumber int not null,
gameId int not null,
foreign key (gameId) references games(gameId));


