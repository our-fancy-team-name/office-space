package com.ourfancyteamname.officespace.neo4j.configurations;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import lombok.Data;

@Data
public class Neo4jDriver implements AutoCloseable {

  private static final String URL = "bolt://localhost:7687";
  private static final String USER = "neo4j";
  private static final String CRE = "neo4j@2021";


  private static final Driver driver;

  static {
    driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "neo4j@2021"));
  }

  public static Session getSession() {
    return driver.session();
  }

  @Override
  public void close() throws Exception {
    driver.close();
  }
}
