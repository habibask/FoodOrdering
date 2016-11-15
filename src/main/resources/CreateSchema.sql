use FoodOrdering;

create table Customer (
	id int primary key,
    name varchar(200) not null,
	address varchar(500) not null,
	email varchar(200) not null unique,
	password varchar(200) not null,
	phone varchar(10) not null
);

create table Restaurant (
	id int primary key,
    name varchar(200) not null,
	address varchar(500) not null,
	email varchar(200) not null unique,
	password varchar(200) not null,
	phone varchar(10) not null
);


create table Orders(
	id int primary key,
    orderedBy int not null,
    foreign key(orderedBy) references Customer(id)
       on update cascade on delete cascade,
	orderedFrom int not null,
    foreign key(orderedFrom) references Restaurant(id)
       on update cascade on delete cascade,
	totalItems int not null,
    totalCost double not null
);

create table Reviews(
	id int primary key,
    ratedBy int not null,
    foreign key(ratedBy) references Customer(id)
       on update cascade on delete cascade,
	ratedFor int not null,
    foreign key(ratedFor) references Restaurant(id)
       on update cascade on delete cascade,
	description varchar(500),
    rating int not null check (rating between 1 and 5)
);

create table MenuItem(
	id int primary key,
    name varchar(200) not null,
    type enum('CHINESE', 'INDIAN', 'THAI', 'ITALIAN', 'MEXICAN' ,'AMERICAN'),
    description varchar(500)
);

create table Menu(
	restaurantId int,
    menuitem int,
    foreign key(restaurantId) references Restaurant(id)
       on update cascade on delete cascade,
	foreign key(menuitem) references MenuItem(id)
       on update cascade on delete cascade,
    primary key(restaurantid, menuitem),
    cost double not null
);
