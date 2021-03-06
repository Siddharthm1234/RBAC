create table role_table (role_id bigint not null auto_increment, create_date datetime, last_modified_date datetime, role_description varchar(255), role_name varchar(255), primary key (role_id)) engine=InnoDB;
alter table role_table add constraint UK_kqtgs1ar590vxi6vvfti5sej9 unique (role_name);
