package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.ClusterNodePackage;
import com.ourfancyteamname.officespace.db.entities.ClusterNodePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClusterNodePackageRepository extends JpaRepository<ClusterNodePackage, Integer> {

  List<ClusterNodePath> removeByClusterNodeId(Integer clusterNodeIdTo);
}
