CREATE SCHEMA users AUTHORIZATION "cgl-data";

CREATE SCHEMA announce AUTHORIZATION "cgl-data";


CREATE TABLE users.users (
    userID INT,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    displayName VARCHAR(50),
    userType VARCHAR(50),
    email VARCHAR(50),
    password VARCHAR(300),
    PRIMARY KEY(userID)
);

CREATE TABLE announce.announce(
    announceID INT,
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
    PRIMARY KEY(announceID),
    FOREIGN KEY(refAuthorID) REFERENCES users.users(userID)
);

CREATE TABLE users.history(
    refUserID INT,
    refAnnounceID INT,
    FOREIGN KEY(refUserID) REFERENCES users.users(userID),
    FOREIGN KEY(refAnnounceID) REFERENCES announce.announce(announceID)
);

CREATE TABLE announce.activity(
    refAnnounceID INT,
    slotsNumber SMALLINT NOT NULL,
    slotsAvailable SMALLINT NOT NULL,
    price FLOAT NOT NULL,
    FOREIGN KEY(refAnnounceID) REFERENCES announce.announce(announceID)
);

CREATE TABLE announce.loan(
    refAnnounceID INT,
    FOREIGN KEY(refAnnounceID) REFERENCES announce.announce(announceID)
);

CREATE TABLE announce.service(
    refAnnounceID INT,
    FOREIGN KEY(refAnnounceID) REFERENCES announce.announce(announceID)
);

CREATE TABLE reccurencePattern(
    reccurenceID INT,
    refAnnounceID INT,
    numberOfOccurencies SMALLINT NOT NULL,
    dateTimeStart TIMESTAMP NOT NULL,
    dateTimeEnd TIMESTAMP NOT NULL,
    deyOfWeek VARCHAR(1000) NOT NULL,
    weekOfMonth VARCHAR(1000) NOT NULL,
    dayOfMonth VARCHAR(1000) NOT NULL,
    PRIMARY KEY(reccurenceID),
    FOREIGN KEY(refAnnounceID) REFERENCES announce.announce(announceID)
);

CREATE TABLE announce.occurencies(
    refReccurenciesID INT,
    refAnnounceID INT,
    dateTimeStart TIMESTAMP,
    dateTimeEnd TIMESTAMP,
    FOREIGN KEY(refReccurenciesID) REFERENCES reccurencePattern(reccurenceID),
    FOREIGN KEY(refAnnounceID) REFERENCES announce.announce(announceID)
);

CREATE TABLE tags(
    tagID INT,
    name VARCHAR(50),
    category VARCHAR(50),
    PRIMARY KEY(tagID)
);

CREATE TABLE users.tags(
    refUserID INT,
    refTagID INT,
    FOREIGN KEY(refUserID) REFERENCES users.users(userID),
    FOREIGN KEY(refTagID) REFERENCES tags(tagID)
);

CREATE TABLE announce.tags(
    refAnnounceID INT,
    reftagID INT,
    FOREIGN KEY(refAnnounceID) REFERENCES announce.announce(announceID),
    FOREIGN KEY(refTagID) REFERENCES tags(tagID)
);

CREATE TABLE locations (
    locationID INT,
    name VARCHAR(50),
    PRIMARY KEY(locationID)
);

CREATE TABLE users.location(
    refUserID INT,
    refLocationID INT,
    FOREIGN KEY(refUserID) REFERENCES users.users(userID),
    FOREIGN KEY(refLocationID) REFERENCES locations(locationID)
);

CREATE TABLE announce.location(
    refAnnounceID INT,
    refLocationID INT,
    FOREIGN KEY(refAnnounceID) REFERENCES announce.announce(announceID),
    FOREIGN KEY(refLocationID) REFERENCES locations(locationID)
);

