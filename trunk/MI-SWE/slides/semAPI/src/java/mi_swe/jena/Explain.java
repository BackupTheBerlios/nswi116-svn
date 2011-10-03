package mi_swe.jena;

import java.io.PrintWriter;
import java.util.Iterator;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.*;

public class Explain {
	public static void main(String[] args) {
		Model helloModel = ReadWrite.readHelloRdfFile();
		Reasoner reasoner = ReasonerRegistry.getOWLMicroReasoner();
	    // Turn on derivation logging - slows down the performance!!!
		reasoner.setDerivationLogging(true);
		// Attach the model to the reasoner
		InfModel infModel = ModelFactory.createInfModel(reasoner, helloModel);
		// Create data for a query - predicate: rdf:type, object: swe:Human
		Property rdfType = infModel.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		RDFNode sweHuman = infModel.createResource("http://www.fit.cvut.cz/subjects/mi-swe#Human");
		PrintWriter out = new PrintWriter(System.out); // Necessary of printTrace, see bellow
		// Select matching statements (subject arbitrary) and iterate
		for (Statement s : infModel.listStatements(null, rdfType, sweHuman).toList()) {
		    System.out.println("Statement is " + s);
		    for (Iterator<Derivation> id = infModel.getDerivation(s); id.hasNext(); ) {
		        Derivation deriv = id.next();
		        // Print a deep traceback of this derivation back to axioms and source assertions.
		        deriv.printTrace(out, true); //print bindings = true
		    }
		    out.flush();
		}
	}
}
