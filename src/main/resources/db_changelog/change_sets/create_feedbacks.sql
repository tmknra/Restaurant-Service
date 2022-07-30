-- drop table if exists feedbacks;
create table if not exists feedbacks
(
    id            serial primary key,
    restaurant_id int not null references restaurants (id) on delete cascade,
    feedback      varchar(255),
    rating        int not null check (rating <= 5 and rating > 0)
);

insert into feedbacks (restaurant_id, feedback, rating)
values (1, 'best of the best', 5),
       (1, 'may be hotter', 4),
       (2, 'fried chicken heaven', 4),
       (2, 'too slooow', 4),
       (3, 'pretty tasty but too expensive', 4),
       (3, 'the best steak i have ever ate', 5);