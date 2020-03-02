-- drop database test;
-- create database test;
-- use test;
create table if not exists Products(
	id int not null auto_increment primary key,
    name varchar(100) not null,
    category enum("VEGETABLES", "MUSHROOMS", "FLOUR PRODUCTS", "SWEETS"),
    price double(10,3) not null,
    discount double(10,3) not null,
    version int,
    created timestamp not null default current_timestamp,
    updated timestamp not null default current_timestamp on update current_timestamp
) engine = InnoDB;

create table if not exists Orders(
	id int not null auto_increment primary key,
    name varchar(100) not null,
    user_id int,
    version int,
    created timestamp not null default current_timestamp,
    updated timestamp not null default current_timestamp on update current_timestamp
) engine = InnoDB;

create table if not exists Users(
	id int not null auto_increment primary key,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    address varchar(100),
    email varchar(100),
    version int,
    created timestamp not null default current_timestamp,
    updated timestamp not null default current_timestamp on update current_timestamp
) engine = InnoDB;

create table if not exists Order_product_list(
	id int not null auto_increment primary key,
    order_id int,
    product_id int,
    count int,
    foreign key(order_id)
		references Orders(id)
        on delete cascade,
	foreign key(product_id)
		references Products(id)
        on delete cascade
) engine = InnoDB;