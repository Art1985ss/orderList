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
    password varchar(100),
    version int default 1,
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


CREATE TABLE IF NOT EXISTS authorities (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    authority VARCHAR(50),
    FOREIGN KEY (user_id)
        REFERENCES user (id)
        ON DELETE CASCADE
);

CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;