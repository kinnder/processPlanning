package application.storage.owl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.jena.ontology.OntModel;

public class OWLFile<T> {

	private OWLSchema<T> owlSchema;

	public OWLFile(OWLSchema<T> owlSchema) {
		this.owlSchema = owlSchema;
	}

	public void save(T object, String path) throws IOException {
		save(object, Paths.get(path));
	}

	public void save(T object, Path path) throws IOException {
		OntModel m = owlSchema.combine(object);
		OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(path));
		m.write(outputStream, "RDF/XML");
	}

	public T load(String path) {
		// TODO Auto-generated method stub
		return null;
	}
}
