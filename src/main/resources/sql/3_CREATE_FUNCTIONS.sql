CREATE OR REPLACE FUNCTION calculate_total_albums_count()
    RETURNS integer AS $$
DECLARE
    total_albums_count integer;
BEGIN
    SELECT SUM(albums_count) INTO total_albums_count
    FROM music_band;
    RETURN total_albums_count;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION count_albums_greater_than(threshold integer)
    RETURNS integer AS $$
DECLARE
    count_above_threshold integer;
BEGIN
    SELECT COUNT(*)
    INTO count_above_threshold
    FROM music_band
    WHERE albums_count > threshold;
    RETURN count_above_threshold;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION increase_participants(musicband_id INT)
    RETURNS VOID AS $$
BEGIN
    UPDATE music_band
    SET number_of_participants = number_of_participants + 1
    WHERE id = musicband_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION delete_musicband(musicband_id INT)
    RETURNS VOID AS $$
BEGIN
    DELETE FROM music_band
    WHERE id = musicband_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION count_music_bands_by_genre(genre_name VARCHAR)
    RETURNS INT AS $$
BEGIN
    RETURN (SELECT COUNT(*) FROM music_band WHERE genre = genre_name);
END;
$$ LANGUAGE plpgsql;

select increase_participants(5);
