package hu.nje.androidmusic.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteSongDao {

    @Insert
    void insert(FavoriteSong song);

    @Delete
    void delete(FavoriteSong song);

    @Query("SELECT * FROM favorites")
    List<FavoriteSong> getAllFavorites();

    @Query("SELECT * FROM favorites WHERE trackId = :trackId LIMIT 1")
    FavoriteSong findByTrackId(String trackId);
}