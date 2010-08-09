package nswi116.helloworld;

import java.io.File;
import java.io.IOException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

public class CostructXpathFunctions
{
	public static void main (String args[]) throws IOException
	{
	    Model model = ModelFactory.createDefaultModel();


	    File cur_dir = new File(".");
	    String prefix = "file:///" + cur_dir.getCanonicalPath() + "/data/";

	    System.out.println(prefix);

	    model.read(prefix + "Meex.n3", "", "N3");
	    model.read(prefix + "MusicMoz.n3", "", "N3");
	    model.read(prefix + "SampleInstance-MusicMoz.n3", "", "N3");
	    model.read(prefix + "SampleInstance-MusicBrainz.n3", "", "N3");
	    model.read(prefix + "google_sample.n3", "", "N3");
	    model.read(prefix + "Google.n3", "", "N3");
	    model.read(prefix + "MeexBindings.n3", "", "N3");
	    model.read(prefix + "MusicBrainz.n3", "", "N3");
	    model.read(prefix + "EVDB.n3", "", "N3");

	    Reasoner reasoner = ReasonerRegistry.getOWLMicroReasoner();
	    Model model2 = ModelFactory.createInfModel(reasoner, model);


	    String sparqlQueryStringPerformer =
	        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
	  		+ "PREFIX meex: <http://swa.cefriel.it/meex#>\n"
	  		+ "PREFIX fn: <http://www.w3.org/2005/xpath-functions#>\n"
	  		+ "PREFIX afn: <http://jena.hpl.hp.com/ARQ/function#>\n"
	  		+ "PREFIX apf: <http://jena.hpl.hp.com/ARQ/property#>\n"
			+ "CONSTRUCT {\n"
			+ "		?performer	meex:apf_concat	?apf_concat."
			+ "		?performer	meex:fn_concat	?fn_concat."
			+ "		?performer	meex:afn_concat	?afn_concat."
			+ "		?performer	meex:labelUP	?labelUP."
			+ "}\n"	  		
	  		+ "WHERE { "
	  		+		"?performer a meex:Performer. \n"
	  		+		"?performer rdfs:label ?label.\n"
	  		+		"?apf_concat  apf:concat(\"apf_concat: \" ?label).\n"
	  		+		"LET (?fn_concat := fn:concat('fn_concat: ', ?label))\n"
	  		+		"LET (?afn_concat := afn:strjoin(' ', 'afn_concat:', ?label))\n"
	  		+		"LET (?labelUP := fn:upper-case(?label))\n"
	  		+	"}";
	    
	    Query query = QueryFactory.create(sparqlQueryStringPerformer, Syntax.syntaxARQ);
	    QueryExecution qexec = QueryExecutionFactory.create(query, model2);
	    Model resultModel = qexec.execConstruct();
	    
	    resultModel.write(System.out, "N3");
	}
}
