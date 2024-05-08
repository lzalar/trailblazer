create table race_projection
(
    id       uuid primary key,
    name     varchar(255) not null,
    distance varchar(255) not null
);

create table applied_races_projection
(
    id            uuid primary key,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    club          varchar(255),
    race_id       uuid         not null,
    race_name     varchar(255) not null,
    user_id       uuid         not null,
    race_distance varchar(255) not null
);
