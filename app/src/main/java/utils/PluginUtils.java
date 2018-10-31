package utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PluginUtils {

    public static void extraAssets(Context context, String sourceName) {
        final AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            inputStream = assetManager.open(sourceName);
            File extractFile = context.getFileStreamPath(sourceName);
            fos = new FileOutputStream(extractFile);

            byte[] bytes = new byte[1024];
            int len = 0;

            while ((len = inputStream.read(bytes)) > 0) {
                fos.write(bytes, 0, len);
            }

            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(fos);
            closeSilently(inputStream);
        }
    }

    private static void closeSilently(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
    }
}
