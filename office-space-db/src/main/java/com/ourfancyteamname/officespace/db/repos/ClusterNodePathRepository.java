package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.ClusterNodePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClusterNodePathRepository extends JpaRepository<ClusterNodePath, Integer> {

  @Query("select cnp from ClusterNodePath cnp " +
      "left join ClusterNode cn on cn.id = cnp.clusterNodeIdTo " +
      "left join ProcessCluster pc on pc.id = cn.clusterId " +
      "where pc.id= :clusterId")
  List<ClusterNodePath> findAllByCLusterId(@Param("clusterId") Integer clusterId);

  List<ClusterNodePath> findByClusterNodeIdTo(Integer clusterNodeIdTo);

  List<ClusterNodePath> findByClusterNodeIdFrom(Integer clusterNodeIdFrom);

  List<ClusterNodePath> removeByClusterNodeIdToOrClusterNodeIdFrom(Integer clusterNodeIdTo, Integer clusterNodeIdFrom);

}
