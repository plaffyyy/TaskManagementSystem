CREATE TABLE user_task (
   user_id int not null ,
   task_id int not null ,
   primary key (user_id, task_id),
   foreign key (user_id) references users(id) on delete cascade,
   FOREIGN KEY (task_id) references tasks(id) on delete cascade
);