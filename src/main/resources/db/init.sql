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

CREATE OR REPLACE FUNCTION generate_iban() RETURNS TEXT AS $$
DECLARE
    country_code CHAR(2) := 'BY';                -- Country code
    checksum CHAR(2);                            -- Check digits
    account_number CHAR(24);                     -- Unique account number
BEGIN
    -- Generate a random account number of 24 characters
    account_number := (
        SELECT string_agg(substring('0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ' FROM floor(random()*36)::int+1 FOR 1), '')
        FROM generate_series(1, 24)
    );

    -- Generate check digits (for simplicity, random 2 digits)
    checksum := lpad((floor(random() * 100))::int::text, 2, '0');

    -- Combine all parts into one IBAN
    RETURN country_code || checksum || account_number;
END;
$$ LANGUAGE plpgsql;


CREATE TABLE account
(
    id      VARCHAR PRIMARY KEY DEFAULT generate_iban(),
    balance DECIMAL(10, 2),
    currency VARCHAR(3) NOT NULL,
    bank_id INT,
    user_id INT,
    is_saving_account BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW(),
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
    amount                 DECIMAL(10,2) NOT NULL,
    transaction_type_id    INT NOT NULL,
    account_source_id      VARCHAR NULL,
    account_destination_id VARCHAR NULL,
    created_at             TIMESTAMP DEFAULT NOW(),
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

INSERT INTO account (id, balance, currency, bank_id, user_id, is_saving_account)
VALUES
    ('BY01ABCDE12345678901234567890', 5000.31, 'BYN', 1, 1, True),
    ('BY02FGHIJ23456789012345678901', 3000.52, 'BYN', 2, 2, True),
    ('BY03KLMNO34567890123456789012', 7000.73, 'BYN', 3, 3, False),
    ('BY04PQRST45678901234567890123', 4000.94, 'BYN', 4, 4, True),
    ('BY05UVWXY56789012345678901234', 6000.15, 'BYN', 5, 5, False),
    ('BY06ABCDE67890123456789012345', 4500.36, 'BYN', 1, 6, True),
    ('BY07FGHIJ78901234567890123456', 2500.47, 'BYN', 2, 7, False),
    ('BY08KLMNO89012345678901234567', 5500.58, 'BYN', 3, 8, True),
    ('BY09PQRST90123456789012345678', 3500.69, 'BYN', 4, 9, False),
    ('BY10UVWXY01234567890123456789', 6500.70, 'BYN', 5, 10, True),
    ('BY11ABCDE12345678901234567890', 5200.81, 'BYN', 1, 11, True),
    ('BY12FGHIJ23456789012345678901', 3200.92, 'BYN', 2, 12, False),
    ('BY13KLMNO34567890123456789012', 7200.13, 'BYN', 3, 13, True),
    ('BY14PQRST45678901234567890123', 4200.24, 'BYN', 4, 14, False),
    ('BY15UVWXY56789012345678901234', 6200.35, 'BYN', 5, 15, True),
    ('BY16ABCDE67890123456789012345', 4800.46, 'BYN', 1, 16, True),
    ('BY17FGHIJ78901234567890123456', 2800.57, 'BYN', 2, 17, False),
    ('BY18KLMNO89012345678901234567', 5800.68, 'BYN', 3, 18, True),
    ('BY19PQRST90123456789012345678', 3800.79, 'BYN', 4, 19, False),
    ('BY20UVWXY01234567890123456789', 6800.80, 'BYN', 5, 20, True),
    ('BY21ABCDE12345678901234567891', 5100.91, 'BYN', 1, 1, False),
    ('BY22FGHIJ23456789012345678902', 3100.02, 'BYN', 2, 2, True),
    ('BY23KLMNO34567890123456789013', 7100.13, 'BYN', 3, 3, False),
    ('BY24PQRST45678901234567890124', 4100.24, 'BYN', 4, 4, True),
    ('BY25UVWXY56789012345678901235', 6100.35, 'BYN', 5, 5, False),
    ('BY26ABCDE67890123456789012346', 4600.46, 'BYN', 1, 6, True),
    ('BY27FGHIJ78901234567890123457', 2600.57, 'BYN', 2, 7, False),
    ('BY28KLMNO89012345678901234568', 5600.68, 'BYN', 3, 8, True),
    ('BY29PQRST90123456789012345679', 3600.79, 'BYN', 4, 9, False),
    ('BY30UVWXY01234567890123456780', 6600.80, 'BYN', 5, 10, True),
    ('BY31ABCDE12345678901234567892', 5300.91, 'BYN', 1, 11, True),
    ('BY32FGHIJ23456789012345678903', 3300.02, 'BYN', 2, 12, False),
    ('BY33KLMNO34567890123456789014', 7300.13, 'BYN', 3, 13, True),
    ('BY34PQRST45678901234567890125', 4300.24, 'BYN', 4, 14, False),
    ('BY35UVWXY56789012345678901236', 6300.35, 'BYN', 5, 15, True),
    ('BY36ABCDE67890123456789012347', 4900.46, 'BYN', 1, 16, True),
    ('BY37FGHIJ78901234567890123458', 2900.57, 'BYN', 2, 17, False),
    ('BY38KLMNO89012345678901234569', 5900.68, 'BYN', 3, 18, True),
    ('BY39PQRST90123456789012345680', 3900.79, 'BYN', 4, 19, False),
    ('BY40UVWXY01234567890123456791', 6900.80, 'BYN', 5, 20, True);

INSERT INTO transaction_type (type)
VALUES ('DEPOSIT'),
       ('WITHDRAW'),
       ('INTEREST')



