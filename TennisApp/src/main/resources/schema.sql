CREATE TABLE tennis_club (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE court (
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255) NOT NULL
);

insert into tennis_club(name) values ('Club works, yay!');
insert into court(name) values ('Court works, yupie!');