package com.ezginur.repsy.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PackageMetadataDTO {

    @NotBlank(message = "packageName zorunludur")
    private String packageName;

    @NotBlank(message = "version zorunludur")
    @Pattern(regexp = "^\\d+\\.\\d+\\.\\d+$", message = "Version formatı geçersiz (örn: 1.0.0)")
    private String version;

    @NotBlank(message = "author zorunludur")
    private String author;
}