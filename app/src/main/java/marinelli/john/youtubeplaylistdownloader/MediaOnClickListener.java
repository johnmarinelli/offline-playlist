package marinelli.john.youtubeplaylistdownloader;

import android.app.DownloadManager;
import android.net.Uri;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;

/**
 * Click listener for the 'download' button.
 */
public class MediaOnClickListener implements View.OnClickListener {
    private MediaModel mMedia;
    private DownloadManager mDownloadManager;

    public MediaOnClickListener(MediaModel media, DownloadManager dm) {
        super();
        mMedia = media;
        mDownloadManager = dm;
    }

    @Override
    public void onClick(View v) {
        // Create the URL we use to parse the html from youtube
        Uri link = Uri.parse(ApiLinks.MP3_DOWNLOAD_SERVICE_LINK.toString())
                .buildUpon()
                .appendQueryParameter("host", Uri.encode(mMedia.mSource))
                .appendQueryParameter("path", Uri.encode(mMedia.mPath))
                .appendQueryParameter("query", Uri.encode(mMedia.mQuery))
                .build();

        // Start the download request
        DownloadManager.Request request = new DownloadManager.Request(link);

        long downloadId = mDownloadManager.enqueue(request);

        // Store this download id with a media model.
        MediaDownloadManager.addModel(downloadId, mMedia);

        // Change button icon
        BootstrapButton button = (BootstrapButton) v;
        button.setFontAwesomeIcon("fa_check");
    }
}
