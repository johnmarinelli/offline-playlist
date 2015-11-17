package marinelli.john.youtubeplaylistdownloader;

/**
 * App-specific representation of a piece of media.
 */
public class MediaModel {
    public String mTitle, mArtist, mMediaId;
    private String mExternalStoragePath;

    public MediaModel(String title, String artist, String mediaId) {
        mTitle = title;
        mArtist = artist;
        mMediaId = mediaId;
    }

    public void setExternalStoragePath(String path) {
        mExternalStoragePath = path;
    }
}
