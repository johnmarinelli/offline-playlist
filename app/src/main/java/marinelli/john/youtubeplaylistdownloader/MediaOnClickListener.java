package marinelli.john.youtubeplaylistdownloader;

import android.app.DownloadManager;
import android.net.Uri;
import android.view.View;

/**
 * Click listener for the 'download' button.
 */
public class MediaOnClickListener implements View.OnClickListener {
    MediaModel mMedia;
    DownloadManager mDownloadManager;
    long mEnqueue;

    public MediaOnClickListener(MediaModel media, DownloadManager dm, long enqueue) {
        super();
        mMedia = media;
        mDownloadManager = dm;
        mEnqueue = enqueue;
    }

    @Override
    public void onClick(View v) {
        // Create the URL we use to parse the html from youtube
        Uri link = Uri.parse(ApiLinks.MP3_DOWNLOAD_SERVICE_LINK.toString())
                .buildUpon()
                .appendQueryParameter("f", mMedia.mMediaId)
                .build();

        DownloadManager.Request request = new DownloadManager.Request(link);
        mEnqueue = mDownloadManager.enqueue(request);
    }
}
