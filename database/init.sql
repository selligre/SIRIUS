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

-- Additional tables required by the services
CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS notification (
    uuid uuid PRIMARY KEY,
    announce_id INTEGER NOT NULL,
    creation_date TIMESTAMP,
    has_been_red BOOLEAN NOT NULL DEFAULT FALSE,
    title VARCHAR(255),
    user_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS tag (
    id SERIAL PRIMARY KEY,
    category VARCHAR(255),
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_tag (
    id BIGSERIAL PRIMARY KEY,
    ref_client_id INTEGER NOT NULL,
    ref_tag_id INTEGER NOT NULL
);

INSERT INTO app_user(username,email) VALUES ('bloo','bloo@mail.com');