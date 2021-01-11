package com.ourfancyteamname.officespace.db.repos.view;

import com.ourfancyteamname.officespace.db.entities.view.ClusterNodePackageListView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessListViewRepository extends JpaRepository<ClusterNodePackageListView, UUID>,
    JpaSpecificationExecutor<ClusterNodePackageListView> {

  boolean existsByProductIdAndClusterCurrentNotNull(Integer productId);

  boolean existsBySerialAndClusterCurrentNotNull(String serial);
}
