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

	protected OWLModel owlModel;

	protected OWLSchema<T> owlSchema;

	public OWLFile() {
	}

	OWLFile(OWLModel owlModel, OWLSchema<T> owlSchema) {
		this.owlModel = owlModel;
		this.owlSchema = owlSchema;
	}

	public void save(T object, String path) throws IOException {
		save(object, Paths.get(path));
	}

	public void save(T object, Path path) throws IOException {
		owlModel.createOntologyModel();
		owlSchema.combine(object);
		try (OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(path))) {
			// TODO (2021-04-02 #31): запись онтологии в файл выполняется очень медленно
			owlModel.getOntologyModel().write(outputStream, "RDF/XML");
		}
	}

	public T load(String path) throws IOException {
		return load(Paths.get(path));
	}

	public T load(Path path) throws IOException {
		OntModel ontModel = owlModel.createOntologyModelBase();
		try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(path))) {
			ontModel.read(inputStream, "RDF/XML");
		}
		owlModel.connectOntologyModel(ontModel);
		// TODO (2021-02-01 #31): загружаться должен основной индивид, не null
		return owlSchema.parse(null);
	}
}
