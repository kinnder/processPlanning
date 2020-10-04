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

	OWLSchema<Object> owlSchema_mock;

	OntModel ontModel_mock;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setup() {
		owlSchema_mock = context.mock(OWLSchema.class);
		ontModel_mock = context.mock(OntModel.class);

		testable = new OWLFile<Object>(owlSchema_mock, ontModel_mock);
	}

	@Test
	public void load_path() throws IOException {
		final Path path_mock = context.mock(Path.class);
		final Object object_mock = context.mock(Object.class);

		context.checking(new Expectations() {
			{
				oneOf(path_mock).getFileSystem();

				oneOf(ontModel_mock).read(with(any(BufferedInputStream.class)), with("RDF/XML"));

				oneOf(owlSchema_mock).parse(ontModel_mock);
				will(returnValue(object_mock));
			}
		});

		assertEquals(object_mock, testable.load(path_mock));
	}

	@Test
	public void save_path() throws IOException {
		final Path path_mock = context.mock(Path.class);
		final Object object_mock = context.mock(Object.class);

		context.checking(new Expectations() {
			{
				oneOf(path_mock).getFileSystem();

				oneOf(owlSchema_mock).combine(object_mock);
				will(returnValue(ontModel_mock));

				oneOf(ontModel_mock).write(with(any(BufferedOutputStream.class)), with("RDF/XML"));
			}
		});

		testable.save(object_mock, path_mock);
	}
}
