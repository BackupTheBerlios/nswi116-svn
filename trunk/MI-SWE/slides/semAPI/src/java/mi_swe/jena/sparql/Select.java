package mi_swe.jena.sparql;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;

public class Select {
	public static String queryString =
		"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
		"SELECT ?resource ?label " +
		"WHERE {?resource rdfs:label ?label}";
	public static void main(String[] args) {
		QueryExecution qexec = Construct.prepareHelloQueryExecution(queryString);		
		ResultSet resultSet = qexec.execSelect(); //select query !!!
		//iterate over the results
	    while (resultSet.hasNext())  {
	    	QuerySolution querySolution = resultSet.next();
	    	//obtain a resource form the variable name
	    	Resource resource = querySolution.getResource("?resource");
	    	//obtain a literal form the variable name
	    	Literal label = querySolution.getLiteral("?label");
	    	//print resource URI
	    	System.out.println(resource.getURI());
	    	//obtain string value and language code of the literal and print 
	    	System.out.println(label.getString());
	    	System.out.println(label.getLanguage());
	    }
	}
}
