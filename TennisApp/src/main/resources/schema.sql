-- Create the surface_type table
CREATE TABLE surface_type (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(50) NOT NULL UNIQUE,
                              price_per_minute DOUBLE NOT NULL,
                              deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create the court table
CREATE TABLE court (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    surface_type_id INT NOT NULL,
    FOREIGN KEY (surface_type_id) REFERENCES surface_type(id)
);

-- Create the customer table
CREATE TABLE customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create the reservation table
CREATE TABLE reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    court_id INT NOT NULL,
    customer_id INT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    is_doubles BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (court_id) REFERENCES court(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

-- Insert test data into the surface_type table
INSERT INTO surface_type (name, price_per_minute, deleted)VALUES
    ('CLAY', 250, FALSE),
    ('GRASS', 300, FALSE),
    ('CARPET', 400, FALSE);

-- Insert test data into the court table
INSERT INTO court (name, deleted, surface_type_id)VALUES
    ('Court 1.0 works, yupie!', FALSE, (SELECT id FROM surface_type WHERE name = 'CLAY')),
    ('Court 1.1 works, yupie!', FALSE, (SELECT id FROM surface_type WHERE name = 'GRASS')),
    ('Court 2.0 works, yupie!', FALSE, (SELECT id FROM surface_type WHERE name = 'CLAY')),
    ('Court 2.1 works, yupie!', FALSE, (SELECT id FROM surface_type WHERE name = 'CARPET'));

-- Insert test data into the customer table
INSERT INTO customer (name, phone_number, deleted)VALUES
    ('John Doe', '123456789', FALSE),
    ('Jane Smith', '987654321', FALSE);

-- Insert test data into the reservation table
INSERT INTO reservation (court_id, customer_id, start_time, end_time, deleted) VALUES
    ((SELECT id FROM court WHERE name = 'Court 1.0 works, yupie!'), (SELECT id FROM customer WHERE phone_number = '123456789'), '2024-12-11 10:00:00', '2024-12-12 11:00:00', FALSE),
    ((SELECT id FROM court WHERE name = 'Court 1.0 works, yupie!'), (SELECT id FROM customer WHERE phone_number = '123456789'), '2024-12-12 10:00:00', '2024-12-13 11:00:00', FALSE),
    ((SELECT id FROM court WHERE name = 'Court 1.1 works, yupie!'), (SELECT id FROM customer WHERE phone_number = '987654321'), '2024-12-01 12:00:00', '2024-12-01 13:00:00', TRUE),
    ((SELECT id FROM court WHERE name = 'Court 2.0 works, yupie!'), (SELECT id FROM customer WHERE phone_number = '123456789'), '2024-12-02 09:00:00', '2024-12-02 10:00:00', FALSE);
