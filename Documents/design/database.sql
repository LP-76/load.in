//// -- website used https://dbdiagram.io/d/5fa6d7203a78976d7b7aee30
//// -- LEVEL 1
//// -- Tables and References

// Creating tables
Table users as U {
  id int [pk, increment] // auto-increment
  first_name varchar
  last_name varchar
  email varchar
  phone_number varchar
  avatar varchar
  password varchar
  notes text
  address varchar
  city varchar
  state varchar
  zip varchar
  activated boolean
  activated_at timestamp
  last_login timestamp
  created_at timestamp
  deleted_at timestamp
  updated_at timestamp
}

Table user_roles {
  user_id int [pk]
  role_id int [pk]
  user_type varchar
 }

 Table user_inventory {
  user_id int [pk]
  weight double
  fragility varchar
  measurements varchar
  box_dimenstions varchar
 }

  Table analytics {
  user_id int [pk]
  heat_map_id int [pk]
  location_id int [pk]
 }

 Table location {
   id int [pk]
   user_id int [pk]
   start timestamp
   end timestamp
   distance_traveled int
 }

Table roles {
  id int [pk]
  name varchar
  display_name varchar
  description varchar
  created_at timestamp
  updated_at timestamp
 }

  Table permission {
  id int [pk]
  name varchar
  description varchar
  created_at timestamp
  updated_at timestamp
 }

 Table user_permissions {
  user_id int [pk]
  permission_id int [pk]
  user_type varchar
 }

  Table role_permissions {
  role_id int [pk]
  permission_id int [pk]
 }

  Table feedback {
  id int [pk]
  user_id int [pk]
  rating int
  description varchar
  created_at timestamp
  updated_at timestamp
 }

  Table box_sizes {
  id int [pk]
  description varchar
  dimensions varchar
  created_at timestamp
  updated_at timestamp
 }

  Table truck_sizes {
  id int [pk]
  description varchar
  dimensions varchar
  created_at timestamp
  updated_at timestamp
 }

  Table furniture_sizes {
  id int [pk]
  description varchar
  dimensions varchar
  created_at timestamp
  updated_at timestamp
 }

  Table heat_map {
  id int [pk]
  home_screen_id int [pk]
  profile_screen_id int [pk]
 }

 Table home_screen{
   id int [pk]
   image_capture int
   help_button int
 }

 Table profile_screen{
   id int [pk]
   setting int
 }

 Table expert_tips{
   id int [pk]
   moving_stage varchar
   description varchar
 }
// Creating references
// You can also define relaionship separately
// > many-to-one; < one-to-many; - one-to-one

//----------------------------------------------//

//// -- Level 3
//// -- Enum, Indexes

Ref: "user_roles"."user_id" < "users"."id"

Ref: "user_roles"."role_id" < "roles"."id"

Ref: "user_inventory"."user_id" < "users"."id"

Ref: "users"."id" < "user_permissions"."user_id"

Ref: "user_permissions"."permission_id" < "permission"."id"

Ref: "roles"."id" < "role_permissions"."role_id"

Ref: "permission"."id" < "role_permissions"."permission_id"

Ref: "users"."id" < "feedback"."user_id"

Ref: "analytics"."heat_map_id" < "heat_map"."id"

Ref: "analytics"."user_id" < "users"."id"

Ref: "home_screen"."id" < "heat_map"."home_screen_id"

Ref: "heat_map"."profile_screen_id" < "profile_screen"."id"

Ref: "location"."user_id" < "users"."id"

Ref: "analytics"."location_id" < "location"."id"