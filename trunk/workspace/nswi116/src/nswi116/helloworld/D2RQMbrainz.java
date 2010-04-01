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
		
	    String sparqlQueryString = 
		    "PREFIX meex: <http://swa.cefriel.it/meex#>\n" +
	    	"PREFIX mbr: <http://musicbrainz.org/>\n"
	    	+ "DESCRIBE <mbr:artist/835a6d9c-fea0-4a71-ae52-9c4da946433a.html>";
	    	//+ "DESCRIBE <mb:artist/a35237a0-4f47-40a6-b6f3-1e786db23402.html>";
	    
	    
/*	    
	    Query query = QueryFactory.create(sparqlQueryString);
	    QueryExecution qexec = QueryExecutionFactory.create(query, model2);
	    Model resultModel = qexec.execDescribe();
		resultModel.write(System.out);
/**/		
	    Query query = QueryFactory.create(sparqlQueryString);	    
	    QueryExecution qexec = QueryExecutionFactory.create(query, m);	    
	    Model resultSet = qexec.execDescribe();
	    
	    resultSet.write(System.out);
	    

	}
	
}
