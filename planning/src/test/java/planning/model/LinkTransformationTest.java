package planning.model;

import static org.junit.Assert.assertNotNull;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class LinkTransformationTest {

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

	LinkTransformation testable;

	@BeforeEach
	public void setup() {
		testable = new LinkTransformation("id-template", "link-name", "link-value");
	}

	@Test
	public void constructor() {
		assertNotNull(testable);
	}
}
