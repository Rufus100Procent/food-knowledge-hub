package com.example.foodknowledgehub.service.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Service
public class ImageStorageService {

    private static final Set<String> ALLOWED_IMAGE_FILE_EXTENSIONS = Set.of("png", "jpg", "jpeg");

    private final Path defaultLocation;
    private final Path foodLocation;
    private final Path vitaminLocation;
    private final Path microMineralLocation;
    private final Path macroMineralLocation;
    private final FileSystemOperations fileSystemOperations;

    @Autowired
    public ImageStorageService(FileSystemOperations fileSystemOperations) {
        this.fileSystemOperations = fileSystemOperations;
        this.defaultLocation = Paths.get("./src/main/resources/images");
        this.foodLocation = Paths.get("./src/main/resources/images/foods");
        this.vitaminLocation = Paths.get("./src/main/resources/images/vitamins");
        this.microMineralLocation = Paths.get("./src/main/resources/images/micro");
        this.macroMineralLocation = Paths.get("./src/main/resources/images/macro");

        try {
            fileSystemOperations.createDirectories(defaultLocation);
            fileSystemOperations.createDirectories(foodLocation);
            fileSystemOperations.createDirectories(vitaminLocation);
            fileSystemOperations.createDirectories(microMineralLocation);
            fileSystemOperations.createDirectories(macroMineralLocation);
        } catch (IOException e) {
            throw new IllegalStateException("Could not create image storage folders", e);
        }
    }

    public String storeImage(MultipartFile file, String itemName, String sourceType) {
        validateFile(file);
        validateItemName(itemName);

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalStateException("Original filename is null or empty");
        }

        String cleanOriginalFilename = StringUtils.cleanPath(originalFilename);
        String fileExtension = StringUtils.getFilenameExtension(cleanOriginalFilename);

        if (fileExtension == null || fileExtension.isEmpty()) {
            throw new IllegalStateException("File extension is missing");
        }

        String safeItemName = sanitizeFileName(itemName);
        Path targetLocation = resolveLocation(sourceType);

        try {
            File canonicalTargetDirectory = fileSystemOperations.getCanonicalFile(targetLocation.toFile());
            Path safePath = resolveUniqueFilename(canonicalTargetDirectory.toPath(), safeItemName, fileExtension);

            File canonicalTargetFile = fileSystemOperations.getCanonicalFile(safePath.toFile());

            if (!canonicalTargetFile.getPath().startsWith(canonicalTargetDirectory.getPath() + File.separator) &&
                    !canonicalTargetFile.getPath().equals(canonicalTargetDirectory.getPath())) {
                throw new SecurityException("Cannot store file outside designated directory");
            }

            try (InputStream inputStream = file.getInputStream();
                 OutputStream out = fileSystemOperations.createOutputStream(canonicalTargetFile)) {

                byte[] buffer = new byte[8192];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                return canonicalTargetFile.getAbsolutePath();
            }

        } catch (IOException e) {
            throw new IllegalStateException("Failed to store image file", e);
        }
    }

    private Path resolveLocation(String sourceType) {
        if (sourceType == null) {
            throw new IllegalStateException("Source type cannot be null");
        }

        String normalizedSourceType = sourceType.trim().toLowerCase();

        return switch (normalizedSourceType) {
            case "food" -> foodLocation;
            case "vitamin" -> vitaminLocation;
            case "micro" -> microMineralLocation;
            case "macro" -> macroMineralLocation;
            default -> defaultLocation;
        };
    }

    private Path resolveUniqueFilename(Path directory, String baseName, String extension) {
        String sanitizedBase = baseName.replaceAll("[^a-zA-Z0-9_-]", "_");
        String sanitizedExt = extension.replaceAll("[^a-zA-Z0-9]", "");

        String filename = sanitizedBase + "." + sanitizedExt.toLowerCase();
        Path targetFile = directory.resolve(filename);

        try {
            File canonicalFile = fileSystemOperations.getCanonicalFile(targetFile.toFile());
            File canonicalDirectory = fileSystemOperations.getCanonicalFile(directory.toFile());

            if (!canonicalFile.getPath().startsWith(canonicalDirectory.getPath())) {
                throw new SecurityException("Invalid file path");
            }

            int counter = 1;
            while (fileSystemOperations.exists(canonicalFile)) {
                filename = sanitizedBase + counter + "." + sanitizedExt.toLowerCase();
                targetFile = directory.resolve(filename);
                canonicalFile = fileSystemOperations.getCanonicalFile(targetFile.toFile());

                if (!canonicalFile.getPath().startsWith(canonicalDirectory.getPath())) {
                    throw new SecurityException("Invalid file path");
                }
                counter++;
            }

            return Paths.get(canonicalFile.getPath());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to resolve unique filename", e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalStateException("Image file is empty or null");
        }

        long maxFileSize = 5L * 1024 * 1024;
        if (file.getSize() > maxFileSize) {
            throw new IllegalStateException("Image size exceeds 5 MB limit");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalStateException("Original filename is null");
        }

        String fileExtension = StringUtils.getFilenameExtension(originalFilename);
        if (fileExtension == null || !ALLOWED_IMAGE_FILE_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new IllegalStateException("Invalid image format. Only PNG, JPG, JPEG allowed.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalStateException("Invalid image content type");
        }
    }

    private void validateItemName(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalStateException("Item name cannot be null or empty");
        }
    }

    private String sanitizeFileName(String input) {
        return input.trim().replaceAll("[^a-zA-Z0-9-_.]", "_");
    }

}