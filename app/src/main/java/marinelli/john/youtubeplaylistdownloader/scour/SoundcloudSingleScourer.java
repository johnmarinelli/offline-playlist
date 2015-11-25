package marinelli.john.youtubeplaylistdownloader.scour;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import marinelli.john.youtubeplaylistdownloader.ApiLinks;
import marinelli.john.youtubeplaylistdownloader.MediaModel;

/**
 * Given a youtube video url, extracts a MediaModel.
 */
public class SoundcloudSingleScourer extends MediaHtmlPageScourer  {

    public SoundcloudSingleScourer(String songPath, Context context, ProgressDialog progressDialog) {
        super(context, progressDialog);

        // Create the URL we use to parse the html from soundcloud
        mUrl = Uri.parse(ApiLinks.SOUNDCLOUD_BASE_LINK.toString())
                  .buildUpon()
                  .path(songPath)
                  .build();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // TODO: refactor into testable modules
            Document doc = Jsoup.connect(Uri.decode(mUrl.toString()))
                    .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                    .get();

            // Get title of song
            Element titleElement = doc.select("meta[property=\"twitter:title\"]")
                    .first();
            String title = titleElement.attr("content");

            // Get artist/uploader of song
            Element ownerElement = doc.select("meta[name=\"twitter:audio:artist_name\"]")
                    .first();
            String songOwner = ownerElement.attr("content");

            // Default media model will set title as song title, artist as song owner
            mVideos.add(new MediaModel(title,
                    songOwner,
                    ApiLinks.SOUNDCLOUD_BASE_LINK.getAuthority(),
                    mUrl.getPath(),
                    ""));
        } catch (IOException e) {
            mException = e;
            e.printStackTrace();
        }

        return null;
    }
}
