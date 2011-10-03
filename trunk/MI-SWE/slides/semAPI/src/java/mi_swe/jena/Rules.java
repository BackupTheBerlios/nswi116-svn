package mi_swe.jena;

import mi_swe.jena.builtin.AddDaysToDateTime;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.BuiltinRegistry;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;
//http://jena.sourceforge.net/inference/
//http://hydrogen.informatik.tu-cottbus.de/wiki/index.php/JenaRules
public class Rules {
	public static void main(String[] args) {
		Model helloModel = Reasoning.makeInferedHelloModel();    
		// register our builtin AddDaysToDate
		BuiltinRegistry.theRegistry.register(new AddDaysToDateTime());
		// create a resource (empty resource)
		Resource configuration = helloModel.createResource();
		// set engine mode
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");
		// set the rules file
		configuration.addProperty(ReasonerVocabulary.PROPruleSet, "hello-rules.jena");
		// Create an instance of such a reasoner
		Reasoner reasoner = GenericRuleReasonerFactory.theInstance().create(configuration);
		// Attach the model to the reasoner
		InfModel inf = ModelFactory.createInfModel(reasoner, helloModel );
		// Write the output		
		inf.write(System.out, "N3");
	}
}