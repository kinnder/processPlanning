package planning.storage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

	SystemXMLSchema testable;

	@BeforeEach
	public void setup() {
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

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildren("systemObject");
				will(returnValue(objects));

				// <-- parseSystemObject

				oneOf(object_mock).getChildText("name");

				oneOf(object_mock).getChildText("id");

				oneOf(object_mock).getChildren("attribute");

				oneOf(object_mock).getChildren("link");

				// parseSystemObject -->

				oneOf(root_mock).getChildren("link");
				will(returnValue(links));

				// <-- parseLink

				oneOf(link_mock).getChildText("name");

				oneOf(link_mock).getChildText("objectId1");

				oneOf(link_mock).getChildText("objectId2");

				// parseLink -->
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

		context.checking(new Expectations() {
			{
				oneOf(system_mock).getObjects();
				will(returnValue(systemObjects));

				// <-- combineSystemObject

				oneOf(systemObject_mock).getName();

				oneOf(systemObject_mock).getId();

				oneOf(systemObject_mock).getAttributes();

				oneOf(systemObject_mock).getLinks();

				// combineSystemObject -->

				oneOf(system_mock).getLinks();
				will(returnValue(links));

				// <-- combineLink

				oneOf(link_mock).getName();

				oneOf(link_mock).getObjectId1();

				oneOf(link_mock).getObjectId2();

				// combineLink -->
			}
		});

		Element element = testable.combine(system_mock);
		assertNotNull(element.getChild("systemObject"));
	}
}
