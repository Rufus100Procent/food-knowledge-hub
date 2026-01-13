package com.example.foodknowledgehub.service.image;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private MultipartFile mockFile;

    private ImageService imageService;

    @BeforeEach
    void setUp() {
        // Mock the Files.createDirectories to prevent real directory creation
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);
            imageService = new ImageService();
        }
    }

    @Test
    void testStoreImage_Success() throws IOException {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            // Setup mocks
            String sourceType = "food";
            byte[] fileContent = "fake image content".getBytes();

            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.png");
            when(mockFile.getContentType()).thenReturn("image/png");
            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

            // Mock Files.newOutputStream
            mockedFiles.when(() -> Files.newOutputStream(any(), any(), any()))
                    .thenReturn(new java.io.ByteArrayOutputStream());

            // Act
            String result = imageService.storeImage(mockFile, sourceType);

            // Assert
            assertNotNull(result);
            assertTrue(result.startsWith("food-"));
            assertTrue(result.endsWith(".png"));
        }
    }

    @Test
    void testStoreImage_NullFile() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> imageService.storeImage(null, "food"));

            assertEquals("File is empty", exception.getMessage());
        }
    }

    @Test
    void testStoreImage_EmptyFile() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            when(mockFile.isEmpty()).thenReturn(true);

            assertThrows(IllegalStateException.class, () -> imageService.storeImage(mockFile, "food"));
        }
    }

    @Test
    void testStoreImage_FileSizeExceedsLimit() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(6L * 1024 * 1024);

            assertThrows(IllegalStateException.class, () -> imageService.storeImage(mockFile, "food"));
        }
    }

    @Test
    void testStoreImage_InvalidExtension() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.gif");

            assertThrows(IllegalStateException.class, () -> imageService.storeImage(mockFile, "food"));
        }
    }

    @Test
    void testStoreImage_InvalidContentType_PDF() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.png");
            when(mockFile.getContentType()).thenReturn("application/pdf");

            assertThrows(IllegalStateException.class, () ->
                    imageService.storeImage(mockFile, "food"));
        }
    }

    @Test
    void testStoreImage_InvalidContentType_PlainText() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.png");
            when(mockFile.getContentType()).thenReturn("text/plain");

            assertThrows(IllegalStateException.class, () ->
                    imageService.storeImage(mockFile, "food"));
        }
    }

    @Test
    void testStoreImage_NullSourceType() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.png");
            when(mockFile.getContentType()).thenReturn("image/png");

            assertThrows(IllegalStateException.class, () -> imageService.storeImage(mockFile, null));
        }
    }

    @Test
    void testStoreImage_UnknownSourceType() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.png");
            when(mockFile.getContentType()).thenReturn("image/png");

            assertThrows(IllegalStateException.class, () -> imageService.storeImage(mockFile, "unknown"));
        }
    }

    @Test
    void testStoreImage_MissingFileExtension() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("testfile");

            assertThrows(IllegalStateException.class, () -> imageService.storeImage(mockFile, "food"));
        }
    }

    @Test
    void testStoreImage_FoodLocation() throws IOException {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            byte[] fileContent = "content".getBytes();
            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.jpg");
            when(mockFile.getContentType()).thenReturn("image/jpeg");
            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

            mockedFiles.when(() -> Files.newOutputStream(any(), any(), any()))
                    .thenReturn(new java.io.ByteArrayOutputStream());

            String result = imageService.storeImage(mockFile, "food");

            assertNotNull(result);
            assertTrue(result.startsWith("food-"));
            assertTrue(result.endsWith(".jpg"));
        }
    }

    @Test
    void testStoreImage_VitaminLocation() throws IOException {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            byte[] fileContent = "content".getBytes();
            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.png");
            when(mockFile.getContentType()).thenReturn("image/png");
            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

            mockedFiles.when(() -> Files.newOutputStream(any(), any(), any()))
                    .thenReturn(new java.io.ByteArrayOutputStream());

            String result = imageService.storeImage(mockFile, "vitamin");

            assertNotNull(result);
            assertTrue(result.startsWith("vitamin-"));
            assertTrue(result.endsWith(".png"));
        }
    }

    @Test
    void testStoreImage_MicroLocation() throws IOException {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            byte[] fileContent = "content".getBytes();
            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.jpeg");
            when(mockFile.getContentType()).thenReturn("image/jpeg");
            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

            mockedFiles.when(() -> Files.newOutputStream(any(), any(), any()))
                    .thenReturn(new java.io.ByteArrayOutputStream());

            String result = imageService.storeImage(mockFile, "micro");

            assertNotNull(result);
            assertTrue(result.startsWith("micro-"));
            assertTrue(result.endsWith(".jpeg"));
        }
    }

    @Test
    void testStoreImage_MacroLocation() throws IOException {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            byte[] fileContent = "content".getBytes();
            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.png");
            when(mockFile.getContentType()).thenReturn("image/png");
            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

            mockedFiles.when(() -> Files.newOutputStream(any(), any(), any()))
                    .thenReturn(new java.io.ByteArrayOutputStream());

            String result = imageService.storeImage(mockFile, "macro");

            assertNotNull(result);
            assertTrue(result.startsWith("macro-"));
            assertTrue(result.endsWith(".png"));
        }
    }

    @Test
    void testLoadImageAsResource_InvalidFilename() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            assertThrows(IllegalStateException.class, () -> imageService.loadImageAsResource("invalid_no_dash.png"));
        }
    }

    @Test
    void testLoadImageAsResource_NullFilename() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            assertThrows(IllegalStateException.class, () -> imageService.loadImageAsResource(null));
        }
    }

    @Test
    void testLoadImageAsResource_PathTraversalAttempt() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            assertThrows(IllegalStateException.class, () -> imageService.loadImageAsResource("../../../etc/passwd"));
        }
    }

    @Test
    void testDeleteImage_InvalidFilename() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            assertThrows(IllegalStateException.class, () -> imageService.deleteImage("invalid_no_dash.png"));
        }
    }

    @Test
    void testDeleteImage_NullFilename() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            assertThrows(IllegalStateException.class, () -> imageService.deleteImage(null));
        }
    }

    @Test
    void testDeleteImage_PathTraversalAttempt() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            assertThrows(IllegalStateException.class, () -> imageService.deleteImage("../../../etc/passwd"));
        }
    }

    @Test
    void testStoreImage_MultipleUniqueFiles() throws IOException {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            byte[] fileContent1 = "content1".getBytes();
            byte[] fileContent2 = "content2".getBytes();

            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.png");
            when(mockFile.getContentType()).thenReturn("image/png");

            mockedFiles.when(() -> Files.newOutputStream(any(), any(), any()))
                    .thenReturn(new java.io.ByteArrayOutputStream());

            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent1));
            String filename1 = imageService.storeImage(mockFile, "food");

            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent2));
            String filename2 = imageService.storeImage(mockFile, "food");

            assertNotNull(filename1);
            assertNotNull(filename2);
            assertNotEquals(filename1, filename2);
            assertTrue(filename1.startsWith("food-"));
            assertTrue(filename2.startsWith("food-"));
        }
    }

    @Test
    void testStoreImage_CaseInsensitiveSourceType() throws IOException {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            byte[] fileContent = "content".getBytes();
            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.png");
            when(mockFile.getContentType()).thenReturn("image/png");
            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

            mockedFiles.when(() -> Files.newOutputStream(any(), any(), any()))
                    .thenReturn(new java.io.ByteArrayOutputStream());

            String result = imageService.storeImage(mockFile, "FOOD");

            assertNotNull(result);
            assertTrue(result.startsWith("food-"));
        }
    }

    @Test
    void testStoreImage_SourceTypeWithWhitespace() throws IOException {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            byte[] fileContent = "content".getBytes();
            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.png");
            when(mockFile.getContentType()).thenReturn("image/png");
            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

            mockedFiles.when(() -> Files.newOutputStream(any(), any(), any()))
                    .thenReturn(new java.io.ByteArrayOutputStream());

            String result = imageService.storeImage(mockFile, "  vitamin  ");

            assertNotNull(result);
            assertTrue(result.startsWith("vitamin-"));
        }
    }

    @Test
    void testStoreImage_JpgExtension() throws IOException {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            byte[] fileContent = "content".getBytes();
            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.jpg");
            when(mockFile.getContentType()).thenReturn("image/jpeg");
            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

            mockedFiles.when(() -> Files.newOutputStream(any(), any(), any()))
                    .thenReturn(new java.io.ByteArrayOutputStream());

            String result = imageService.storeImage(mockFile, "food");

            assertNotNull(result);
            assertTrue(result.endsWith(".jpg"));
        }
    }

    @Test
    void testStoreImage_JpegExtension() throws IOException {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            byte[] fileContent = "content".getBytes();
            when(mockFile.isEmpty()).thenReturn(false);
            when(mockFile.getSize()).thenReturn(1024L);
            when(mockFile.getOriginalFilename()).thenReturn("test.jpeg");
            when(mockFile.getContentType()).thenReturn("image/jpeg");
            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(fileContent));

            mockedFiles.when(() -> Files.newOutputStream(any(), any(), any()))
                    .thenReturn(new java.io.ByteArrayOutputStream());

            String result = imageService.storeImage(mockFile, "food");

            assertNotNull(result);
            assertTrue(result.endsWith(".jpeg"));
        }
    }

    @Test
    void testLoadImageAsResource_WrongPrefix() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.createDirectories(any(Path.class)))
                    .thenReturn(null);

            assertThrows(IllegalStateException.class, () -> imageService.loadImageAsResource("vitamin-12345.png"));
        }
    }
}