package nswi116.helloworld;

import java.io.File;
import java.io.IOException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFReader;

public class MMozGRDDL
{

	public static void main(String[] args) throws IOException
	{		
		Model mmModel = ModelFactory.createDefaultModel();
		RDFReader reader = mmModel.getReader("GRDDL");
		
	    File cur_dir = new File(".");
	    String prefix = "file:///" + cur_dir.getCanonicalPath() + "/data/";

	    System.out.println(prefix);

//	    model.read(prefix + "Meex.n3", "", "N3");

//		reader.read(mmModel, "file:///.../musicmoz.bandsandartists.xml");
		reader.read(mmModel, prefix + "musicmoz.lists.styles.xml");
		
		mmModel.write(System.out);

	}

}
