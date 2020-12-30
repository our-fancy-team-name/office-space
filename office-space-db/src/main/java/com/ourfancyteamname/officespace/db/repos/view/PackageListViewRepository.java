package com.ourfancyteamname.officespace.db.repos.view;

import com.ourfancyteamname.officespace.db.entities.view.PackageListView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageListViewRepository
    extends JpaRepository<PackageListView, Integer>, JpaSpecificationExecutor<PackageListView> {
}
