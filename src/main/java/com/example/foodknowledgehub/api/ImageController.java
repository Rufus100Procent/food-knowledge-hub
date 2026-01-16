package com.example.foodknowledgehub.api;


import com.example.foodknowledgehub.service.image.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @org.springframework.web.bind.annotation.PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file")
            MultipartFile file,
            @RequestParam("sourceType")
            String sourceType
    ) {
        return ResponseEntity.ok(
                imageService.storeImage(file, sourceType)
        );
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> get(
            @PathVariable String filename
    ) {
        Resource resource =
                imageService.loadImageAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + filename + "\"")
                .body(resource);
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<Void> delete(
            @PathVariable String filename
    ) {
        imageService.deleteImage(filename);
        return ResponseEntity.noContent().build();
    }
}
