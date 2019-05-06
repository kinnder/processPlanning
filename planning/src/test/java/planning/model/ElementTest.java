package planning.model;

import java.util.Map;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class ElementTest {

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

	Element testable;

	String operation;

	System template_mock;

	Transformation transformation_mock;

	Transformation[] transformation;

	@BeforeEach
	public void setup() {
		operation = "operation";
		template_mock = context.mock(System.class, "template");
		transformation_mock = context.mock(Transformation.class, "transformation");
		transformation = new Transformation[] { transformation_mock };

		testable = new Element(operation, template_mock, transformation);
	}

	@Test
	public void applyTo() {
		final System system_mock = context.mock(System.class, "system");
		@SuppressWarnings("unchecked")
		final Map<String, String> matchings = context.mock(Map.class, "matchings");

		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).applyTo(system_mock, matchings);
			}
		});

		testable.applyTo(system_mock, matchings);
	}
}
