-- to execute this line of code from cmd follow the steps
-- go to start, find search bar and type "cmd",
-- paste this code then hit Enter-> cd C:\Program Files\MySQL\MySQL Server 8.0\bin  {{ CHECK THE VERSION IN YOUR FOLDER }}
-- mysql -u username -p {{ at "username" put your database username }}
-- add password
-- once in, paste this code ->   CREATE DATABASE IF NOT EXISTS sql_voyage_well_schema;
-- then paste this code -> use sql_voyage_well_schema
-- then paste this code -> INSERT INTO role (name) VALUES ('USER');
-- then paste this code -> INSERT INTO role (name) VALUES ('EDITOR');
-- then paste this code -> INSERT INTO role (name) VALUES ('ADMIN');
-- once created hit-> exit;