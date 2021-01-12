package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.ClusterNodePackage;
import com.ourfancyteamname.officespace.enums.PackageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClusterNodePackageRepository extends JpaRepository<ClusterNodePackage, Integer> {

  List<ClusterNodePackage> removeByClusterNodeId(Integer clusterNodeIdTo);

  Optional<ClusterNodePackage> findByPackageIdAndClusterNodeIdAndStatus(Integer packageId, Integer clusterNodeId,
      PackageStatus status);
}
