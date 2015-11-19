package marinelli.john.youtubeplaylistdownloader;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.View;

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
                .appendQueryParameter("f", mMedia.mMediaId)
                .build();

        // Start the download request
        DownloadManager.Request request = new DownloadManager.Request(link);
        long downloadId = mDownloadManager.enqueue(request);

        // Store this download id with a media model.
        MediaDownloadManager.addModel(downloadId, mMedia);
    }
}
