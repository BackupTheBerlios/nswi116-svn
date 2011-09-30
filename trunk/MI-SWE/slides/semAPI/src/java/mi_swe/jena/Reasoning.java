package mi_swe.jena;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

public class Reasoning
{
	public static Model makeInferedHelloModel() {
		Model helloModel = ReadWrite.readHelloRdfFile();
		//create a reasoner, see also:
		//getOWLMicroReasoner(), getOWLMiniReasoner(), getOWLReasoner()
		//getRDFSReasoner(), getRDFSSimpleReasoner(), getTransitiveReasoner()
		Reasoner reasoner = ReasonerRegistry.getOWLMicroReasoner();
		//attach the model to the reasoner
		Model inferedModel = ModelFactory.createInfModel(reasoner, helloModel);
		return inferedModel;
	}
	public static void main(String[] args)	{
		makeInferedHelloModel().write(System.out, "N-TRIPLE");
	}
}
