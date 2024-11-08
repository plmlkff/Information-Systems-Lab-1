package ru.itmo.is_lab1.domain.filter;

public enum TableColumn {
    ID("id"),

    MUSIC_BAND_NAME("name"),
    MUSIC_BAND_COORDINATE_X("x"),
    MUSIC_BAND_COORDINATE_Y("y"),
    MUSIC_BAND_CREATION_DATE("creationDate"),
    MUSIC_BAND_MUSIC_GENRE("genre"),
    MUSIC_BAND_NUMBER_OF_PARTICIPANTS("numberOfParticipants"),
    MUSIC_BAND_SINGLES_COUNT("singlesCount"),
    MUSIC_BAND_DESCRIPTION("description"),
    MUSIC_BAND_BEST_ALBUM_NAME("name"),
    MUSIC_BAND_BEST_ALBUM_SALES("sales"),
    MUSIC_BAND_ALBUMS_COUNT("albumsCount"),
    MUSIC_BAND_ESTABLISHMENT_DATE("establishmentDate"),
    MUSIC_BAND_STUDIO_ADDRESS("address"),
    MUSIC_BAND_USER_LOGIN("login"),

    COORDINATE_X("x"),
    COORDINATE_Y("y"),

    BEST_ALBUM_NAME("name"),
    BEST_ALBUM_SALES("sales"),

    STUDIO_ADDRESS("address"),

    USER_LOGIN("login"),
    USER_ROLE("role"),
    USER_IS_APPROVED("isApproved");

    private final String attributeName;

    TableColumn(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public String toString() {
        return attributeName;
    }
}
