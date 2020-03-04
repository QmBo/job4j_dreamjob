create table if not exists users(
  id serial primary key,
  name varchar(2000),
  login varchar(2000),
  email varchar(2000),
  create_time bigint
);