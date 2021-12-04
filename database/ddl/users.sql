create table if not exists user
(
    id         bigint auto_increment,
    first_name varchar(100)                          not null,
    last_name  varchar(100)                          null,
    email      varchar(100)                          not null,
    insert_ts  datetime                              not null,
    update_ts  timestamp default current_timestamp() not null on update current_timestamp(),
    password   varchar(100)                          not null,
    constraint user_id_uindex
        unique (id)
);

alter table user
    add primary key (id);