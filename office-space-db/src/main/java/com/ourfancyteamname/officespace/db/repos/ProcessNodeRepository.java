package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.ProcessNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessNodeRepository
    extends JpaRepository<ProcessNode, Integer>, JpaSpecificationExecutor<ProcessNode> {
  
  boolean existsByCode(String code);
}
