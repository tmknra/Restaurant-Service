create table if not exists roles_user(
    id serial primary key,
    user_id int not null references users(id),
    role_id int not null references roles(id)
);