create table user_group_mapping_table
(
    user_group_id bigint not null auto_increment,
    group_id      bigint,
    user_id       bigint,
    primary key (user_group_id)
) engine=InnoDB;
create table user_table
(
    user_id            bigint not null auto_increment,
    create_date        datetime,
    first_name         varchar(255),
    last_modified_date datetime,
    last_name          varchar(255),
    user_name          varchar(255),
    primary key (user_id)
) engine=InnoDB;
alter table user_group_mapping_table
    add constraint UK5rhnboke9fcw3ok6rfpvfeda4 unique (user_id, group_id);
alter table user_table
    add constraint UK_p50irg6kthpq3f33xu9r1kw4x unique (user_name);
