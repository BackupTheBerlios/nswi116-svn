package nswi116.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import nswi116.babel.RDFXML2JSON;

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
	protected static String dataDirUrlPrefix = null;
	protected static Reasoner reasoner = null; 
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
	
	public Model integrateDataForMusicStyle(String music_style) throws UnsupportedEncodingException
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

	    	System.err.print("looup for: " + artist_name + "\t\t" + artist);
	    	
	    	Model mbModel = musicBrainz.addArtistData(artist);
	    	resultModel.add(mbModel);
	    	System.err.print("\t mb(" + mbModel.size()+")\t");	    	 
	    	
	    	Model evdbModel = evdb.getEventsForArtistLinked(artist, artist_name.toString());
	    	resultModel.add(evdbModel);
	    	System.err.println("evdb(" + evdbModel.size()+')');	    	 
	    }
		
	    resultModel.add(mainModel);	    
	    return makeInferedModel(resultModel);
	}
		
	public static void convertAndWriteModelToJson(Model model, final Writer output) throws IOException
	{
	    final PipedInputStream pipe_in = new PipedInputStream();  
	    final PipedOutputStream pipe_out = new PipedOutputStream(pipe_in);
	    	    	    
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					RDFXML2JSON.convert(new InputStreamReader(pipe_in),	output);
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
		}.start();
		
	    model.write(pipe_out);
	    pipe_out.close();
	}

	public static Model constructModelForPresentation(Model integretedModel)
	{
		String sparqlQueryString =
	      	  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
			+ "PREFIX meex: <http://swa.cefriel.it/meex#>\n"
			+ "PREFIX gd:   <http://maps.google.com/>\n"
			+ "CONSTRUCT {\n" 
			+ "?event	rdfs:label	?event_label;\n" 
			+ "			meex:hasPerformer	?artist;\n"
			+ "			a	meex:Event.\n"
			+ "?artist	rdfs:label ?artist_label;\n"
			+ "			a	meex:Performer.}\n"
			+ "WHERE {\n"
			+ "?artist	meex:performsEvent	?event;\n" 
			+ "			rdfs:label	?artist_label.\n"
			+ "?event	rdfs:label	?event_label;\n"
			+ "			a meex:Event.}\n";
//			+ "		meex:hasWhen ?when;"
//			+ "		meex:hasWhere ?where."
//			+ "?when gd:startTime ?when_startTime;"
//			+ "		gd:endTime ?when_endTime."
//			+ "?where gd:label ?where_label;"
//			+ "		gd:postalAddress ?where_address;"
//			+ "		gd:hasGeoPt ?geoPt."
//			+ "?geoPt gd:lat ?where_lat;"
//			+ "		gd:lon ?where_lon."
//			+ "?performer meex:performsEvent ?event;"
//			+ "		rdfs:label ?performer_label;"
//			+ "		meex:fromCountry ?fromCountry.}";
		
	    Query query = QueryFactory.create(sparqlQueryString);
	    QueryExecution qexec = QueryExecutionFactory.create(query, integretedModel);
	    Model resultSet = qexec.execConstruct();
	    
		String realtedArtistsQueryString =
	      	  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
			+ "PREFIX meex: <http://swa.cefriel.it/meex#>\n"
			+ "CONSTRUCT {\n"
			+ "?related	rdfs:label	?rel_label;\n"
			+ "			a	meex:Performer.\n" 
			+ "?artist	meex:relatedPerformer	?related.}\n"
			+ "WHERE { \n"
			+ "?artist	meex:performsEvent	?event;\n"
			+ "			meex:relatedPerformer	?related.\n" 
			+ "?event	a	meex:Event.\n" 
			+ "?related	rdfs:label	?rel_label.}";

	    Query query2 = QueryFactory.create(realtedArtistsQueryString);
	    QueryExecution qexec2 = QueryExecutionFactory.create(query2, integretedModel);
	    qexec2.execConstruct(resultSet);
	    
		String sparqlLatLangQueryString =
			          "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
					+ "PREFIX meex: <http://swa.cefriel.it/meex#>\n"
					+ "PREFIX gd:   <http://maps.google.com/>\n"
					+ "SELECT DISTINCT ?event ?lat ?lng \n"
					+ "WHERE { ?performer meex:performsEvent	?event.\n" 
					+ "        ?event	a	meex:Event;\n"
					+ "              	meex:hasWhere ?where.\n"
					+ "        ?where	gd:hasGeoPt	?geoPt.\n"
					+ "        ?geoPt	gd:lat ?lat.\n"
					+ "        ?geoPt	gd:lon ?lng.}";

		Query query3 = QueryFactory.create(sparqlLatLangQueryString);
	    QueryExecution qexec3 = QueryExecutionFactory.create(query3, integretedModel);
	    ResultSet res = qexec3.execSelect();
	    
	    while (res.hasNext())
	    {
	    	QuerySolution sol = res.next();
	    	String lat = sol.getLiteral("?lat").toString();
	    	String lng = sol.getLiteral("?lng").toString();
	    	Resource event = sol.getResource("?event");
	    	
	    	String latLng = lat +','+ lng;  
	    	
	    	resultSet.add(
	    			resultSet.createLiteralStatement(
	    					event,
	    					resultSet.createProperty(
	    							"http://swa.cefriel.it/meex#hasLatLng"),
	    							latLng));
	    }



		return resultSet;
	}

	
	protected static Model makeInferedModel(Model origModel)
	{
	    Model inferedModel = ModelFactory.createInfModel(reasoner, origModel);
	    return inferedModel;
	}

	protected static void readStaticOntologiesToModel(Model model)
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
	
	
	public static void main(String[] args) throws Exception
	{


/***/		
		Integration data_integration = new Integration();
		Model integred_model =  data_integration.integrateDataForMusicStyle("British Invasion");		
		integred_model.write(new FileOutputStream("result_model.xml"));
/***		
		Model integred_model =  ModelFactory.createDefaultModel();
		integred_model.read(new FileInputStream("result_model.xml"), null);
/***/		
		
		Model presentation_model = constructModelForPresentation(integred_model);
		
		convertAndWriteModelToJson(presentation_model, 		
				new OutputStreamWriter(
						new FileOutputStream("result_model.js")));

		
	    presentation_model.write(new FileOutputStream("presentation.xml"));
						
	}
}
