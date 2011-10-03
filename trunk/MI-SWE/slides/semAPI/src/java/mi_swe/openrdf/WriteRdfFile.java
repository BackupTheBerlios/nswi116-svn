package mi_swe.openrdf;

import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.rdfxml.util.RDFXMLPrettyWriter;

public class WriteRdfFile {
	public static void main(String[] args) throws Exception {
		//create a RDF writer to standard output
		//Also possible: RDFXMLWriter, N3Writer, NTriplesWriter, TurtleWriter 
		RDFWriter rdfWriter = new RDFXMLPrettyWriter(System.out);
		//write the output during parsing
		ReadRdfFile.ParseHello(rdfWriter);
	}
}
