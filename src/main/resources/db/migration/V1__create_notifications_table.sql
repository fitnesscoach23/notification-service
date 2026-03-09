create extension if not exists "pgcrypto";

create table notifications (
    id uuid primary key default gen_random_uuid(),
    member_id uuid not null,
    coach_email varchar(255) not null,
    channel varchar(20) not null,
    notification_type varchar(40) not null,
    recipient varchar(255) not null,
    subject varchar(255),
    message text not null,
    status varchar(20) not null,
    error_message text,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone
);

create index idx_notifications_coach_email on notifications(coach_email);
create index idx_notifications_member_id on notifications(member_id);
create index idx_notifications_channel on notifications(channel);
create index idx_notifications_status on notifications(status);
