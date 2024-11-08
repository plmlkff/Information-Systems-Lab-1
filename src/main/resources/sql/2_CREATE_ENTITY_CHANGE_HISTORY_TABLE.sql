create table entity_change_history
(
    id            bigserial    primary key ,
    music_band_id integer      not null,
    operation     varchar(255) not null,
    time          timestamp(6) not null,
    login         varchar(255) not null
);
alter table if exists entity_change_history add constraint entity_change_history_person_fk foreign key (login) references person;