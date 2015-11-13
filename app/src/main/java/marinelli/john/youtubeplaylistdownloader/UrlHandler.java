package marinelli.john.youtubeplaylistdownloader;

import android.net.Uri;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import marinelli.john.lib.StringUtilities;
/**
 * Class to handle input urls.
 */
public class UrlHandler {

    public enum UrlType {
        NONE,
        YOUTUBE_VIDEO,
        YOUTUBE_PLAYLIST,
        SOUNDCLOUD_SINGLE,
        SOUNDCLOUD_PLAYLIST
    }

    public static UrlType getUrlType(URL url) {
        String authority = url.getAuthority();
        String path = url.getPath();

        UrlType urlType = UrlType.NONE;

        // Handle youtube url
        if (StringUtilities.regexMatches("youtube.com", authority)) {
            // Handles single youtube video
            if (StringUtilities.regexMatches("watch", path)) urlType = UrlType.YOUTUBE_VIDEO;
            // Handle youtube playlist
            else if (StringUtilities.regexMatches("playlist", path)) urlType = UrlType.YOUTUBE_PLAYLIST;
        }

        // Handle soundcloud url
        else if (StringUtilities.regexMatches("youtube.com", authority)) {

        }

        return urlType;
    }

    // Every media url will have a unique identifier
    // we need this identifier to be able to manipulate the media
    public static String getMediaId(UrlType urlType, URL url) {
        String mediaId = "";

        // store params into KV pairs
        String[] paramString = url.getQuery().split("&");
        ArrayList<String> conjoinedParams = new ArrayList<>(Arrays.asList(paramString));
        HashMap<String, String> paramsMap = new HashMap<>();

        for (String p : conjoinedParams) {
            String[] s = p.split("=");
            paramsMap.put(s[0], s[1]);
        }

        switch (urlType) {
            case YOUTUBE_VIDEO: mediaId = paramsMap.get("v"); break;
            case YOUTUBE_PLAYLIST: mediaId = paramsMap.get("list"); break;
        }

        return mediaId;
    }

}
