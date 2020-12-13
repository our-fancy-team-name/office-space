package com.ourfancyteamname.officespace.db.repos.view;

import com.ourfancyteamname.officespace.db.entities.view.RoleUserListView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserListViewRepository extends JpaRepository<RoleUserListView, Integer>,
    JpaSpecificationExecutor<RoleUserListView> {
}
