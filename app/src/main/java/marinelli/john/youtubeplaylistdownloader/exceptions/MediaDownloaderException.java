package marinelli.john.youtubeplaylistdownloader.exceptions;

/**
 * Superclass for any custom exceptions.
 */
public class MediaDownloaderException extends RuntimeException {
    public MediaDownloaderException(Exception e) { super(e); }
    public MediaDownloaderException(String msg) { super(msg); }
}
