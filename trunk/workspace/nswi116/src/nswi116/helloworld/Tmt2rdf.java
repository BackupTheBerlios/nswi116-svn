package nswi116.helloworld;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.parsers.ParserConfigurationException;

import nswi116.data.SAXXMLModifier;

import org.xml.sax.SAXException;

import com.generationjava.io.xml.SimpleXmlWriter;
import com.generationjava.io.xml.XmlWriter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFReader;

public class Tmt2rdf
{
	protected String directory;
	protected String[] file_list;
	protected Model defaultModel;
	protected RDFReader reader;
	
	public Tmt2rdf(String directory)
	{
		this.directory = directory;
		
		File dir = new File(directory);
		file_list = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String file_name) {
				return file_name.endsWith("tmt");
			}
		});
	}

	protected void createGRDDLContainer(final String file_name) throws ParserConfigurationException, SAXException, IOException
	{
		SAXXMLModifier m = new SAXXMLModifier()
		{
			@Override
			public void endDocument() throws SAXException {
				try {
					outputXmlWriter.endEntity();
				} catch (IOException e) {
					throw new SAXException(e);
				}
				
				super.endDocument();
			}

			@Override
			public void startDocument() throws SAXException {
				try {
					outputXmlWriter.writeEntity("tmt_document_file_container");
					outputXmlWriter.writeAttribute("filename", file_name);
					outputXmlWriter.writeAttribute("xmlns:grddl", "http://www.w3.org/2003/g/data-view#");
					outputXmlWriter.writeAttribute("grddl:transformation", "file:data/tmt.xsl");

				} catch (IOException e) {
					throw new SAXException(e);
				}
				super.startDocument();
			}
			
		};
		
		m.parse(getFilePath(file_name), getContainerFilePath(file_name));		
	}
	
	protected String getOutputFilePath(String file_name)
	{
		return directory + "/out/" + file_name.substring(0, file_name.length()-3) + "rdf";				
	}

	protected String getContainerFilePath(String file_name)
	{
		return directory + "/out/" + file_name.substring(0, file_name.length()-4) + "_container.xml";		
	}

	protected String getFilePath(String file_name)
	{
		return directory + "/" + file_name;
	}

	protected void createGRDDLContainers() throws ParserConfigurationException, SAXException, IOException
	{		
		for (int i = 0; i < file_list.length; i++)
		{
			createGRDDLContainer(file_list[i]);			
		}				
	}


	protected void CreateFileList() throws IOException
	{		
		String file_list_file_name =  directory + "/file_list.xml";
		XmlWriter xmlwriter = new SimpleXmlWriter(new OutputStreamWriter(new FileOutputStream(file_list_file_name), "utf-8"));
		xmlwriter.writeXmlVersion("1.0", "utf-8");
		xmlwriter.writeEntity("tmt_file_list");
		for (int i = 0; i < file_list.length; i++)
		{
			xmlwriter.writeEntityWithText("file", file_list[i]);
		}
		xmlwriter.endEntity();
		xmlwriter.close();


		
		
	}

	protected void produceGRDDLRdfFile(String filename) throws FileNotFoundException
	{
		reader.read(defaultModel, "file:"+ getContainerFilePath(filename));
		
		defaultModel.write(new FileOutputStream(getOutputFilePath(filename)));
		defaultModel.removeAll();
		
	}
	
	protected void produceGRDDLRdfFiles() throws FileNotFoundException
	{
		defaultModel = ModelFactory.createDefaultModel();
		reader = defaultModel.getReader("GRDDL");
		
		for (int i = 0; i < file_list.length; i++)
		{
			produceGRDDLRdfFile(file_list[i]);
			System.out.format("%2d %s done\n", i+1, getOutputFilePath(file_list[i]));

		}
//		reader.setProperty("grddl.xml-xform", "file:data/tmt.xsl");
		
//		String url = "http://api.eventful.com/rest/events/search?app_key=9Lvz5Drd6NNB8w5c&keywords="+URLEncoder.encode(label, "UTF-8")+"&date=Future";		
	
		
	}
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException
	{
		System.out.println("start");
		Tmt2rdf t = new Tmt2rdf("data/accidents_tmt");
		System.out.println("init done");
//		t.CreateFileList();
		t.createGRDDLContainers();
		System.out.println("containers done");
		t.produceGRDDLRdfFiles();
		
	}
	

	public static void main3(String[] args) throws FileNotFoundException
	{
		java.util.logging.LogManager.getLogManager().reset();

		Model tmtModel = ModelFactory.createDefaultModel();
		RDFReader reader = tmtModel.getReader("GRDDL");
		reader.setProperty("grddl.xml-xform", "file:data/tmt.xsl");
		
//		String url = "http://api.eventful.com/rest/events/search?app_key=9Lvz5Drd6NNB8w5c&keywords="+URLEncoder.encode(label, "UTF-8")+"&date=Future";		
	
		reader.read(tmtModel, "file:data/GATE_Document_00008.xml");
		
		tmtModel.write(new FileOutputStream("data/GATE_Document_00008.owl"));

	}

}
