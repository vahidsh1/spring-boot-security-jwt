insert into users (id,username,password ,  email )
values (1,'vahid', '$2y$10$8QlFvBG4s75EC3cXWdtJ6./w3aATwGhm4PEZvByY3jMWsGbB8Pg96', 'vahidsh1@gmail.com');
insert into users (id,username,password ,  email )
values (2,'hamid', '$2y$10$8QlFvBG4s75EC3cXWdtJ6./w3aATwGhm4PEZvByY3jMWsGbB8Pg96', 'hamidsh1@gmail.com');
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

insert into user_roles (role_id,user_id) values (1,1);
insert into user_roles (role_id,user_id) values (2,1);
insert into user_roles (role_id,user_id) values (3,1);
insert into user_roles (role_id,user_id) values (2,2);