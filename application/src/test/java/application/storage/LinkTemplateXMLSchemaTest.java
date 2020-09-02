package application.storage;

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
				will(returnValue("template-name"));

				oneOf(root_mock).getChildText("objectId1");
				will(returnValue("template-id-1"));

				oneOf(root_mock).getChildText("objectId2");
				will(returnValue("template-id-2"));
			}
		});
		LinkTemplate result = testable.parse(root_mock);
		assertNotNull(result);
		assertEquals("template-name", result.getName());
		assertEquals("template-id-1", result.getObjectId1());
		assertEquals("template-id-2", result.getObjectId2());
	}

	@Test
	public void parse_with_null() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("template-name"));

				oneOf(root_mock).getChildText("objectId1");
				will(returnValue(null));

				oneOf(root_mock).getChildText("objectId2");
				will(returnValue(null));
			}
		});
		LinkTemplate result = testable.parse(root_mock);
		assertNotNull(result);
		assertEquals("template-name", result.getName());
		assertEquals(null, result.getObjectId1());
		assertEquals(null, result.getObjectId2());
	}

	@Test
	public void combine() {
		final LinkTemplate linkTemplate_mock = context.mock(LinkTemplate.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_mock).getName();
				will(returnValue("template-name"));

				oneOf(linkTemplate_mock).getObjectId1();
				will(returnValue("template-id-1"));

				oneOf(linkTemplate_mock).getObjectId2();
				will(returnValue("template-id-2"));
			}
		});

		Element element = testable.combine(linkTemplate_mock);
		assertEquals("template-name", element.getChildText("name"));
		assertEquals("template-id-1", element.getChildText("objectId1"));
		assertEquals("template-id-2", element.getChildText("objectId2"));
	}

	@Test
	public void combine_with_null() {
		final LinkTemplate linkTemplate_mock = context.mock(LinkTemplate.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTemplate_mock).getName();
				will(returnValue("template-name"));

				oneOf(linkTemplate_mock).getObjectId1();
				will(returnValue(null));

				oneOf(linkTemplate_mock).getObjectId2();
				will(returnValue(null));
			}
		});

		Element element = testable.combine(linkTemplate_mock);
		assertEquals("template-name", element.getChildText("name"));
		assertNull(element.getChildText("objectId1"));
		assertNull(element.getChildText("objectId2"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("linkTemplate", testable.getSchemaName());
	}
}
