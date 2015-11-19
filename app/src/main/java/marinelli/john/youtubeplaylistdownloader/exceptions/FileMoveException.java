package marinelli.john.youtubeplaylistdownloader.exceptions;

/**
 * Exception class for FileUtilities.moveFile
 */
public class FileMoveException extends MediaDownloaderException {
    public FileMoveException(Exception e) { super(e); }
    public FileMoveException(String msg) { super(msg); }
}
