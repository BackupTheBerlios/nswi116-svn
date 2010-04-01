package nswi116.helloworld;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

import de.fuberlin.wiwiss.d2rq.ModelD2RQ;

public class D2RQMbrainz {

	public static void main(String[] args)
	{
		// Set up the ModelD2RQ using a mapping file
		Model m = new ModelD2RQ("file:data/D2RQ-MusicBrainzDB.n3");
/*		
	    String sparqlQueryString = 
	    	"DESCRIBE <http://musicbrainz.org/artist/835a6d9c-fea0-4a71-ae52-9c4da946433a.html>";
	    	    
	    Query query = QueryFactory.create(sparqlQueryString);	    
	    QueryExecution qexec = QueryExecutionFactory.create(query, m);	    
	    Model resultSet = qexec.execDescribe();
	    
	    resultSet.write(System.out);
	    
/***/	    
	    String sparqlQueryStringPerformer =
	        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
	  		+ "prefix mb: <http://musicbrainz.org/>\n"
	  		+ "SELECT DISTINCT ?performer \n"
	  		+ "WHERE { ?performer a mb:Artist. ?performer rdfs:label 'The Beatles'. }";


	    Query query = QueryFactory.create(sparqlQueryStringPerformer);
	    QueryExecution qexec = QueryExecutionFactory.create(query, m);
	    ResultSet resultSet = qexec.execSelect();

	    while (resultSet.hasNext())
	    {
	    	QuerySolution sol = resultSet.next();

	    	System.out.println(sol.getResource("?performer"));
	    }
/***/
	    

	}
	
}
