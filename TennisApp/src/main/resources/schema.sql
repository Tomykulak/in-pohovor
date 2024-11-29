CREATE TABLE tennis_club (
    id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL
);

CREATE TABLE surface_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
cost INT NOT NULL
);

CREATE TABLE court (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    tennis_club_id INT NOT NULL,
    surface_type_id INT NOT NULL,
    FOREIGN KEY (tennis_club_id) REFERENCES tennis_club(id) ON DELETE CASCADE,
    FOREIGN KEY (surface_type_id) REFERENCES surface_type(id)
);

INSERT INTO tennis_club(name) VALUES ('Club 1 works, yay!');
INSERT INTO tennis_club(name) VALUES ('Club 2 works, yay!');

INSERT INTO surface_type (name, cost) VALUES
    ('CLAY', 250),
    ('GRASS', 300),
    ('CARPET', 400);

INSERT INTO court (name, tennis_club_id, surface_type_id) VALUES
    ('Court 1.0 works, yupie!', 1, (SELECT id FROM surface_type WHERE name = 'CLAY')),
    ('Court 1.1 works, yupie!', 1, (SELECT id FROM surface_type WHERE name = 'GRASS')),
    ('Court 2.0 works, yupie!', 2, (SELECT id FROM surface_type WHERE name = 'CLAY')),
    ('Court 2.1 works, yupie!', 2, (SELECT id FROM surface_type WHERE name = 'CARPET'));
