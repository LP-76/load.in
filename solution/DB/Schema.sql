CREATE TABLE IF NOT EXISTS USERS (
    `ID` int(11) NOT NULL,
    `MOVE_PLAN_ID` int(11) DEFAULT NULL,
    `FEEDBACK_ID` int(11) DEFAULT NULL,
    `FIRST_NAME` varchar(50) NOT NULL,
    `LAST_NAME` varchar(50) NOT NULL,
    `EMAIL` varchar(50) NOT NULL,
    `PHONE_NUMBER` varchar(50) DEFAULT NULL,
    `PASSWORD` varchar(50) NOT NULL,
    PRIMARY KEY (`ID`)
    );



CREATE TABLE IF NOT EXISTS BOX_SIZES (
     `ID` int PRIMARY KEY,
     `DESCRIPTION` varchar(255),
     `DIMENSIONS` varchar(255),
     `CREATED_AT` timestamp,
     `UPDATED_AT` timestamp
);