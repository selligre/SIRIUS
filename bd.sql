CREATE TABLE
    user (
        user_id SERIAL PRIMARY KEY,
        first_name VARCHAR(50),
        last_name VARCHAR(50),
        display_name VARCHAR(50),
        user_type VARCHAR(50),
        email VARCHAR(50),
        password VARCHAR(300)
        PRIMARY KEY (user_id)
    );

CREATE TABLE
    Announces (
        announceID SERIAL PRIMARY KEY,
        refAuthorID INT,
        publicationDate TIMESTAMP,
        status CHAR(50),
        type CHAR(50),
        title VARCHAR(100),
        description VARCHAR(1000) NOT NULL,
        dateTimeStart TIMESTAMP,
        duration FLOAT NOT NULL,
        dateTimeEnd TIMESTAMP NOT NULL,
        isReccurent BOOL,
        PRIMARY KEY (announceID),
        FOREIGN KEY (refAuthorID) REFERENCES Users (userID)
    );

CREATE TABLE
    UserHistory (
        refUserID INT,
        refAnnounceID INT,
        FOREIGN KEY (refUserID) REFERENCES Users (userID),
        FOREIGN KEY (refAnnounceID) REFERENCES Announces (announceID)
    );

CREATE TABLE
    Activities (
        refAnnounceID INT,
        slotsNumber SMALLINT NOT NULL,
        slotsAvailable SMALLINT NOT NULL,
        price FLOAT NOT NULL,
        FOREIGN KEY (refAnnounceID) REFERENCES announce.announce (announceID)
    );

CREATE TABLE
    Loans (
        refAnnounceID INT,
        FOREIGN KEY (refAnnounceID) REFERENCES announce.announce (announceID)
    );

CREATE TABLE
    Services (
        refAnnounceID INT,
        FOREIGN KEY (refAnnounceID) REFERENCES announce.announce (announceID)
    );

CREATE TABLE
    AnnounceReccurencePattern (
        reccurenceID SERIAL PRIMARY KEY,
        refAnnounceID INT,
        numberOfOccurencies SMALLINT NOT NULL,
        dateTimeStart TIMESTAMP NOT NULL,
        dateTimeEnd TIMESTAMP NOT NULL,
        deyOfWeek VARCHAR(1000) NOT NULL,
        weekOfMonth VARCHAR(1000) NOT NULL,
        dayOfMonth VARCHAR(1000) NOT NULL,
        PRIMARY KEY (reccurenceID),
        FOREIGN KEY (refAnnounceID) REFERENCES announce.announce (announceID)
    );

CREATE TABLE
    AnnounceOccurencies (
        refReccurenciesID INT,
        refAnnounceID INT,
        dateTimeStart TIMESTAMP,
        dateTimeEnd TIMESTAMP,
        FOREIGN KEY (refReccurenciesID) REFERENCES reccurencePattern (reccurenceID),
        FOREIGN KEY (refAnnounceID) REFERENCES announce.announce (announceID)
    );

CREATE TABLE
    Tags (
        tagID SERIAL PRIMARY KEY,
        name VARCHAR(50),
        category VARCHAR(50),
        PRIMARY KEY (tagID)
    );

CREATE TABLE
    UsersTags (
        refUserID INT,
        refTagID INT,
        FOREIGN KEY (refUserID) REFERENCES users.users (userID),
        FOREIGN KEY (refTagID) REFERENCES tags (tagID)
    );

CREATE TABLE
    AnnounceTags (
        refAnnounceID INT,
        reftagID INT,
        FOREIGN KEY (refAnnounceID) REFERENCES announce.announce (announceID),
        FOREIGN KEY (refTagID) REFERENCES tags (tagID)
    );

CREATE TABLE
    Locations (
        locationID SERIAL PRIMARY KEY,
        name VARCHAR(50),
        PRIMARY KEY (locationID)
    );

CREATE TABLE
    UserLocation (
        refUserID INT,
        refLocationID INT,
        FOREIGN KEY (refUserID) REFERENCES users.users (userID),
        FOREIGN KEY (refLocationID) REFERENCES locations (locationID)
    );

CREATE TABLE
    AnnounceLocation (
        refAnnounceID INT,
        refLocationID INT,
        FOREIGN KEY (refAnnounceID) REFERENCES announce.announce (announceID),
        FOREIGN KEY (refLocationID) REFERENCES locations (locationID)
    );