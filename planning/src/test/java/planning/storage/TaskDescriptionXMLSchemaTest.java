package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.method.TaskDescription;
import planning.model.Attribute;
import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;

public class TaskDescriptionXMLSchemaTest {

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

	TaskDescriptionXMLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new TaskDescriptionXMLSchema();
	}

	@Test
	public void getTaskDescription() {
		assertTrue(testable.getTaskDescription() instanceof TaskDescription);
	}

	@Test
	public void setTaskDescription() {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);

		testable.setTaskDescription(taskDescription_mock);
		assertEquals(taskDescription_mock, testable.getTaskDescription());
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				// <-- parseTaskDescription

				oneOf(root_mock).getChild("initialSystem");

				oneOf(root_mock).getChild("finalSystem");

				// parseTaskDescription -->
			}
		});

		testable.parse(root_mock);
	}

	@Test
	public void combine() {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);

		context.checking(new Expectations() {
			{
				// <-- combineTaskDescription

				oneOf(taskDescription_mock).getInitialSystem();

				oneOf(taskDescription_mock).getFinalSystem();

				// combineTaskDescription -->
			}
		});

		testable.setTaskDescription(taskDescription_mock);
		assertNotNull(testable.combine());
	}

	@Test
	public void parseTaskDescription() {
		final Element root_mock = context.mock(Element.class, "root");
		final Element initialSystem_mock = context.mock(Element.class, "initial-system-element");
		final Element finalSystem_mock = context.mock(Element.class, "final-system-element");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChild("initialSystem");
				will(returnValue(initialSystem_mock));

				// <-- parseSystem

				oneOf(initialSystem_mock).getChildren("systemObject");

				// parseSystem -->

				oneOf(root_mock).getChild("finalSystem");
				will(returnValue(finalSystem_mock));

				// <-- parseSystem

				oneOf(finalSystem_mock).getChildren("systemObject");

				// parseSystem -->
			}
		});

		TaskDescription result = testable.parseTaskDescription(root_mock);
		assertTrue(result instanceof TaskDescription);
		assertNotNull(result.getInitialSystem());
		assertNotNull(result.getFinalSystem());
	}

	@Test
	public void combineTaskDescription() {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final System initialSystem_mock = context.mock(System.class, "initial-system");
		final System finalSystem_mock = context.mock(System.class, "final-system");

		context.checking(new Expectations() {
			{
				oneOf(taskDescription_mock).getInitialSystem();
				will(returnValue(initialSystem_mock));

				// <-- combineSystem

				oneOf(initialSystem_mock).getObjects();

				// combineSystem -->

				oneOf(taskDescription_mock).getFinalSystem();
				will(returnValue(finalSystem_mock));

				// <-- combineSystem

				oneOf(finalSystem_mock).getObjects();

				// combineSystem -->
			}
		});

		Element element = testable.combineTaskDescription(taskDescription_mock);
		assertEquals("taskDescription", element.getName());
		assertEquals("../taskDescription.xsd", element.getAttributeValue("noNamespaceSchemaLocation",
				Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")));
		assertNotNull(element.getChild("initialSystem"));
		assertNotNull(element.getChild("finalSystem"));
	}

	@Test
	public void parseSystem() {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> objects = new ArrayList<>();
		final Element object_mock = context.mock(Element.class, "object");
		objects.add(object_mock);

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
			}
		});

		assertTrue(testable.parseSystem(root_mock) instanceof System);
	}

	@Test
	public void combineSystem() {
		final System system_mock = context.mock(System.class);
		final List<SystemObject> systemObjects = new ArrayList<>();
		final SystemObject systemObject_mock = context.mock(SystemObject.class);
		systemObjects.add(systemObject_mock);

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
			}
		});

		Element element = testable.combineSystem(system_mock);
		assertNotNull(element.getChild("systemObject"));
	}

	@Test
	public void parseSystemObject() {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> attributes = new ArrayList<>();
		final Element attribute_mock = context.mock(Element.class, "attribute");
		attributes.add(attribute_mock);
		final List<Element> links = new ArrayList<>();
		final Element link_mock = context.mock(Element.class, "link");
		links.add(link_mock);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("object-name"));

				oneOf(root_mock).getChildText("id");
				will(returnValue("object-id"));

				oneOf(root_mock).getChildren("attribute");
				will(returnValue(attributes));

				// <-- parseAttribute

				oneOf(attribute_mock).getChildText("name");

				oneOf(attribute_mock).getChild("value");

				// parseAttribute -->

				oneOf(root_mock).getChildren("link");
				will(returnValue(links));

				// <-- parseLink

				oneOf(link_mock).getChildText("name");

				oneOf(link_mock).getChildText("objectId");

				// parseLink -->
			}
		});

		assertTrue(testable.parseSystemObject(root_mock) instanceof SystemObject);
	}

	@Test
	public void combineSystemObjectTemplate() {
		final SystemObject systemObject_mock = context.mock(SystemObject.class);
		final Attribute attribute_mock = context.mock(Attribute.class);
		final Link link_mock = context.mock(Link.class);
		final List<Attribute> attributes = new ArrayList<>();
		attributes.add(attribute_mock);
		final List<Link> links = new ArrayList<>();
		links.add(link_mock);

		context.checking(new Expectations() {
			{
				oneOf(systemObject_mock).getName();
				will(returnValue("object-name"));

				oneOf(systemObject_mock).getId();
				will(returnValue("object-id"));

				oneOf(systemObject_mock).getAttributes();
				will(returnValue(attributes));

				// <-- combineAttribute

				oneOf(attribute_mock).getName();

				oneOf(attribute_mock).getValue();

				// combineAttribute -->

				oneOf(systemObject_mock).getLinks();
				will(returnValue(links));

				// <-- combineLink

				oneOf(link_mock).getName();

				oneOf(link_mock).getObjectId();

				// combineLink -->
			}
		});

		Element element = testable.combineSystemObject(systemObject_mock);
		assertEquals("object-name", element.getChildText("name"));
		assertEquals("object-id", element.getChildText("id"));
		assertNotNull(element.getChild("attribute"));
		assertNotNull(element.getChild("link"));
	}

	@Test
	public void parseAttribute() {
		final Element root_mock = context.mock(Element.class, "root");
		final Element value_mock = context.mock(Element.class, "value");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChild("value");
				will(returnValue(value_mock));

				// <-- parseValue

				oneOf(value_mock).getAttributeValue("type", "string");

				oneOf(value_mock).getText();

				// parseValue -->
			}
		});

		Attribute result = testable.parseAttribute(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals("", result.getValue());
	}

	@Test
	public void combineAttribute() {
		final Attribute attribute_mock = context.mock(Attribute.class);
		final Object value_mock = context.mock(Object.class);

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(attribute_mock).getValue();
				will(returnValue(value_mock));
			}
		});

		Element element = testable.combineAttribute(attribute_mock);
		assertEquals("attribute-name", element.getChildText("name"));
		assertNotNull(element.getChild("value"));
	}

	@Test
	public void combineAttribute_empty_value() {
		final Attribute attribute_mock = context.mock(Attribute.class);

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(attribute_mock).getValue();
				will(returnValue(null));
			}
		});

		Element element = testable.combineAttribute(attribute_mock);
		assertEquals("attribute-name", element.getChildText("name"));
		assertNull(element.getChild("value"));
	}

	@Test
	public void parseLink() {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChildText("objectId");
				will(returnValue("value"));
			}
		});
		Link result = testable.parseLink(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals("value", result.getObjectId());
	}

	@Test
	public void parseLinkTemplate_with_null() {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChildText("objectId");
				will(returnValue(null));
			}
		});
		Link result = testable.parseLink(root_mock);
		assertNotNull(result);
		assertEquals("name", result.getName());
		assertEquals(null, result.getObjectId());
	}

	@Test
	public void combineLink() {
		final Link link_mock = context.mock(Link.class);

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link-name"));

				oneOf(link_mock).getObjectId();
				will(returnValue("link-value"));
			}
		});

		Element element = testable.combineLink(link_mock);
		assertEquals("link-name", element.getChildText("name"));
		assertEquals("link-value", element.getChildText("objectId"));
	}

	@Test
	public void combineLink_empty_value() {
		final Link link_mock = context.mock(Link.class);

		context.checking(new Expectations() {
			{
				oneOf(link_mock).getName();
				will(returnValue("link-name"));

				oneOf(link_mock).getObjectId();
				will(returnValue(null));
			}
		});

		Element element = testable.combineLink(link_mock);
		assertEquals("link-name", element.getChildText("name"));
		assertNull(element.getChild("objectId"));
	}
}
