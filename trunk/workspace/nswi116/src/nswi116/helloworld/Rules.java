package nswi116.helloworld;

import java.io.PrintWriter;
import java.util.Iterator;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Derivation;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class Rules {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		//http://jena.sourceforge.net/inference/
		//http://hydrogen.informatik.tu-cottbus.de/wiki/index.php/JenaRules
	    String NS = "http://example.eg/";
	    Model input_model = ModelFactory.createDefaultModel();
	    
		// create a resource (empty resource)
		Resource configuration = input_model.createResource();

		// set engine mode
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");

		// set the rules file
		configuration.addProperty(ReasonerVocabulary.PROPruleSet, "data/rules.rules");

		// Create an instance of such a reasoner
		Reasoner reasoner = GenericRuleReasonerFactory.theInstance().create(configuration);

	    reasoner.setDerivationLogging(true);
	    input_model.read("file:data/rules_data.n3", "", "N3");

		InfModel inf = ModelFactory.createInfModel(reasoner, input_model );
		
		inf.write(System.out, "N3");
		System.out.println("---------------------------");
		
		PrintWriter out = new PrintWriter(System.out);
	    Resource A = inf.createResource(NS + "A");
		Property p = inf.createProperty(NS, "p");
		RDFNode D = inf.createResource(NS + "D");
		for (StmtIterator i = inf.listStatements(A, p, D); i.hasNext(); ) {
	        Statement s = i.nextStatement(); 
	        System.out.println("Statement is " + s);
	        for (Iterator<Derivation> id = inf.getDerivation(s); id.hasNext(); ) {
	            Derivation deriv = id.next();
	            deriv.printTrace(out, true);
	        }
	    }
	    out.flush();




	}

}
