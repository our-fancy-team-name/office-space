package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.ClusterNode;
import com.ourfancyteamname.officespace.dtos.ProcessGeneralDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClusterNodeRepository extends JpaRepository<ClusterNode, Integer> {

  @Query("select " +
      "new com.ourfancyteamname.officespace.dtos.ProcessGeneralDto(cn.id, pn.code, pn.name, pn.description) " +
      "from ProcessNode pn " +
      "left join ClusterNode cn on cn.nodeId = pn.id " +
      "left join ProcessCluster pc on cn.clusterId = pc.id " +
      "where pc.id = :clusterId")
  List<ProcessGeneralDto> getClusterNodesByClusterId(@Param("clusterId") int clusterId);
}
