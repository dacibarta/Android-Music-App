package hu.nje.androidmusic.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import hu.nje.androidmusic.network.JamendoApi;
import hu.nje.androidmusic.network.JamendoResponse;
import hu.nje.androidmusic.network.JamendoTrack;
import hu.nje.androidmusic.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongsViewModel extends ViewModel{
    private final MutableLiveData<List<JamendoTrack>> tracksLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public LiveData<List<JamendoTrack>> getTracksLiveData() {
        return tracksLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void loadTracks(String clientId) {
        loadingLiveData.setValue(true);

        JamendoApi api = RetrofitClient.getInstance().getApi();
        Call<JamendoResponse> call = api.getTracks(
                clientId,
                "json",
                "musicinfo+stats",
                "mp32",
                200,
                "popularity_week",
                null
        );

        call.enqueue(new Callback<JamendoResponse>() {
            @Override
            public void onResponse(Call<JamendoResponse> call, Response<JamendoResponse> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    tracksLiveData.setValue(response.body().getResults());
                } else {
                    errorLiveData.setValue("API error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JamendoResponse> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue(t.getMessage());
            }
        });
    }

    public void loadSearchedTracks(String clientId, String searchText) {
        loadingLiveData.setValue(true);

        JamendoApi api = RetrofitClient.getInstance().getApi();
        Call<JamendoResponse> call = api.getTracks(
                clientId,
                "json",
                "musicinfo+stats",
                "mp32",
                200,
                "popularity_week",
                searchText
        );

        call.enqueue(new Callback<JamendoResponse>() {
            @Override
            public void onResponse(Call<JamendoResponse> call, Response<JamendoResponse> response) {
                loadingLiveData.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    tracksLiveData.setValue(response.body().getResults());
                } else {
                    errorLiveData.setValue("API error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JamendoResponse> call, Throwable t) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue(t.getMessage());
            }
        });
    }
}
