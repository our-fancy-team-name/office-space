package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
  List<RolePermission> removeByRoleId(int roleId);
}
