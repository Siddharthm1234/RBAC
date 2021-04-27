# Role Based Access Control - Microservice Architecture

---
To create the project we use **Spring Cloud Gateway**, **Hystrix Cloud Circuit Breaker**, **Eureka Service Discovery**
from the Spring Cloud Netflix technology stack.

---

## Services Port

- #### User Service : [http://localhost:8000](http://localhost:8000)
- #### Group Service : [http://localhost:8100](http://localhost:8100)
- #### Role Service : [http://localhost:8200](http://localhost:8200)
- #### Eureka Discovery Service : [http://localhost:8761](http://localhost:8761)
- #### Spring Cloud Gateway : [http://localhost:8765](http://localhost:8765)

---

## Architecture diagram of RBAC Microservices

![RBAC_Architecture](https://user-images.githubusercontent.com/76997642/109539969-eb617980-7ae7-11eb-87e9-562e6263ca77.jpg)

---

## Deliverables

- [x] Create User_Table (user_id, create_date, last_modified_date, user_name, first_name, last_name)
- [x] Create User_Group_Mapping_Table (user_group_id, user_id, group_id)
- [x] Create Group_Table (group_id, create_date, last_modified_date, group_name, group_description)
- [x] Create Group_Role_Mapping_Table (group_role_id, group_id, role_id)
- [x] Create Roles_Table (role_id, create_date, last_modified_date, role_name, role_description)
- [x] Modify basic profile of a User based on user_name
- [x] Modify basic details of a Group based on group_id
- [x] Modify basic details of a Role based on role_id
- [x] Delete a User, Group, Role
- [x] Add one or more Roles to a Group
- [x] Remove one or more Roles from a Group
- [x] Add a User to one or more Groups
- [x] Remove a User from one or more Groups
- [x] Get all Users, Groups, Roles
- [x] Given a user_name, return the basic profile
- [x] Given a user_name/userid, return the basic profile & list of group names
- [x] Given a group_id, return the basic group details
- [x] Given a group_id, return the basic group details & the list of role names
- [x] Given a role_id, return the basic role details
- [x] Given a user_name and group_id return true or false depending on whether the group is assigned to user
- [x] Given a user_name and role_id, return true or false depending on whether the role is assigned to user

---

## Database configuration

- #### User Table :

```
create table user_table (user_id bigint not null auto_increment, create_date datetime, first_name varchar(255), last_modified_date datetime, last_name varchar(255), user_name varchar(255), primary key (user_id)) engine=InnoDB;
alter table user_table add constraint UK_p50irg6kthpq3f33xu9r1kw4x unique (user_name);
```

- #### User Group Mapping Table :

```
create table user_group_mapping_table (user_group_id bigint not null auto_increment, group_id bigint, user_id bigint, primary key (user_group_id)) engine=InnoDB;
alter table user_group_mapping_table add constraint UK5rhnboke9fcw3ok6rfpvfeda4 unique (user_id, group_id);
```

- #### Group Table :

```
create table group_table (group_id bigint not null auto_increment, create_date datetime, group_description varchar(255), group_name varchar(255), last_modified_date datetime, primary key (group_id)) engine=InnoDB;
alter table group_table add constraint UK_5awb2k4ure0fi6kk52ui3hyvk unique (group_name);
```

- #### Group Role Mapping Table :

```
create table group_role_mapping_table (group_role_id bigint not null auto_increment, group_id bigint, role_id bigint, primary key (group_role_id)) engine=InnoDB;
alter table group_role_mapping_table add constraint UKi8npy666oxm10heecsn6vusah unique (group_id, role_id);
```

- #### Role Table :

```
create table role_table (role_id bigint not null auto_increment, create_date datetime, last_modified_date datetime, role_description varchar(255), role_name varchar(255), primary key (role_id)) engine=InnoDB;
alter table role_table add constraint UK_kqtgs1ar590vxi6vvfti5sej9 unique (role_name);
```

---

## Database Schema

![RBAC_DB_Schema](https://user-images.githubusercontent.com/76997642/109540066-0502c100-7ae8-11eb-976e-a5fa544fe4dd.JPG)

---




















