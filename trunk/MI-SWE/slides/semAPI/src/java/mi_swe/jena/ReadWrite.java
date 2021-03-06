package mi_swe.jena;

import java.io.FileOutputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class ReadWrite
{
	public static Model readHelloRdfFile()	{
		// create an empty model
		Model model = ModelFactory.createDefaultModel();
		// parse file (or URL) into the model	    
		model.read("file:hello.rdf", "TURTLE");
		return model;
	}	
	public static void main(String[] args) throws Exception {
	    Model model = readHelloRdfFile();	    	    
	    // write the whole model to the standard output
	    model.write(System.out, "RDF/XML");
	    // and to a file
	    model.write(new FileOutputStream("hello.nt"), "N-TRIPLE");
	}
}
