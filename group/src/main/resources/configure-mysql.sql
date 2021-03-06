CREATE DATABASE rbac;

CREATE USER 'dev'@'localhost' IDENTIFIED BY '123';

GRANT SELECT ON rbac.* to 'dev'@'localhost';
GRANT INSERT ON rbac.* to 'dev'@'localhost';
GRANT DELETE ON rbac.* to 'dev'@'localhost';
GRANT UPDATE ON rbac.* to 'dev'@'localhost';
