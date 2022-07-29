drop table if exists restaurants;
create table if not exists restaurants
(
    id          serial primary key,
    name        varchar(45) not null,
    description text        not null
);

insert into restaurants (name, description)
values ('burger_king', 'king of burgers'),
       ('kfc', 'chicken God'),
       ('tgi_fridays',
        'A network of restaurants with a festive atmosphere, where beer, cocktails and dishes of American cuisine are served.');