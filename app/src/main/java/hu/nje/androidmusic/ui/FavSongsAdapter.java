package hu.nje.androidmusic.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.nje.androidmusic.R;
import hu.nje.androidmusic.database.FavoriteSong;

public class FavSongsAdapter extends RecyclerView.Adapter<FavSongsAdapter.FavViewHolder> {

    private List<FavoriteSong> songs;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(FavoriteSong song);
    }

    public FavSongsAdapter(List<FavoriteSong> songs, OnItemClickListener listener) {
        this.songs = songs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        holder.bind(songs.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    static class FavViewHolder extends RecyclerView.ViewHolder {
        TextView title, artist;
        FavViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            artist = itemView.findViewById(R.id.textArtist);
        }

        void bind(FavoriteSong song, OnItemClickListener listener) {
            title.setText(song.title);
            artist.setText(song.artist);

            itemView.setOnClickListener(v -> listener.onClick(song));
        }
    }
}