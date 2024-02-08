package com.company.attach;

import com.company.attach.dtos.AttachResp;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/attach")
public class AttachController {
    private final AttachService attachService;

    public AttachController(AttachService attachService) {
        this.attachService = attachService;
    }

    @PostMapping("/upload")
    public ResponseEntity<AttachResp> upload(@RequestParam("file") MultipartFile file) {
        AttachResp fileName = attachService.save(file);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/get-image-by-id/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable UUID id) {

        byte[] imageBytes = attachService.getImage(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @GetMapping("/download-pdf/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") UUID id) {
        return attachService.download(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        ResponseEntity<?> response = attachService.delete(id);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Image with ID " + id + " has been deleted successfully.");
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the image with ID " + id + ".");
        }
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(attachService.getAllPdf());
    }
}