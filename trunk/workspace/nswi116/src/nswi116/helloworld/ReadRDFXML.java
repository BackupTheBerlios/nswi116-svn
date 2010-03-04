package nswi116.helloworld;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;

import java.io.*;

public class ReadRDFXML {
	// note that this path is specific
	static final String inputFileName  = "D:\\SemanticWeb\\Jena-2.6.2\\doc\\tutorial\\RDF_API\\data\\vc-db-1.rdf";
	                          
	public static void main (String args[]) {
	    // create an empty model
	    Model model = ModelFactory.createDefaultModel();
	
	    InputStream in = FileManager.get().open( inputFileName );
	    if (in == null) {
	        throw new IllegalArgumentException( "File: " + inputFileName + " not found");
	    }
	    
	    // read the RDF/XML file
	    model.read(in, "");
	                
	    // write it to standard out
	    model.write(System.out);            
	}
}
