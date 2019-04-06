DROP TABLE IF EXISTS superheroSightingsTest.Organization_Supers;

DROP TABLE IF EXISTS superheroSightingsTest.Super_Sightings;

DROP TABLE IF EXISTS superheroSightingsTest.Organizations;

DROP TABLE IF EXISTS superheroSightingsTest.Supers;

DROP TABLE IF EXISTS superheroSightingsTest.Sightings;

DROP TABLE IF EXISTS superheroSightingsTest.Locations;

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
address varchar(100),
phoneNumber varchar(14) not null,
email varchar(35)
);

create table Locations(
locationId int primary key auto_increment,
`name` varchar(30) not null,
description varchar(50) not null,
address varchar(100),
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

INSERT INTO superheroSightingsTest.Supers (`name`, description, superpower)
	VALUES 	('Superman', 'So super', 'flight'),
			('Batman', 'the bat man', 'tech'),
            ('Freeze', 'cold', 'icing');
            
INSERT INTO superheroSightingsTest.Locations (`name`, description, address, latitude, longitude)
	VALUES 	('Casa Jacob', 'Chill', '123 North Street Alpharetta, GA 30022', '-23.029365', '43.654734'),
			('SWG', 'Place to learn', '15 5th Street Minneapolis, MN', '-33.029375', '56.654734'),
            ('Home', 'Casa', '1522 Portland Avenue #309, St. Paul, MN 55104', '-24.029365', '45.654734');
            
INSERT INTO superheroSightingsTest.Sightings (`date`, locationId)
	VALUES 	('2019-03-03', '1'),
			('2020-03-07', '2'),
            ('2021-04-03', '2'),
            ('2017-02-01', '2');
            
INSERT INTO superheroSightingsTest.Organizations (`name`, description, address, phoneNumber, email)
	VALUES 	('Marvel', 'Sweet org', '456 South Street Alpharetta, GA 30022', '7702410271', 'marvel@marvel.com'),
			('DC', 'D org', '125 North Street Alpharetta, GA 30022', '7701234567', 'dc@marvel.com'),
            ('SeaOrg', 'Scientology HQ', '123 North Street Clearwater, FL', '7707654321', 'lron@hubbard.com');

INSERT INTO superheroSightingsTest.Organization_Supers (organizationId, superId)
	VALUES 	('1', '1'),
			('2', '2'),
            ('3', '2'),
			('3', '3'),
            ('3', '1');
