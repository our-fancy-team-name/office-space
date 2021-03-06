package com.ourfancyteamname.officespace.db.repos;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ourfancyteamname.officespace.db.entities.RolePermission;
import com.ourfancyteamname.officespace.enums.CacheName;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

  @CacheEvict(cacheNames = {CacheName.USER_PRINCIPLE, CacheName.PERMISSIONS}, allEntries = true)
  List<RolePermission> removeByRoleId(int roleId);

  @Override
  @CacheEvict(cacheNames = {CacheName.USER_PRINCIPLE, CacheName.PERMISSIONS}, allEntries = true)
  <S extends RolePermission> List<S> saveAll(Iterable<S> iterable);

  @Override
  @CacheEvict(cacheNames = {CacheName.USER_PRINCIPLE, CacheName.PERMISSIONS}, allEntries = true)
  <S extends RolePermission> S save(S s);

  @Override
  @CacheEvict(cacheNames = {CacheName.USER_PRINCIPLE, CacheName.PERMISSIONS}, allEntries = true)
  void deleteById(Integer integer);
}
