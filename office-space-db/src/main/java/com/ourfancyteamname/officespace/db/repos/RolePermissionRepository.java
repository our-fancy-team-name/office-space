package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.RolePermission;
import com.ourfancyteamname.officespace.enums.CacheName;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

  @CacheEvict(cacheNames = CacheName.USER_PRINCIPLE, allEntries = true)
  List<RolePermission> removeByRoleId(int roleId);

  @Override
  @CacheEvict(cacheNames = CacheName.USER_PRINCIPLE, allEntries = true)
  <S extends RolePermission> List<S> saveAll(Iterable<S> iterable);

  @Override
  @CacheEvict(cacheNames = CacheName.USER_PRINCIPLE, allEntries = true)
  <S extends RolePermission> S save(S s);

  @Override
  @CacheEvict(cacheNames = CacheName.USER_PRINCIPLE, allEntries = true)
  void deleteById(Integer integer);
}
