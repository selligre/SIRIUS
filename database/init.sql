CREATE TABLE IF NOT EXISTS announce (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );