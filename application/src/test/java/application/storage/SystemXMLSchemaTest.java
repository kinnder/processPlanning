package application.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
import planning.model.System;
import planning.model.SystemObject;

public class SystemXMLSchemaTest {

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

	SystemObjectXMLSchema systemObjectXMLSchema_mock;

	LinkXMLSchema linkXMLSchema_mock;

	SystemXMLSchema testable;

	@BeforeEach
	public void setup() {
		systemObjectXMLSchema_mock = context.mock(SystemObjectXMLSchema.class);
		linkXMLSchema_mock = context.mock(LinkXMLSchema.class);

		testable = new SystemXMLSchema(systemObjectXMLSchema_mock, linkXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> objects = new ArrayList<>();
		final Element object_mock = context.mock(Element.class, "object");
		objects.add(object_mock);
		final List<Element> links = new ArrayList<>();
		final Element link_mock = context.mock(Element.class, "link");
		links.add(link_mock);
		final SystemObject systemObject = new SystemObject("object-name");
		final Link link = new Link("link-name", "link-id-1", "link-id-2");

		context.checking(new Expectations() {
			{
				oneOf(systemObjectXMLSchema_mock).getSchemaName();
				will(returnValue("systemObject"));

				oneOf(root_mock).getChildren("systemObject");
				will(returnValue(objects));

				oneOf(systemObjectXMLSchema_mock).parse(object_mock);
				will(returnValue(systemObject));

				oneOf(linkXMLSchema_mock).getSchemaName();
				will(returnValue("link"));

				oneOf(root_mock).getChildren("link");
				will(returnValue(links));

				oneOf(linkXMLSchema_mock).parse(link_mock);
				will(returnValue(link));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof System);
	}

	@Test
	public void combine() {
		final System system_mock = context.mock(System.class);
		final List<SystemObject> systemObjects = new ArrayList<>();
		final SystemObject systemObject_mock = context.mock(SystemObject.class);
		systemObjects.add(systemObject_mock);
		final List<Link> links = new ArrayList<>();
		final Link link_mock = context.mock(Link.class);
		links.add(link_mock);
		final Element systemObject = new Element("systemObject");
		final Element link = new Element("link");

		context.checking(new Expectations() {
			{
				oneOf(system_mock).getObjects();
				will(returnValue(systemObjects));

				oneOf(systemObjectXMLSchema_mock).combine(systemObject_mock);
				will(returnValue(systemObject));

				oneOf(system_mock).getLinks();
				will(returnValue(links));

				oneOf(linkXMLSchema_mock).combine(link_mock);
				will(returnValue(link));
			}
		});

		Element element = testable.combine(system_mock);
		assertEquals(systemObject, element.getChild("systemObject"));
		assertEquals(link, element.getChild("link"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("system", testable.getSchemaName());
	}
}
