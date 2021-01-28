package com.ourfancyteamname.officespace.db.repos.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ourfancyteamname.officespace.db.entities.view.UserRoleListView;

@Repository
public interface UserRoleListViewRepository extends JpaRepository<UserRoleListView, Integer>,
    JpaSpecificationExecutor<UserRoleListView> {
}
