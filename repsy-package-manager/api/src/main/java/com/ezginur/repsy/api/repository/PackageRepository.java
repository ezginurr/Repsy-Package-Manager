package com.ezginur.repsy.api.repository;

import com.ezginur.repsy.api.model.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
    Optional<PackageEntity> findByPackageNameAndVersion(String packageName, String version);
}