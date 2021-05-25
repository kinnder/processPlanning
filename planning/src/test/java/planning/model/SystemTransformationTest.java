package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SystemTransformationTest {

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

	SystemTransformation testable;

	Action action_mock;

	SystemTemplate systemTemplate_mock;

	Transformation transformation_mock;

	Transformation[] transformations;

	@BeforeEach
	public void setup() {
		action_mock = context.mock(Action.class, "action");
		systemTemplate_mock = context.mock(SystemTemplate.class, "template");
		transformation_mock = context.mock(Transformation.class, "transformation");
		transformations = new Transformation[] { transformation_mock };

		testable = new SystemTransformation("system-transformation-name", action_mock, systemTemplate_mock,
				transformations);
	}

	@Test
	public void getAction() {
		assertEquals(action_mock, testable.getAction());
	}

	@Test
	public void newInstance() {
		testable = new SystemTransformation("system-transformation-name");

		assertEquals("system-transformation-name", testable.getName());
		assertNull(testable.getAction());
		assertNull(testable.getSystemTemplate());
		assertNull(testable.getTransformations());
	}

	@Test
	public void setAction() {
		testable = new SystemTransformation("system-transformation-name");
		testable.setAction(action_mock);

		assertEquals(action_mock, testable.getAction());
	}

	@Test
	public void setSystemTemplate() {
		testable = new SystemTransformation("system-transformation-name");
		testable.setSystemTemplate(systemTemplate_mock);

		assertEquals(systemTemplate_mock, testable.getSystemTemplate());
	}

	@Test
	public void setTransformations() {
		testable = new SystemTransformation("system-transformation-name");
		testable.setTransformations(transformations);

		assertEquals(transformations, testable.getTransformations());
	}

	@Test
	public void applyTo() throws CloneNotSupportedException {
		final System system_mock = context.mock(System.class, "system");
		final System systemClone_mock = context.mock(System.class, "system-clone");
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class, "ids-matching");
		final IdsMatching idsMatchings[] = new IdsMatching[] { idsMatching_mock };

		context.checking(new Expectations() {
			{
				oneOf(systemTemplate_mock).matchIds(system_mock);
				will(returnValue(idsMatchings));

				oneOf(system_mock).clone();
				will(returnValue(systemClone_mock));

				oneOf(action_mock).haveAllPreConditionsPassed(with(any(SystemVariant.class)));
				will(returnValue(true));

				oneOf(transformation_mock).applyTo(with(any(SystemVariant.class)));

				oneOf(action_mock).updateParameters(with(any(SystemVariant.class)));
			}
		});

		SystemVariant systemVariants[] = testable.applyTo(system_mock);
		assertEquals(1, systemVariants.length);
	}

	@Test
	public void applyTo_actionConditionsFails() throws CloneNotSupportedException {
		final System system_mock = context.mock(System.class, "system");
		final System systemClone_mock = context.mock(System.class, "system-clone");
		final IdsMatching idsMatching_mock = context.mock(IdsMatching.class, "ids-matching");
		final IdsMatching idsMatchings[] = new IdsMatching[] { idsMatching_mock };

		context.checking(new Expectations() {
			{
				oneOf(systemTemplate_mock).matchIds(system_mock);
				will(returnValue(idsMatchings));

				oneOf(system_mock).clone();
				will(returnValue(systemClone_mock));

				oneOf(action_mock).haveAllPreConditionsPassed(with(any(SystemVariant.class)));
				will(returnValue(false));
			}
		});

		SystemVariant systemVariants[] = testable.applyTo(system_mock);
		assertEquals(0, systemVariants.length);
	}

	@Test
	public void getName() {
		assertEquals("system-transformation-name", testable.getName());
	}

	@Test
	public void getSystemTemplate() {
		assertEquals(systemTemplate_mock, testable.getSystemTemplate());
	}

	@Test
	public void getTransformations() {
		assertEquals(transformations, testable.getTransformations());
	}
}
