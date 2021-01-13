package com.ourfancyteamname.officespace.db.repos.view;

import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleListViewRepository extends JpaRepository<UserRoleListView, Integer>,
    JpaSpecificationExecutor<UserRoleListView> {
}
