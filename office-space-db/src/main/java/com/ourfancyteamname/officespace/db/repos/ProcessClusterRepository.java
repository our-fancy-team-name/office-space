package com.ourfancyteamname.officespace.db.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ourfancyteamname.officespace.db.entities.ProcessCluster;

@Repository
public interface ProcessClusterRepository
    extends JpaRepository<ProcessCluster, Integer>, JpaSpecificationExecutor<ProcessCluster> {

  boolean existsByCode(String code);

  Optional<ProcessCluster> findByCode(String code);
}
