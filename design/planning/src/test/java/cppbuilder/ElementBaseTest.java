package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cppbuilder.ElementBase;
import cppbuilder.ElementCollection;
import cppbuilder.SystemState;
import cppbuilder.objects.MaterialPoint;
import cppbuilder.objects.Obstacle;
import cppbuilder.systems.MaterialPoints;

public class ElementBaseTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	ElementBase out;

	@Before
	public void setUp() {
		out = new ElementBase();
	}

	@Test
	public void findElement() {
		SystemState given = MaterialPoints.onePointWithOneObstacle();
		ElementCollection expected = new ElementCollection();
		expected.add(MaterialPoint.moveUp());
		expected.add(MaterialPoint.moveDown());
		expected.add(MaterialPoint.moveLeft());
		expected.add(MaterialPoint.moveRight());
		expected.add(Obstacle.collision());

		ElementCollection actual = MaterialPoints.movements().find(given);
		Assert.assertEquals(expected.size(), actual.size());
		Assert.assertEquals(true, expected.operator_equality(actual));
	}

	@Test
	public void findStateChangingElements() {
		SystemState given = MaterialPoints.onePointWithOneObstacle();
		ElementCollection expected = new ElementCollection();
		expected.add(Obstacle.collision());

		ElementCollection actual = MaterialPoints.movements().findStateChangingElements(given);
		Assert.assertEquals(true, expected.operator_equality(actual));
	}
}
