create table if not exists user_role(
    id serial primary key,
    user_id int not null references users(id),
    role_id int not null references u_roles(id)
);