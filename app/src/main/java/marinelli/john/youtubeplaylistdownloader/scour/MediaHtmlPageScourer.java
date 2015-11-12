package marinelli.john.youtubeplaylistdownloader.scour;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

import marinelli.john.youtubeplaylistdownloader.MediaModel;

/**
 * Scours an HTML page for teh infoz.
 */
public abstract class MediaHtmlPageScourer extends AsyncTask<Void, Void, Void> {
    Context mContext;
    Uri mUrl;
    ProgressDialog mProgressDialog;
    ArrayList<MediaModel> mVideos = new ArrayList<MediaModel>();
    public MediaHtmlPageAsyncResponse mDelegate = null;
    IOException mException = null;
}
