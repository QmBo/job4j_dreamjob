create table if not exists roles (
id serial primary key,
role varchar(2000)
);
CREATE UNIQUE INDEX roles_role_uindex ON roles (role);
insert into roles(role) values ('Administrator'), ('User');
alter table users add column if not exists role_id int references roles(id);
update users set role_id = (select id from roles where role = 'User');
update users set role_id = (select id from roles where role = 'Administrator') where login = 'root';