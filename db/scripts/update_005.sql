create table if not exists countrys (
id serial primary key,
country varchar(2000)
);
create table if not exists citys (
id serial primary key,
country_id int references countrys(id),
city varchar(2000)
);
insert into countrys(country) values ('Russia'), ('Germany');
insert into citys(city, country_id) values
('Moscow', (select id from countrys where country = 'Russia')),
('St. Petersburg', (select id from countrys where country = 'Russia')),
('Perm', (select id from countrys where country = 'Russia')),
('Rostov on Don', (select id from countrys where country = 'Russia')),
('Cologne', (select id from countrys where country = 'Germany')),
('Munich', (select id from countrys where country = 'Germany'));
alter table users add column if not exists city_id int references citys(id);
update users set city_id = (select id from citys where city = 'Moscow') where city_id is null;