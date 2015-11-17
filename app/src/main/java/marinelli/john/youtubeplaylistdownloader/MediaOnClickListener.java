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
    private MediaDataInputDialogFactory mDialogFactory;
    private long mEnqueue;
    private Context mContext;

    public MediaOnClickListener(Context context, MediaModel media, DownloadManager dm, long enqueue) {
        super();
        mContext = context;
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
        MediaDownloadManager.addId(mEnqueue);
        MediaDownloadManager.addModel(mEnqueue, mMedia);
    }
}
