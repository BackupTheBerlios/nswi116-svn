package mi_swe.babel;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.Properties;
import mi_swe.openrdf.InsertIntoRepository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.Sail;
import edu.mit.simile.babel.exhibit.ExhibitJsonWriter;

public class ConvertToExhibitJson
{
	public static void main(String[] args) throws Exception {
		SailRepository repo = InsertIntoRepository.insertHelloIntoMemoryStore();
		// Create new writer 
		ExhibitJsonWriter writer = new ExhibitJsonWriter();
		// Get Sail interface 
		Sail sail = repo.getSail();
		// Prepare writer properties
		Properties properties = new Properties();
		properties.setProperty("namespace", "urn:babel:");
		properties.setProperty("url", "urn:babel:/");
		// Write Json data to a file  
		writer.write(
				new OutputStreamWriter(new FileOutputStream("hello.js")),
				sail, properties, Locale.getDefault());		
	}
}
