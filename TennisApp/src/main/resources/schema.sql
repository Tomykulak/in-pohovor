CREATE TABLE tennis_club (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE court (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    tennis_club_id INT NOT NULL,
    FOREIGN KEY (tennis_club_id) REFERENCES tennis_club(id) ON DELETE CASCADE
);

insert into tennis_club(name) values ('Club 1 works, yay!');
insert into tennis_club(name) values ('Club 2 works, yay!');
insert into court(name, tennis_club_id) values ('Court 1.0 works, yupie!', 1);
insert into court(name, tennis_club_id) values ('Court 1.1 works, yupie!', 1);
insert into court(name, tennis_club_id) values ('Court 2.0 works, yupie!', 2);
insert into court(name, tennis_club_id) values ('Court 2.1 works, yupie!', 2);