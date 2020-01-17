package planning.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

	XMLFile testable;

	XMLSchema xmlSchema_mock;

	SAXBuilder saxBuilder_mock;

	@BeforeEach
	public void setup() {
		xmlSchema_mock = context.mock(XMLSchema.class);
		saxBuilder_mock = context.mock(SAXBuilder.class);

		testable = new XMLFile(xmlSchema_mock, saxBuilder_mock);
	}

	@Test
	public void load() throws JDOMException, IOException {
		final InputStream inputStream_mock = context.mock(InputStream.class);
		final Document document_mock = context.mock(Document.class);
		final Element element_mock = context.mock(Element.class);

		context.checking(new Expectations() {
			{
				oneOf(saxBuilder_mock).build(inputStream_mock);
				will(returnValue(document_mock));

				oneOf(document_mock).getRootElement();
				will(returnValue(element_mock));

				oneOf(xmlSchema_mock).parse(element_mock);
			}
		});

		testable.load(inputStream_mock);
	}

	@Test
	public void save() throws IOException {
		final OutputStream outputStream_mock = context.mock(OutputStream.class);
		final Element element = new Element("document");

		context.checking(new Expectations() {
			{
				oneOf(xmlSchema_mock).combine();
				will(returnValue(element));

				oneOf(outputStream_mock).write(with(any(byte[].class)), with(any(int.class)), with(any(int.class)));

				oneOf(outputStream_mock).flush();

				oneOf(outputStream_mock).flush();
			}
		});

		testable.save(outputStream_mock);
	}
}
