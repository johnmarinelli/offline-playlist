package marinelli.john.youtubeplaylistdownloader;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import marinelli.john.lib.MediaScannerWrapper;

/**
 * Receiver for when a file is done downloading.
 */
public class MediaReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            long downloadId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, 0);

            if (MediaDownloadManager.hasId(downloadId)) {
                MediaDownloadManager.popId(downloadId);
            }

            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            Cursor c = MediaDownloadManager.getManager().query(query);
            if (c.moveToFirst()) {
                int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                    File musicDir =
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);

                    File musicCustomDir = new File(musicDir, "john");

                    if (!musicCustomDir.exists()) {
                        musicCustomDir.mkdir();
                    }

                    String inputPath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                    String inputFilename = inputPath.substring(inputPath.lastIndexOf('/') + 1, inputPath.length());
                    String outputPath = musicCustomDir.getAbsolutePath()
                            + "/"
                            + inputFilename
                            + "/";
                    try {
                        FileInputStream in = new FileInputStream(inputPath);
                        FileOutputStream out = new FileOutputStream(outputPath);

                        byte[] buf = new byte[1024];
                        int read;

                        while ((read = in.read(buf)) != -1) {
                            out.write(buf, 0, read);
                        }

                        in.close();
                        in = null;

                        out.flush();
                        out.close();
                        out = null;
                        MediaDownloadManager.getModel(downloadId).setExternalStoragePath(outputPath);

                        try {
                            AudioFile mp3 = AudioFileIO.read(new File(outputPath));
                            MediaModel model = MediaDownloadManager.getModel(downloadId);
                            Tag tag = mp3.getTag();
                            tag.setField(FieldKey.ARTIST, model.mArtist);
                            tag.setField(FieldKey.TITLE, model.mTitle);
                            mp3.commit();
                        } catch (CannotReadException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (TagException e) {
                            e.printStackTrace();
                        } catch (ReadOnlyFileException e) {
                            e.printStackTrace();
                        } catch (InvalidAudioFrameException e) {
                            e.printStackTrace();
                        } catch (CannotWriteException e) {
                            e.printStackTrace();
                        }
                        new MediaScannerWrapper(context, outputPath, "audio/mpeg3").scan();

                    } catch (java.io.FileNotFoundException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                    } catch (java.io.IOException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                    }

                }
            }

            MediaDownloadManager.removeModel(downloadId);
            c.close();
        }
    }
}
