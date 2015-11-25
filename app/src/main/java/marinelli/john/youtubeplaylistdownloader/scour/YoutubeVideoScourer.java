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
public class YoutubeVideoScourer extends MediaHtmlPageScourer {
    public YoutubeVideoScourer(String videoId, Context context, ProgressDialog progressDialog) {
        super(context, progressDialog);

        // Create the URL we use to parse the html from youtube
        mUrl = Uri.parse(ApiLinks.CO_YOUTUBE_VIDEO_HTML_LINK.toString())
                .buildUpon()
                .appendQueryParameter("v", videoId)
                .build();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // TODO: refactor into testable modules
            Document doc = Jsoup.connect(Uri.decode(mUrl.toString()))
                    .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                    .get();

            // Get title of video
            Element titleElement = doc.select("span#eow-title").first();
            String title = titleElement.attr("title");

            // Get artist/uploader of video
            Element ownerElement = doc.select(".yt-user-info a").first();
            String videoOwner = ownerElement.html();

            // Get video id
            String videoId = mUrl.getQueryParameter("v");

            // Default youtube media model will set title as video title, artist as video owner
            mVideos.add(new MediaModel(title,
                    videoOwner,
                    ApiLinks.YOUTUBE_BASE_LINK.getAuthority(),
                    ApiLinks.YOUTUBE_VIDEO_LINK.getPath(),
                    String.format("v=%s", videoId)));

        } catch (IOException e) {
            mException = e;
            e.printStackTrace();
        }

        return null;
    }
}
