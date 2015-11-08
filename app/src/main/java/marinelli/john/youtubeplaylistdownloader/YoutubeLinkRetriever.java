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
public class YoutubeLinkRetriever extends AsyncTask<Void, Void, Void> {
    Context mContext;
    String mUrl;
    ArrayList<String> mVideoLinks;
    ProgressDialog mProgressDialog;
    YoutubeLinkRetrieverAsyncResponse mDelegate = null;

    public YoutubeLinkRetriever(String playlistId, Context c, ProgressDialog progressDialog) {
        super();

        mUrl = "https://crossorigin.me/http://youtube.com/playlist?list=" + playlistId;
        mContext = c;
        mProgressDialog = progressDialog;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document doc = Jsoup.connect(mUrl).get();

            Elements videoLinks = doc.select("a.pl-video-title-link");

            // fill video links array
            for (Element link : videoLinks)  mVideoLinks.add(link.attr("href"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog.setTitle("Android Basic JSoup Tutorial");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mDelegate.processYoutubeLinkRetrievalFinish(mVideoLinks);
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
