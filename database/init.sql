CREATE TABLE IF NOT EXISTS announces (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );