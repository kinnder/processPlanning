package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
	public void parse() throws DataConversionException {
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

		TaskDescription result = (TaskDescription) testable.parse(root_mock);
		assertTrue(result instanceof TaskDescription);
		assertNotNull(result.getInitialSystem());
		assertNotNull(result.getFinalSystem());
	}

	@Test
	public void combine() {
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

		Element element = testable.combine(taskDescription_mock);
		assertEquals("taskDescription", element.getName());
		assertEquals("../taskDescription.xsd", element.getAttributeValue("noNamespaceSchemaLocation",
				Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")));
		assertNotNull(element.getChild("initialSystem"));
		assertNotNull(element.getChild("finalSystem"));
	}

	@Test
	public void parseSystem() throws DataConversionException {
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
}
