drop table if exists "SmartContact".application_user cascade;
drop table if exists "SmartContact".user_group cascade;
drop table if exists "SmartContact".user_group_label cascade;

--drop table if exists ht_application_user cascade;
--drop table if exists ht_user_group cascade;
--drop table if exists ht_user_group_label cascade;

drop sequence if exists "SmartContact".application_user_sequence cascade;
drop sequence if exists "SmartContact".user_group_sequence cascade;
drop sequence if exists "SmartContact".user_group_label_sequence cascade;