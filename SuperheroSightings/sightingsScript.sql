drop database if exists superheroSightings;
create database superheroSightings;

use superheroSightings;

create table Supers(
superId int primary key auto_increment,
`name` varchar(30) not null,
description varchar(50) not null,
superpower varchar(100) not null
);

create table Organizations(
organizationId int primary key auto_increment,
`name` varchar(30) not null,
description varchar(50) not null,
address varchar(100) not null,
phoneNumber varchar(14) not null,
email varchar(35) not null
);

create table Locations(
locationId int primary key auto_increment,
`name` varchar(30) not null,
description varchar(50) not null,
address varchar(100) not null,
latitude decimal(11,8) not null,
longitude decimal(18,8) not null
);

create table Sightings(
sightingId int primary key auto_increment,
`date` date not null,
locationId int not null,
foreign key (locationId) references Locations(locationId)
);

create table Super_Sightings(
superId int not null,
sightingId int not null,
primary key(superId, sightingId),
foreign key (superId) references Supers(superId),
foreign key (sightingId) references Sightings(sightingId));

create table Organization_Supers(
organizationId int not null,
superId int not null,
primary key(organizationId, superId),
foreign key (organizationId) references Organizations(organizationId),
foreign key (superId) references Supers(superId));

drop database if exists superheroSightingsTest;
create database superheroSightingsTest;

use superheroSightingsTest;

create table Supers(
superId int primary key auto_increment,
`name` varchar(30) not null,
description varchar(50) not null,
superpower varchar(100) not null
);

create table Organizations(
organizationId int primary key auto_increment,
`name` varchar(30) not null,
description varchar(50) not null,
address varchar(100) not null,
phoneNumber varchar(14) not null,
email varchar(35) not null
);

create table Locations(
locationId int primary key auto_increment,
`name` varchar(30) not null,
description varchar(50) not null,
address varchar(100) not null,
latitude decimal(11,8) not null,
longitude decimal(18,8) not null
);

create table Sightings(
sightingId int primary key auto_increment,
`date` date not null,
locationId int not null,
foreign key (locationId) references Locations(locationId)
);

create table Super_Sightings(
superId int not null,
sightingId int not null,
primary key(superId, sightingId),
foreign key (superId) references Supers(superId),
foreign key (sightingId) references Sightings(sightingId));

create table Organization_Supers(
organizationId int not null,
superId int not null,
primary key(organizationId, superId),
foreign key (organizationId) references Organizations(organizationId),
foreign key (superId) references Supers(superId));
