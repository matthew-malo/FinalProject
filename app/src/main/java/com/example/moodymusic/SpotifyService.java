package com.example.moodymusic;

import java.util.Map;

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
/*
    String limit_4 = "4";
    String limit_50 = "50";
    String time_range = "short_term";
    String genre = "happy";

    @GET("/me/top/artists")
    void getTopArtists(Callback<Pager<Artist>> artist_ids, String limit_4, String time_range);
//    String seed_artists = artist_ids;

    @GET("/recommendations")
//    SpotifyService(options=[seed_artists = top_artists, seed_genres = genre, limit=50], Callback<Recommendations> callback);
    void getRecommendations(options<artist_ids, genre, limit_50>, Callback<Recommendations> callback);
    for tracks in callback
    get track ids
    add to tracksArray

    @GET("/me/top/tracks")
    getTopTracks([time_range=short, limit=4], Callback<Pager<Tracks>> callback);
    for Tracks in callback
    get track ids
    add to array
    for track in results['tracks']:
//        track_ids.append(track['id'])
    @GET("/recommendations")
    SpotifyService(options=[seed_artists = top_tracks, seed_genres = genre, limit=50], Callback<Recommendations> callback);
    for tracks in callback
    get track ids
    add to tracksArray



    @POST("/users/{user_id}/playlists")
    createPlaylist(@Path("user_id") String userId, @Body Map<String, Object> body, options=(username, playlist_name, public=True) ,Callback<Playlist> callback);
	for id in callback
    get playlist id
    add playlist id to array
    convert array to string
	for uri in callback
    get playlist uri
    add playlist uri to array
    convert array to string

    @POST("/users/{user_id}/playlists/{playlist_id}/tracks")
    SpotifyService(@Path("user_id") String userId, @Path("playlist_id") String playlistId, @QueryMap Map<String, Object> queryParameters, @Body Map<String, Object> body BODY_IS_THE_TRACKSARRAY, Callback<Pager<PlaylistTrack>> callback);

*/
}
