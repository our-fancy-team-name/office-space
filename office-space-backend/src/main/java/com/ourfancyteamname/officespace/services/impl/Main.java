package com.ourfancyteamname.officespace.services.impl;

import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;

import com.ourfancyteamname.officespace.neo4j.configurations.Neo4jDriver;


public class Main {

  public static void main(String[] args) {
    try (Session session = Neo4jDriver.getSession()) {
      String greeting = session.writeTransaction(tx -> {
        Result result = tx.run("CREATE (a:Greeting) " +
                "SET a.message = $message " +
                "RETURN a.message + ', from node ' + id(a)",
            Values.parameters("message", "ahihi do ngok"));
        return result.single().get(0).asString();
      });
      System.out.println(greeting);
    }

  }

}
