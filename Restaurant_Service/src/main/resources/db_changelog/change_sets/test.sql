create table if not exists test
(
    id          serial primary key,
    name        varchar(45) not null,
    description text        not null
);