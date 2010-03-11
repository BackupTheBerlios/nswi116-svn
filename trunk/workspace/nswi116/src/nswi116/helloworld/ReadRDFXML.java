package nswi116.helloworld;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;

import java.io.*;

public class ReadRDFXML {
	// note that this path is specific
	static final String inputFileName  = "data/vc-db-1.rdf";
	                          
	public static void main (String args[]) {
	    // create an empty model
	    Model model = ModelFactory.createDefaultModel();
	
	    
	    String prefix = "file:///D:/nswi116/workspace/nswi116/data/";
	    
	    model.read(prefix + "Meex.n3", "", "N3");
	    model.read("Google.n3", "", "N3");
	    model.read("MeexBindings.n3", "", "N3");
	    model.read("MusicBrainz.n3", "", "N3");
	    model.read("MusicMoz.n3", "", "N3");
	    model.read("EVDB.n3", "", "N3");
	                
	    // write it to standard out
	    model.write(System.out);            
	}
}
