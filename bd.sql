CREATE TABLE
    "user" (
        user_id SERIAL,
        first_name VARCHAR(50) NOT NULL,
        last_name VARCHAR(50) NOT NULL,
        display_name VARCHAR(50) NOT NULL,
        user_type VARCHAR(50) NOT NULL,
        email VARCHAR(50) NOT NULL,
        password VARCHAR(300) NOT NULL,
        PRIMARY KEY (user_id)
    );

CREATE TABLE
    announce (
        announce_id SERIAL,
        ref_author_id INT NOT NULL,
        publication_date TIMESTAMP NOT NULL,
        status CHAR(50) NOT NULL,
        type CHAR(50) NOT NULL,
        title VARCHAR(100) NOT NULL,
        description VARCHAR(1000),
        date_time_start TIMESTAMP NOT NULL,
        duration FLOAT,
        date_time_end TIMESTAMP,
        is_reccurent BOOL NOT NULL,
        PRIMARY KEY (announce_id),
        FOREIGN KEY (ref_author_id) REFERENCES "user" (user_id)
    );

CREATE TABLE
    user_history (
        ref_user_id INT NOT NULL,
        ref_announce_id INT NOT NULL,
        FOREIGN KEY (ref_user_id) REFERENCES "user" (user_id),
        FOREIGN KEY (ref_announce_id) REFERENCES announce (announce_id)
    );

CREATE TABLE
    activity (
        ref_announce_id INT NOT NULL,
        number_of_slots SMALLINT,
        slots_available SMALLINT,
        price FLOAT,
        FOREIGN KEY (ref_announce_id) REFERENCES announce (announce_id)
    );

CREATE TABLE
    loan (
        ref_announce_id INT NOT NULL,
        FOREIGN KEY (ref_announce_id) REFERENCES announce (announce_id)
    );

CREATE TABLE
    service (
        ref_announce_id INT NOT NULL,
        FOREIGN KEY (ref_announce_id) REFERENCES announce (announce_id)
    );

CREATE TABLE
    recurrence_pattern (
        recurrence_id SERIAL,
        ref_announce_id INT NOT NULL,
        number_of_occurencies SMALLINT,
        date_time_start TIMESTAMP,
        date_time_end TIMESTAMP,
        day_of_week VARCHAR(1000),
        week_of_month VARCHAR(1000),
        day_of_month VARCHAR(1000),
        PRIMARY KEY (recurrence_id),
        FOREIGN KEY (ref_announce_id) REFERENCES announce (announce_id)
    );

CREATE TABLE
    announce_occurencies (
        ref_recurrence_id INT NOT NULL,
        ref_announce_id INT NOT NULL,
        date_time_start TIMESTAMP NOT NULL,
        date_time_end TIMESTAMP NOT NULL,
        FOREIGN KEY (ref_recurrence_id) REFERENCES recurrence_pattern (recurrence_id),
        FOREIGN KEY (ref_announce_id) REFERENCES announce (announce_id)
    );

CREATE TABLE
    tag (
        tag_id SERIAL,
        name VARCHAR(50) NOT NULL,
        category VARCHAR(50) NOT NULL,
        PRIMARY KEY (tag_id)
    );

CREATE TABLE
    user_tag (
        ref_user_id INT NOT NULL,
        ref_tag_id INT NOT NULL,
        FOREIGN KEY (ref_user_id) REFERENCES "user" (user_id),
        FOREIGN KEY (ref_tag_id) REFERENCES tag (tag_id)
    );

CREATE TABLE
    announce_tag (
        ref_announce_id INT NOT NULL,
        ref_tag_id INT NOT NULL,
        FOREIGN KEY (ref_announce_id) REFERENCES announce (announce_id),
        FOREIGN KEY (ref_tag_id) REFERENCES tag (tag_id)
    );

CREATE TABLE
    location (
        location_id SERIAL,
        name VARCHAR(50) NOT NULL,
        PRIMARY KEY (location_id)
    );

CREATE TABLE
    user_location (
        ref_user_id INT NOT NULL,
        ref_location_id INT NOT NULL,
        FOREIGN KEY (ref_user_id) REFERENCES "user" (user_id),
        FOREIGN KEY (ref_location_id) REFERENCES location (location_id)
    );

CREATE TABLE
    announce_location (
        ref_announce_id INT NOT NULL,
        ref_location_id INT NOT NULL,
        FOREIGN KEY (ref_announce_id) REFERENCES announce (announce_id),
        FOREIGN KEY (ref_location_id) REFERENCES location (location_id)
    );