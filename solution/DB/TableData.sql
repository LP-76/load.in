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


INSERT INTO USERS (ID, MOVE_PLAN_ID, FEEDBACK_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, PASSWORD)
VALUES (1, NULL, NULL, 'Ricky', 'Bobby', 'myemail@outlook.com', NULL, 'qiyh4XPJGsOZ2MEAyLkfWqeQ');
/*
CREATE TABLE `USER_ROLES` (
  `user_id` int,
  `role_id` int,
  `user_type` varchar(255)
);

CREATE TABLE `ROLES` (
  `ID` int(11) DEFAULT NULL,
  `name` varchar(255),
  `display_name` varchar(255),
  `description` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp,
  PRIMARY KEY ('ID')
);

CREATE TABLE `PERMISSION` (
  `ID` int PRIMARY KEY,
  `NAME` varchar(255),
  `DESCRIPTION` varchar(255),
  `CREATED_AT` timestamp,
  `UPDATED_AT` timestamp,
  PRIMARY KEY ('ID')
);

CREATE TABLE `USER_PERMISSIONS` (
  `USER_ID` int PRIMARY KEY,
  `PERMISSION_ID` int,
  `USER_TYPE` varchar(255)
);

CREATE TABLE `ROLE_PERMISSIONS` (
  `ROLE_ID` int PRIMARY KEY,
  `PERMISSION_ID` int
);

*/
