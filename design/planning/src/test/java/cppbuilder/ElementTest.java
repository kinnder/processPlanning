package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cppbuilder.objects.MaterialPoint;
import cppbuilder.objects.Obstacle;
import cppbuilder.objects.Point;
import cppbuilder.objects.Rect;
import cppbuilder.objects.RobotPickAndPlace;
import cppbuilder.systems.AssembleLine;
import cppbuilder.systems.MaterialPoints;

public class ElementTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	Element out1;

	Element out2;

	@Before
	public void setUp() {
		out1 = new Element();
		out2 = new Element();

		prepare_Random(out2);
	}

	public static void prepare_Random(Element element) {
		SystemStateTest.prepare_Random(element.systemStateBeforeTransition);
		SystemStateTest.prepare_Random(element.systemStateAfterTransition);
		TransitionTest.prepare_Random(element.transition);
	}

	@Test
	public void equality() {
		Assert.assertEquals(false, out2.operator_equality(out1));

		Assert.assertEquals(true, out2.operator_equality(out2));
	}

	@Test
	public void calculateSystemState_pointMoveToRight() {
		SystemState given = MaterialPoints.twoPoints(new Point(0, 0), new Point(0, 0));
		SystemStateCollection expected = new SystemStateCollection();
		expected.add(MaterialPoints.twoPoints(new Point(1, 0), new Point(0, 0)));
		expected.add(MaterialPoints.twoPoints(new Point(0, 0), new Point(1, 0)));

		SystemStateCollection actual = MaterialPoint.moveRight().calculateSystemState(given);
		Assert.assertEquals(expected.size(), actual.size());
		Assert.assertEquals(true, expected.operator_equality(actual));
	}

	@Test
	public void calculateSystemState_BoxMovedToTable1() {
		SystemState given = AssembleLine.robotPickAndPlace(Attribute.VALUE_SHUTTLE);
		SystemStateCollection expected = new SystemStateCollection();
		expected.add(AssembleLine.robotPickAndPlace(Attribute.VALUE_TABLE1));

		SystemStateCollection actual = RobotPickAndPlace.moveBoxFromShuttleToTable1().calculateSystemState(given);
		Assert.assertEquals(expected.size(), actual.size());
		Assert.assertEquals(true, expected.operator_equality(actual));
	}

	@Test
	public void calculateSystemState_PointCollidedWithObstacle() {
		SystemState given = MaterialPoints.onePointWithOneObstacle(new Point(4, 4), new Rect(3, 3, 5, 5));
		SystemStateCollection expected = new SystemStateCollection();
		expected.add(MaterialPoints.onePointCollidedWithOneObstacle(new Point(4, 4), new Rect(3, 3, 5, 5)));

		SystemStateCollection actual = Obstacle.collision().calculateSystemState(given);
		Assert.assertEquals(expected.size(), actual.size());
		Assert.assertEquals(true, expected.operator_equality(actual));
	}

	@Test
	public void applyStateChangeToState() {
		SystemState given = MaterialPoints.onePointWithOneObstacle(new Point(1, 1),
				new Rect(new Point(0, 0), new Point(2, 2)));
		SystemState expected = MaterialPoints.onePointCollidedWithOneObstacle(new Point(1, 1),
				new Rect(new Point(0, 0), new Point(2, 2)));

		Obstacle.collision().applyStateChangeToState(given);
		Assert.assertEquals(true, expected.operator_equality(given));
	}
}
