# Init all tables

# --- !Ups
INSERT INTO PUBLIC.USER (ID, NAME, PASSWORD, SALT, MOBILE, CREATE_TIME, UPDATE_TIME) VALUES (1, 'tom', '123456', '1111', '18138438111', '2018-04-17 13:26:59.954000000', '2018-04-17 13:26:59.954000000');

# --- !Downs
delete from user where ID = 1;
