package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.SystemTransformation;

public class SystemTransformationsXMLFileTest {

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

	SystemTransformationsXMLFile testable;

	@BeforeEach
	public void setup() {
		testable = new SystemTransformationsXMLFile();
	}

	@Test
	public void getSystemTransformations() {
		assertEquals(0, testable.getSystemTransformations().length);
	}

	@Test
	public void setSystemTransformations() {
		final SystemTransformation systemTransformation_mock = context.mock(SystemTransformation.class);
		final SystemTransformation systemTransformations[] = new SystemTransformation[] { systemTransformation_mock };

		testable.setSystemTransformations(systemTransformations);
		assertEquals(1, testable.getSystemTransformations().length);
	}

	@Test
	public void getXMLSchema() {
		assertTrue(testable.getXMLSchema() instanceof SystemTransformationsXMLSchema);
	}
}
