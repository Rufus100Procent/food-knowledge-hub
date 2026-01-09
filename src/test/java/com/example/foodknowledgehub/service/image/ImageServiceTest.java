package com.example.foodknowledgehub.service.image;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.nio.file.Paths;
import java.util.Base64;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private FileSystemOperations fileSystemOperations;

    @Mock
    private MultipartFile mockFile;

    private ImageService imageService;

    @BeforeEach
    void setUp() throws IOException {
        // Mock directory creation so no real folders is created
        doNothing().when(fileSystemOperations).createDirectories(any(Path.class));

        imageService = new ImageService(fileSystemOperations);
    }

    @Test
    void testStoreImage_Success() throws IOException {
        // Arrange
        String itemName = "test-item";
        String sourceType = "food";
        byte[] fileContent = "fake image content".getBytes();

        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1024L);
        when(mockFile.getOriginalFilename()).thenReturn("test.png");
        when(mockFile.getContentType()).thenReturn("image/png");
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

        // Mock the directory and file objects
        File mockCanonicalDirectory = mock(File.class);
        File mockCanonicalFile = mock(File.class);

        when(mockCanonicalDirectory.getPath()).thenReturn("/fake/path/foods");
        when(mockCanonicalDirectory.toPath()).thenReturn(Paths.get("/fake/path/foods"));
        when(mockCanonicalFile.getPath()).thenReturn("/fake/path/foods/test-item.png");
        when(mockCanonicalFile.getAbsolutePath()).thenReturn("/fake/path/foods/test-item.png");

        // Setup mock behavior for getCanonicalFile calls
        when(fileSystemOperations.getCanonicalFile(any(File.class)))
                .thenAnswer(call -> {
                    File file = call.getArgument(0);
                    String path = file.getPath();
                    if (path.contains("foods") && !path.contains(".png")) {
                        return mockCanonicalDirectory;
                    } else {
                        return mockCanonicalFile;
                    }
                });

        when(fileSystemOperations.exists(mockCanonicalFile)).thenReturn(false);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(fileSystemOperations.createOutputStream(mockCanonicalFile)).thenReturn(outputStream);

        // Act
        String result = imageService.storeImage(mockFile, itemName, sourceType);

        // Assert
        assertNotNull(result);
        assertEquals("/fake/path/foods/test-item.png", result);
        assertArrayEquals(fileContent, outputStream.toByteArray());
        verify(fileSystemOperations).createOutputStream(mockCanonicalFile);
    }

    @Test
    void testStoreImage_NullFile() {
        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            imageService.storeImage(null, "item", "micro");
        });

        assertEquals("Image file is empty or null", exception.getMessage());
    }

    @Test
    void testStoreImage_EmptyFile() {
        // Arrange
        when(mockFile.isEmpty()).thenReturn(true);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            imageService.storeImage(mockFile, "item", "macro");
        });

        assertEquals("Image file is empty or null", exception.getMessage());
    }

    @Test
    void testStoreImage_FileSizeExceedsLimit() {
        // Arrange
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(6L * 1024 * 1024); // 6 MB

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            imageService.storeImage(mockFile, "item", "food");
        });
    }

    @Test
    void testStoreImage_InvalidExtension() {
        // Arrange
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1024L);
        when(mockFile.getOriginalFilename()).thenReturn("test.gif");

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            imageService.storeImage(mockFile, "item", "food");
        });

    }

    @ParameterizedTest
    @ValueSource    (strings = {
            "application/pdf",
            "text/plain",
            "application/octet-stream"
    })
    void testStoreImage_InvalidContentType(String contentType) {
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1024L);
        when(mockFile.getOriginalFilename()).thenReturn("test.png");
        when(mockFile.getContentType()).thenReturn(contentType);

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () ->
                        imageService.storeImage(mockFile, "item", "food"));

        assertEquals("Invalid image content type", exception.getMessage());
    }


    @Test
    void testStoreImage_NullItemName() {
        // Arrange
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1024L);
        when(mockFile.getOriginalFilename()).thenReturn("test.png");
        when(mockFile.getContentType()).thenReturn("image/png");

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            imageService.storeImage(mockFile, null, "food");
        });

        assertEquals("Item name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testStoreImage_EmptyItemName() {
        // Arrange
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1024L);
        when(mockFile.getOriginalFilename()).thenReturn("test.png");
        when(mockFile.getContentType()).thenReturn("image/png");

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            imageService.storeImage(mockFile, "   ", "food");
        });

        assertEquals("Item name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testStoreImage_DuplicateFilename() throws IOException {
        // Arrange
        String itemName = "duplicate";
        String sourceType = "vitamin";
        byte[] fileContent = "content".getBytes();

        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1024L);
        when(mockFile.getOriginalFilename()).thenReturn("test.jpg");
        when(mockFile.getContentType()).thenReturn("image/jpeg");
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

        File mockCanonicalDirectory = mock(File.class);
        File mockExistingFile = mock(File.class);
        File mockNewFile = mock(File.class);

        when(mockCanonicalDirectory.getPath()).thenReturn("/fake/path/vitamins");
        when(mockCanonicalDirectory.toPath()).thenReturn(Paths.get("/fake/path/vitamins"));
        when(mockExistingFile.getPath()).thenReturn("/fake/path/vitamins/duplicate.jpg");
        when(mockNewFile.getPath()).thenReturn("/fake/path/vitamins/duplicate1.jpg");
        when(mockNewFile.getAbsolutePath()).thenReturn("/fake/path/vitamins/duplicate1.jpg");

        // Mock getCanonicalFile to return appropriate files
        when(fileSystemOperations.getCanonicalFile(any(File.class)))
                .thenAnswer(call -> {
                    File file = call.getArgument(0);
                    String path = file.getPath();
                    if (path.contains("vitamins") && !path.contains(".jpg")) {
                        return mockCanonicalDirectory;
                    } else if (path.contains("duplicate1")) {
                        return mockNewFile;
                    } else {
                        return mockExistingFile;
                    }
                });

        when(fileSystemOperations.exists(mockExistingFile)).thenReturn(true);
        when(fileSystemOperations.exists(mockNewFile)).thenReturn(false);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(fileSystemOperations.createOutputStream(mockNewFile)).thenReturn(outputStream);

        // Act
        String result = imageService.storeImage(mockFile, itemName, sourceType);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("duplicate1.jpg"));
    }

    @Test
    void testStoreImage_VitaminLocation() throws IOException {
        testLocationMapping("vitamin", "vitamins");
    }

    @Test
    void testStoreImage_MicroMineralLocation() throws IOException {
        testLocationMapping("micro", "micro");
    }

    @Test
    void testStoreImage_MacroMineralLocation() throws IOException {
        testLocationMapping("macro", "macro");
    }

    @Test
    void testStoreImage_DefaultLocation() throws IOException {
        testLocationMapping("unknown", "images");
    }

    @Test
    void testStoreImage_SecurityViolation() throws IOException {
        // Arrange
        String itemName = "test";
        String sourceType = "food";

        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1024L);
        when(mockFile.getOriginalFilename()).thenReturn("test.png");
        when(mockFile.getContentType()).thenReturn("image/png");

        File mockCanonicalDirectory = mock(File.class);
        File mockCanonicalFileInResolve = mock(File.class);
        File mockCanonicalFileInStore = mock(File.class);

        when(mockCanonicalDirectory.getPath()).thenReturn("/fake/path/foods");
        when(mockCanonicalDirectory.toPath()).thenReturn(Paths.get("/fake/path/foods"));

        // First canonical file check in resolveUniqueFilename - should pass validation
        when(mockCanonicalFileInResolve.getPath()).thenReturn("/fake/path/foods/test.png");

        // Second canonical file check in storeImage - should fail validation
        when(mockCanonicalFileInStore.getPath()).thenReturn("/different/path/test.png");

        // Mock getCanonicalFile to return different files for different calls
        when(fileSystemOperations.getCanonicalFile(any(File.class)))
                .thenReturn(mockCanonicalDirectory)           // First call: directory in storeImage
                .thenReturn(mockCanonicalFileInResolve)       // Second call: file in resolveUniqueFilename
                .thenReturn(mockCanonicalDirectory)           // Third call: directory in resolveUniqueFilename
                .thenReturn(mockCanonicalFileInStore);        // Fourth call: file in storeImage for final validation

        when(fileSystemOperations.exists(mockCanonicalFileInResolve)).thenReturn(false);

        // Act & Assert
        SecurityException exception = assertThrows(SecurityException.class, () -> {
            imageService.storeImage(mockFile, itemName, sourceType);
        });

        assertEquals("Cannot store file outside designated directory", exception.getMessage());
    }

    @Test
    void testStoreImage_IoExceptionDuringStore() throws IOException {
        // Arrange
        String itemName = "test";
        String sourceType = "food";

        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1024L);
        when(mockFile.getOriginalFilename()).thenReturn("test.png");
        when(mockFile.getContentType()).thenReturn("image/png");
        when(mockFile.getInputStream()).thenThrow(new IOException("Read error"));

        File mockCanonicalDirectory = mock(File.class);
        when(mockCanonicalDirectory.getPath()).thenReturn("/fake/path/foods");
        when(mockCanonicalDirectory.toPath()).thenReturn(Paths.get("/fake/path/foods"));

        when(fileSystemOperations.getCanonicalFile(any(File.class))).thenReturn(mockCanonicalDirectory);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            imageService.storeImage(mockFile, itemName, sourceType);
        });

        assertEquals("Failed to store image file", exception.getMessage());
    }

    @Test
    void testStoreImage_SpecialCharactersInItemName() throws IOException {
        // Arrange
        String itemName = "test@#$%item!";
        String sourceType = "food";
        byte[] fileContent = "content".getBytes();

        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1024L);
        when(mockFile.getOriginalFilename()).thenReturn("test.png");
        when(mockFile.getContentType()).thenReturn("image/png");
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

        File mockCanonicalDirectory = mock(File.class);
        File mockCanonicalFile = mock(File.class);

        when(mockCanonicalDirectory.getPath()).thenReturn("/fake/path/foods");
        when(mockCanonicalDirectory.toPath()).thenReturn(Paths.get("/fake/path/foods"));
        when(mockCanonicalFile.getPath()).thenReturn("/fake/path/foods/test_____item_.png");
        when(mockCanonicalFile.getAbsolutePath()).thenReturn("/fake/path/foods/test_____item_.png");

        when(fileSystemOperations.getCanonicalFile(any(File.class)))
                .thenAnswer(invocation -> {
                    File file = invocation.getArgument(0);
                    String path = file.getPath();
                    if (path.contains("foods") && !path.contains(".png")) {
                        return mockCanonicalDirectory;
                    } else {
                        return mockCanonicalFile;
                    }
                });

        when(fileSystemOperations.exists(mockCanonicalFile)).thenReturn(false);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(fileSystemOperations.createOutputStream(mockCanonicalFile)).thenReturn(outputStream);

        // Act
        String result = imageService.storeImage(mockFile, itemName, sourceType);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("test_____item_"));
    }

    private void testLocationMapping(String sourceType, String expectedPathPart) throws IOException {
        byte[] fileContent = "content".getBytes();

        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getSize()).thenReturn(1024L);
        when(mockFile.getOriginalFilename()).thenReturn("test.png");
        when(mockFile.getContentType()).thenReturn("image/png");
        when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

        File mockCanonicalDirectory = mock(File.class);
        File mockCanonicalFile = mock(File.class);

        String expectedPath = "/fake/path/" + expectedPathPart;
        when(mockCanonicalDirectory.getPath()).thenReturn(expectedPath);
        when(mockCanonicalDirectory.toPath()).thenReturn(Paths.get(expectedPath));
        when(mockCanonicalFile.getPath()).thenReturn(expectedPath + "/test.png");
        when(mockCanonicalFile.getAbsolutePath()).thenReturn(expectedPath + "/test.png");

        when(fileSystemOperations.getCanonicalFile(any(File.class)))
                .thenAnswer(invocation -> {
                    File file = invocation.getArgument(0);
                    String path = file.getPath();
                    if (path.contains(expectedPathPart) && !path.contains(".png")) {
                        return mockCanonicalDirectory;
                    } else {
                        return mockCanonicalFile;
                    }
                });

        when(fileSystemOperations.exists(mockCanonicalFile)).thenReturn(false);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(fileSystemOperations.createOutputStream(mockCanonicalFile)).thenReturn(outputStream);

        String result = imageService.storeImage(mockFile, "test", sourceType);

        assertNotNull(result);
        assertTrue(result.contains(expectedPathPart));
    }
    @Test
    void testGetImageAsDataUri_NullPath() {
        String result = imageService.getImageAsDataUri(null);
        assertNull(result);
    }

    @Test
    void testGetImageAsDataUri_BlankPath() {
        String result = imageService.getImageAsDataUri("   ");
        assertNull(result);
    }
    @Test
    void testGetImageAsDataUri_FileDoesNotExist() {
        String result = imageService.getImageAsDataUri("/does/not/exist.png");
        assertNull(result);
    }

    @Test
    void testGetImageAsDataUri_Success() throws IOException {
        Path tempDir = Files.createTempDirectory("image-test");
        Path imageFile = tempDir.resolve("test.png");

        byte[] imageBytes = new byte[] { 1, 2, 3, 4, 5 };
        Files.write(imageFile, imageBytes);

        String result = imageService.getImageAsDataUri(imageFile.toString());

        assertNotNull(result);
        assertTrue(result.startsWith("data:image/"));
        assertTrue(result.contains(";base64,"));

        String base64Part = result.substring(result.indexOf(",") + 1);
        byte[] decoded = Base64.getDecoder().decode(base64Part);

        assertArrayEquals(imageBytes, decoded);
    }

    @Test
    void testGetImageAsDataUri_UnknownContentTypeDefaultsToPng() throws IOException {
        Path tempDir = Files.createTempDirectory("image-test");
        Path imageFile = tempDir.resolve("test.unknown");

        byte[] imageBytes = new byte[] { 9, 8, 7 };
        Files.write(imageFile, imageBytes);

        String result = imageService.getImageAsDataUri(imageFile.toString());

        assertNotNull(result);
        assertTrue(result.startsWith("data:image/png;base64,"));
    }

    @Test
    void testGetImageAsDataUri_ReadFailureThrowsException() throws IOException {
        Path tempDir = Files.createTempDirectory("image-test");

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () ->
                        imageService.getImageAsDataUri(tempDir.toString()));

        assertTrue(exception.getMessage().contains("Failed to read image from path"));
    }

}