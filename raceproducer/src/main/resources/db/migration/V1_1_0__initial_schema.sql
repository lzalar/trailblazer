create table app_user
(
    id         uuid primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    email      varchar(255) not null,
    birth_date date         not null,
    role       varchar(255) not null
);

create table race
(
    id       uuid primary key,
    name     varchar(255) not null,
    distance varchar(255) not null
);

create table race_application
(
    id         uuid primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    club       varchar(255),
    race_id    uuid references race(id)
);

create index idx_race_application_race_id on race_application (race_id);


