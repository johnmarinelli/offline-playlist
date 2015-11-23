package marinelli.john.youtubeplaylistdownloader.scour;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import marinelli.john.youtubeplaylistdownloader.ApiLinks;
import marinelli.john.youtubeplaylistdownloader.MediaModel;

/**
 * Given a playlist URL, extracts links
 */
public class YoutubePlaylistSplitter extends MediaHtmlPageScourer {
    public YoutubePlaylistSplitter(String playlistId, Context context, ProgressDialog progressDialog) {
        super(context, progressDialog);

        // Create the URL we use to parse the html from youtube
        mUrl = Uri.parse(ApiLinks.CO_YOUTUBE_PLAYLIST_HTML_LINK.toString())
                .buildUpon()
                .appendQueryParameter("list", playlistId)
                .build();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // TODO: refactor into testable modules
            Document doc = Jsoup.connect(Uri.decode(mUrl.toString())).get();

            Elements videos = doc.select("tr.pl-video.yt-uix-tile");

            // Select information from youtube to build a video model
            for (Element v : videos) {
                Element videoListItem = v
                        .getElementsByClass("pl-video-title")
                        .first();

                Element linkAndTitle = videoListItem
                        .getElementsByClass("pl-video-title-link")
                        .first();

                String title = linkAndTitle.text();
                String videoId = linkAndTitle
                        .attr("href")
                        .split("\\?v=")[1]
                        .split("&")[0];

                String videoOwner = videoListItem
                        .getElementsByClass("pl-video-owner")
                        .first()
                        .getElementsByClass("yt-uix-sessionlink")
                        .first()
                        .text();

                // Default youtube media model will set title as video title, artist as video owner
                mVideos.add(new MediaModel(title, videoOwner, videoId));
            }
        } catch (IOException e) {
            mException = e;
            e.printStackTrace();
        }

        return null;
    }

}
