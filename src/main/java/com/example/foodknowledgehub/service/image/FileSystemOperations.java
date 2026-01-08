package com.example.foodknowledgehub.service.image;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;


public interface FileSystemOperations {
    void createDirectories(Path path) throws IOException;
    File getCanonicalFile(File file) throws IOException;
    boolean exists(File file);
    OutputStream createOutputStream(File file) throws IOException;
}