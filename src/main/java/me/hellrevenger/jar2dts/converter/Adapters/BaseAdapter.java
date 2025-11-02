package me.hellrevenger.jar2dts.converter.Adapters;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class BaseAdapter {
    public BaseAdapter(File file) {}
    public List<String> getClasses() {
        return null;
    }
    public byte[] getFileData(String filename) {
        return null;
    }
    public InputStream getInputStream(String filename) {
        return null;
    }
}
