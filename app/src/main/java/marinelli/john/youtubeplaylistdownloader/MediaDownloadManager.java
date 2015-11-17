package marinelli.john.youtubeplaylistdownloader;

import android.app.DownloadManager;

import java.util.HashMap;
import java.util.HashSet;

/**
 *  Wrapper for DownloadManager.
 *  Manages an internal long[] to keep track of download ids.
 */
public class MediaDownloadManager {
    private static DownloadManager mDownloadManager = null;
    private static HashSet<Long> mIds = new HashSet<>();
    private static HashMap<Long, MediaModel> mMediaModelHashmap = new HashMap<>();

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

    public static void addModel(long id, MediaModel model) {
        mMediaModelHashmap.put(id, model);
    }

    public static MediaModel getModel(long id) {
        return mMediaModelHashmap.get(id);
    }

    public static MediaModel removeModel(long id) {
        return mMediaModelHashmap.remove(id);
    }

    public static HashSet<Long> getEnqueue() {
        return mIds;
    }
}
