package marinelli.john.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import marinelli.john.youtubeplaylistdownloader.exceptions.FileMoveException;

/**
 * Utility class to deal with moving files.
 */
public class FileUtilities {
    public static boolean moveFile(String inputPath, String outputPath)
            throws FileMoveException {
        boolean success = false;

        try {
            FileInputStream in = new FileInputStream(inputPath);
            FileOutputStream out = new FileOutputStream(outputPath);

            byte[] buf = new byte[1024];
            int read;

            while ((read = in.read(buf)) != -1) {
                out.write(buf, 0, read);
            }

            in.close();
            in = null;

            out.flush();
            out.close();

            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileMoveException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileMoveException(e);
        }

        return success;
    }

    public static boolean createDir(String path) {
        File dir = new File(path);
        return createDir(dir);
    }

    public static boolean createDir(File dir) {
        boolean success = false;
        if (!dir.exists()) {
            success = dir.mkdir();
        }
        return success;
    }

    public static String getExtension(String name){
        String ext;
        if(name.lastIndexOf(".") == -1){
            ext = "";
        }
        else {
            int index = name.lastIndexOf(".");
            ext = name.substring(index+1, name.length());
        }
        return ext;
    }
}
