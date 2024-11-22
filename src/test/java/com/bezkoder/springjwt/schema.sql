create table user
(
    id bigint not null,
    username varchar(20) not null,
    email varchar(50) not null,
    password varchar(120) not null,
    primary key(id),
    constraint count_name_key unique (email),
    constraint count_name_key unique (username),

);

create table role
(
    id integer not null,
    name ENUM (
        'ROLE_ADMIN',
        'ROLE_USER',
        'ROLE_MODERATOR'        )
    primary key(id)
);
