CREATE TABLE IF NOT EXISTS announce (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    author VARCHAR(255),
    publication_date TIMESTAMP NOT NULL,
    type INTEGER DEFAULT 0,
    status INTEGER DEFAULT 0,
    date_time_start TIMESTAMP,
    date_time_end TIMESTAMP,
    duration REAL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
