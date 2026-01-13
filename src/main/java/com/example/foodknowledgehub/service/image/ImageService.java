package com.example.foodknowledgehub.service.image;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

import java.io.OutputStream;

@Service
public class ImageService {

    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;
    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of("png", "jpg", "jpeg");
    private static final String BASE_IMAGE_PATH = "./src/main/resources/images";
    private static final String FOOD_DIR = "foods";
    private static final String VITAMIN_DIR = "vitamins";
    private static final String MICRO_DIR = "micro";
    private static final String MACRO_DIR = "macro";
    private static final String SOURCE_TYPE_FOOD = "food";
    private static final String SOURCE_TYPE_VITAMIN = "vitamin";
    private static final String SOURCE_TYPE_MICRO = "micro";
    private static final String SOURCE_TYPE_MACRO = "macro";

    private final Path foodDir;
    private final Path vitaminDir;
    private final Path microDir;
    private final Path macroDir;

    public ImageService() {
        Path baseDir = Paths.get(BASE_IMAGE_PATH).toAbsolutePath().normalize();
        this.foodDir = baseDir.resolve(FOOD_DIR);
        this.vitaminDir = baseDir.resolve(VITAMIN_DIR);
        this.microDir = baseDir.resolve(MICRO_DIR);
        this.macroDir = baseDir.resolve(MACRO_DIR);

        try {
            Files.createDirectories(foodDir);
            Files.createDirectories(vitaminDir);
            Files.createDirectories(microDir);
            Files.createDirectories(macroDir);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create image directories", e);
        }
    }

    public String storeImage(MultipartFile file, String sourceType) {
        validateFile(file);

        Path directory = resolveDirectoryFromSource(sourceType);
        String extension = extractExtension(file);
        String normalizedSourceType = sourceType.trim().toLowerCase();
        String filename = generateUniqueFilename(directory, normalizedSourceType, extension);
        Path targetPath = directory.resolve(filename).toAbsolutePath().normalize();

        validateTargetPath(directory, targetPath);

        try (InputStream in = file.getInputStream()) {
            File targetFile = targetPath.toFile();
            File canonicalFile = targetFile.getCanonicalFile();
            File canonicalDir = directory.toFile().getCanonicalFile();

            if (!canonicalFile.getPath().startsWith(canonicalDir.getPath() + File.separator) &&
                    !canonicalFile.getPath().equals(canonicalDir.getPath())) {
                throw new SecurityException("Cannot store file outside designated directory");
            }

            try (OutputStream out = Files.newOutputStream(canonicalFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }
            return filename;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to store image", e);
        }
    }

    public Resource loadImageAsResource(String filename) {
        validateFilename(filename);
        Path directory = resolveDirectoryFromFilename(filename);
        Path imagePath = directory.resolve(filename).toAbsolutePath().normalize();

        validateTargetPath(directory, imagePath);

        if (!Files.exists(imagePath)) {
            throw new IllegalStateException("Image not found: " + filename);
        }

        try {
            return new UrlResource(imagePath.toUri());
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Failed to load image", e);
        }
    }

    public void deleteImage(String filename) {
        validateFilename(filename);
        Path directory = resolveDirectoryFromFilename(filename);
        Path imagePath = directory.resolve(filename).toAbsolutePath().normalize();

        validateTargetPath(directory, imagePath);

        try {
            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to delete image", e);
        }
    }

    private Path resolveDirectoryFromSource(String sourceType) {
        if (sourceType == null) {
            throw new IllegalStateException("Source type cannot be null");
        }

        String normalized = sourceType.trim().toLowerCase();
        return switch (normalized) {
            case SOURCE_TYPE_FOOD -> foodDir;
            case SOURCE_TYPE_VITAMIN -> vitaminDir;
            case SOURCE_TYPE_MICRO -> microDir;
            case SOURCE_TYPE_MACRO -> macroDir;
            default -> throw new IllegalStateException("Unknown source type: " + sourceType);
        };
    }

    private Path resolveDirectoryFromFilename(String filename) {
        if (filename == null || !filename.contains("-")) {
            throw new IllegalStateException("Invalid image filename");
        }

        String prefix = filename.substring(0, filename.indexOf('-')).toLowerCase();

        return switch (prefix) {
            case SOURCE_TYPE_FOOD -> foodDir;
            case SOURCE_TYPE_VITAMIN -> vitaminDir;
            case SOURCE_TYPE_MICRO -> microDir;
            case SOURCE_TYPE_MACRO -> macroDir;
            default -> throw new IllegalStateException("Unknown image prefix: " + prefix);
        };
    }

    private String generateUniqueFilename(Path directory, String sourceType, String extension) {
        String filename;
        try {
            File canonicalDir = directory.toFile().getCanonicalFile();
            do {
                filename = sourceType.toLowerCase() + "-" + System.nanoTime() + "." + extension;
                File candidateFile = canonicalDir.toPath().resolve(filename).toFile().getCanonicalFile();

                if (!candidateFile.getPath().startsWith(canonicalDir.getPath() + File.separator)) {
                    throw new SecurityException("Invalid file path");
                }

                if (!candidateFile.exists()) {
                    break;
                }
            } while (true);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to generate unique filename", e);
        }
        return filename;
    }

    private void validateTargetPath(Path directory, Path targetPath) {
        try {
            File canonicalDir = directory.toFile().getCanonicalFile();
            File canonicalTarget = targetPath.toFile().getCanonicalFile();

            if (!canonicalTarget.getPath().startsWith(canonicalDir.getPath() + File.separator) &&
                    !canonicalTarget.getPath().equals(canonicalDir.getPath())) {
                throw new SecurityException("Invalid image path");
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to validate target path", e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalStateException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalStateException("Image exceeds 5 MB limit");
        }

        String extension = extractExtension(file);
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalStateException("Invalid image format");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalStateException("Invalid image content type");
        }
    }

    private String extractExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        if (name == null || !name.contains(".")) {
            throw new IllegalStateException("File extension missing");
        }
        return name.substring(name.lastIndexOf('.') + 1).toLowerCase();
    }

    private void validateFilename(String filename) {
        if (filename == null || filename.isEmpty() || filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            throw new IllegalStateException("Invalid filename");
        }
    }
}