package hu.nje.androidmusic.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.nje.androidmusic.R;
import hu.nje.androidmusic.database.AppDatabase;
import hu.nje.androidmusic.database.FavoriteSong;
import hu.nje.androidmusic.database.FavoriteSongDao;

public class FavSongsListFragment extends Fragment{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.favourite_songs, container, false);

            return view;
        }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        RecyclerView recycler = view.findViewById(R.id.favSongsRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        FavoriteSongDao dao = AppDatabase.getInstance(requireContext()).favoriteSongDao();
        List<FavoriteSong> favs = dao.getAllFavorites();

        FavSongsAdapter adapter = new FavSongsAdapter(favs, song -> {
            Bundle b = new Bundle();
            b.putString("trackId", song.trackId);
            b.putString("title", song.title);
            b.putString("artist", song.artist);
            b.putString("audioUrl", song.audioUrl);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.detailViewFragment, b);
        });

        recycler.setAdapter(adapter);
    }
}
