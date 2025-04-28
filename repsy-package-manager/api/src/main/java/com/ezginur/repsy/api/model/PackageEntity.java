package com.ezginur.repsy.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "packages", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"packageName", "version"})
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packageName;
    private String version;
    private String author;

    private LocalDateTime createdAt;
}