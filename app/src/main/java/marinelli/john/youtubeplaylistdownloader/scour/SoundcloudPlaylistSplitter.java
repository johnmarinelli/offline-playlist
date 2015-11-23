package marinelli.john.youtubeplaylistdownloader.scour;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import marinelli.john.youtubeplaylistdownloader.ApiLinks;

/**
 * Given a soundcloud playlist URL, extracts links
 */
public class SoundcloudPlaylistSplitter extends MediaHtmlPageScourer {

    public SoundcloudPlaylistSplitter(String playlistId, Context context, ProgressDialog progressDialog) {
        super(context, progressDialog);

        // Create the URL we use to parse the html from youtube
        // TODO
        //mUrl = Uri.parse(ApiLinks.CO_SOUNDCLOUD_PLAYLIST_HTML_LINK.toString())
    }

    @Override
    protected Void doInBackground(Void... params) {
        // TODO
        return null;
    }
}
