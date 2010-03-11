package nswi116.helloworld;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.FileManager;

import java.io.*;

public class ReadRDFXML {
	// note that this path is specific
	static final String inputFileName  = "data/vc-db-1.rdf";
	                          
	public static void main (String args[]) throws IOException {
	    // create an empty model
	    Model model = ModelFactory.createDefaultModel();
	
	    
	    File cur_dir = new File(".");
	    String prefix = "file:///" + cur_dir.getCanonicalPath() + "/data/";
	    
	    System.out.println(prefix);
	    
	    model.read(prefix + "Meex.n3", "", "N3");
	    model.read(prefix + "MusicMoz.n3", "", "N3");
	    model.read(prefix + "SampleInstance-MusicMoz.n3", "", "N3");
	    model.read(prefix + "Google.n3", "", "N3");
	    model.read(prefix + "MeexBindings.n3", "", "N3");
	    model.read(prefix + "MusicBrainz.n3", "", "N3");
	    model.read(prefix + "EVDB.n3", "", "N3");
	                
	    // write it to standard out
	    model.write(System.out);            
	}
}
