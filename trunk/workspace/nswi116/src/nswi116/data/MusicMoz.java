package nswi116.data;

import java.io.File;
import java.io.IOException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFReader;

public class MusicMoz
{
	protected Model mmModel = null;
	
	public MusicMoz() throws IOException
	{
		mmModel = ModelFactory.createDefaultModel();
		RDFReader reader = mmModel.getReader("GRDDL");

	    File cur_dir = new File(".");
	    String prefix = "file:///" + cur_dir.getCanonicalPath() + "/data/";

//		reader.read(mmModel, prefix + "musicmoz.bandsandartists.d.delirious.xml");
		reader.read(mmModel, prefix + "musicmoz.bandsandartists.xml");
		reader.read(mmModel, prefix + "musicmoz.lists.styles.xml");
		
//		mmModel.write(new FileOutputStream("mmModel.xml"));
	}

	public Model getModel()
	{
		return mmModel;
	}
}
