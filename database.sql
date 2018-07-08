create database clientes;

use clientes;

create table cliente(
id bigint auto_increment,
name varchar(50),
age int,
primary key (id)
);

insert into cliente(name, age) value ('Gabriel', 21);

select * from cliente;