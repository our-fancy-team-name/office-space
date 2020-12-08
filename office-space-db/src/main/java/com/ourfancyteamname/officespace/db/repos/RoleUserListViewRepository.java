package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.view.RoleUserListView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserListViewRepository extends JpaRepository<RoleUserListView, Integer>,
    JpaSpecificationExecutor<RoleUserListView> {
}
