package com.ezginur.repsy.api.core;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageService {
    void save(String path, MultipartFile file);
    InputStream load(String path);
}