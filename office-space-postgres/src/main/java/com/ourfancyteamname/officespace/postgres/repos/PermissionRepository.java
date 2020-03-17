package com.ourfancyteamname.officespace.postgres.repos;

import com.ourfancyteamname.officespace.postgres.entities.Permission;
import com.ourfancyteamname.officespace.postgres.enums.PermissionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

  @Query("select p.code from Permission p " +
      "join RolePermission rp on rp.permissionId = p.id " +
      "where rp.roleId = :roleId")
  List<PermissionCode> findPermissionCodeByRoleId(@Param("roleId") Integer roleId);
}
