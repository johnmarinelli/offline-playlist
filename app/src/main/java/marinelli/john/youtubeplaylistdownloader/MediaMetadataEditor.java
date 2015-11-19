package marinelli.john.youtubeplaylistdownloader;

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
import java.io.IOException;

import marinelli.john.youtubeplaylistdownloader.exceptions.MetadataAccessorException;

/**
 * Wrapper for app-specific Jaudiotagger functions.
 */
public class MediaMetadataEditor {
    public static boolean writeMediaMetadata(MediaModel model, String path) {
        boolean success = false;
        try {
            AudioFile mp3 = AudioFileIO.read(new File(path));
            Tag tag = mp3.getTag();
            tag.setField(FieldKey.ARTIST, model.mArtist);
            tag.setField(FieldKey.TITLE, model.mTitle);
            mp3.commit();
            success = true;
        } catch (CannotReadException e) {
            e.printStackTrace();
            throw new MetadataAccessorException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MetadataAccessorException(e);
        } catch (TagException e) {
            e.printStackTrace();
            throw new MetadataAccessorException(e);
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
            throw new MetadataAccessorException(e);
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
            throw new MetadataAccessorException(e);
        } catch (CannotWriteException e) {
            e.printStackTrace();
            throw new MetadataAccessorException(e);
        }
        return success;
    }

}
