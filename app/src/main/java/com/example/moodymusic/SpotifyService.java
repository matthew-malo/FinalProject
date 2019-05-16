package com.example.moodymusic;

import java.util.*;
import kaaes.spotify.webapi.android.annotations.DELETEWITHBODY;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Albums;
import kaaes.spotify.webapi.android.models.AlbumsPager;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
import kaaes.spotify.webapi.android.models.ArtistsCursorPager;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.AudioFeaturesTrack;
import kaaes.spotify.webapi.android.models.AudioFeaturesTracks;
import kaaes.spotify.webapi.android.models.CategoriesPager;
import kaaes.spotify.webapi.android.models.Category;
import kaaes.spotify.webapi.android.models.FeaturedPlaylists;
import kaaes.spotify.webapi.android.models.NewReleases;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistFollowPrivacy;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import kaaes.spotify.webapi.android.models.PlaylistsPager;
import kaaes.spotify.webapi.android.models.Recommendations;
import kaaes.spotify.webapi.android.models.Result;
import kaaes.spotify.webapi.android.models.SavedAlbum;
import kaaes.spotify.webapi.android.models.SavedTrack;
import kaaes.spotify.webapi.android.models.SeedsGenres;
import kaaes.spotify.webapi.android.models.SnapshotId;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;
import kaaes.spotify.webapi.android.models.TracksToRemove;
import kaaes.spotify.webapi.android.models.TracksToRemoveWithPosition;
import kaaes.spotify.webapi.android.models.UserPrivate;
import kaaes.spotify.webapi.android.models.UserPublic;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface SpotifyService {

    String time_range = "short_term";
    String genre = "happy";
    String PlaylistName = "Moody - Happy";

    @POST("/users/{user_id}/playlists")
    void createPlaylist(@Path("user_id") String userId, String PlaylistName, @Body Map<String, Object> body, Callback<Playlist> callback);

    @GET("/me/top/artists")
    void getTopArtists(@QueryMap HashMap<String, Object> options = new HashMap<String, Object>(), Callback<Artists> callback);
    options.put(SpotifyService.LIMIT, 4);
    options.put(SpotifyService.TIME_RANGE, time_range);

    @GET("/me/top/tracks")
    void getTopTracks(@QueryMap Map<String, Object> options = new HashMap<>, Callback<Tracks> callback);
    options.put(SpotifyService.LIMIT, 4);
    options.put(SpotifyService.TIME_RANGE, time_range);

    @GET("/recommendations")
    void getRecommendations(@QueryMap Map<String, Object> options = new HashMap<>, Callback<Recommendations> callback);
    options.put(SpotifyService.LIMIT, 50);
    options.put(SpotifyService.SEED_GENRES, genre);
    options.put(SpotifyService.SEED_ARTISTS, Artists);

    @POST("/users/{user_id}/playlists/{Playlist}/tracks")
    void addTracksToPlaylist(@Path("user_id") String userId, @Path("playlist_id") String Playlist, @QueryMap Map<String, Object> queryParameters, @Body Map<String, Object> body, Callback<Pager<PlaylistTrack>> callback);
    options.put(SpotifyService.TRACKS, Recommendations);

    @GET("/recommendations")
    void getRecommendations(@QueryMap Map<String, Object> options = new HashMap<>, Callback<Recommendations> callback);
    options.put(SpotifyService.LIMIT, 50);
    options.put(SpotifyService.SEED_GENRES, genre);
    options.put(SpotifyService.SEED_ARTISTS, Tracks);

    @POST("/users/{user_id}/playlists/{Playlist}/tracks")
    void addTracksToPlaylist(@Path("user_id") String userId, @Path("playlist_id") String Playlist, @QueryMap Map<String, Object> queryParameters, @Body Map<String, Object> body, Callback<Pager<PlaylistTrack>> callback);
    options.put(SpotifyService.TRACKS, Recommendations);

}
