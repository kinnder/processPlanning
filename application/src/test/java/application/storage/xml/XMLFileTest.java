package application.storage.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class XMLFileTest {

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

	XMLFile<Object> testable;

	XMLSchema<Object> xmlSchema_mock;

	SAXBuilder saxBuilder_mock;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setup() {
		xmlSchema_mock = context.mock(XMLSchema.class);
		saxBuilder_mock = context.mock(SAXBuilder.class);

		testable = new XMLFile<Object>(xmlSchema_mock, saxBuilder_mock);
	}

	@Test
	public void load_path() throws IOException, JDOMException {
		final Path path_mock = context.mock(Path.class);
		final Document document_mock = context.mock(Document.class);
		final Element element_mock = context.mock(Element.class);
		final Object data_mock = context.mock(Object.class);

		context.checking(new Expectations() {
			{
				oneOf(path_mock).getFileSystem();

				oneOf(saxBuilder_mock).build(with(any(BufferedInputStream.class)));
				will(returnValue(document_mock));

				oneOf(document_mock).getRootElement();
				will(returnValue(element_mock));

				oneOf(xmlSchema_mock).parse(element_mock);
				will(returnValue(data_mock));
			}
		});

		assertEquals(data_mock, testable.load(path_mock));
	}

	@Test
	public void save_path() throws IOException {
		final Path path_mock = context.mock(Path.class);
		final Element element = new Element("document");
		final Object object_mock = context.mock(Object.class);

		context.checking(new Expectations() {
			{
				oneOf(xmlSchema_mock).combine(object_mock);
				will(returnValue(element));

				oneOf(path_mock).getFileSystem();
			}
		});

		testable.save(object_mock, path_mock);
	}
}
