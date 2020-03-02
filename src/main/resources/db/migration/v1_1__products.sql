create table if not exists Products(
	id int not null auto_increment primary key,
    name varchar(100) not null,
    category enum("Vegetables", "Mushrooms", "Flour products", "Sweets"),
    price double(10,3) not null,
    discount double(10,3) not null,
    version int not null,
    created timestamp not null default current_timestamp,
    updated timestamp not null default current_timestamp on update current_timestamp
) engine = InnoDB;