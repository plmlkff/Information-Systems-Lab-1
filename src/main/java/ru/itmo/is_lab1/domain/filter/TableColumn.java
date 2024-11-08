package ru.itmo.is_lab1.domain.filter;

public enum TableColumn {
    ID("id"),
    MUSIC_BAND_NAME("name"),
    COORDINATE_X("x"),
    COORDINATE_Y("y"),
    CREATION_DATE("creationDate"),
    MUSIC_GENRE("genre"),
    NUMBER_OF_PARTICIPANTS("numberOfParticipants"),
    SINGLES_COUNT("singlesCount"),
    DESCRIPTION("description"),
    BEST_ALBUM_NAME("name"),
    BEST_ALBUM_SALES("sales"),
    ALBUMS_COUNT("albumsCount"),
    ESTABLISHMENT_DATE("establishmentDate"),
    STUDIO_ADDRESS("address"),
    USER_LOGIN("login");
    private final String attributeName;

    TableColumn(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public String toString() {
        return attributeName;
    }
}
