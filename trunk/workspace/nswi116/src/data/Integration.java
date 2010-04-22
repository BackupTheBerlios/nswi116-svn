package data;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

public class Integration
{
	protected MusicMoz musicMoz = null;
	protected MusicBrainz musicBrainz = null;
	protected EVDB evdb = null;
	protected String dataDirUrlPrefix = null;
	protected Reasoner reasoner = null; 
	protected Model mainModel = null;
		
	public Integration() throws IOException
	{
		java.util.logging.LogManager.getLogManager().reset();
		
	    File cur_dir = new File(".");
	    dataDirUrlPrefix = "file:///" + cur_dir.getCanonicalPath() + "/data/";
	    
	    reasoner = ReasonerRegistry.getOWLMicroReasoner();

		musicMoz = new MusicMoz();
		System.err.println("initalized - MusicMoz");
		
		musicBrainz = new MusicBrainz();
		System.err.println("initalized - MusicBrainz");
		
		evdb = new EVDB();
		System.err.println("initalized - EVDB");
				
		initMainModel();
		System.err.println("initalized - main model");
	}
	
	protected void initMainModel()
	{
		mainModel = musicMoz.getModel();
		readStaticOntologiesToModel(mainModel);
		mainModel = makeInferedModel(mainModel);		
	}
	
	protected Model integrateDataForMusicStyle(String music_style) throws UnsupportedEncodingException
	{
		String sparqlQueryString =
		      "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX meex: <http://swa.cefriel.it/meex#>\n"
				+ "SELECT DISTINCT ?performer ?artist_name \n"
				+ "WHERE { ?performer meex:performsStyle ?style.\n" 
				+ "        ?performer rdfs:label ?artist_name.\n"
				+ "        ?style rdfs:label \"" + music_style + "\".}";
		
	    Query query = QueryFactory.create(sparqlQueryString);
	    QueryExecution qexec = QueryExecutionFactory.create(query, mainModel);
	    ResultSet resultSet = qexec.execSelect();
	    
	    Model resultModel = ModelFactory.createDefaultModel();
	    
	    while (resultSet.hasNext())
	    {
	    	QuerySolution sol = resultSet.next();
	    	
	    	Resource artist = sol.getResource("?performer");
	    	Literal artist_name = sol.getLiteral("?artist_name");

	    	System.err.println("looup for: " + artist_name + "\t\t" + artist);
	    	
	    	Model mbModel = musicBrainz.addArtistData(artist);
	    	resultModel.add(mbModel);
	    	
	    	Model evdbModel = evdb.getEventsForArtist(artist_name.toString());
	    	resultModel.add(evdbModel);
	    }
		
		return resultModel;
	}
		

	protected Model makeInferedModel(Model origModel)
	{
	    Model inferedModel = ModelFactory.createInfModel(reasoner, origModel);
	    return inferedModel;
	}

	protected void readStaticOntologiesToModel(Model model)
	{
		model.read(dataDirUrlPrefix + "Meex.n3", "", "N3");
	    model.read(dataDirUrlPrefix + "MusicMoz.n3", "", "N3");
	    model.read(dataDirUrlPrefix + "MusicBrainz.n3", "", "N3");
	    model.read(dataDirUrlPrefix + "EVDB.n3", "", "N3");		
	    model.read(dataDirUrlPrefix + "Google.n3", "", "N3");
	    model.read(dataDirUrlPrefix + "MeexBindings.n3", "", "N3");
	    
/*
	    model.read(dataDirUrlPrefix + "SampleInstance-MusicMoz.n3", "", "N3");
	    model.read(dataDirUrlPrefix + "SampleInstance-MusicBrainz.n3", "", "N3");
	    model.read(dataDirUrlPrefix + "google_sample.n3", "", "N3");
*/	    
	}
	
	
	public static void main(String[] args) throws IOException
	{
		Integration data_integration = new Integration();
		
		Model integred_model =  data_integration.integrateDataForMusicStyle("Christian Rock");
		
		integred_model.write(System.out);		
	}
}
