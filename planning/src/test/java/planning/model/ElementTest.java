package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		context.checking(new Expectations() {
			{
				oneOf(transformation_mock).applyTo(systemVariant_mock);
			}
		});

		testable.applyTo(systemVariant_mock);
	}

	@Test
	public void getOperation() {
		assertEquals(operation, testable.getOperation());
	}

	@Test
	public void prepareSystemVariants() {
		final System system_mock = context.mock(System.class, "system");
		final System systemClone_mock = context.mock(System.class, "system-clone");
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class, "ids-matching");
		final IdsMatching idsMatchings[] = new IdsMatching[] { idsMatching_mock };

		context.checking(new Expectations() {
			{
				oneOf(system_mock).matchIds(template_mock);
				will(returnValue(idsMatchings));

				oneOf(system_mock).clone();
				will(returnValue(systemClone_mock));
			}
		});

		SystemVariant systemVariants[] = testable.prepareSystemVariants(system_mock);
		assertEquals(1, systemVariants.length);
	}
}
