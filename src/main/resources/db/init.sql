DROP TABLE IF EXISTS account, bank, transaction_type, transaction_, user_ cascade;

CREATE TABLE bank
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE user_
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR,
    email VARCHAR
);

CREATE TABLE account
(
    id      SERIAL PRIMARY KEY,
    number  INT NOT NULL,
    balance DECIMAL(10,2),
    bank_id INT,
    user_id INT,
    FOREIGN KEY (bank_id) REFERENCES bank (id),
    FOREIGN KEY (user_id) REFERENCES user_ (id)
);

CREATE TABLE transaction_type
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR
);

CREATE TABLE transaction_
(
    id                  SERIAL PRIMARY KEY,
    amount              DECIMAL(2),
    created_at           DATE,
    transaction_type_id INT,
    FOREIGN KEY (transaction_type_id) REFERENCES transaction_type
);

INSERT INTO bank (name)
VALUES ('Alfa  Bank'),
       ('Belarus Bank'),
       ('Prior Bank'),
       ('MTBank'),
       ('Agroprombank');

INSERT INTO user_ (name, email)
VALUES ('Denis Kaydunov', 'denis.kaydunov@gmail.com'),
       ('John Smith', 'john.smith@example.com'),
       ('Emily Johnson', 'emily.johnson@example.com'),
       ('Michael Davis', 'michael.davis@example.com'),
       ('Sarah Wilson', 'sarah.wilson@example.com'),
       ('David Anderson', 'david.anderson@example.com'),
       ('Jennifer Martinez', 'jennifer.martinez@example.com'),
       ('Christopher Taylor', 'christopher.taylor@example.com'),
       ('Jessica Thomas', 'jessica.thomas@example.com'),
       ('Matthew Garcia', 'matthew.garcia@example.com'),
       ('Elizabeth Brown', 'elizabeth.brown@example.com'),
       ('Daniel Rodriguez', 'daniel.rodriguez@example.com'),
       ('Linda Martinez', 'linda.martinez@example.com'),
       ('Andrew Lee', 'andrew.lee@example.com'),
       ('Maria Hernandez', 'maria.hernandez@example.com'),
       ('James Clark', 'james.clark@example.com'),
       ('Jessica Lewis', 'jessica.lewis@example.com'),
       ('Robert Walker', 'robert.walker@example.com'),
       ('Karen Young', 'karen.young@example.com'),
       ('William Turner', 'william.turner@example.com');

INSERT INTO account (number, balance, bank_id, user_id)
VALUES
    (1, 5000.31, 1, 1),
    (2, 3000.52, 2, 2),
    (3, 7000.73, 3, 3),
    (4, 4000.94, 4, 4),
    (5, 6000.15, 5, 5),
    (6, 4500.36, 1, 6),
    (7, 2500.47, 2, 7),
    (8, 5500.58, 3, 8),
    (9, 3500.69, 4, 9),
    (10, 6500.70, 5, 10),
    (11, 5200.81, 1, 11),
    (12, 3200.92, 2, 12),
    (13, 7200.13, 3, 13),
    (14, 4200.24, 4, 14),
    (15, 6200.35, 5, 15),
    (16, 4800.46, 1, 16),
    (17, 2800.57, 2, 17),
    (18, 5800.68, 3, 18),
    (19, 3800.79, 4, 19),
    (20, 6800.80, 5, 20),
    (21, 5100.91, 1, 1),
    (22, 3100.02, 2, 2),
    (23, 7100.13, 3, 3),
    (24, 4100.24, 4, 4),
    (25, 6100.35, 5, 5),
    (26, 4600.46, 1, 6),
    (27, 2600.57, 2, 7),
    (28, 5600.68, 3, 8),
    (29, 3600.79, 4, 9),
    (30, 6600.80, 5, 10),
    (31, 5300.91, 1, 11),
    (32, 3300.02, 2, 12),
    (33, 7300.13, 3, 13),
    (34, 4300.24, 4, 14),
    (35, 6300.35, 5, 15),
    (36, 4900.46, 1, 16),
    (37, 2900.57, 2, 17),
    (38, 5900.68, 3, 18),
    (39, 3900.79, 4, 19),
    (40, 6900.80, 5, 20);

INSERT INTO transaction_type (type)
VALUES ('withdraw'),
       ('deposit');


