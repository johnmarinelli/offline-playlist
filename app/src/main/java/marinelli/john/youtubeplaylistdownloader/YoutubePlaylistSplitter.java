package marinelli.john.youtubeplaylistdownloader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Given a playlist URL, extracts links
 */
public class YoutubePlaylistSplitter extends AsyncTask<Void, Void, Void> {
    Context mContext;
    String mUrl;
    ProgressDialog mProgressDialog;
    YoutubePlaylistSplitterAsyncResponse mDelegate = null;
    ArrayList<MediaModel> mVideos = new ArrayList<MediaModel>();

    public YoutubePlaylistSplitter(String playlistId, Context c, ProgressDialog progressDialog) {
        super();

        mUrl = "https://crossorigin.me/http://youtube.com/playlist?list=" + playlistId;
        mContext = c;
        mProgressDialog = progressDialog;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document doc = Jsoup.connect(mUrl).get();

            Elements videos = doc.select("tr.pl-video.yt-uix-tile");

            for (Element v : videos) {
                Element videoListItem = v.getElementsByClass("pl-video-title")
                        .first();

                Element linkAndTitle = videoListItem.getElementsByClass("pl-video-title-link")
                        .first();

                String title = linkAndTitle.text();
                String videoId = linkAndTitle.attr("href")
                        .split("\\?v=")[1]
                        .split("&")[0];

                String videoOwner = videoListItem.getElementsByClass("pl-video-owner")
                        .first()
                        .getElementsByClass("yt-uix-sessionlink")
                        .first()
                        .text();

                mVideos.add(new MediaModel(title, videoOwner, videoId));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog.setTitle("Playlist Splitter");
        mProgressDialog.setMessage("Splitting playlist into videos...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mDelegate.processYoutubePlaylistSplitFinish(mVideos);
        mProgressDialog.dismiss();
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
        onCancelled();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        mProgressDialog.dismiss();
    }
}
