package planning.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class AttributeTransformationTest {

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

	AttributeTransformation testable;

	@BeforeEach
	public void setup() {
		testable = new AttributeTransformation("id-template", "attribute-name", "attribute-value");
	}

	@Test
	public void constructor() {
		assertNotNull(testable);
	}
}
