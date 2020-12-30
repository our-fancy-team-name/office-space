package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.ProcessCluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessClusterRepository
    extends JpaRepository<ProcessCluster, Integer>, JpaSpecificationExecutor<ProcessCluster> {

  boolean existsByCode(String code);

  Optional<ProcessCluster> findByCode(String code);
}
