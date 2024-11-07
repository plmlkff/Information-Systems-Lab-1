create table album
(
    id    serial primary key,
    name  varchar(255) not null,
    sales integer check (sales >= 1)
);
create table coordinates
(
    id serial primary key,
    x  integer not null check (x >= -144),
    y  integer check (y <= 675)
);
create table music_band
(
    id                     serial primary key,
    albums_count           integer      not null check (albums_count >= 1),
    creation_date          date         not null,
    description            varchar(255) not null,
    establishment_date     timestamp(6) not null,
    genre                  varchar(255) not null,
    name                   varchar(255) not null,
    number_of_participants integer      not null,
    singles_count          integer      not null,
    album_id               integer      not null,
    coordinates_id         integer      not null,
    owner_login            varchar(255)
);
create table musicband_to_studio
(
    musicband_id integer not null,
    studio_id    integer not null
);
create table studio
(
    id      serial primary key,
    address varchar(255) not null unique
);
create table person
(
    login       varchar(255) primary key,
    is_approved boolean,
    password    varchar(255) not null,
    role        varchar(255)
);
alter table if exists music_band add constraint music_band_album_id_fk foreign key (album_id) references album;
alter table if exists music_band add constraint music_band_coordinates_id_fk foreign key (coordinates_id) references coordinates;
alter table if exists music_band add constraint music_band_owner_login_fk foreign key (owner_login) references person;
alter table if exists musicband_to_studio add constraint musicband_to_studio_studio_id_fk foreign key (studio_id) references studio;
alter table if exists musicband_to_studio add constraint musicband_to_studio_musicband_id_fk foreign key (musicband_id) references music_band;