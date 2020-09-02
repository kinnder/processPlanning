package application.storage.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

	SystemXMLSchema systemXMLSchema_mock;

	TaskDescriptionXMLSchema testable;

	@BeforeEach
	public void setup() {
		systemXMLSchema_mock = context.mock(SystemXMLSchema.class);

		testable = new TaskDescriptionXMLSchema(systemXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new TaskDescriptionXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element initialSystem_mock = context.mock(Element.class, "initial-system-element");
		final Element finalSystem_mock = context.mock(Element.class, "final-system-element");
		final System initialSystem = new System();
		final System finalSystem = new System();

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChild("initialSystem");
				will(returnValue(initialSystem_mock));

				oneOf(systemXMLSchema_mock).parse(initialSystem_mock);
				will(returnValue(initialSystem));

				oneOf(root_mock).getChild("finalSystem");
				will(returnValue(finalSystem_mock));

				oneOf(systemXMLSchema_mock).parse(finalSystem_mock);
				will(returnValue(finalSystem));
			}
		});

		TaskDescription result = (TaskDescription) testable.parse(root_mock);
		assertTrue(result instanceof TaskDescription);
		assertEquals(initialSystem, result.getInitialSystem());
		assertEquals(finalSystem, result.getFinalSystem());
	}

	@Test
	public void combine() {
		final TaskDescription taskDescription_mock = context.mock(TaskDescription.class);
		final System initialSystem_mock = context.mock(System.class, "initial-system");
		final System finalSystem_mock = context.mock(System.class, "final-system");
		final Element initialSystem = new Element("system");
		final Element finalSystem = new Element("system");

		context.checking(new Expectations() {
			{
				oneOf(taskDescription_mock).getInitialSystem();
				will(returnValue(initialSystem_mock));

				oneOf(systemXMLSchema_mock).combine(initialSystem_mock);
				will(returnValue(initialSystem));

				oneOf(taskDescription_mock).getFinalSystem();
				will(returnValue(finalSystem_mock));

				oneOf(systemXMLSchema_mock).combine(finalSystem_mock);
				will(returnValue(finalSystem));
			}
		});

		Element element = testable.combine(taskDescription_mock);
		assertEquals("taskDescription", element.getName());
		assertEquals("../taskDescription.xsd", element.getAttributeValue("noNamespaceSchemaLocation", Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance")));
		assertEquals(initialSystem, element.getChild("initialSystem"));
		assertEquals(finalSystem, element.getChild("finalSystem"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("taskDescription", testable.getSchemaName());
	}
}
