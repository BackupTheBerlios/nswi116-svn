package nswi116.helloworld;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFReader;

// Congratulations! Your new application key is 9Lvz5Drd6NNB8w5c.
// http://api.eventful.com/rest/events/search?app_key=9Lvz5Drd6NNB8w5c&keywords=books&location=San+Diego&date=Future

public class EVDB
{

	public static void main(String[] args) throws IOException
	{
		Model mmModel = ModelFactory.createDefaultModel();
		RDFReader reader = mmModel.getReader("GRDDL");
		reader.setProperty("grddl.xml-xform", "file:data/evdb-time.xsl");
		
		String url = "http://api.eventful.com/rest/events/search?app_key=9Lvz5Drd6NNB8w5c&keywords=books&location=San+Diego&date=Future";		
	
		reader.read(mmModel, url);				
		
		mmModel.write(System.out);		
	}
	

	
	
	
	public static void main2(String[] args) throws IOException, TransformerFactoryConfigurationError, TransformerException, InterruptedException
	{
		URL url = new URL("http://api.eventful.com/rest/events/search?app_key=9Lvz5Drd6NNB8w5c&keywords=books&location=San+Diego&date=Future");		
		final InputStream http_in = url.openStream();
		
		final Transformer transform = TransformerFactory.newInstance().
			newTransformer(new StreamSource("data/evdb-time.xsl"));
					    	    	      
	    PipedInputStream pipe_in = new PipedInputStream();  
	    final PipedOutputStream pipe_out = new PipedOutputStream(pipe_in);

		new Thread()
		{
			@Override
			public void run() {
				try {
									
					transform.transform(
							new StreamSource(http_in),
							new StreamResult(pipe_out));
					
					//IMPORTANT
					//A thread that writes to a stream should always close 
					//the OutputStream before terminating.
					pipe_out.close();
				
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}.start();
		
		
		Model model = ModelFactory.createDefaultModel();
		model.read(pipe_in, null);		
		model.write(System.out);
	}
}