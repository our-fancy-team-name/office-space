package com.ourfancyteamname.officespace.db.repos;

import com.ourfancyteamname.officespace.db.entities.User;
import com.ourfancyteamname.officespace.enums.CacheName;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);

  @Override
  @CacheEvict(cacheNames = CacheName.USER_PRINCIPLE, allEntries = true)
  <S extends User> S save(S s);

  @Override
  @CacheEvict(cacheNames = CacheName.USER_PRINCIPLE, allEntries = true)
  void deleteById(Integer integer);
}
