package nswi116.babel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Locale;
import java.util.Properties;

import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.Sail;
import org.openrdf.sail.memory.MemoryStore;

import edu.mit.simile.babel.exhibit.ExhibitJsonWriter;
import edu.mit.simile.babel.generic.RdfXmlConverter;

public class RDFXML2JSON {

	/**
	info: http://simile.mit.edu/babel/
	download: http://libstaff.mit.edu/facade/index.php/Public:PIM_Tools#Download_and_build_SIMILE_Babel
	 */
	public static void convert(Reader input, Writer output) throws Exception
	{
		RdfXmlConverter reader = new RdfXmlConverter();
		ExhibitJsonWriter writer = new ExhibitJsonWriter();
		
        SailRepository repo = new SailRepository(new MemoryStore());
        repo.initialize();
        
        Sail sail = repo.getSail();

        Properties properties = new Properties();
        properties.setProperty("namespace", "urn:babel:");
        properties.setProperty("url", "urn:babel:/");
        
        Locale locale = Locale.getDefault();
                        
        reader.read(input, sail, properties, locale);
        writer.write(output, sail, properties, locale);		
	}
	
	public static void main(String[] args) throws FileNotFoundException, Exception
	{
		convert(new InputStreamReader(new FileInputStream("data/vc-db-1.rdf")),
				new OutputStreamWriter(System.out));
	}

}
