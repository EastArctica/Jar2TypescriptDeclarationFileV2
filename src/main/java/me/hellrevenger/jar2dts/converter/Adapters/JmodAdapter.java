package me.hellrevenger.jar2dts.converter.Adapters;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

public class JmodAdapter extends BaseAdapter {
    private List<String> classesCache = null;
    private final File jmod;
    private final ZipFile zipFile;

    public JmodAdapter(File file) throws IOException {
        super(file);
        if(!file.exists()) {
            throw new IOException("File not found: " + file.getAbsolutePath());
        }
        this.jmod = file;
        this.zipFile = new ZipFile(jmod);
    }

    @Override
    public List<String> getClasses() {
        if(classesCache != null) {
            return classesCache;
        }
        classesCache = new ArrayList<>();

        var entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            var entry = entries.nextElement();
            if (!entry.getName().startsWith("classes/")
                    || !entry.getName().endsWith(".class")
                    || entry.getName().contains("-")) {
                continue;
            }


            classesCache.add(entry.getName());
        }

        return classesCache;
    }

    @Override
    public byte[] getFileData(String filename) {
        try (var zip = new ZipFile(jmod)) {
            var entry = zip.getEntry(filename);
            if (entry == null) {
                throw new IOException("File not found in jmod: " + filename);
            }
            var inputStream = zip.getInputStream(entry);
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getInputStream(String filename) {
        try {
            return zipFile.getInputStream(zipFile.getEntry(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
