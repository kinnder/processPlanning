package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

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

	Map<?, ?> idsMatching;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setup() {
		system_mock = context.mock(System.class);
		idsMatching = context.mock(Map.class);

		testable = new SystemVariant(system_mock, (Map<String, String>) idsMatching);
	}

	@Test
	public void getObjectIdByIdMatch() {
		context.checking(new Expectations() {
			{
				oneOf(idsMatching).get("#ID-1");
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
				oneOf(idsMatching).get("#ID-1");
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
}
