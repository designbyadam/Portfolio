drop database if exists HotelReservation;

create database if not exists HotelReservation;
 
use HotelReservation; 
 
create table if not exists Customers (
	CustomerID int(11) primary key auto_increment,
	FirstName varchar(50) not null,
    LastName varchar(50) not null,
    Email varchar(100) null,
    PhoneNumber varchar(15) not null,
    StreetAddress varchar(150) not null,
    City varchar(100) not null,
    State varchar(20) not null,
    ZipCode varchar(15) not null
);

create table if not exists PromoCodes (
	PromoCodeID int(11) primary key auto_increment,
    Name varchar(50) not null,
    StartDate date not null,
    EndDate date not null,
    PercentOff Decimal(4,2) null,
    DollarOff Decimal(5,2) null
);

create table if not exists Reservations (
	ReservationID int(15) primary key auto_increment,
    StartDate date not null,
    EndDate date not null,
    CustomerID int(11) not null,
    PromoCodeID int(11) null
);

create table if not exists Amenities (
	AmenityID int(11) primary key auto_increment,
	Name varchar(5) not null,
    Description varchar(100) null
);

create table if not exists RoomTypes (
	RoomTypeID int(11) primary key auto_increment,
    Name varchar(50) not null
);

create table if not exists Rooms (
	RoomID int(11) primary key auto_increment,
    Floor int(11) not null,
    OccupLimit int(11) not null,
    RoomTypeID int(11)  not null
);

create table if not exists RoomRates (
	RoomRateID int(11) primary key auto_increment,
    Description varchar(100) null,
    StartDate date not null,
    EndDate date not null,
    RoomTypeID int(11) not null
);

create table if not exists Guests (
	GuestID int(11) primary key auto_increment,
	FirstName varchar(50) null,
    LastName varchar(50) null,
    Age int(3) null,
    ReservationID int(15) not null
);

create table if not exists Bills (
	BillingID int(15) primary key auto_increment,
	Total decimal (7,2) not null,
    Tax decimal(5,2) not null,
    ReservationID int(15) not null
);

create table if not exists BillDetails (
	BillDetailID int(11) primary key auto_increment,
	ItemName varchar(150) not null,
    ItemDescription varchar(250) null,
    Price decimal(7,2) not null,
    BillingID int(11) not null
);

create table if not exists AddOns (
	AddOnID int(11) primary key auto_increment,
	Name varchar(50) not null,
	Description varchar(150) null
);

create table if not exists AddOnRates (
	AddOnRateID int(11) primary key auto_increment,
    Description varchar(100) null,
    StartDate date not null,
    EndDate date not null,
    AddOnID int(11) not null,
    BillDetailID int(11) null
);

create table if not exists RoomReservations (
	RoomID int(11) not null,
    ReservationID int(15) not null,
    primary key pk_RoomReservations (RoomID, ReservationID),
    foreign key fk_RoomReservations_Rooms (RoomID)
		references Rooms(RoomID),
	foreign key fk_RoomReservations_Reservations (ReservationID)
		references Reservations(ReservationID)
);

create table if not exists RoomAmenities (
	RoomID int(11) not null,
    AmenityID int(11) not null,
    primary key pk_RoomAmenities (RoomID, AmenityID),
    foreign key fk_RoomAmenities_Rooms (RoomID)
		references Rooms(RoomID),
	foreign key fk_RoomAmenities_Amenities (AmenityID)
		references Amenities(AmenityID)
);

alter table Reservations
	add constraint fk_Reservations_Customers
		foreign key (CustomerID)
        references Customers(CustomerID),
    add constraint fk_Reservations_PromoCode
        foreign key (PromoCodeID)
        references PromoCodes(PromoCodeID);
        
alter table Rooms
	add constraint fk_Rooms_RoomTypes
		foreign key (RoomTypeID)
        references RoomTypes(RoomTypeID);
        
alter table RoomRates
	add constraint fk_RoomRates_RoomTypes
		foreign key (RoomTypeID)
        references RoomTypes(RoomTypeID);
        
alter table Guests
	add constraint fk_Guests_Reservations
		foreign key (ReservationID)
        references Reservations(ReservationID);
        
alter table Bills
	add constraint fk_Bills_Reservations
		foreign key (ReservationID)
        references Reservations(ReservationID);

alter table BillDetails
	add constraint fk_BillDetails_Bills
		foreign key (BillingID)
        references Bills(BillingID);
    
alter table AddOnRates
	add constraint fk_AddOnRates_AddOns
		foreign key (AddOnID)
        references AddOns(AddOnID),
	add constraint fk_AddOnRates_BillDetails
		foreign key (BillDetailID)
        references BillDetails(BillDetailID);