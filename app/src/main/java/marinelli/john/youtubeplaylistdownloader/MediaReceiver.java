package marinelli.john.youtubeplaylistdownloader;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import marinelli.john.lib.FileUtilities;

/**
 * Receiver for when a file is done downloading.
 */
public class MediaReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        // When we receive a 'download complete' signal
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            // Get the download id
            long downloadId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, 0);

            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            Cursor c = MediaDownloadManager.getManager().query(query);

            // If there is an entry for this download id
            if (c.moveToFirst()) {
                int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                // If the download was successful
                if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                    String inputPath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                    String ext = FileUtilities.getExtension(inputPath);
                    // if downloaded file isn't an mp3
                    if (!ext.equals("mp3")) {
                        Toast.makeText(context,
                                "There was an error downloading your file.",
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Start a new activity for user to manually input metadata
                    Intent metadataDialog = new Intent(context, MediaDataInputActivity.class);
                    metadataDialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    metadataDialog.putExtra("downloadId", downloadId);
                    metadataDialog.putExtra("inputPath", inputPath);
                    context.startActivity(metadataDialog);
                }

                // TODO: if download wasn't successful
            }
            c.close();
        }
    }
}
