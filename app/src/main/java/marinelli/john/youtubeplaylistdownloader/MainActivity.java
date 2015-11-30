package marinelli.john.youtubeplaylistdownloader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.DownloadManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import marinelli.john.lib.KeyboardUtilities;
import marinelli.john.youtubeplaylistdownloader.scour.MediaHtmlPageAsyncResponse;
import marinelli.john.youtubeplaylistdownloader.scour.MediaHtmlPageScourer;
import marinelli.john.youtubeplaylistdownloader.scour.SoundcloudPlaylistSplitter;
import marinelli.john.youtubeplaylistdownloader.scour.SoundcloudSingleScourer;
import marinelli.john.youtubeplaylistdownloader.scour.YoutubePlaylistSplitter;
import marinelli.john.youtubeplaylistdownloader.scour.YoutubeVideoScourer;

public class MainActivity extends Activity implements MediaHtmlPageAsyncResponse {
    private DownloadManager mDownloadManager;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TypefaceProvider.registerDefaultIconSets();

        mProgressDialog = new ProgressDialog(this);
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        MediaDownloadManager.setManager(mDownloadManager);

        // Whenever a download is completed, create a new MediaReceiver().
        getApplicationContext().registerReceiver(new MediaReceiver(), new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    /*
    * Scrape the page for video links.
     */
    public void handleInputUrl(View view) {
        // Get HTML
        try {
            URL url = new URL(((EditText) findViewById(R.id.playlist_url)).getText().toString());
            UrlHandler urlHandler = new UrlHandler();
            UrlHandler.UrlType urlType = urlHandler.getUrlType(url);
            String mediaId = null;

            // throws MalformedUrlException if there is no media Id
            mediaId = urlHandler.getMediaId(urlType, url);

            MediaHtmlPageScourer scourer = null;

            // We need to use different scouring techniques for each kind of url
            switch (urlType) {
                case YOUTUBE_VIDEO:
                    scourer = new YoutubeVideoScourer(mediaId,
                            MainActivity.this,
                            mProgressDialog);
                    break;

                case YOUTUBE_PLAYLIST:
                    scourer = new YoutubePlaylistSplitter(mediaId,
                            MainActivity.this,
                            mProgressDialog);
                    break;
                case SOUNDCLOUD_SINGLE:
                    scourer = new SoundcloudSingleScourer(mediaId,
                            MainActivity.this,
                            mProgressDialog);
                    break;
                case SOUNDCLOUD_PLAYLIST:
                    scourer = new SoundcloudPlaylistSplitter(mediaId,
                            MainActivity.this,
                            mProgressDialog);
                    break;
            }

            // Once we're done handling page, this class will call the delegated function
            scourer.mDelegate = this;
            scourer.execute();

            // Hide the keyboard
            KeyboardUtilities.hideKeyboard(this);
        } catch (NullPointerException|MalformedURLException e) {
            Toast.makeText(this,
                    e.getMessage() + "\nPlease make sure your URL is correct.",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    /*
    * Receive ArrayList of links.
     */
    @Override
    public void processInputUrlFinish(ArrayList<MediaModel> list) {
        setMediaList(list);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mProgressDialog != null) mProgressDialog.dismiss();
    }

    private void setMediaList(ArrayList<MediaModel> list) {
        if (list != null) {
            // Set listview adapter.
            ListView mediaList = (ListView) findViewById(R.id.video_list);
            MediaAdapter mediaAdapter = new MediaAdapter(MainActivity.this,
                    R.layout.video_list_item,
                    list,
                    mDownloadManager);

            mediaList.setAdapter(mediaAdapter);
        }
    }
}
