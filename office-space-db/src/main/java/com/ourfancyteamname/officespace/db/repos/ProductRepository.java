package com.ourfancyteamname.officespace.db.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ourfancyteamname.officespace.db.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

  boolean existsByPartNumber(String partNumber);

  boolean existsByName(String name);

  Optional<Product> findByName(String name);

  Optional<Product> findByPartNumber(String partNumber);
}
