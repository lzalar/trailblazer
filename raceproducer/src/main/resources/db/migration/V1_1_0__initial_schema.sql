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

create table race_projection
(
    id       uuid primary key,
    name     varchar(255) not null,
    distance varchar(255) not null
);

create table applied_races_per_user_projection
(
    id                uuid primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    race_applications jsonb
);

create table applied_races_projection
(
    id         uuid primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    club       varchar(255),
    race_id    uuid         not null,
    race_name  varchar(255) not null,
    user_id    uuid         not null,
    distance   varchar(255) not null
);

create table race_application
(
    id         uuid primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    club       varchar(255),
    race_id    uuid references race (id),
    user_id    uuid references app_user (id)
);

create index idx_race_application_race_id on race_application (race_id);
create index idx_race_application_user_id on race_application (user_id);

insert into app_user
values (gen_random_uuid(), 'ivan', 'horvat', 'ivan.horvat@gmail.com', '1997-05-15', 'ADMINISTRATOR')


