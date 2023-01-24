package testtools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatcherTest {

	Matcher<Object> testable;

	@BeforeEach
	public void setup() {
		testable = new Matcher<>();
	}

	@Test
	public void describeTo() {
		final Description description = new StringDescription();
		testable.describeTo(description);
		assertEquals("", description.toString());
	}

	@Test
	public void compareString() {
		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				compare("testField", "testValue", "testValue");
			}
		});

		assertTrue(testable.matches(new Object()));
	}

	@Test
	public void compareString_mismatch() {
		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				compare("testField", "testValue", "anotherValue");
			}
		});

		assertFalse(testable.matches(new Object()));

		final Description description = new StringDescription();
		testable.describeTo(description);
		assertEquals("field [testField] - expected: [testValue], actual: [anotherValue]", description.toString());
	}

	@Test
	public void compareLong() {
		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				compare("testField", (long) 100, (long) 100);
			}
		});

		assertTrue(testable.matches(new Object()));
	}

	@Test
	public void compareLong_mismatch() {
		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				compare("testField", (long) 100, (long) 200);
			}
		});

		assertFalse(testable.matches(new Object()));

		final Description description = new StringDescription();
		testable.describeTo(description);
		assertEquals("field [testField] - expected: [100], actual: [200]", description.toString());
	}

	@Test
	public void compareInt() {
		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				compare("testField", (int) 300, (int) 300);
			}
		});

		assertTrue(testable.matches(new Object()));
	}

	@Test
	public void compareInt_mismatch() {
		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				compare("testField", (int) 300, (int) 400);
			}
		});

		assertFalse(testable.matches(new Object()));

		final Description description = new StringDescription();
		testable.describeTo(description);
		assertEquals("field [testField] - expected: [300], actual: [400]", description.toString());
	}

	@Test
	public void compareBoolean() {
		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				compare("testField", true, true);
			}
		});

		assertTrue(testable.matches(new Object()));
	}

	@Test
	public void compareBoolean_mismatch() {
		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				compare("testField", true, false);
			}
		});

		assertFalse(testable.matches(new Object()));

		final Description description = new StringDescription();
		testable.describeTo(description);
		assertEquals("field [testField] - expected: [true], actual: [false]", description.toString());
	}

	@Test
	public void compareObject() {
		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				Object object = new Object();
				compare("testField", object, object);
			}
		});

		assertTrue(testable.matches(new Object()));
	}

	@Test
	public void compareObject_mismatch() {
		Object expected = new Object();
		Object actual = new Object();

		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				compare("testField", expected, actual);
			}
		});

		assertFalse(testable.matches(new Object()));

		final Description description = new StringDescription();
		testable.describeTo(description);
		assertEquals("field [testField] - expected: [" + expected.toString() + "], actual: [" + actual.toString() + "]",
				description.toString());
	}

	@Test
	public void compareTimestamps() {
		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				compareTimestamps("testField", (long) 100, (long) 100);
			}
		});

		assertTrue(testable.matches(new Object()));
	}

	@Test
	public void compareTimestamps_mismatch() {
		testable.addExpectation(testable.new MatcherExpectation() {
			@Override
			public void trigger(Object arg) {
				compareTimestamps("testField", (long) 100, (long) 10000);
			}
		});

		assertFalse(testable.matches(new Object()));

		final Description description = new StringDescription();
		testable.describeTo(description);
		assertEquals("field [testField] - expected: [100], actual: [10000], difference grater than [1000]",
				description.toString());
	}
}
