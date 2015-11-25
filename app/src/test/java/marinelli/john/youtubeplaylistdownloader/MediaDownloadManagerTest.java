package marinelli.john.youtubeplaylistdownloader;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the MediaDownloadManager internal hashmap functions.
 */
public class MediaDownloadManagerTest {
    @Test
    public void testAddModel() throws Exception {
        MediaDownloadManager.addModel(1, new MediaModel("t", "a", "s", "p", "q"));
        MediaDownloadManager.addModel(2, new MediaModel("t2", "a2", "s2", "p2", "q2"));
        MediaDownloadManager.addModel(2, new MediaModel("t2", "a2", "s2", "p2", "q2"));

        assertEquals("Same Ids won't be overwritten", 2, MediaDownloadManager.getNumOfModels());
    }

    @Test
    public void testGetModel() throws Exception {
        MediaDownloadManager.addModel(1, new MediaModel("t", "a", "s", "p", "q"));
        MediaDownloadManager.addModel(2, new MediaModel("t2", "a2", "s2", "p2", "q2"));

        assertEquals("MediaDownloadManager doesn't get correct model", "a", MediaDownloadManager.getModel(1).mArtist);
    }

    @Test
    public void testRemoveModel() throws Exception {
        MediaDownloadManager.addModel(1, new MediaModel("t", "a", "s", "p", "q"));
        MediaDownloadManager.addModel(2, new MediaModel("t2", "a2", "s2", "p2", "q2"));
        MediaDownloadManager.removeModel(1);

        assertEquals("MediaDownloadManager doesn't remove model", 1, MediaDownloadManager.getNumOfModels());


    }
}