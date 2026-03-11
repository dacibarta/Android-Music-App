package hu.nje.androidmusic.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.nje.androidmusic.R;
import hu.nje.androidmusic.network.JamendoTrack;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(JamendoTrack track);
    }

    private List<JamendoTrack> tracks;
    private final OnItemClickListener listener;

    public SongsAdapter(List<JamendoTrack> tracks, OnItemClickListener listener) {
        this.tracks = tracks;
        this.listener = listener;
    }

    public void updateData(List<JamendoTrack> newTracks) {
        this.tracks = newTracks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        JamendoTrack track = tracks.get(position);
        holder.bind(track, listener);
    }

    @Override
    public int getItemCount() {
        return tracks == null ? 0 : tracks.size();
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView artistText;

        SongViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.textTitle);
            artistText = itemView.findViewById(R.id.textArtist);
        }

        void bind(final JamendoTrack track, final OnItemClickListener listener) {
            titleText.setText(track.getName());
            artistText.setText(track.getArtist_name());
            itemView.setOnClickListener(v -> listener.onItemClick(track));
        }
    }
}
