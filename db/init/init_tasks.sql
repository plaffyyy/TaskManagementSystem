CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   name varchar(255) NOT NULL,
   description varchar,
   status varchar(50),
   priority varchar(50)
);