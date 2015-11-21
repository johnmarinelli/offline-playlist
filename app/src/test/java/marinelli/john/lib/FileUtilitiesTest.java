package marinelli.john.lib;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.*;

/**
 * Tests FileUtilities.
 */
public class FileUtilitiesTest {
    @Rule
    public TemporaryFolder mFolder = new TemporaryFolder();
    private File mFile;

    @Before
    public void setUp() throws Exception {
        mFile = mFolder.newFile("file_utilities_test");
        FileOutputStream stream = new FileOutputStream(mFile);
        stream.write("123,456,789,101,121,314".getBytes());
    }

    @After
    public void tearDown() throws Exception {
        mFile.delete();
    }

    @Test
    public void testMoveFile() throws Exception {
        File file2 = mFolder.newFile("test_utilities_file");
        FileUtilities.moveFile(mFile.getAbsolutePath(), file2.getAbsolutePath());

        FileInputStream stream = new FileInputStream(file2);
        byte[] buf = new byte[32];

        stream.read(buf);
        String out = new String(buf);

        assertEquals("MoveFile moves files.", "123,456,789,101,121,314", out);
    }

    @Test
    public void testCreateDir() throws Exception {
        File tmp = mFolder.newFolder();
        boolean dirCreated = FileUtilities.createDir(tmp.getAbsolutePath() + "/test");
        File dir = new File(tmp.getAbsolutePath() + "/test");
        boolean dirExists = dir.exists() && dir.isDirectory();
        assertEquals("createDir creates a directory from string", true, dirCreated);
        assertEquals("createDir creates a directory from string", true, dirExists);
    }
}