package hu.nje.androidmusic.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JamendoApi {
    @GET("tracks")
    Call<JamendoResponse> getTracks(
            @Query("client_id") String clientId,
            @Query("format") String format,
            @Query("include") String include,
            @Query("audioformat") String audioFormat,
            @Query("limit") int limit,
            @Query("order") String order,
            @Query("name") String name
    );
}
