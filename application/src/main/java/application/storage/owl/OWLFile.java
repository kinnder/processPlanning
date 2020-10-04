package application.storage.owl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;

public class OWLFile<T> {

	private OWLSchema<T> owlSchema;

	private OntModel ontModel = ModelFactory.createOntologyModel();

	public OWLFile(OWLSchema<T> owlSchema) {
		this.owlSchema = owlSchema;
	}

	OWLFile(OWLSchema<T> owlSchema, OntModel ontModel) {
		this(owlSchema);
		this.ontModel = ontModel;
	}

	public void save(T object, String path) throws IOException {
		save(object, Paths.get(path));
	}

	public void save(T object, Path path) throws IOException {
		ontModel = owlSchema.combine(object);
		OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(path));
		ontModel.write(outputStream, "RDF/XML");
	}

	public T load(String path) throws IOException {
		return load(Paths.get(path));
	}

	public T load(Path path) throws IOException {
		InputStream inputStream = new BufferedInputStream(Files.newInputStream(path));
		ontModel.read(inputStream, "RDF/XML");
		return owlSchema.parse(ontModel);
	}
}
