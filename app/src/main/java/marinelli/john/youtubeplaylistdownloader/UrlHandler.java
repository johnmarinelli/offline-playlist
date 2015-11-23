package marinelli.john.youtubeplaylistdownloader;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

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

    public UrlType getUrlType(URL url) {
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
        else if (StringUtilities.regexMatches("soundcloud.com", authority)) {
            if (path.split("/").length == 3) urlType = UrlType.SOUNDCLOUD_SINGLE;
            else if (StringUtilities.regexMatches("sets", path.split("/")[2]))
                urlType = UrlType.SOUNDCLOUD_PLAYLIST;
        }

        return urlType;
    }

    public String getMediaId(UrlType urlType, URL url)
        throws MalformedURLException {
        if (urlType == UrlType.YOUTUBE_VIDEO ||
                urlType == UrlType.YOUTUBE_PLAYLIST) return getYoutubeMediaId(urlType, url);
        else if (urlType == UrlType.SOUNDCLOUD_SINGLE ||
                    urlType == UrlType.SOUNDCLOUD_PLAYLIST) return getSoundcloudMediaId(urlType, url);
        else throw new MalformedURLException();
    }

    private String getSoundcloudMediaId(UrlType urlType, URL url)
        throws MalformedURLException {
        String mediaId = "";
        String path = url.getPath();

        Queue<String> subPaths = new LinkedList<String>();
        subPaths.addAll(Arrays.asList(path.split("/")));

        // first item will be an empty string
        if (!subPaths.isEmpty()) subPaths.remove();
        if (!subPaths.isEmpty()) {
            for (String p : subPaths) {
                mediaId += p;
                mediaId += "/";
            }
            // remove the last backslash
            mediaId = mediaId.substring(0, mediaId.lastIndexOf("/"));
            return mediaId;
        }
        else throw new MalformedURLException();
    }

    // Every media url will have a unique identifier
    // we need this identifier to be able to manipulate the media
    private String getYoutubeMediaId(UrlType urlType, URL url)
        throws MalformedURLException {
        String query = url.getQuery();

        // store params into KV pairs
        if (query != null) {
            String mediaId = null;
            String[] paramString = query.split("&");
            ArrayList<String> conjoinedParams = new ArrayList<>(Arrays.asList(paramString));
            HashMap<String, String> paramsMap = new HashMap<>();

            for (String p : conjoinedParams) {
                String[] s = p.split("=");
                paramsMap.put(s[0], s[1]);
            }

            // if there isn't a "v" or "list" param, the url is bad
            if (!(paramsMap.containsKey("v") || paramsMap.containsKey("list"))) {
                throw new MalformedURLException();
            }

            switch (urlType) {
                case YOUTUBE_VIDEO:
                    mediaId = paramsMap.get("v");
                    break;
                case YOUTUBE_PLAYLIST:
                    mediaId = paramsMap.get("list");
                    break;
            }

            return mediaId;
        }
        else throw new MalformedURLException();
    }

}
