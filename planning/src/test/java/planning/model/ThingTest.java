package planning.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class ThingTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery();

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	Thing testable;

	@BeforeEach
	public void setup() {
		testable = new Thing();
	}

	@Test
	public void constructor() {
		assertNotNull(testable);
	}
}
