package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.LinkTemplate;

public class LinkTemplateXMLSchemaTest {

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

	LinkTemplateXMLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new LinkTemplateXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChildText("value");
				will(returnValue("value"));
			}
		});
		LinkTemplate result = testable.parse(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals("value", result.getObjectId1());
	}

	@Test
	public void parse_with_null() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChildText("value");
				will(returnValue(null));
			}
		});
		LinkTemplate result = testable.parse(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals(null, result.getObjectId1());
	}

	@Test
	public void combine() {
		final LinkTemplate linkTemplate_mock = context.mock(LinkTemplate.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_mock).getName();
				will(returnValue("link-name"));

				oneOf(linkTemplate_mock).getObjectId1();
				will(returnValue("link-value"));
			}
		});

		Element element = testable.combine(linkTemplate_mock);
		assertEquals("link-name", element.getChildText("name"));
		assertEquals("link-value", element.getChildText("value"));
	}

	@Test
	public void combine_empty_value() {
		final LinkTemplate linkTemplate_mock = context.mock(LinkTemplate.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_mock).getName();
				will(returnValue("link-name"));

				oneOf(linkTemplate_mock).getObjectId1();
				will(returnValue(null));
			}
		});

		Element element = testable.combine(linkTemplate_mock);
		assertEquals("link-name", element.getChildText("name"));
		assertNull(element.getChild("value"));
	}
}
