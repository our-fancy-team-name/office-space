package com.ourfancyteamname.officespace.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ourfancyteamname.officespace.db.entities.Namespace;

@Repository
public interface NamespaceRepository extends JpaRepository<Namespace, Integer> {
}
