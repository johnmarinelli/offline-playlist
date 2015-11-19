package marinelli.john.youtubeplaylistdownloader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import marinelli.john.lib.FileUtilities;
import marinelli.john.lib.MediaScannerWrapper;
import marinelli.john.youtubeplaylistdownloader.exceptions.MediaDownloaderException;

/**
 * Custom dialog box for inputting artist and title for a piece of media.
 */
public class MediaDataInputActivity extends Activity {

    long mDownloadId = -1;
    String mInputPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_data_input);
        mDownloadId = getIntent().getLongExtra("downloadId", -1);
        mInputPath = getIntent().getStringExtra("inputPath");

        MediaModel model = MediaDownloadManager.getModel(mDownloadId);
        if (null != model) {
            // The default metadata has already been stored when we finished processing the html
            setDefaultMediadata(model.mArtist, model.mTitle);
        }
    }

    /*
    * Sets default title and artist text.
     */
    private void setDefaultMediadata(String defaultArtist, String defaultTitle) {
        EditText artistInput = (EditText) findViewById(R.id.artist);
        EditText titleInput = (EditText) findViewById(R.id.title);
        artistInput.setText(defaultArtist);
        titleInput.setText(defaultTitle);
    }

    private File getOutputFile(String artist, String title) {
        // Create a new artist directory
        File musicDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);

        // Create folder Internal Storage/Music/<ARTIST>
        File musicCustomDir = new File(musicDir, artist);
        FileUtilities.createDir(musicCustomDir);

        // Create file Internal Storage/Music/<ARTIST>/<TITLE>
        File outputFile = new File(musicCustomDir.getAbsolutePath(), title + ".mp3");

        return outputFile;
    }


    /*
    * Move from downloads to Internal Storage/Music/<ARTIST>
     */
    private boolean moveFromDownloadsToExternalStorage(String artist, String title) {
        String outputPath = getOutputFile(artist, title).getAbsolutePath();
        return FileUtilities.moveFile(mInputPath, outputPath);
    }

    private boolean setMetadata(String artist, String title) {
        String outputPath = getOutputFile(artist, title).getAbsolutePath();

        // Edit metadata for file
        MediaModel m = MediaDownloadManager.getModel(mDownloadId);
        m.mArtist = artist;
        m.mTitle = title;
        MediaDownloadManager.addModel(mDownloadId, m);

        // wrapper for jaudiotagger.  sets the artist name and title.
        return MediaMetadataEditor.writeMediaMetadata(m, outputPath);
    }

    private void refreshMediaLibrary(String artist, String title) {
        String outputPath = getOutputFile(artist, title).getAbsolutePath();

        // rescan music library
        MediaScannerWrapper.scan(getApplicationContext(), outputPath, "audio/mpeg3");
    }

    public void submitMediaData(View v) {
        EditText artistInput = (EditText) findViewById(R.id.artist);
        EditText titleInput = (EditText) findViewById(R.id.title);

        String artist = artistInput.getText().toString();
        String title = titleInput.getText().toString();

        // After we submit, we need to move the file from downloads,
        // set the metadata,
        // and refresh Android's music library
        try {
            moveFromDownloadsToExternalStorage(artist, title);
            setMetadata(artist, title);
            refreshMediaLibrary(artist, title);
        } catch (MediaDownloaderException e) {
            Toast.makeText(getApplicationContext(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        MediaDataInputActivity.this.finish();
    }
}
