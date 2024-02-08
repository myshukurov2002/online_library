package com.company.attach;

import com.company.attach.dtos.AttachResp;
import com.company.attach.entity.AttachEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AttachService {
    @Value("${attach.folder.name}")
    private String FOLDER_NAME;

    @Value("${server.url}")
    private String SERVER_URL;

    private final AttachRepository attachRepository;

    public AttachResp save(MultipartFile file) {

        String pathFolder = getYmDString(); // 2022/04/23
        File folder = new File(FOLDER_NAME + "/" + pathFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        UUID key = UUID.randomUUID();
        String extension = getExtension(file.getOriginalFilename());
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FOLDER_NAME + "/" + pathFolder + "/" + key + "." + extension);

            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setId(key);
            entity.setPath(pathFolder); // 2022/04/23
            entity.setSize(file.getSize());
            entity.setOriginalName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setVisibility(true);

            attachRepository.save(entity);

            return toDTO(entity);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Resource> download(UUID id) {
        AttachEntity entity = get(id);

        try {
            String filePath = Paths.get(FOLDER_NAME, entity.getPath(), id + "." + entity.getExtension()).toString();
            Path file = Paths.get(filePath);

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDisposition(ContentDisposition.builder("attachment")
                        .filename(entity.getOriginalName()).build());

                return ResponseEntity.ok().headers(headers).body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<?> delete(UUID id) {

        AttachEntity entity = get(id);
        try {
            String url = FOLDER_NAME + "/" + entity.getPath() + "/" + id + "." + entity.getExtension();
            File file = new File(url);
            if (file.exists()) {
                if (file.delete()) {
                    attachRepository.delete(entity);

                    return ResponseEntity.ok("File with ID " + id + " has been deleted successfully.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the file with ID " + id + ".");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the file with ID " + id + ".");
        }
    }

    public List<AttachResp> getAllPdf() {

        return attachRepository
                .findAllByVisibilityTrue()
                .stream()
                .filter(a -> a.getExtension().equals("pdf"))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AttachResp toDTO(AttachEntity entity) {

        AttachResp dto = new AttachResp();
        dto.setId(entity.getId());
        if (entity.getExtension().equals("pdf")) {
            dto.setUrl(getPdfUrl(entity.getId()));
        } else {
            dto.setUrl(getPhotoUrl(entity.getId()));
        }
        dto.setOriginalName(entity.getOriginalName());
        dto.setExtension(entity.getExtension());
        dto.setSize(entity.getSize());
        return dto;
    }

    public String getPdfUrl(UUID id) {
        return SERVER_URL + "/api/v1/attach/download-pdf/" + id;
    }
    public String getPhotoUrl(UUID id) {
        return SERVER_URL + "/api/v1/attach/get-image-by-id/" + id;
    }

    public AttachEntity get(UUID id) {

        try {
            return attachRepository
                    .findById(id)
                    .orElseThrow(() -> new IOException("file.not.found"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getYmDString() {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public byte[] getImage(UUID id) {

        AttachEntity entity = get(id);
        try {
            String url = FOLDER_NAME + "/" + entity.getPath() + "/" + id + "." + entity.getExtension();
            File file = new File(url);

            byte[] bytes = new byte[(int) file.length()];

            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
