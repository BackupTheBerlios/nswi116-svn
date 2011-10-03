package mi_swe.jena.sparql;

import mi_swe.jena.Reasoning;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;

public class Construct {
	public static String queryString =
		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
		"PREFIX swe: <http://www.fit.cvut.cz/subjects/mi-swe#>" +
		"CONSTRUCT { ?property a swe:ConstructedProperty}" +
		"WHERE {?property a rdf:Property}";
	public static QueryExecution prepareHelloQueryExecution(String sparqlQueryString)
	{
		Model inferedHelloModel = Reasoning.makeInferedHelloModel();
		//build a query object from string 
		Query query = QueryFactory.create(sparqlQueryString);
		//attach the model to the query object
		QueryExecution qexec = QueryExecutionFactory.create(query, inferedHelloModel);
		return qexec;
	}
	public static void main(String[] args) {
		QueryExecution qexec = prepareHelloQueryExecution(queryString);
		//execute the construct query !!!
		Model constructedModel = qexec.execConstruct();
		constructedModel.write(System.out, "RDF/XML-ABBREV");
	}
}
