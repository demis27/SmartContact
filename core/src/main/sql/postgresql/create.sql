create sequence "SmartContact".user_group_sequence;
create table "SmartContact".user_group (
    user_group_id       integer default nextval ('"SmartContact".user_group_sequence') not null,
    creation_date       timestamp without time zone default current_date not null,
    modification_date   timestamp without time zone default current_date not null,
    "name"              character varying(64),
    constraint user_group_pk primary key (user_group_id)
);

create sequence "SmartContact".user_group_label_sequence;
create table "SmartContact".user_group_label (
    user_group_label_id integer default nextval ('"SmartContact".user_group_label_sequence') not null,
    creation_date       timestamp without time zone default current_date not null,
    modification_date   timestamp without time zone default current_date not null,
    user_group_id       integer not null,
    label               character varying(64) not null,
    locale              character varying(5) not null,
    constraint user_group_label_pk primary key (user_group_label_id),
    constraint user_group_fk foreign key (user_group_id)
        references "SmartContact".user_group (user_group_id) match simple
        on update no action on delete no action
);

create sequence "SmartContact".application_user_sequence;
create table "SmartContact".application_user (
    user_id             integer default nextval ('"SmartContact".application_user_sequence') not null,
    creation_date       timestamp without time zone default current_date not null,
    modification_date   timestamp without time zone default current_date not null,
    email               character varying(255),
    first_name          character varying(64),
    last_name           character varying(64),
    login               character varying(64) not null,
    password            character varying(64) not null,
    user_group_id       integer not null,
    constraint user_pk primary key (user_id),
    constraint unique_login unique (login),
    constraint user_group_fk foreign key (user_group_id)
        references "SmartContact".user_group (user_group_id) match simple
        on update no action on delete no action
);

create sequence "SmartContact".incoming_transport_sequence;
create table "SmartContact".incoming_transport (
    incoming_transport_id   integer default nextval ('"SmartContact".incoming_transport_sequence') not null,
    creation_date           timestamp without time zone default current_date not null,
    modification_date       timestamp without time zone default current_date not null,
    "name"                  character varying(64) unique,
    constraint incoming_transport_pk primary key (incoming_transport_id)
);

create sequence "SmartContact".incoming_email_sequence;
create table "SmartContact".incoming_email (
    incoming_email_id       integer default nextval ('"SmartContact".incoming_email_sequence') not null,
    creation_date           timestamp without time zone default current_date not null,
    modification_date       timestamp without time zone default current_date not null,
    email_size              integer not null,
    incoming_transport_id   integer not null,
    binary_content          text not null,
    constraint incoming_email_pk primary key (incoming_email_id),
    constraint incoming_email_transport_fk foreign key (incoming_transport_id)
        references "SmartContact".incoming_transport (incoming_transport_id) match simple
        on update no action on delete no action
);
