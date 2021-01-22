package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.Role;
import com.ourfancyteamname.officespace.enums.CacheName;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

  @Query("""
      select r from Role r 
      left join UserRole ur on r.id = ur.roleId 
      where ur.userId= :userId""")
  List<Role> findByUserId(@Param("userId") Integer userId);

  @Query("""
      select r from Role r 
      left join UserRole ur on r.id = ur.roleId 
      where ur.userId= :userId and ur.isRecentlyUse = true""")
  Optional<Role> findLastUsageByUserId(@Param("userId") Integer userId);

  boolean existsByCode(String code);

  Optional<Role> findByCode(String code);

  @Query("select r.code from Role r")
  List<String> findAllCode();

  @Override
  @CacheEvict(cacheNames = CacheName.USER_PRINCIPLE, allEntries = true)
  <S extends Role> S save(S s);

  @Override
  @CacheEvict(cacheNames = CacheName.USER_PRINCIPLE, allEntries = true)
  void deleteById(Integer integer);
}
