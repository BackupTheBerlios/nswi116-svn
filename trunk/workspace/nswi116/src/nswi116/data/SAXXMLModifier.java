package nswi116.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.generationjava.io.xml.SimpleXmlWriter;

public class SAXXMLModifier extends DefaultHandler
{
	private SAXParser sax_parser;
	protected SimpleXmlWriter outputXmlWriter;


	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException
	{
		try {
			outputXmlWriter.writeText(new String(arg0, arg1, arg2));
		} catch (IOException e) {
			throw new SAXException(e); 
		}
	}

	@Override
	public void endDocument() throws SAXException
	{
		try {
			outputXmlWriter.close();
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}

	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		try {
			outputXmlWriter.endEntity();
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		try {
			outputXmlWriter.writeEntity(qName);
			for (int a=0; a<attributes.getLength(); a++)
			{
				outputXmlWriter.writeAttribute(attributes.getQName(a), attributes.getValue(a));
			}
		} catch (IOException e) {
			throw new SAXException(e); 
		}
	}

	public SAXXMLModifier() throws ParserConfigurationException, SAXException
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
	    spf.setValidating(false); // No validation required
	    sax_parser = spf.newSAXParser();		
	}

	public void parse(InputSource input, Writer output) throws SAXException, IOException
	{
		outputXmlWriter = new SimpleXmlWriter(output);
		outputXmlWriter.writeXmlVersion("1.0", "utf-8");

		sax_parser.parse(input, this);
		
	}

	public void parse(String input_file_name, String output_file_name) throws SAXException, IOException
	{
		parse(
				new InputSource(new FileInputStream(input_file_name)),
				new OutputStreamWriter(new FileOutputStream(output_file_name), "utf-8")
		);				
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
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
					outputXmlWriter.writeAttribute("filename", "data/accidents_tmt/jihomoravsky47443.txt.xml_00034.tmt");
				} catch (IOException e) {
					throw new SAXException(e);
				}
				super.startDocument();
			}
			
		};
		
		m.parse("data/accidents_tmt/jihomoravsky47443.txt.xml_00034.tmt", "data/accidents_tmt/file_list_modyfied.xml");
	}
	


}
