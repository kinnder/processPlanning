package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

				oneOf(initialSystem_mock).getChildren("link");

				// parseSystem -->

				oneOf(root_mock).getChild("finalSystem");
				will(returnValue(finalSystem_mock));

				// <-- parseSystem

				oneOf(finalSystem_mock).getChildren("systemObject");

				oneOf(finalSystem_mock).getChildren("link");

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

				oneOf(initialSystem_mock).getLinks();

				// combineSystem -->

				oneOf(taskDescription_mock).getFinalSystem();
				will(returnValue(finalSystem_mock));

				// <-- combineSystem

				oneOf(finalSystem_mock).getObjects();

				oneOf(finalSystem_mock).getLinks();

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
}
