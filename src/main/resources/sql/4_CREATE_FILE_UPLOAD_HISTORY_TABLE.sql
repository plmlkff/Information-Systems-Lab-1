create table file_upload_history
(
    id            bigserial primary key,
    login         varchar(255) not null,
    state         varchar(255) not null,
    count         integer not null,
    time          timestamp(6) not null
);
alter table if exists file_upload_history add constraint file_upload_history_person_fk foreign key (login) references person;