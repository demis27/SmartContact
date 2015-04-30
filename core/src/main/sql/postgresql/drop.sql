drop table if exists "SmartContact".application_user cascade;
drop table if exists "SmartContact".user_group cascade;
drop table if exists "SmartContact".user_group_label cascade;
drop table if exists "SmartContact".incoming_transport cascade;
drop table if exists "SmartContact".incoming_email cascade;

drop sequence if exists "SmartContact".application_user_sequence cascade;
drop sequence if exists "SmartContact".user_group_sequence cascade;
drop sequence if exists "SmartContact".user_group_label_sequence cascade;
drop sequence if exists "SmartContact".incoming_transport_sequence cascade;
drop sequence if exists "SmartContact".incoming_email_sequence cascade;
