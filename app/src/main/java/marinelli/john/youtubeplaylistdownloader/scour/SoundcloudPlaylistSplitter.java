package marinelli.john.youtubeplaylistdownloader.scour;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import marinelli.john.youtubeplaylistdownloader.ApiLinks;
import marinelli.john.youtubeplaylistdownloader.MediaModel;

/**
 * Given a soundcloud playlist URL, extracts links
 */
public class SoundcloudPlaylistSplitter extends MediaHtmlPageScourer {

    public SoundcloudPlaylistSplitter(String playlistPath, Context context, ProgressDialog progressDialog) {
        super(context, progressDialog);

        // Create the URL we use to parse the html from soundcloud
        mUrl = Uri.parse(ApiLinks.SOUNDCLOUD_BASE_LINK.toString())
                .buildUpon()
                .path(playlistPath)
                .build();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // TODO: refactor into testable modules
            Document doc = Jsoup.connect(Uri.decode(mUrl.toString()))
                    .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                    .get();

            Elements tracks = doc.select("article[itemprop=\"track\"]");

            for (Element t : tracks) {
                // Get path of track
                Element trackPathElement = t
                        .getElementsByAttributeValue("itemprop", "url")
                        .first();
                String trackPath = trackPathElement.attr("href");

                // Get artist/uploader of song
                Element ownerElement = trackPathElement
                        .nextElementSibling();
                String songOwner = ownerElement.text();

                // Get title of song
                String title = trackPathElement.text();

                // Default media model will set title as song title, artist as song owner
                mVideos.add(new MediaModel(title,
                        songOwner,
                        ApiLinks.SOUNDCLOUD_BASE_LINK.getAuthority(),
                        trackPath,
                        ""));
            }
        } catch (IOException e) {
            mException = e;
            e.printStackTrace();
        }

        return null;
    }
}
