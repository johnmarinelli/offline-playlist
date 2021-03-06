package marinelli.john.youtubeplaylistdownloader;

import android.net.Uri;

/**
 * Class to hold the various links for services/APIs.
 */
public class ApiLinks {

    public static Uri YOUTUBE_BASE_LINK =
            new Uri.Builder()
                    .scheme("http")
                    .authority("youtube.com")
                    .build();

    public static Uri SOUNDCLOUD_BASE_LINK =
            new Uri.Builder()
                    .scheme("http")
                    .authority("soundcloud.com")
                    .build();

    public static Uri YOUTUBE_PLAYLIST_LINK = Uri.parse(Uri.decode(YOUTUBE_BASE_LINK.toString()))
            .buildUpon()
            .path("playlist")
            .build();

    public static Uri YOUTUBE_VIDEO_LINK = Uri.parse(Uri.decode(YOUTUBE_BASE_LINK.toString()))
                .buildUpon()
                .path("watch")
                .build();

    public static Uri CO_HTML_LINK =
            new Uri.Builder()
                .scheme("http")
                .authority("crossorigin.me")
                .build();

    public static Uri CO_YOUTUBE_VIDEO_HTML_LINK = Uri.parse(Uri.decode(CO_HTML_LINK.toString()))
                .buildUpon()
                .path(Uri.decode(YOUTUBE_VIDEO_LINK.toString()))
                .build();

    // Remember to use Uri::appendQueryParameter('list' || 'watch', playlist || video Id)
    public static Uri CO_YOUTUBE_PLAYLIST_HTML_LINK =
            new Uri.Builder()
                .scheme("http")
                .authority("crossorigin.me")
                .path(Uri.decode(YOUTUBE_PLAYLIST_LINK.toString()))
                .build();

    public static Uri MP3_DOWNLOAD_SERVICE_LINK =
            new Uri.Builder()
                .scheme("https")
                .authority("thawing-eyrie-3660.herokuapp.com")
                .path("dl")
                .build();
}
