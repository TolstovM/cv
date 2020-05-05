create table users (
    id bigint not null auto_increment,
    username varchar(30) not null,
    email varchar(60) not null,
    password varchar(60) not null,
    unique uq_user_email (email),
    primary key (id)
);

create table roles (
    id bigint not null auto_increment,
    name varchar(20) not null,
    unique uq_role_name (`name`),
    primary key (id)
);

create table users_roles (
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint fk_users_roles_user_id foreign key (user_id)
            references users(id),
    constraint fk_users_roles_role_id foreign key (role_id)
            references roles(id)
);

create table adverts (
    id bigint not null auto_increment,
    user_id bigint not null,
    name varchar(60) not null,
    description text not null,
    price decimal not null,
    primary key (id),
    constraint fk_advert_uid foreign key (user_id)
                references users(id)
);

update  adverts
   set  name=:name,
        description=:description,
        price=:price,
        user_id=:user_id
 where  id=:id;

select  *
  from  adverts
 where  (lower(name) like '%:text%'
        or lower(description like '%:text%'))