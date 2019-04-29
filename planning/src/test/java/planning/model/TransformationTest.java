package planning.model;

import java.util.HashMap;
import java.util.Map;

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
		final System system_mock = context.mock(System.class);
		final Map<String, String> idsMatching = new HashMap<String, String>();
		idsMatching.put("id-template", "id");

		testable.applyTo(system_mock, idsMatching);
	}
}
