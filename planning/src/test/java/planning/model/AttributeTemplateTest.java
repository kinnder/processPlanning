package planning.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class AttributeTemplateTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery();

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	AttributeTemplate testable;

	@BeforeEach
	public void setup() {
		testable = new AttributeTemplate("attribute", "value");
	}

	@Test
	public void matches() {
		final Attribute template = new Attribute("attribute", "value");
		assertTrue(testable.matches(template));
	}

	@Test
	public void matches_differentName() {
		final Attribute template = new Attribute("different", "value");
		assertFalse(testable.matches(template));
	}

	@Test
	public void matches_differentType() {
		final Attribute template = new Attribute("attribute", 10);
		assertFalse(testable.matches(template));
	}

	@Test
	public void matches_differentValue() {
		final Attribute template = new Attribute("attribute", "different");
		assertFalse(testable.matches(template));
	}
}
