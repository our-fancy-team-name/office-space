package com.ourfancyteamname.officespace.db.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ourfancyteamname.officespace.db.entities.Iri;

@Repository
public interface IriRepository extends JpaRepository<Iri, Integer> {

  Optional<Iri> findByNamespaceAndLocalName(String namespace, String localName);

  boolean existsByNamespaceAndLocalName(String namespace, String localName);

}
