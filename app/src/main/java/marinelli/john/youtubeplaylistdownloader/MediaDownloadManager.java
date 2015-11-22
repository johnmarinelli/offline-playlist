package marinelli.john.youtubeplaylistdownloader;

import android.app.DownloadManager;

import java.util.HashMap;
import java.util.HashSet;

/**
 *  Wrapper for DownloadManager.
 *  Manages an internal HashMap<id, MediaModel></id,> to keep track of download ids.
 */
public class MediaDownloadManager {
    private static DownloadManager mDownloadManager = null;
    private static HashMap<Long, MediaModel> mMediaModelHashmap = new HashMap<>();

    public MediaDownloadManager(DownloadManager manager) {
        mDownloadManager = manager;
    }

    public static DownloadManager getManager() {
        return mDownloadManager;
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

    public static int getNumOfModels() { return mMediaModelHashmap.size(); }
}
