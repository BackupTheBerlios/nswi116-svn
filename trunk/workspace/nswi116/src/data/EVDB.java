package data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFReader;

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

}
