package com.example.foodknowledgehub.service.image;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DefaultFileSystemOperations implements FileSystemOperations {
    @Override
    public void createDirectories(Path path) throws IOException {
        Files.createDirectories(path);
    }

    @Override
    public File getCanonicalFile(File file) throws IOException {
        return file.getCanonicalFile();
    }

    @Override
    public boolean exists(File file) {
        return file.exists();
    }

    @Override
    public OutputStream createOutputStream(File file) throws IOException {
        return new FileOutputStream(file);
    }
}