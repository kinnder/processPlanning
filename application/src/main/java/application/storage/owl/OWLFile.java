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

public class OWLFile<T> {

	private OWLSchema<T> owlSchema;

	private OWLModel owlModel;

	public OWLFile(OWLModel owlModel, OWLSchema<T> owlSchema) {
		this.owlSchema = owlSchema;
		this.owlModel = owlModel;
	}

	public void save(T object, String path) throws IOException {
		save(object, Paths.get(path));
	}

	public void save(T object, Path path) throws IOException {
		OntModel ontModel = owlModel.createOntologyModel();
		owlSchema.connectOntologyModel(ontModel);
		owlSchema.combine(object);
		OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(path));
		ontModel.write(outputStream, "RDF/XML");
	}

	public T load(String path) throws IOException {
		return load(Paths.get(path));
	}

	public T load(Path path) throws IOException {
		InputStream inputStream = new BufferedInputStream(Files.newInputStream(path));
		OntModel ontModel = owlModel.createOntologyModel();
		ontModel.read(inputStream, "RDF/XML");
		owlSchema.connectOntologyModel(ontModel);
		return owlSchema.parse();
	}
}
