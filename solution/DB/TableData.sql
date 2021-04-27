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
VALUES (2, 1, 18, 18, 16, 'Moms Winter Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'leggings, sweatshirts'),
              (2, 2, 18, 18, 16, 'Moms Summer Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'tshirts, shorts'),
              (2, 3, 18, 18, 16, 'Moms Other Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'other clothes'),
              (2, 4, 18, 18, 16, 'Moms Everyday Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'jeans, socks'),
              (2, 5, 24, 24, 24, 'Parents Bedding', '1', 2, NOW(), NOW(), 'At Source', 'parents bedroom', 'sheets, pillow cases, comforter'),
              (2, 6, 24, 24, 24, 'Parents Pillows', '1', 1, NOW(), NOW(),'At Source', 'parents bedroom', 'pillows'),
              (2, 7, 60, 20, 34, 'Parents Dresser', '1', 8, NOW(), NOW(), 'At Source', 'parents bedroom', 'clothing dresser'),
              (2, 8, 11, 80, 70, 'Parents Mattress', '1', 8, NOW(), NOW(), 'At Source', 'parents bedroom', 'king mattress'),
              (2, 9, 18, 18, 24, 'Parents Desk Lamp', '5', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'lamp base, lamp shade'),
              (2, 10, 45, 4, 24, 'Bedroom TV', '5', 3, NOW(), NOW(), 'At Source', 'parents bedroom', 'flatscreen TV'),
              (2, 11, 18, 18, 16, 'Dads Winter Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'khaki pants, sweatshirts'),
              (2, 12, 18, 18, 16, 'Dads Summer Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'tshirts, basketball shorts'),
              (2, 13, 18, 18, 16, 'Dads Other Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'other clothes'),
              (2, 14, 18, 18, 16, 'Dads Everyday Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'jeans, socks'),

              (2, 15, 18, 18, 16, 'Sallys Winter Clothes', '1', 4, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'leggings, sweatshirts'),
              (2, 16, 18, 18, 16, 'Sallys Summer Clothes', '1', 4, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'tshirts, basketball shorts, short shorts'),
              (2, 17, 18, 18, 16, 'Sallys Other Clothes', '1', 4, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'other clothes'),
              (2, 18, 18, 18, 16, 'Sallys Everyday Clothes', '1', 4, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'jeans, socks'),
              (2, 19, 24, 24, 24, 'Sallys Bedding', '1', 2, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'sheets, pillow cases, comforter'),
              (2, 20, 24, 24, 24, 'Sallys Pillows', '1', 1, NOW(), NOW(),'At Source', 'Sallys bedroom', 'pillows'),
              (2, 21, 60, 20, 34, 'Sallys Dresser', '1', 8, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'clothing dresser'),
              (2, 22, 11, 75, 50, 'Sallys Mattress', '1', 7, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'king mattress'),
              (2, 23, 18, 18, 24, 'Sallys Desk Lamp', '5', 4, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'lamp base, lamp shade'),

              (2, 24, 36, 30, 70, 'Refrigerator', '1', 10, NOW(), NOW(), 'At Source', 'kitchen', 'empty fridge'),
              (2, 25, 18, 18, 24, 'Kitchen Cleaning Supplies', '3', 3, NOW(), NOW(), 'At Source', 'kitchen', 'soap, sponges, dustpan'),
              (2, 26, 18, 18, 24, 'Kitchen Towels', '1', 2, NOW(), NOW(), 'At Source', 'kitchen', 'paper towels, napkins, kitchen towels'),
              (2, 27, 24, 18, 24, 'Kitchen Pans', '3', 7, NOW(), NOW(), 'At Source', 'kitchen', '8 pans, 5 lids'),
              (2, 28, 24, 18, 24, 'Kitchen Pots', '3', 7, NOW(), NOW(), 'At Source', 'kitchen', '3 pots, 2 lids'),
              (2, 29, 16, 12, 12, 'Cooking Utensils', '2', 5, NOW(), NOW(), 'At Source', 'kitchen', '8 spatulas, 2 whisks, 5 wood spoons'),
              (2, 30, 16, 12, 12, 'Cooking Utensils', '2', 5, NOW(), NOW(), 'At Source', 'kitchen', 'rubber spatulas, ice cream scooper, ladle'),
              (2, 31, 18, 18, 24, 'Fine China', '5', 6, NOW(), NOW(), 'At Source', 'kitchen', 'plates, cups'),
              (2, 32, 18, 18, 24, 'Dishes', '4', 6, NOW(), NOW(), 'At Source', 'kitchen', 'plates, glasses'),
              (2, 33, 18, 18, 24, 'Dishes', '4', 6, NOW(), NOW(), 'At Source', 'kitchen', 'bowls, plates'),
              (2, 34, 24, 18, 24, 'Casserole Dishes', '3', 7, NOW(), NOW(), 'At Source', 'kitchen', '3 casserole dishes, 2 cookie sheets'),
              (2, 35, 24, 18, 24, 'Casserole Dishes', '3', 7, NOW(), NOW(), 'At Source', 'kitchen', '3 casserole dishes, 2 cookie sheets'),
              (2, 36, 24, 18, 24, 'Stand Mixer', '2', 5, NOW(), NOW(), 'At Source', 'kitchen', 'stand mixer'),
              (2, 37, 24, 18, 24, 'Coffee Machine', '4', 5, NOW(), NOW(), 'At Source', 'kitchen', 'coffee machine, coffee filters, coffee'),
              (2, 38, 24, 18, 24, 'Silverware', '1', 5, NOW(), NOW(), 'At Source', 'kitchen', 'forks, knives, spoons'),

              (2, 39, 24, 24, 24, 'Guest Room Bedding', '1', 1, NOW(), NOW(), 'At Source', 'guest bedroom', 'sheets, pillow cases, comforter'),
              (2, 40, 24, 24, 24, 'Guest Room Pillows', '1', 1, NOW(), NOW(),'At Source', 'guest bedroom', 'pillows'),
              (2, 41, 60, 20, 34, 'Guest Room Dresser', '1', 8, NOW(), NOW(), 'At Source', 'guest bedroom', 'clothing dresser'),
              (2, 42, 11, 80, 70, 'Guest Room Mattress', '1', 7, NOW(), NOW(), 'At Source', 'guest bedroom', 'king mattress'),
              (2, 43, 18, 18, 24, 'Guest Room Desk Lamp', '5', 4, NOW(), NOW(), 'At Source', 'guest bedroom', 'lamp base, lamp shade'),
              (2, 44, 45, 4, 24, 'Guest Room TV', '5', 5, NOW(), NOW(), 'At Source', 'guest bedroom', 'flatscreen TV'),

              (2, 45, 45, 4, 24, 'Living Room TV', '5', 5, NOW(), NOW(), 'At Source', 'living room', 'flatscreen TV'),
              (2, 46, 24, 24, 24, 'Living Room Pillows', '1', 1, NOW(), NOW(),'At Source', 'living room', 'pillows'),
              (2, 47, 18, 18, 24, 'Blankets', '1', 2, NOW(), NOW(), 'At Source', 'living room', 'blankets'),
              (2, 48, 18, 18, 24, 'Board games', '1', 3, NOW(), NOW(), 'At Source', 'living room', 'monopoly, clue, scrabble'),
              (2, 49, 16, 12, 12, 'Books', '2', 6, NOW(), NOW(), 'At Source', 'living room', 'Harry Potter, Fahrenheit 451'),
              (2, 50, 16, 12, 12, 'More Books', '2', 6, NOW(), NOW(), 'At Source', 'living room', 'Tom Jones, Pride and Prejudice, Mrs Dalloway'),
              (2, 51, 16, 12, 12, 'More Books', '2', 6, NOW(), NOW(), 'At Source', 'living room', 'Great Expectations, Jane Eyre, Bleak House'),

              (2, 52, 16, 12, 12, 'More Books', '2', 6, NOW(), NOW(), 'At Source', 'living room', 'Wuthering Heights, Oryx and Crake, Atonement'),
              (2, 53, 16, 12, 12, 'More Books', '2', 6, NOW(), NOW(), 'At Source', 'living room', 'MaddAddam, The Year of the Flood, Nineteen Eighty-four'),
              (2, 54, 36, 36, 4, 'Dog bed', '1', 3, NOW(), NOW(), 'At Source', 'living room', 'dog bed'),
              (2, 55, 16, 12, 12, 'Dog bowls', '1', 3, NOW(), NOW(), 'At Source', 'living room', 'water bowl, food bowl'),
              (2, 56, 24, 18, 24, 'Dog food', '1', 6, NOW(), NOW(), 'At Source', 'living room', 'dog food'),
              (2, 57, 18, 18, 12, 'Dog toys', '1', 2, NOW(), NOW(), 'At Source', 'living room', 'ducky, leash, Gumby'),

              (2, 58, 16, 12, 12, 'Toiletries', '2', 4, NOW(), NOW(), 'At Source', 'parents bathroom', 'toilet paper, soap, shampoo, toothpaste'),
              (2, 59, 16, 12, 12, 'Toiletries', '2', 4, NOW(), NOW(), 'At Source', 'Sallys bathroom', 'toilet paper, soap, shampoo, toothpaste'),
              (2, 60, 16, 12, 12, 'Toiletries', '2', 4, NOW(), NOW(), 'At Source', 'bathroom', 'toilet paper, soap, bandaids, advil'),
              (2, 61, 16, 12, 12, 'Cleaning Supplies', '5', 2, NOW(), NOW(), 'At Source', 'utility room', 'bleach, windex, trash bags'),
              (2, 62, 16, 12, 12, 'Laundry Supplies', '4', 2, NOW(), NOW(), 'At Source', 'utility room', 'detergent, drier sheets'),

              (2, 63, 16, 12, 12, 'Parents electronics', '4', 2, NOW(), NOW(), 'At Source', 'miscellaneous', 'laptop, chargers, keyboard'),
              (2, 64, 16, 12, 24, 'Sallys electronics', '4', 3, NOW(), NOW(), 'At Source', 'miscellaneous', 'laptop, chargers, speakers'),
              (2, 65, 24, 18, 24, 'Printer', '4', 4, NOW(), NOW(), 'At Source', 'miscellaneous', 'printer, ink'),

              (2, 66, 24, 18, 24, 'Tools', '1', 6, NOW(), NOW(), 'At Source', 'garage', 'ratchets, nails, bolts, nuts'),
              (2, 67, 24, 18, 24, 'Tools', '1', 6, NOW(), NOW(), 'At Source', 'garage', 'wrenches, screws, drill bits, hammer'),
              (2, 68, 24, 18, 24, 'Tools', '1', 7, NOW(), NOW(), 'At Source', 'garage', 'drill, spade, shears'),

              (2, 69, 18, 18, 24, 'Holiday Decorations', '2', 4, NOW(), NOW(), 'At Source', 'miscellaneous', 'Christmas decorations'),
              (2, 70, 18, 18, 24, 'Holiday Decorations', '2', 4, NOW(), NOW(), 'At Source', 'miscellaneous', 'Halloween decorations'),
              (2, 71, 18, 18, 24, 'Odds and ends', '1', 3, NOW(), NOW(), 'At Source', 'miscellaneous', 'towels, slippers, nerf ball'),

              (2, 72, 18, 18, 16, 'Moms Winter Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'leggings, sweatshirts'),
              (2, 73, 18, 18, 16, 'Moms Summer Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'tshirts, shorts'),
              (2, 74, 18, 18, 16, 'Moms Other Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'other clothes'),
              (2, 75, 18, 18, 16, 'Moms Everyday Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'jeans, socks'),

              (2, 76, 18, 18, 16, 'Dads Winter Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'khaki pants, sweatshirts'),
              (2, 77, 18, 18, 16, 'Dads Summer Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'tshirts, basketball shorts'),
              (2, 78, 18, 18, 16, 'Dads Other Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'other clothes'),
              (2, 79, 18, 18, 16, 'Dads Everyday Clothes', '1', 4, NOW(), NOW(), 'At Source', 'parents bedroom', 'jeans, socks'),

              (2, 80, 18, 18, 16, 'Sallys Winter Clothes', '1', 4, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'leggings, sweatshirts'),
              (2, 81, 18, 18, 16, 'Sallys Summer Clothes', '1', 4, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'tshirts, basketball shorts, short shorts'),
              (2, 82, 18, 18, 16, 'Sallys Other Clothes', '1', 4, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'other clothes'),
              (2, 83, 18, 18, 16, 'Sallys Everyday Clothes', '1', 4, NOW(), NOW(), 'At Source', 'Sallys bedroom', 'jeans, socks'),

              (2, 84, 16, 12, 12, 'LEGO', '1', 5, NOW(), NOW(), 'At Source', 'miscellaneous', 'red, blue, yellow lego'),
              (2, 85, 16, 12, 12, 'LEGO', '1', 5, NOW(), NOW(), 'At Source', 'miscellaneous', 'orange, purple, green lego'),
              (2, 86, 16, 12, 12, 'LEGO', '1', 5, NOW(), NOW(), 'At Source', 'miscellaneous', 'black, white, gray lego'),
              (2, 87, 16, 12, 12, 'LEGO', '1', 5, NOW(), NOW(), 'At Source', 'miscellaneous', 'random color lego');








INSERT INTO EXPERT_TIP (KEYWORD, TITLE, CONTENT, IMAGE, VIDEO, COMMENTS, CREATED_AT, UPDATED_AT)
VALUES
('Heavy item','Use small boxes for heavy items.', 'LIFT WITH YOUR BACK!', NULL, 'https://loadinvideohost.s3.amazonaws.com/heavy.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Eric','LET ERIC IN', 'HE NEEDS TO SCREAMMMMMMMMMMM', NULL,'https://loadinvideohost.s3.amazonaws.com/eric.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Star Wars','May the force be with you', 'I have brought peace and freedom to my new empire', NULL, 'https://loadinvideohost.s3.amazonaws.com/starwars.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Janet','Check to see if you have original boxes for your electronics', 'Check to see if you stashed these boxes somewhere — attic? Garage? If you don’t have them, make a list of what you’ll need to buy or borrow to properly cushion your stuff.', NULL, 'https://loadinvideohost.s3.amazonaws.com/janet.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Grinch','Im Janet and I love Load.In and Grinch Green', 'Using Load.In Green is how you secure an A in CS411W', NULL, 'https://loadinvideohost.s3.amazonaws.com/grinchEditted.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Pack a Box', 'How to pack a box', 'Ensure that all heavy items are at the bottom of the box. Make sure that you do not overload the box.', NULL, 'https://loadinvideohost.s3.amazonaws.com/prep_box.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Fine China', 'How to pack fine china' , 'Ensure that the fine china is in a box with proper amount of packing material. Make sure that you do not overload the box.', NULL, 'https://loadinvideohost.s3.amazonaws.com/fine_china.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Heavy', 'How to lift a heavy item', 'Ensure that you lift with your back. Make sure that you do not overload the box.', NULL, 'https://loadinvideohost.s3.amazonaws.com/heavy_real.mp4', 'THIS TIP SUCKS', NOW(), NOW())
     ,('Christmas Ornaments', 'How to pack your christmas ornaments', 'Ensure that you properly package the ornaments. Use spare newspaper and pack the ornaments so that they are at the top of the box.', NULL, 'https://loadinvideohost.s3.amazonaws.com/packornament.mp4', 'LOADINNUMBA1', NOW(), NOW())
     ,('Grinchs heart', 'Peace through all of 411', 'Twas the night before the demo when all through the class, not a student was working, not even Byron.', NULL, 'https://loadinvideohost.s3.amazonaws.com/janetshearteditted.mp4', 'LOADINNUMBA1', NOW(), NOW());



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








