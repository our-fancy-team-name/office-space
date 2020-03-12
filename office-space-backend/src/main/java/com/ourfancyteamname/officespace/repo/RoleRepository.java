package com.ourfancyteamname.officespace.repo;

import com.ourfancyteamname.officespace.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  @Query("select r from Role r " +
      "join UserRole ur on r.id = ur.roleId " +
      "where ur.userId= :userId")
  List<Role> findByUserId(@Param("userId") Integer userId);

  @Query("select r from Role r " +
      "join UserRole ur on r.id = ur.roleId " +
      "where ur.userId= :userId " +
      "and ur.isRecentlyUse = true")
  Optional<Role> findLastUsageByUserId(@Param("userId") Integer userId);

}
