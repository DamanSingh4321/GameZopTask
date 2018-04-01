package com.singh.daman.gamezoptask.utils;

import com.singh.daman.gamezoptask.interfaces.ZipDownloadListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Daman on 4/1/2018.
 */

public class Utils {

    public static void unpackZipFile(String path, ZipDownloadListener listener) {
        InputStream is;
        ZipInputStream zis;
        File file;
        File destFile = new File(path);
        try {
            String filename;
            is = new FileInputStream(path);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;
            destFile = new File(path);
            while ((ze = zis.getNextEntry()) != null) {
                filename = ze.getName();

                if (ze.isDirectory()) {
                    file = new File(path, filename);
                    file.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(path + filename);

                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listener.onUnZip(destFile);
    }
}
