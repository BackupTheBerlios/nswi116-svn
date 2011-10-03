package mi_swe.openrdf;

import org.openrdf.model.Statement;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.util.RDFInserter;
import org.openrdf.sail.memory.MemoryStore;

public class InsertIntoRepository {
	public static SailRepository insertHelloIntoMemoryStore() throws Exception {
		// Create in-memory repository
		// Other possibilities: MySqlStore, NativeStore, PgSqlStore
		SailRepository repository = new SailRepository(new MemoryStore());
		repository.initialize();
		// Get connection object
		RepositoryConnection connection = repository.getConnection();
		// Create an inserter and attach the connection to it
		RDFInserter inserter = new RDFInserter(connection);
		// Parse a RDF file using the inserter
		ReadRdfFile.ParseHello(inserter);
		return repository;
	}
	public static void main(String[] args) throws Exception {
		RepositoryConnection connection = insertHelloIntoMemoryStore().getConnection();
		// Get all matching statements from the connection TODO: describe arguments
		RepositoryResult<Statement> result = connection.getStatements(null, null, null, true);		
		// Print all statements		
		for (Statement statement : result.asList()) {
			System.out.println(statement);			
		}
	}
}