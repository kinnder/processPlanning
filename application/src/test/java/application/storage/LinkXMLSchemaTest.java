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

import planning.model.Link;

public class LinkXMLSchemaTest {
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

	LinkXMLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new LinkXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChildText("objectId1");
				will(returnValue("id-1"));

				oneOf(root_mock).getChildText("objectId2");
				will(returnValue("id-2"));
			}
		});
		Link result = testable.parse(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals("id-1", result.getObjectId1());
		assertEquals("id-2", result.getObjectId2());
	}

	@Test
	public void parse_with_null() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChildText("objectId1");
				will(returnValue(null));

				oneOf(root_mock).getChildText("objectId2");
				will(returnValue(null));
			}
		});
		Link result = testable.parse(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals(null, result.getObjectId1());
		assertEquals(null, result.getObjectId2());
	}

	@Test
	public void combine() {
		final Link link_mock = context.mock(Link.class);

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("name"));

				oneOf(link_mock).getObjectId1();
				will(returnValue("id-1"));

				oneOf(link_mock).getObjectId2();
				will(returnValue("id-2"));
			}
		});

		Element element = testable.combine(link_mock);
		assertEquals("name", element.getChildText("name"));
		assertEquals("id-1", element.getChildText("objectId1"));
		assertEquals("id-2", element.getChildText("objectId2"));
	}

	@Test
	public void combine_with_null() {
		final Link link_mock = context.mock(Link.class);

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("name"));

				oneOf(link_mock).getObjectId1();
				will(returnValue(null));

				oneOf(link_mock).getObjectId2();
				will(returnValue(null));
			}
		});

		Element element = testable.combine(link_mock);
		assertEquals("name", element.getChildText("name"));
		assertNull(element.getChild("objectId1"));
		assertNull(element.getChild("objectId2"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("link", testable.getSchemaName());
	}
}
