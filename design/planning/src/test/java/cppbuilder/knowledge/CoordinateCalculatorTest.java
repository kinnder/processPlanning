package cppbuilder.knowledge;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoordinateCalculatorTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	CoordinateCalculator out;

	@Before
	public void setUp() {
		out = new CoordinateCalculator();
	}

	@Test
	public void getXCoordinateForPosition() {
		Assert.assertEquals(4, out.getXCoordinateForPosition(1));
		Assert.assertEquals(12, out.getXCoordinateForPosition(5));
		Assert.assertEquals(4, out.getXCoordinateForPosition(6));
		Assert.assertEquals(12, out.getXCoordinateForPosition(10));
	}

	@Test
	public void getYCoordinateForPosition() {
		Assert.assertEquals(15, out.getYCoordinateForPosition(1));
		Assert.assertEquals(15, out.getYCoordinateForPosition(5));
		Assert.assertEquals(12, out.getYCoordinateForPosition(6));
		Assert.assertEquals(12, out.getYCoordinateForPosition(10));
	}

	@Test
	public void getZCoordinateForPosition() {
		Assert.assertEquals(0, out.getZCoordinateForPosition(0));
		Assert.assertEquals(10, out.getZCoordinateForPosition(1));
	}
}
