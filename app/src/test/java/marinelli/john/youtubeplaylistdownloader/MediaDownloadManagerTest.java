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
        MediaDownloadManager.addModel(1, new MediaModel("t", "a", "i"));
        MediaDownloadManager.addModel(2, new MediaModel("t2", "a2", "i2"));
        MediaDownloadManager.addModel(2, new MediaModel("t2", "a2", "i2"));

        assertEquals("Same Ids will be overwritten", 2, MediaDownloadManager.getNumOfModels());
    }

    @Test
    public void testGetModel() throws Exception {
        MediaDownloadManager.addModel(1, new MediaModel("t", "a", "i"));
        MediaDownloadManager.addModel(2, new MediaModel("t2", "a2", "i2"));

        assertEquals("MediaDownloadManager gets correct model", "a", MediaDownloadManager.getModel(1).mArtist);
    }

    @Test
    public void testRemoveModel() throws Exception {
        MediaDownloadManager.addModel(1, new MediaModel("t", "a", "i"));
        MediaDownloadManager.addModel(2, new MediaModel("t2", "a2", "i2"));
        MediaDownloadManager.removeModel(1);

        assertEquals("MediaDownloadManager removes model", 1, MediaDownloadManager.getNumOfModels());


    }
}