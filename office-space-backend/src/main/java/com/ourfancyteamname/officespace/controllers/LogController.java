package com.ourfancyteamname.officespace.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.types.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourfancyteamname.officespace.db.entities.DataRespone;
import com.ourfancyteamname.officespace.db.entities.Log;
import com.ourfancyteamname.officespace.db.services.LogService;

@RestController
@RequestMapping("/api/log")
public class LogController {

  @Autowired
  private LogService logService;

  @PostMapping("/insertLog")
  public Set<String>insertLog(@RequestBody Log log) {
    logService.insertLog(log);
    Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "neo4j@2021"));
    Set<String> set = new HashSet<>();
    try (Session session = driver.session()) {
      Result result = session.run("MATCH (" + log.getMessage() + ")-[]-(a) RETURN a");
      while (result.hasNext()) {
        Record a = result.next();
        Node node = a.values().get(0).asNode();
        String name = node.labels().iterator().next() + " ";
        Iterator z = node.values().iterator();
        while(z.hasNext()){
          name += z.next()   + " ";
        }
        set.add(name);
      }
    } catch (Exception e) {
      System.out.println("abc");
    }
    return set;
  }
}
