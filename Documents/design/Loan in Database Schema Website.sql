-- website used https://dbdiagram.io/
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
 Table user_inventory_items {
  id int [pk]
  move_plan_id int [pk]
  loaded_boxes_id int [pk]
  weight double
  fragility varchar
  measurements varchar
  box_dimenstions varchar
 }
 Table location {
   id int [pk]
   user_id int [pk]
   start_location varchar
   end_location varchar
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
  expert_tips_id int [pk]
  rental_company_id int [pk]
  move_plan_id int [pk]
  rating boolean
  description varchar
  feedback_responds varchar
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
  rental_company_id int [pk]
  description varchar
  dimensions varchar
  availability boolean
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
 Table heat_map_event{
   id int [pk]
   heat_map_event_type_id int [pk]
  screen_type_id varchar [pk]
  action_name varchar
  started_on timestamp
  ended_on timestamp
 }
 Table expert_tips{
   id int [pk]
   description varchar
   images varchar
   video varchar
   comments varchar
   created_at timestamp
   updated_at timestamp
 }
  Table move_plan{
   id int [pk]
   user_id int [pk]
   movers_id int [pk]
   created_at timestamp
   updated_at timestamp
 }
 Table movers{
  id int [pk]
  user_id int [pk]
  created_at timestamp
  updated_at timestamp
 }
 Table rental_companys {
   id int [pk]
   name varchar
   created_at timestamp
   updated_at timestamp
 }
 Table loaded_boxes {
   id int [pk]
   description varchar
   qr_code varchar
   dimensions varchar
   created_at timestamp
   updated_at timestamp
 }
  Table post_help {
   id int [pk]
   description varchar
   question varchar
   awswer varchar
   created_at timestamp
   updated_at timestamp
 }
  Table log {
   id int [pk]
   user_id int [pk]
   heat_map_event_id int [pk]
   log_time timestamp
 }
 Table event_type{
   id int [pk]
   value varchar
 }
 Table log_detail{
   id int [pk]
   log_id int [pk]
   event_type_id int [pk]
   value varchar
 }
 Table heat_map_event_type{
   id int [pk]
   description varchar
 }
 Table screen_type{
   id int [pk]
   description varchar

 }
// Creating references
// You can also define relaionship separately
// > many-to-one; < one-to-many; - one-to-one
//----------------------------------------------//
//// -- Level 3
//// -- Enum, Indexes
Ref: "user_roles"."user_id" < "users"."id"
Ref: "user_roles"."role_id" > "roles"."id"
Ref: "users"."id" > "user_permissions"."user_id"
Ref: "user_permissions"."permission_id" > "permission"."id"
Ref: "roles"."id" > "role_permissions"."role_id"
Ref: "permission"."id" > "role_permissions"."permission_id"
Ref: "users"."id" > "feedback"."user_id"
Ref: "location"."user_id" < "users"."id"
Ref: "user_inventory_items"."move_plan_id" < "move_plan"."id"
Ref: "move_plan"."user_id" < "users"."id"
Ref: "movers"."user_id" < "users"."id"
Ref: "move_plan"."movers_id" < "movers"."id"
Ref: "feedback"."expert_tips_id" > "expert_tips"."id"
Ref: "rental_companys"."id" < "feedback"."rental_company_id"
Ref: "rental_companys"."id" > "truck_sizes"."rental_company_id"
Ref: "loaded_boxes"."id" < "user_inventory_items"."loaded_boxes_id"
Ref: "log"."user_id" < "users"."id"
Ref: "log_detail"."log_id" < "log"."id"
Ref: "heat_map_event_type"."id" < "heat_map_event"."heat_map_event_type_id"
Ref: "event_type"."id" < "log_detail"."event_type_id"
Ref: "heat_map_event"."id" < "log"."heat_map_event_id"
Ref: "screen_type"."id" < "heat_map_event"."screen_type_id"
Ref: "feedback"."move_plan_id" < "move_plan"."id"