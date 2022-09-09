create table if not exists users
(
    id                serial primary key,
    lastname          varchar(45)  not null,
    name              varchar(45)  not null,
    patronymic        varchar(45)  not null,
    email             varchar(255) unique not null,
    registration_date timestamp default current_date
);