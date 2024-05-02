create table app_user
(
    id         bigserial primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    email      varchar(255) not null,
    birth_date date         not null,
    role       varchar(255) not null
);

create table race
(
    id       bigserial primary key,
    name     varchar(255) not null,
    distance varchar(255) not null
);

create table race_application
(
    id         bigserial primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    club       varchar(255)
);


