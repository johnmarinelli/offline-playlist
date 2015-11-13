package marinelli.john.youtubeplaylistdownloader;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.DownloadManager;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import marinelli.john.lib.KeyboardUtilities;
import marinelli.john.youtubeplaylistdownloader.scour.MediaHtmlPageAsyncResponse;
import marinelli.john.youtubeplaylistdownloader.scour.MediaHtmlPageScourer;
import marinelli.john.youtubeplaylistdownloader.scour.YoutubePlaylistSplitter;
import marinelli.john.youtubeplaylistdownloader.scour.YoutubeVideoScourer;

public class MainActivity extends AppCompatActivity implements MediaHtmlPageAsyncResponse {
    private long mEnqueue;
    private DownloadManager mDownloadManager;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressDialog = new ProgressDialog(this);
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
/*
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    Query query = new Query();
                    query.setFilterById(enqueue);
                    Cursor c = mDownloadManager.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c
                                .getInt(columnIndex)) {
                        }
                    }
                }
            }
        };

        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));*/
    }

    /*
    * Scrape the page for video links.
     */
    public void handleInputUrl(View view) {
        // Get HTML
        try {
            URL url = new URL(((EditText) findViewById(R.id.playlist_url)).getText().toString());
            UrlHandler.UrlType urlType = UrlHandler.getUrlType(url);
            String mediaId = UrlHandler.getMediaId(urlType, url);

            MediaHtmlPageScourer page = null;

            switch (urlType) {
                case YOUTUBE_VIDEO:
                    page = new YoutubeVideoScourer(mediaId,
                            MainActivity.this,
                            mProgressDialog);
                    break;

                case YOUTUBE_PLAYLIST:
                    page = new YoutubePlaylistSplitter(mediaId,
                            MainActivity.this,
                            mProgressDialog);
                    break;
            }

            page.mDelegate = this;
            page.execute();

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
    * Receive ArrayList of video links.
     */
    @Override
    public void processInputUrlFinish(ArrayList<MediaModel> videos) {
        // Set listview adapter.
        ListView mediaList = (ListView) findViewById(R.id.video_list);
        MediaAdapter mediaAdapter = new MediaAdapter(this, R.layout.video_list_item, videos, mDownloadManager, mEnqueue);
        mediaList.setAdapter(mediaAdapter);
    }

    public void showDownload(View view) {
        Intent i = new Intent();
        i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
