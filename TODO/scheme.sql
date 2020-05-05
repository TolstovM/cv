create table `user` (
    id bigint not null auto_increment,
    username varchar(30) not null,
    email varchar(30) not null,
    password varchar(50) not null,
    primary key(id),
    unique(username)
);

create table `item` (
    id bigint not null auto_increment,
    user_id bigint not null,
    is_done bit(1) not null default 0,
    comment varchar(250) not null,
    primary key (id),
    foreign key (user_id)
        references `user`(id)
        on delete cascade
);