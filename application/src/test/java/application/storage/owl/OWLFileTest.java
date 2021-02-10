package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.jena.ontology.OntModel;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class OWLFileTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	OWLFile<Object> testable;

	OWLModel<Object> owlModel_mock;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(OWLModel.class);
		testable = new OWLFile<Object>(owlModel_mock);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void load_path() throws IOException {
		final Object object_mock = context.mock(Object.class);
		final Path path_mock = context.mock(Path.class);
		final OntModel ontModel_mock = context.mock(OntModel.class);
		final OWLSchema<Object> owlSchema_mock = context.mock(OWLSchema.class);

		context.checking(new Expectations() {
			{
				oneOf(path_mock).getFileSystem();

				oneOf(owlModel_mock).createOntologyModelBase();
				will(returnValue(ontModel_mock));

				oneOf(ontModel_mock).read(with(any(BufferedInputStream.class)), with("RDF/XML"));

				oneOf(owlModel_mock).connectOntologyModel(ontModel_mock);

				oneOf(owlModel_mock).getOWLSchema();
				will(returnValue(owlSchema_mock));

				oneOf(owlSchema_mock).parse(null);
				will(returnValue(object_mock));
			}
		});

		assertEquals(object_mock, testable.load(path_mock));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void save_path() throws IOException {
		final Path path_mock = context.mock(Path.class);
		final Object object_mock = context.mock(Object.class);
		final OntModel ontModel_mock = context.mock(OntModel.class);
		final OWLSchema<Object> owlSchema_mock = context.mock(OWLSchema.class);

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).createOntologyModel();

				oneOf(owlModel_mock).getOWLSchema();
				will(returnValue(owlSchema_mock));

				oneOf(owlSchema_mock).combine(object_mock);

				oneOf(path_mock).getFileSystem();

				oneOf(owlModel_mock).getOntologyModel();
				will(returnValue(ontModel_mock));

				oneOf(ontModel_mock).write(with(any(BufferedOutputStream.class)), with("RDF/XML"));
			}
		});

		testable.save(object_mock, path_mock);
	}
}
