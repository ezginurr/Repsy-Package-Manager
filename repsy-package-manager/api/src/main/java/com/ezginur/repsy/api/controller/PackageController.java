package com.ezginur.repsy.api.controller;

import com.ezginur.repsy.api.core.StorageService;
import com.ezginur.repsy.api.dto.PackageMetadataDTO;
import com.ezginur.repsy.api.model.PackageEntity;
import com.ezginur.repsy.api.repository.PackageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class PackageController {

    private final StorageService storageService;
    private final PackageRepository packageRepository;

    @PostMapping("/{packageName}/{version}")
    public ResponseEntity<String> uploadPackage(
            @PathVariable String packageName,
            @PathVariable String version,
            @RequestPart("meta") @Valid PackageMetadataDTO meta,
            @RequestPart("file") MultipartFile file
    ) {
        if (!meta.getPackageName().equals(packageName) || !meta.getVersion().equals(version)) {
            return ResponseEntity.badRequest().body("Path ile meta.json bilgileri uyuşmuyor.");
        }

        boolean exists = packageRepository.findByPackageNameAndVersion(packageName, version).isPresent();
        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Bu paket ve versiyon zaten var.");
        }

        String fullPath = packageName + "/" + version + "/" + file.getOriginalFilename();
        storageService.save(fullPath, file);

        PackageEntity entity = PackageEntity.builder()
                .packageName(packageName)
                .version(version)
                .author(meta.getAuthor())
                .createdAt(LocalDateTime.now())
                .build();

        packageRepository.save(entity);

        return ResponseEntity.ok("Yükleme başarılı: " + fullPath);
    }

    @GetMapping("/{packageName}/{version}/{fileName}")
    public ResponseEntity<byte[]> downloadPackageFile(
            @PathVariable String packageName,
            @PathVariable String version,
            @PathVariable String fileName
    ) {
        try {
            String path = packageName + "/" + version + "/" + fileName;
            InputStream is = storageService.load(path);
            byte[] content = is.readAllBytes();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(content);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(("Dosya bulunamadı: " + e.getMessage()).getBytes());
        }
    }
}