package marinelli.john.youtubeplaylistdownloader.scour;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import marinelli.john.youtubeplaylistdownloader.MediaModel;

/**
 * Scours an HTML page for the info.
 */
public abstract class MediaHtmlPageScourer extends AsyncTask<Void, Void, Void> {
    Context mContext;
    Uri mUrl;
    ProgressDialog mProgressDialog;
    ArrayList<MediaModel> mVideos = new ArrayList<>();
    public MediaHtmlPageAsyncResponse mDelegate = null;
    IOException mException = null;

    MediaHtmlPageScourer(Context context, ProgressDialog progressDialog) {
        mContext = context;
        mProgressDialog = progressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Show a progress dialog telling user what we're doing
        mProgressDialog.setTitle("Playlist Splitter");
        mProgressDialog.setMessage("Splitting playlist into videos...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mException == null) {
            mDelegate.processInputUrlFinish(mVideos);
        }
        else {
            Toast.makeText(mContext, mException.getMessage(), Toast.LENGTH_LONG).show();
        }
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
