package marinelli.john.youtubeplaylistdownloader;

/**
 * App-specific representation of a piece of media.
 */
public class MediaModel {
    String mTitle, mArtist, mMediaId;

    public MediaModel(String title, String artist, String mediaId) {
        mTitle = title;
        mArtist = artist;
        mMediaId = mediaId;
    }
}
