package com.ourfancyteamname.officespace.db.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ourfancyteamname.officespace.db.entities.ClusterNode;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;


@Repository
public interface ClusterNodeRepository extends JpaRepository<ClusterNode, Integer> {

  boolean existsByClusterIdAndNodeId(Integer clusterId, Integer nodeId);

  @Query("""
      select new com.ourfancyteamname.officespace.dtos.ProcessGeneralDto(cn.id, pn.code, pn.name, pn.description) 
      from ProcessNode pn 
      left join ClusterNode cn on cn.nodeId = pn.id 
      left join ProcessCluster pc on cn.clusterId = pc.id 
      where pc.id = :clusterId""")
  List<ProcessGeneralDto> getClusterNodesByClusterId(@Param("clusterId") int clusterId);

  @Query("""
      select pc.code from ClusterNode cn 
      left join ProcessCluster pc on cn.clusterId = pc.id 
      WHERE cn.id = :clusterId""")
  Optional<String> getClusterSchematic(@Param("clusterId") int clusterId);
}
