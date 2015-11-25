package marinelli.john.youtubeplaylistdownloader;

/**
 * App-specific representation of a piece of media.
 */
public class MediaModel {
    public String mTitle, mArtist, mSource, mPath, mQuery;
    private String mExternalStoragePath;

    public MediaModel(String title, String artist, String source, String path, String query) {
        mTitle = title;
        mArtist = artist;
        mSource = source;
        mPath = path;
        mQuery = query;
    }

    public void setExternalStoragePath(String path) {
        mExternalStoragePath = path;
    }

    public String getExternalStoragePath() {
        return mExternalStoragePath;
    }
}
