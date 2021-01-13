package com.ourfancyteamname.officespace.db.repos.view;

import com.ourfancyteamname.officespace.db.entities.view.ProcessListView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProcessListViewRepository extends JpaRepository<ProcessListView, UUID>,
    JpaSpecificationExecutor<ProcessListView> {

  boolean existsByProductIdAndClusterCurrentNotNull(Integer productId);

  boolean existsBySerialAndClusterCurrentNotNull(String serial);

  @Query("SELECT distinct " +
      "new com.ourfancyteamname.officespace.db.entities.view.ProcessListView(cnplv.serial, cnplv.packageId) " +
      "from ProcessListView cnplv " +
      "where " +
      "( " +
      "cnplv.clusterNodeNext like '%' || ',' || :clusterNodeId || ',' || '%' " +
      " or cnplv.clusterNodeNext like '%' || ',' || :clusterNodeId " +
      " or cnplv.clusterNodeNext like :clusterNodeId || ',' || '%' " +
      " or cnplv.clusterNodeNext like :clusterNodeId or " +
      "cnplv.clusterCurrent like :clusterNodeId " +
      ") " +
      "and cnplv.clusterSchematic = :clusterSchematic")
  List<ProcessListView> findPossiblePkgsOnMiddleNode(@Param("clusterNodeId") String clusterNodeId,
      @Param("clusterSchematic") String clusterSchematic);

  @Query("SELECT distinct " +
      "new com.ourfancyteamname.officespace.db.entities.view.ProcessListView(cnplv.serial, cnplv.packageId) " +
      "from ProcessListView cnplv " +
      "where cnplv.clusterSchematic = :clusterSchematic")
  List<ProcessListView> findPossiblePkgsOnStartNode(@Param("clusterSchematic") String clusterSchematic);
}
