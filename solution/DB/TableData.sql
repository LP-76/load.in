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
VALUES (1, 1, 3, 3, 3, 'Mothers Clothes', '2', 234.5, NOW(), NOW(), 'At Source', 'Bedroom', 'thong, leggings, sweatshirt'),
       (1, 1, 48, 48, 60, 'Fridge', '2', 10, NOW(), NOW(), 'At Source', 'kitchen', 'various food items');

INSERT INTO EXPERT_TIP (KEYWORD, TITLE, CONTENT, IMAGE, VIDEO, COMMENTS, CREATED_AT, UPDATED_AT)
VALUES
('Heavy item','Use small boxes for heavy items.', 'LIFT WITH YOUR BACK!', NULL, 'https://loadinvideohost.s3.amazonaws.com/heavy.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Eric','LET ERIC IN', 'HE NEEDS TO SCREAMMMMMMMMMMM', NULL,'https://loadinvideohost.s3.amazonaws.com/eric.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Star Wars','May the force be with you', 'I have brought peace and freedom to my new empire', NULL, 'https://loadinvideohost.s3.amazonaws.com/starwars.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Janet','Check to see if you have original boxes for your electronics', 'Check to see if you stashed these boxes somewhere — attic? Garage? If you don’t have them, make a list of what you’ll need to buy or borrow to properly cushion your stuff.', NULL, 'https://loadinvideohost.s3.amazonaws.com/janet.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Grinch','Im Janet and I love Load.In and Grinch Green', 'Using Load.In Green is how you secure an A in CS411W', NULL, 'https://loadinvideohost.s3.amazonaws.com/grinchEditted.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Pack a Box', 'How to pack a box', 'Ensure that all heavy items are at the bottom of the box. Make sure that you do not overload the box.', NULL, 'https://loadinvideohost.s3.amazonaws.com/prep_box.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Fine China', 'How to pack fine china' , 'Ensure that the fine china is in a box with proper amount of packing material. Make sure that you do not overload the box.', NULL, 'https://loadinvideohost.s3.amazonaws.com/fine_china.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Heavy', 'How to lift a heavy item', 'Ensure that you lift with your back. Make sure that you do not overload the box.', NULL, 'https://loadinvideohost.s3.amazonaws.com/heavy_real.mp4', 'THIS TIP SUCKS', NOW(), NOW());



INSERT INTO USER_FEEDBACK( ID, USER_ID, ACCOUNT_CREATION_COMMENT, ACCOUNT_CREATION_RATING, ITEM_INPUT_COMMENT, ITEM_INPUT_RATING, LOAD_PLAN_COMMENT, LOAD_PLAN_RATING, EXPERT_TIPS_COMMENT, EXPERT_TIPS_RATING, OVERALL_EXPERIENCE_COMMENT, OVERALL_EXPERIENCE_RATING, CREATED_AT, UPDATED_AT) VALUES (1,1,'LOVE IT',5,'LOVE IT',5,'LOVE IT',5,'LOVE IT',5,'LOVE IT',5,NOW(),NOW());


INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('UHAUL','10ft Truck',76,74,119,12,19.95,0.89);
INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('UHAUL','15ft Truck',92,86,180,10,29.95,0.89);
INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('UHAUL','17ft Truck',92,86,201,10,39.95,0.89);
INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('UHAUL','20ft Truck',92,86,234,8,49.95,0.89);
INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('UHAUL','26ft Truck',98,99,314,8,59.95,0.89);
INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('Budget','12ft Truck',75,72,120,12,39.99,0.89);
INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('Budget','16ft Truck',75,79,192,10,49.99,0.89);
INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('Budget','26ft Truck',97,97,312,8,39.99,0.89);
INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('Penske','12ft Truck',78,73,144,12,69.99,1.29);
INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('Penske','16ft Truck',91,78,192,10,79.99,1.29);
INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('Penske','22ft Truck',97,97,263,8,99.99,1.29);
INSERT INTO TRUCK(COMPANY_NAME, TRUCK_NAME, TRUCK_WIDTH, TRUCK_HEIGHT, TRUCK_LENGTH, MILES_PER_GALLON, BASE_RENTAL_COST, COST_PER_MILE) VALUES ('Penske','26ft Truck',97,97,311,8,149.99,1.29);


INSERT INTO LOAD_PLAN_BOX(ID, X_OFFSET, Y_OFFSET, Z_OFFSET, BOX_STEP, LOAD_NUMBER, TRUCK_ID)
VALUES (1, 23, 23, 23, 1, 1, 1);







