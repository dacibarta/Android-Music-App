package hu.nje.androidmusic.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import hu.nje.androidmusic.database.AppDatabase;
import hu.nje.androidmusic.database.FavoriteSong;
import hu.nje.androidmusic.database.FavoriteSongDao;


import hu.nje.androidmusic.R;

public class DetailViewFragment extends Fragment{

    private ExoPlayer player;
    private String audioUrl;
    private String title;
    private String artist;
    private String image;
    private FavoriteSongDao favoriteDao;
    private FavoriteSong existingFavorite;
    private String trackId;
    private boolean isPlaying = false;

    public DetailViewFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            trackId = getArguments().getString("trackId");
            title = getArguments().getString("title");
            artist = getArguments().getString("artist");
            audioUrl = getArguments().getString("audioUrl");
            image = getArguments().getString("image");
        }

        TextView titleText = view.findViewById(R.id.title_textview);
        TextView artistText = view.findViewById(R.id.artists_textview);
        ImageView coverImage = view.findViewById(R.id.cover_image);

        if (titleText != null) titleText.setText(title);
        if (artistText != null) artistText.setText(artist);
        if (coverImage != null) {
            Glide.with(getContext())
                    .load(image)
                    .into(coverImage);
        };

        player = new ExoPlayer.Builder(requireContext()).build();

        if (audioUrl != null) {
            MediaItem mediaItem = MediaItem.fromUri(audioUrl);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
            isPlaying = true;
        }

        Button playButton = view.findViewById(R.id.play_button);
        playButton.setOnClickListener(v -> {
            if (player == null) return;

            if (player.isPlaying()) {
                player.pause();
                isPlaying = false;
                playButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0, 0, android.R.drawable.ic_media_play, 0
                );
            } else {
                player.play();
                isPlaying = true;
                playButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0, 0, android.R.drawable.ic_media_pause, 0
                );
            }
        });

        favoriteDao = AppDatabase.getInstance(requireContext()).favoriteSongDao();
        existingFavorite = favoriteDao.findByTrackId(trackId);

        Button favoriteButton = view.findViewById(R.id.favorites_button);
        favoriteButton.setOnClickListener(v -> {
            if (existingFavorite == null) {
                FavoriteSong song = new FavoriteSong(trackId, title, artist, audioUrl);
                favoriteDao.insert(song);
                existingFavorite = song;
                Toast.makeText(getContext(), "Hozzáadva a kedvencekhez", Toast.LENGTH_SHORT).show();
            }else {
                favoriteDao.delete(existingFavorite);
                existingFavorite = null;

                Toast.makeText(getContext(), "Eltávolítva a kedvencekből", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
