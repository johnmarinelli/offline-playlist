package marinelli.john.youtubeplaylistdownloader;

import android.app.DownloadManager;

import java.util.HashSet;

/**
 *  Wrapper for DownloadManager.
 *  Manages an internal long[] to keep track of download ids.
 */
public class MediaDownloadManager {
    private static DownloadManager mDownloadManager = null;
    private static HashSet<Long> mIds = new HashSet<>();

    public MediaDownloadManager(DownloadManager manager) {
        mDownloadManager = manager;
    }

    public static DownloadManager getManager() {
        return mDownloadManager;
    }

    public static void addId(long id) {
        mIds.add(id);
    }

    public static boolean popId(long id) {
        return mIds.remove(id);
    }

    public static boolean hasId(long id) {
        return mIds.contains(id);
    }

    public static HashSet<Long> getEnqueue() {
        return mIds;
    }
}
