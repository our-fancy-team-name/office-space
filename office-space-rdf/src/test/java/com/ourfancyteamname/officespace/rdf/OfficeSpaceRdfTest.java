package com.ourfancyteamname.officespace.rdf;

import java.io.File;
import java.util.List;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.repository.util.Repositories;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;

public class OfficeSpaceRdfTest {

  public static void main(String[] args) {
    Repository repo = new SailRepository(new MemoryStore());
    File dataDir = new File("C:\\temp\\myRepository\\");
    Repository repoMemDir = new SailRepository(new MemoryStore(dataDir));
    File dataNative = new File("C:\\temp\\myNative\\");
    Repository repoNative = new SailRepository(new NativeStore(dataNative));
    String ex = "http://example.org/";
    IRI picasso = Values.iri(ex, "Picasso");
    IRI artist = Values.iri(ex, "Artist");

    Model model = new TreeModel();
    model.add(picasso, RDF.TYPE, artist);
    model.add(picasso, FOAF.FIRST_NAME, Values.literal("Pablo"));
//    System.out.println(model);
    ValueFactory vf = Values.getValueFactory();
    model.forEach(System.out::println);

    ModelBuilder modelBuilder = new ModelBuilder();
    Model b = modelBuilder.setNamespace("ex", "http://example.org/")
        .subject("ex:Picasso")
        .add(RDF.TYPE, "ex:Artist")
        .add(FOAF.FIRST_NAME, vf.createLiteral("dang", "en"))
        .subject("ex:Dang")
        .add(RDF.TYPE, "ex:Developer")
        .build();
//    b.forEach(System.out::println);
//    try (var connection = repoNative.getConnection()) {
//      connection.add(b);
//    }
    Repositories.consume(repoNative, r -> r.add(b));

    List<BindingSet> objects =
        Repositories.tupleQuery(repoNative, "SELECT ?x ?p ?y WHERE { ?x ?p ?y }", r -> QueryResults.asList(r));

    try (var connection = repoNative.getConnection()) {
      String queryString = "SELECT ?x ?p ?y WHERE { ?x ?p ?y } ";
      TupleQuery tupleQuery = connection.prepareTupleQuery(queryString);
      try (TupleQueryResult result = tupleQuery.evaluate()) {
        while (result.hasNext()) {  // iterate over the result
          BindingSet bindingSet = result.next();
          Value valueOfX = bindingSet.getValue("x");
          Value valueOfY = bindingSet.getValue("y");
          Value valueOfP = bindingSet.getValue("p");
        }
      }

    }


  }
}
