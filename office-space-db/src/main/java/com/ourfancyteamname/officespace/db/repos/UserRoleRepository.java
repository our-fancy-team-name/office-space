package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
  List<UserRole> removeByRoleId(int roleId);
}
