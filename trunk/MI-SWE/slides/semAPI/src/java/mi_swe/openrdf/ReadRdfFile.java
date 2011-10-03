package mi_swe.openrdf;

import java.io.FileInputStream;

import org.openrdf.model.Statement;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.helpers.StatementCollector;
import org.openrdf.rio.turtle.TurtleParser;

public class ReadRdfFile {
	public static void ParseHello(RDFHandler rdfHandler) throws Exception {
		//create instance of a parser
		RDFParser parser = new TurtleParser(); //NTriplesParser, RDFXMLParser
		//attach the handler to the parser
		parser.setRDFHandler(rdfHandler);
		//parse a file, second parameter is base URI
		parser.parse(new FileInputStream("hello.rdf"), "");
	}	
	public static void main(String[] args) throws Exception {
		//create a statement collector
		StatementCollector collector = new StatementCollector();
		ParseHello(collector);
		//print all statements		
		for (Statement statement : collector.getStatements()) {
			System.out.println(statement);			
		}
	}
}
