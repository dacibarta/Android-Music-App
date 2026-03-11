package hu.nje.androidmusic.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hu.nje.androidmusic.R;
import hu.nje.androidmusic.network.JamendoTrack;

public class SongsListHomeFragment extends Fragment {

    private static final String JAMENDO_CLIENT_ID = "5f55e89f";

    private SongsViewModel viewModel;
    private SongsAdapter adapter;


    public SongsListHomeFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.songs_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.searchListView);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new SongsAdapter(new ArrayList<>(), this::onSongClicked);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(SongsViewModel.class);

        viewModel.getTracksLiveData().observe(getViewLifecycleOwner(), tracks ->
                adapter.updateData(tracks));


        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.loadTracks(JAMENDO_CLIENT_ID);

        Button searchButton = view.findViewById(R.id.searchButton);
        EditText searchBar = view.findViewById(R.id.searchBar);
        searchButton.setOnClickListener(v -> {
            String searchText = searchBar.getText().toString();
            if (searchText.length() == 0) {
                showMessage("Keresés mező üres!");
                viewModel.loadTracks(JAMENDO_CLIENT_ID);
            } else {
                viewModel.loadSearchedTracks(JAMENDO_CLIENT_ID, searchText);
            }

        });
    }
    private void onSongClicked(JamendoTrack track) {
        Bundle bundle = new Bundle();
        bundle.putString("trackId", track.getId());
        bundle.putString("title", track.getName());
        bundle.putString("artist", track.getArtist_name());
        bundle.putString("audioUrl", track.getAudio());
        bundle.putString("image", track.getImage());

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_songsListHomeFragment_to_detailViewFragment, bundle);
    }

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
