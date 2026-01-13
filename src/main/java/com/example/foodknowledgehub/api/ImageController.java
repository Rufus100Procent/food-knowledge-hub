package com.example.foodknowledgehub.api;


import com.example.foodknowledgehub.service.image.ImageService;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @org.springframework.web.bind.annotation.PostMapping("/upload")
    public org.springframework.http.ResponseEntity<String> upload(
            @org.springframework.web.bind.annotation.RequestParam("file")
            org.springframework.web.multipart.MultipartFile file,
            @org.springframework.web.bind.annotation.RequestParam("sourceType")
            String sourceType
    ) {
        return org.springframework.http.ResponseEntity.ok(
                imageService.storeImage(file, sourceType)
        );
    }

    @org.springframework.web.bind.annotation.GetMapping("/{filename}")
    public org.springframework.http.ResponseEntity<org.springframework.core.io.Resource> get(
            @org.springframework.web.bind.annotation.PathVariable String filename
    ) {
        org.springframework.core.io.Resource resource =
                imageService.loadImageAsResource(filename);

        return org.springframework.http.ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + filename + "\"")
                .body(resource);
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{filename}")
    public org.springframework.http.ResponseEntity<Void> delete(
            @org.springframework.web.bind.annotation.PathVariable String filename
    ) {
        imageService.deleteImage(filename);
        return org.springframework.http.ResponseEntity.noContent().build();
    }
}
