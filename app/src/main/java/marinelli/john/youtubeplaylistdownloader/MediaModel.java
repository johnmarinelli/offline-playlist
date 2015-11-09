package marinelli.john.youtubeplaylistdownloader;

/**
 * App-specific representation of a piece of media.
 */
public class MediaModel {
    String mTitle, mArtist, mVideoId;

    public MediaModel(String title, String artist, String videoId) {
        mTitle = title;
        mArtist = artist;
        mVideoId = videoId;
    }
}
