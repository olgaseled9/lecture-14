create table Customers
(
    customer_id bigint      not null primary key,
    first_name  varchar(25) not null,
    last_name   varchar(30) not null
);

insert into Customers (customer_id, first_name, last_name)
values (1, 'Ivan', 'Ivanov'),
       (2, 'Petr', 'Petrov'),
       (3, 'Andrei', 'Fedorov'),
       (4, 'Andrei', 'Andreev'),
       (5, 'Julia', 'Petrova'),
       (6, 'Rita', 'Ivanova'),
       (7, 'Anna', 'Drozd'),
       (8, 'Vita', 'Fedorova'),
       (9, 'Yanna', 'Andreeva'),
       (10, 'Anna', 'Kot');

select *
from Customers;

create table Orders
(
    order_id    bigint not null primary key,
    customer_id bigint not null Unique,
    quantity    int    not null,
    foreign key (customer_id) references Customers (customer_id)
);

insert into Orders (order_id, customer_id, quantity)
values (123, 1, 25),
       (124, 2, 23),
       (125, 3, 21),
       (126, 4, 1),
       (127, 6, 2),
       (128, 5, 3),
       (129, 9, 4),
       (130, 7, 8),
       (131, 8, 9),
       (132, 10, 10);

select *
from Orders;
