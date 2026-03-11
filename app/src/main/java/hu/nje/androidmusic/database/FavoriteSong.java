package hu.nje.androidmusic.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteSong {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String trackId;
    public String title;
    public String artist;
    public String audioUrl;

    public FavoriteSong(String trackId, String title, String artist, String audioUrl) {
        this.trackId = trackId;
        this.title = title;
        this.artist = artist;
        this.audioUrl = audioUrl;
    }
}