package planning.model;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class TransformationTest {

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

	Transformation testable;

	@BeforeEach
	public void setup() {
		testable = new Transformation("id");
	}

	@Test
	public void applyTo() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final SystemObject systemObject_mock = context.mock(SystemObject.class);

		context.checking(new Expectations() {
			{
				oneOf(systemVariant_mock).getObjectByIdMatch("id");
				will(returnValue(systemObject_mock));
			}
		});

		testable.applyTo(systemVariant_mock);
	}
}
