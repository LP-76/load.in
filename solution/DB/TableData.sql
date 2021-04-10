INSERT INTO USER (ID, FEEDBACK_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, PASSWORD, CREATED_AT, UPDATED_AT)
VALUES (1, NULL, 'Ricky', 'Bobby', 'myemail@outlook.com', NULL, 'qiyh4XPJGsOZ2MEAyLkfWqeQ', NOW(), NOW()),
       (2, NULL, 'John' , 'Smith', 'john.smith@test.net', '555-555-5555', 'BlueMango@1', NOW(), NOW());

INSERT INTO BOX_SIZE (ID, DESCRIPTION, DIMENSIONS, CREATED_AT, UPDATED_AT )
VALUES
(1,'Standard Box', '10x15x25', NOW(), NOW()),
(2,'Fine China Box', '15x12x28', NOW(), NOW()),
(3,'Stash Box', '18x29x45', NOW(), NOW()),
(4,'Poster Box', '67x10x12', NOW(), NOW()),
(5,'TV Box', '90x12x40', NOW(), NOW());

INSERT INTO ROLE (DESCRIPTION)
VALUES ('Standard User'), ('Administrator User');

INSERT INTO USER_INVENTORY_ITEM (USER_ID, BOX_ID, BOX_WIDTH, BOX_LENGTH, BOX_HEIGHT, ITEM_DESCRIPTION, FRAGILITY, WEIGHT, CREATED_AT, UPDATED_AT, STATUS, ROOM, ITEM_LIST)
VALUES (1, 1, 3, 3, 3, 'Mothers Clothes', '2', 234.5, NOW(), NOW(), 'At Source', 'Bedroom, thong, leggings, sweatshirt'),
       (1, 1, 48, 48, 60, 'Fridge', '2', 10, NOW(), NOW(), 'At Source');

INSERT INTO EXPERT_TIP (KEYWORD, TITLE, CONTENT, IMAGE, VIDEO, COMMENTS, CREATED_AT, UPDATED_AT)
VALUES ('Heavy item','Use small boxes for heavy items.', 'LIFT WITH YOUR BACK!', NULL, 'https://loadinvideohost.s3.amazonaws.com/heavy.mp4', 'THIS TIP SUCKS', NOW(), NOW());

INSERT INTO EXPERT_TIP (KEYWORD, TITLE, CONTENT, IMAGE, VIDEO, COMMENTS, CREATED_AT, UPDATED_AT)
VALUES ('Eric','LET ERIC IN', 'HE NEEDS TO SCREAMMMMMMMMMMM', NULL,'https://loadinvideohost.s3.amazonaws.com/eric.mp4', 'THIS TIP SUCKS', NOW(), NOW());

INSERT INTO EXPERT_TIP (KEYWORD, TITLE, CONTENT, IMAGE, VIDEO, COMMENTS, CREATED_AT, UPDATED_AT)
VALUES ('Star Wars','May the force be with you', 'I have brought peace and freedom to my new empire', NULL, 'https://loadinvideohost.s3.amazonaws.com/starwars.mp4', 'THIS TIP SUCKS', NOW(), NOW());

INSERT INTO EXPERT_TIP (KEYWORD, TITLE, CONTENT, IMAGE, VIDEO, COMMENTS, CREATED_AT, UPDATED_AT)
VALUES ('Janet','Check to see if you have original boxes for your electronics', 'Check to see if you stashed these boxes somewhere — attic? Garage? If you don’t have them, make a list of what you’ll need to buy or borrow to properly cushion your stuff.', NULL, 'https://loadinvideohost.s3.amazonaws.com/janet.mp4', 'THIS TIP SUCKS', NOW(), NOW());

INSERT INTO EXPERT_TIP (KEYWORD, TITLE, CONTENT, IMAGE, VIDEO, COMMENTS, CREATED_AT, UPDATED_AT)
VALUES ('Grinch','Im Janet and I love Load.In and Grinch Green', 'Using Load.In Green is how you secure an A in CS411W', NULL, 'https://loadinvideohost.s3.amazonaws.com/janetgrinch.mp4', 'THIS TIP SUCKS', NOW(), NOW());

INSERT INTO USER_FEEDBACK( ID, USER_ID, ACCOUNT_CREATION_COMMENT, ACCOUNT_CREATION_RATING, ITEM_INPUT_COMMENT, ITEM_INPUT_RATING, LOAD_PLAN_COMMENT, LOAD_PLAN_RATING, EXPERT_TIPS_COMMENT, EXPERT_TIPS_RATING, OVERALL_EXPERIENCE_COMMENT, OVERALL_EXPERIENCE_RATING, CREATED_AT, UPDATED_AT) VALUES (1,1,'LOVE IT',5,'LOVE IT',5,'LOVE IT',5,'LOVE IT',5,'LOVE IT',5,NOW(),NOW());

INSERT INTO LOAD_PLAN_BOX(ID, X_OFFSET, Y_OFFSET, Z_OFFSET, BOX_STEP, LOAD_NUMBER)
VALUES (1, 23, 23, 23, 1, 1);
