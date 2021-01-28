package com.ourfancyteamname.officespace.db.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ourfancyteamname.officespace.db.entities.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {

  boolean existsBySerialNumber(String serialNumber);

  boolean existsByProductId(Integer productId);

  Optional<Package> findBySerialNumber(String serialNumber);

}
