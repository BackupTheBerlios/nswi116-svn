package nswi116.data;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

import de.fuberlin.wiwiss.d2rq.ModelD2RQ;

public class MusicBrainz
{
	protected Model mbModel = null;
	
	public MusicBrainz()
	{
		mbModel = new ModelD2RQ("file:data/D2RQ-MusicBrainzDB.n3");		
	}

	public Model addArtistData(Resource artist)
	{
	    String sparqlQueryString = 
	    	"DESCRIBE <" + artist.toString() + ">";
	    	    
	    Query query = QueryFactory.create(sparqlQueryString);	    
	    QueryExecution qexec = QueryExecutionFactory.create(query, mbModel);	    
	    Model resultSet = qexec.execDescribe();
	    
	    return resultSet;
	    	    		
	}
}
