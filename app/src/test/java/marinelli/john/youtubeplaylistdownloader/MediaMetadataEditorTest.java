package marinelli.john.youtubeplaylistdownloader;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.junit.Assert.*;

/**
 * Tests JAudioTagger wrapper.
 */
public class MediaMetadataEditorTest {
    @Rule
    public TemporaryFolder mFolder = new TemporaryFolder();
    public File mFile;

    @Before
    public void setUp() throws Exception {
        mFile = new File("./src/test/resources/beep18.mp3");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testWriteMediaMetadata() throws Exception {
        MediaModel m = new MediaModel("title", "artist", "mediaId");
        m.setExternalStoragePath(mFile.getAbsolutePath());
        boolean success = MediaMetadataEditor.writeMediaMetadata(m, m.getExternalStoragePath());
        assertEquals("MediaMetadataEditor successfully writes metadata", true, success);
    }
}