package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SystemVariantTest {

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

	SystemVariant testable;

	System system_mock;

	IdsMatching idsMatching_mock;

	Action action_mock;

	@BeforeEach
	public void setup() {
		system_mock = context.mock(System.class);
		idsMatching_mock = context.mock(IdsMatching.class);
		action_mock = context.mock(Action.class);

		testable = new SystemVariant(system_mock, idsMatching_mock, action_mock);
	}

	@Test
	public void getObjectIdByIdMatch() {
		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get("#ID-1");
				will(returnValue("id-1"));
			}
		});

		assertEquals("id-1", testable.getObjectIdByIdMatch("#ID-1"));
	}

	@Test
	public void getObjectByIdMatch() {
		final SystemObject object_mock = context.mock(SystemObject.class);

		context.checking(new Expectations() {
			{
				oneOf(idsMatching_mock).get("#ID-1");
				will(returnValue("id-1"));

				oneOf(system_mock).getObjectById("id-1");
				will(returnValue(object_mock));
			}
		});

		assertEquals(object_mock, testable.getObjectByIdMatch("#ID-1"));
	}

	@Test
	public void getSystem() {
		assertEquals(system_mock, testable.getSystem());
	}

	@Test
	public void getIdsMatching() {
		assertEquals(idsMatching_mock, testable.getIdsMatching());
	}
}
