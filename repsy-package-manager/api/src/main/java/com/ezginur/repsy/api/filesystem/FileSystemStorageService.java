package com.ezginur.repsy.api.filesystem;

import com.ezginur.repsy.api.core.StorageService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@Service("fileSystem")
@Primary
public class FileSystemStorageService implements StorageService {

    private final Path root = Paths.get("local-packages");

    public FileSystemStorageService() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Dosya dizini oluşturulamadı", e);
        }
    }

    @Override
    public void save(String path, MultipartFile file) {
        try {
            Path target = root.resolve(path);
            Files.createDirectories(target.getParent());
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Dosya kaydedilemedi: " + path, e);
        }
    }

    @Override
    public InputStream load(String path) {
        try {
            return Files.newInputStream(root.resolve(path));
        } catch (IOException e) {
            throw new RuntimeException("Dosya okunamadı: " + path, e);
        }
    }
}