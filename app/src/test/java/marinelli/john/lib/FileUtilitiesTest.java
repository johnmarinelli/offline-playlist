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
        byte[] buf = new byte[1024];
        int read = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while ((read = stream.read(buf)) != 1) {
            out.write(buf, 0, read);
        }

        String content = new String(out.toByteArray());

        assertEquals("MoveFile moves files.", "horses", content);
    }

    @Test
    public void testCreateDir() throws Exception {

    }

    @Test
    public void testCreateDir1() throws Exception {

    }
}