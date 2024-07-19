DROP TABLE IF EXISTS account, bank, transaction_type, transaction, user_ cascade;

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
    balance DECIMAL(10, 2),
    bank_id INT,
    user_id INT,
    is_saving_account BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (bank_id) REFERENCES bank (id),
    FOREIGN KEY (user_id) REFERENCES user_ (id)
);

CREATE TABLE transaction_type
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR
);

CREATE TABLE transaction
(
    id                     SERIAL PRIMARY KEY,
    amount                 DECIMAL(2),
    created_at             DATE,
    transaction_type_id      INT,
    account_source_id      INT,
    account_destination_id INT,
    FOREIGN KEY (transaction_type_id) REFERENCES transaction_type (id),
    FOREIGN KEY (account_source_id) REFERENCES account (id),
    FOREIGN KEY (account_destination_id) REFERENCES account (id)
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

INSERT INTO account (balance, bank_id, user_id, is_saving_account)
VALUES (5000.31, 1, 1, True),
       (3000.52, 2, 2, True),
       (7000.73, 3, 3, False),
       (4000.94, 4, 4, True),
       (6000.15, 5, 5, False),
       (4500.36, 1, 6, True),
       (2500.47, 2, 7, False),
       (5500.58, 3, 8, True),
       (3500.69, 4, 9, False),
       (6500.70, 5, 10, True),
       (5200.81, 1, 11, True),
       (3200.92, 2, 12, False),
       (7200.13, 3, 13, True),
       (4200.24, 4, 14, False),
       (6200.35, 5, 15, True),
       (4800.46, 1, 16, True),
       (2800.57, 2, 17, False),
       (5800.68, 3, 18, True),
       (3800.79, 4, 19, False),
       (6800.80, 5, 20, True),
       (5100.91, 1, 1, False),
       (3100.02, 2, 2, True),
       (7100.13, 3, 3, False),
       (4100.24, 4, 4, True),
       (6100.35, 5, 5, False),
       (4600.46, 1, 6, True),
       (2600.57, 2, 7, False),
       (5600.68, 3, 8, True),
       (3600.79, 4, 9, False),
       (6600.80, 5, 10, True),
       (5300.91, 1, 11, True),
       (3300.02, 2, 12, False),
       (7300.13, 3, 13, True),
       (4300.24, 4, 14, False),
       (6300.35, 5, 15, True),
       (4900.46, 1, 16, True),
       (2900.57, 2, 17, False),
       (5900.68, 3, 18, True),
       (3900.79, 4, 19, False),
       (6900.80, 5, 20, True);


INSERT INTO transaction_type (type)
VALUES ('DEPOSIT'),
       ('WITHDRAW');



