package nswi116.helloworld;

import java.io.PrintWriter;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.*;

public class SparqlEndpoint {

	public static void printHallo(PrintWriter out)
	{
		String artist = "http://musicbrainz.org/artist/76c9a186-75bd-436a-85c0-823e3efddb7f.html";
		String sparqlQueryString = //"PREFIX mb: <http://musicbrainz.org/>\n" +
           "DESCRIBE <" + artist + ">";
		Query query = QueryFactory.create(sparqlQueryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:2020/service/MusicBrainz", query);
		Model resultModel = qexec.execDescribe();
		resultModel.write(System.out);
	}

	public static void main(String[] args)
	{
		printHallo(new PrintWriter(System.out));
	}
}
