package marinelli.john.lib;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;

public class MediaScannerWrapper implements
        MediaScannerConnection.MediaScannerConnectionClient {
    private MediaScannerConnection mConnection;
    private String mPath;
    private String mMimeType;

    // filePath - where to scan;
    // mime type of media to scan i.e. "image/jpeg".
    // use "*/*" for any media
    public MediaScannerWrapper(Context ctx, String filePath, String mime){
        mPath = filePath;
        mMimeType = mime;
        mConnection = new MediaScannerConnection(ctx, this);
    }

    // do the scanning
    private void scan() {
        mConnection.connect();
    }

    // start the scan when scanner is ready
    public void onMediaScannerConnected() {
        mConnection.scanFile(mPath, mMimeType);
        Log.w("MediaScannerWrapper", "media file scanned: " + mPath);
    }

    public void onScanCompleted(String path, Uri uri) {
        // when scan is completes, update media file tags
    }

    public static void scan(Context context, String path, String mime) {
        new MediaScannerWrapper(context, path, mime).scan();
    }
}