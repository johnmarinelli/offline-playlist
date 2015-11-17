package marinelli.john.youtubeplaylistdownloader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Factory for creating a dialog for user-inputted artist, title attributes.
 */
public class MediaDataInputDialogFactory {
    private static Context mContext;

    public MediaDataInputDialogFactory(Context context) {
        mContext = context;
    }

    /*
    * Creates a dialog to change a media model object's metadata to user input
     */
    public AlertDialog createDialog(final long mediaId) {
        // Get media_data_input_dialog.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View promptView = layoutInflater.inflate(R.layout.media_data_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(promptView);

        final EditText title = (EditText) promptView.findViewById(R.id.title);
        final EditText artist = (EditText) promptView.findViewById(R.id.artist);

        // setup a dialog window
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MediaModel m = MediaDownloadManager.getModel(mediaId);
                        m.mArtist = artist.getText().toString();
                        m.mTitle = title.getText().toString();
                        MediaDownloadManager.addModel(mediaId, m);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        return alert;
    }
}
