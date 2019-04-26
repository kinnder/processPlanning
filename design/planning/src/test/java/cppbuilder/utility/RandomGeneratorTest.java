package cppbuilder.utility;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RandomGeneratorTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Before
	public void setUp() {
	}

	@Test
	public void randomString() {
		final int length = 10;
		Assert.assertNotEquals(RandomGenerator.getString(length), RandomGenerator.getString(length));
	}

	@Test
	public void randomInteger() {
		final int min = -1000;
		final int max = 1000;
		Assert.assertNotEquals(RandomGenerator.getInteger(min, max), RandomGenerator.getInteger(min, max));
	}

	@Test
	public void randomBool() {
		final int attemptsMax = 100;
		boolean actual = RandomGenerator.getBool();
		int attempts = 0;
		for (; attempts < attemptsMax; attempts++) {
			if (actual != RandomGenerator.getBool()) {
				break;
			}
		}
		Assert.assertNotEquals(attemptsMax, attempts);
	}

	@Test
	public void randomDouble() {
		final int min = -1000;
		final int max = 1000;
		final int precision = 5;
		Assert.assertNotEquals(RandomGenerator.getDouble(min, max, precision),
				RandomGenerator.getDouble(min, max, precision), 0.00005);
	}

	@Test
	public void randomIntegerFrom() {
		final int attemptsMax = 100;
		final int[] array = { 1, 2, 3 };
		int actual = RandomGenerator.getIntegerFrom(array);
		int attempts = 0;
		for (; attempts < attemptsMax; attempts++) {
			if (actual != RandomGenerator.getIntegerFrom(array)) {
				break;
			}
		}
		Assert.assertNotEquals(attemptsMax, attempts);
	}

	@Test
	public void randomStringFrom() {
		final int attemptsMax = 100;
		final String[] array = { "one", "two", "three" };
		String actual = RandomGenerator.getStringFrom(array);
		int attempts = 0;
		for (; attempts < attemptsMax; attempts++) {
			if (!actual.equals(RandomGenerator.getStringFrom(array))) {
				break;
			}
		}
		Assert.assertNotEquals(attemptsMax, attempts);
	}
}
