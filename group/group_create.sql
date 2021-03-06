create table group_role_mapping_table (group_role_id bigint not null auto_increment, group_id bigint, role_id bigint, primary key (group_role_id)) engine=InnoDB;
create table group_table (group_id bigint not null auto_increment, create_date datetime, group_description varchar(255), group_name varchar(255), last_modified_date datetime, primary key (group_id)) engine=InnoDB;
alter table group_role_mapping_table add constraint UKi8npy666oxm10heecsn6vusah unique (group_id, role_id);
alter table group_table add constraint UK_5awb2k4ure0fi6kk52ui3hyvk unique (group_name);
