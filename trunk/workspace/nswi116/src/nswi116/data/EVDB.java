package nswi116.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.rdf.model.Resource;

public class EVDB
{
	protected RDFReader reader = null;
	protected Model evdbModel = null;
	
	public EVDB()
	{
		evdbModel = ModelFactory.createDefaultModel();
		reader = evdbModel.getReader("GRDDL");
		reader.setProperty("grddl.xml-xform", "file:data/evdb.xsl");
	}

	public Model getEventsForArtist(String artistLabel) throws UnsupportedEncodingException
	{
		Model eventsModel = ModelFactory.createDefaultModel();
		
		String url = "http://api.eventful.com/rest/events/search?" +
				"app_key=9Lvz5Drd6NNB8w5c&" +
				"keywords=" + URLEncoder.encode(artistLabel, "UTF-8")+
				"&date=Future";		
		
		reader.read(eventsModel, url);				
		
		return eventsModel;				
	}

	public Model getEventsForArtistLinked(Resource artist, String artistLabel) throws UnsupportedEncodingException
	{
		Model m = getEventsForArtist(artistLabel);

/**/		
		Integration.readStaticOntologiesToModel(m);
		m = Integration.makeInferedModel(m);
/**/		

//		m.write(System.out);
		
		String sparqlQueryString =
		    "PREFIX meex: <http://swa.cefriel.it/meex#>\n"
		  + "PREFIX evdb: <http://eventuful.com/>\n"
		  + "CONSTRUCT {<" + artist + "> meex:performsEvent ?event.}\n"
		  + "WHERE {?event a evdb:Event.}";
		
	    Query query = QueryFactory.create(sparqlQueryString);
	    QueryExecution qexec = QueryExecutionFactory.create(query, m);
	    Model resultSet = qexec.execConstruct();
	    
	    
	    System.out.println(sparqlQueryString);
	    resultSet.write(System.out);


		
		//TODO: Link the artist information retrieved from MusicMoz and MusicBrainz 
		//to the event information retrieved from EVDB.
		
		return m;
	}

}
