package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.UserRole;
import com.ourfancyteamname.officespace.enums.CacheName;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

  @CacheEvict(cacheNames = CacheName.USER_PRINCIPLE, allEntries = true)
  List<UserRole> removeByRoleId(int roleId);

  @CacheEvict(cacheNames = CacheName.USER_PRINCIPLE, allEntries = true)
  List<UserRole> removeByUserId(int roleId);

  @Override
  @CacheEvict(cacheNames = CacheName.USER_PRINCIPLE, allEntries = true)
  <S extends UserRole> List<S> saveAll(Iterable<S> iterable);
}
