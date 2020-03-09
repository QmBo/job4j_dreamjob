alter TABLE users add column if not exists password varchar(2000);
insert into users(login, password) values('root', 'root');