create table role_table
(
    role_id            bigint not null auto_increment,
    create_date        datetime,
    last_modified_date datetime,
    role_description   varchar(255),
    role_name          varchar(255),
    primary key (role_id)
) engine=InnoDB;
alter table role_table
    add constraint UK_kqtgs1ar590vxi6vvfti5sej9 unique (role_name);
create table group_role_mapping_table
(
    group_role_id bigint not null auto_increment,
    group_id      bigint,
    role_id       bigint,
    primary key (group_role_id)
) engine=InnoDB;