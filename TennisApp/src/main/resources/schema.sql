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
    is_deleted BOOLEAN NOT NULL,
    tennis_club_id INT NOT NULL,
    surface_type_id INT NOT NULL,
    FOREIGN KEY (tennis_club_id) REFERENCES tennis_club(id) ON DELETE CASCADE,
    FOREIGN KEY (surface_type_id) REFERENCES surface_type(id)
);

CREATE TABLE customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    is_deleted BOOLEAN NOT NULL
);

CREATE TABLE reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    court_id INT NOT NULL,
    customer_id INT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    FOREIGN KEY (court_id) REFERENCES court(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

INSERT INTO tennis_club(name) VALUES
    ('Club 1 works, yay!'),
    ('Club 2 works, yay!');

INSERT INTO surface_type (name, cost) VALUES
    ('CLAY', 250),
    ('GRASS', 300),
    ('CARPET', 400);


INSERT INTO court (name, is_deleted, tennis_club_id, surface_type_id) VALUES
    ('Court 1.0 works, yupie!', false, 1, (SELECT id FROM surface_type WHERE name = 'CLAY')),
    ('Court 1.1 works, yupie!', false, 1, (SELECT id FROM surface_type WHERE name = 'GRASS')),
    ('Court 2.0 works, yupie!', false, 2, (SELECT id FROM surface_type WHERE name = 'CLAY')),
    ('Court 2.1 works, yupie!', false, 2, (SELECT id FROM surface_type WHERE name = 'CARPET'));


INSERT INTO customer (name, phone_number, is_deleted) VALUES
    ('John Doe', '123456789', false),
    ('Jane Smith', '987654321', false);


INSERT INTO reservation (court_id, customer_id, start_time, end_time, is_deleted) VALUES
    ((SELECT id FROM court WHERE name = 'Court 1.0 works, yupie!'), (SELECT id FROM customer WHERE phone_number = '123456789'), '2024-12-11 10:00:00', '2024-12-12 11:00:00', false),
    ((SELECT id FROM court WHERE name = 'Court 1.0 works, yupie!'), (SELECT id FROM customer WHERE phone_number = '123456789'), '2024-12-12 10:00:00', '2024-12-13 11:00:00', false),
    ((SELECT id FROM court WHERE name = 'Court 1.1 works, yupie!'), (SELECT id FROM customer WHERE phone_number = '987654321'), '2024-12-01 12:00:00', '2024-12-01 13:00:00', true),
    ((SELECT id FROM court WHERE name = 'Court 2.0 works, yupie!'), (SELECT id FROM customer WHERE phone_number = '123456789'), '2024-12-02 09:00:00', '2024-12-02 10:00:00', false);