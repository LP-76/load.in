CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `first_name` varchar(255),
  `last_name` varchar(255),
  `email` varchar(255),
  `phone_number` varchar(255),
  `avatar` varchar(255),
  `password` varchar(255),
  `notes` text,
  `address` varchar(255),
  `city` varchar(255),
  `state` varchar(255),
  `zip` varchar(255),
  `activated` boolean,
  `activated_at` timestamp,
  `last_login` timestamp,
  `created_at` timestamp,
  `deleted_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `user_roles` (
  `user_id` int,
  `role_id` int,
  `user_type` varchar(255),
  PRIMARY KEY (`user_id`, `role_id`)
);

CREATE TABLE `user_inventory_items` (
  `id` int,
  `move_plan_id` int,
  `loaded_boxes_id` int,
  `weight` double,
  `fragility` varchar(255),
  `measurements` varchar(255),
  `box_dimenstions` varchar(255),
  PRIMARY KEY (`id`, `move_plan_id`, `loaded_boxes_id`)
);

CREATE TABLE `location` (
  `id` int,
  `user_id` int,
  `start_location` varchar(255),
  `end_location` varchar(255),
  `start` timestamp,
  `end` timestamp,
  `distance_traveled` int,
  PRIMARY KEY (`id`, `user_id`)
);

CREATE TABLE `roles` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `display_name` varchar(255),
  `description` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `permission` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `description` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `user_permissions` (
  `user_id` int,
  `permission_id` int,
  `user_type` varchar(255),
  PRIMARY KEY (`user_id`, `permission_id`)
);

CREATE TABLE `role_permissions` (
  `role_id` int,
  `permission_id` int,
  PRIMARY KEY (`role_id`, `permission_id`)
);

CREATE TABLE `feedback` (
  `id` int,
  `user_id` int,
  `expert_tips_id` int,
  `rental_company_id` int,
  `move_plan_id` int,
  `rating` boolean,
  `description` varchar(255),
  `feedback_responds` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp,
  PRIMARY KEY (`id`, `user_id`, `expert_tips_id`, `rental_company_id`, `move_plan_id`)
);

CREATE TABLE `box_sizes` (
  `id` int PRIMARY KEY,
  `description` varchar(255),
  `dimensions` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `truck_sizes` (
  `id` int,
  `rental_company_id` int,
  `description` varchar(255),
  `dimensions` varchar(255),
  `availability` boolean,
  `created_at` timestamp,
  `updated_at` timestamp,
  PRIMARY KEY (`id`, `rental_company_id`)
);

CREATE TABLE `furniture_sizes` (
  `id` int PRIMARY KEY,
  `description` varchar(255),
  `dimensions` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `heat_map_event` (
  `id` int,
  `heat_map_event_type_id` int,
  `screen_type_id` varchar(255),
  `action_name` varchar(255),
  `started_on` timestamp,
  `ended_on` timestamp,
  PRIMARY KEY (`id`, `heat_map_event_type_id`, `screen_type_id`)
);

CREATE TABLE `expert_tips` (
  `id` int PRIMARY KEY,
  `description` varchar(255),
  `images` varchar(255),
  `video` varchar(255),
  `comments` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `move_plan` (
  `id` int,
  `user_id` int,
  `movers_id` int,
  `created_at` timestamp,
  `updated_at` timestamp,
  PRIMARY KEY (`id`, `user_id`, `movers_id`)
);

CREATE TABLE `movers` (
  `id` int,
  `user_id` int,
  `created_at` timestamp,
  `updated_at` timestamp,
  PRIMARY KEY (`id`, `user_id`)
);

CREATE TABLE `rental_companys` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `loaded_boxes` (
  `id` int PRIMARY KEY,
  `description` varchar(255),
  `qr_code` varchar(255),
  `dimensions` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `post_help` (
  `id` int PRIMARY KEY,
  `description` varchar(255),
  `question` varchar(255),
  `awswer` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `log` (
  `id` int,
  `user_id` int,
  `heat_map_event_id` int,
  `log_time` timestamp,
  PRIMARY KEY (`id`, `user_id`, `heat_map_event_id`)
);

CREATE TABLE `event_type` (
  `id` int PRIMARY KEY,
  `value` varchar(255)
);

CREATE TABLE `log_detail` (
  `id` int,
  `log_id` int,
  `event_type_id` int,
  `value` varchar(255),
  PRIMARY KEY (`id`, `log_id`, `event_type_id`)
);

CREATE TABLE `heat_map_event_type` (
  `id` int PRIMARY KEY,
  `description` varchar(255)
);

CREATE TABLE `screen_type` (
  `id` int PRIMARY KEY,
  `description` varchar(255)
);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `user_roles` (`user_id`);

ALTER TABLE `user_roles` ADD FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `user_permissions` (`user_id`);

ALTER TABLE `user_permissions` ADD FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`);

ALTER TABLE `roles` ADD FOREIGN KEY (`id`) REFERENCES `role_permissions` (`role_id`);

ALTER TABLE `permission` ADD FOREIGN KEY (`id`) REFERENCES `role_permissions` (`permission_id`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `feedback` (`user_id`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `location` (`user_id`);

ALTER TABLE `move_plan` ADD FOREIGN KEY (`id`) REFERENCES `user_inventory_items` (`move_plan_id`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `move_plan` (`user_id`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `movers` (`user_id`);

ALTER TABLE `movers` ADD FOREIGN KEY (`id`) REFERENCES `move_plan` (`movers_id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`expert_tips_id`) REFERENCES `expert_tips` (`id`);

ALTER TABLE `feedback` ADD FOREIGN KEY (`rental_company_id`) REFERENCES `rental_companys` (`id`);

ALTER TABLE `rental_companys` ADD FOREIGN KEY (`id`) REFERENCES `truck_sizes` (`rental_company_id`);

ALTER TABLE `user_inventory_items` ADD FOREIGN KEY (`loaded_boxes_id`) REFERENCES `loaded_boxes` (`id`);

ALTER TABLE `users` ADD FOREIGN KEY (`id`) REFERENCES `log` (`user_id`);

ALTER TABLE `log` ADD FOREIGN KEY (`id`) REFERENCES `log_detail` (`log_id`);

ALTER TABLE `heat_map_event` ADD FOREIGN KEY (`heat_map_event_type_id`) REFERENCES `heat_map_event_type` (`id`);

ALTER TABLE `log_detail` ADD FOREIGN KEY (`event_type_id`) REFERENCES `event_type` (`id`);

ALTER TABLE `log` ADD FOREIGN KEY (`heat_map_event_id`) REFERENCES `heat_map_event` (`id`);

ALTER TABLE `heat_map_event` ADD FOREIGN KEY (`screen_type_id`) REFERENCES `screen_type` (`id`);

ALTER TABLE `move_plan` ADD FOREIGN KEY (`id`) REFERENCES `feedback` (`move_plan_id`);

