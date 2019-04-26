package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cppbuilder.objects.Point;
import cppbuilder.objects.Rect;
import cppbuilder.systems.MaterialPoints;

public class SystemStateTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	SystemState out1;
	SystemState out2;

	Transition transition;

	@Before
	public void setUp() {
		out1 = new SystemState();
		out2 = new SystemState();

		prepare_Random(out2);

		transition = new Transition();
		TransitionTest.prepare_Random(transition);
	}

	public static void prepare_Random(SystemState systemState) {
		SystemObjectCollectionTest.prepare_Random(systemState.systemObjects);
	}

	@Test
	public void equality() {
		Assert.assertEquals(false, out1.operator_equality(out2));

		out1 = MaterialPoints.onePoint(new Point(0, 0));
		out2 = MaterialPoints.onePoint(new Point(0, 0));
		Assert.assertEquals(true, out1.operator_equality(out2));
		Assert.assertEquals(true, out1.matches(out2));
		Assert.assertEquals(true, out2.matches(out1));
		Assert.assertEquals(true, out1.includes(out2));
		Assert.assertEquals(true, out2.includes(out1));
	}

	@Test
	public void matching_one_side() {
		out1 = MaterialPoints.onePoint(new Point(0, 0));
		out2 = MaterialPoints.twoPoints(new Point(0, 0), new Point(2, 2));
		Assert.assertEquals(false, out1.operator_equality(out2));
		Assert.assertEquals(true, out1.matches(out2));
		Assert.assertEquals(false, out2.matches(out1));
		Assert.assertEquals(false, out1.includes(out2));
		Assert.assertEquals(true, out2.includes(out1));
	}

	@Test
	public void matching_both_sides() {
		out1 = MaterialPoints.onePoint(new Point(0, 0));
		out2 = MaterialPoints.onePoint(new Point(1, 1));
		Assert.assertEquals(false, out1.operator_equality(out2));
		Assert.assertEquals(true, out1.matches(out2));
		Assert.assertEquals(true, out2.matches(out1));
	}

	@Test
	public void link() {
		out1.link(out2, transition);
		Assert.assertEquals(1, out1.links.size());
		Assert.assertEquals(true, out1.operator_equality(out1.links.get(0).begin));
		Assert.assertEquals(true, out2.operator_equality(out1.links.get(0).end));
		Assert.assertEquals(true, transition.operator_equality(out1.links.get(0).transition));
	}

	@Test
	public void isDeadEndState() {
		Assert.assertEquals(true, out1.isDeadEndState());

		out1.link(out2, transition);
		Assert.assertEquals(false, out1.isDeadEndState());
	}

	@Test
	public void isMatchingTo() {
		out1 = MaterialPoints.onePoint(new Point(0, 0));
		out2 = MaterialPoints.onePoint(new Point(0, 0));
		Assert.assertEquals(true, out1.isMatchingTo(out2));
	}

	@Test
	public void toXMLString() {
		Assert.assertEquals(false, out2.toXMLString().isEmpty());
	}

	@Test
	public void getMatchingSubset() {
		out1 = MaterialPoints.twoPointsWithTwoObstacles(new Point(1, 1), new Point(2, 2), new Rect(10, 10, 10, 10),
				new Rect(20, 20, 20, 20));
		SystemStateCollection expected = new SystemStateCollection();
		expected.add(MaterialPoints.onePointWithOneObstacle(new Point(1, 1), new Rect(10, 10, 10, 10)));
		expected.add(MaterialPoints.onePointWithOneObstacle(new Point(1, 1), new Rect(20, 20, 20, 20)));
		expected.add(MaterialPoints.onePointWithOneObstacle(new Point(2, 2), new Rect(10, 10, 10, 10)));
		expected.add(MaterialPoints.onePointWithOneObstacle(new Point(2, 2), new Rect(20, 20, 20, 20)));

		SystemStateCollection results = out1.findSubset(MaterialPoints.onePointWithOneObstacle());
		Assert.assertEquals(true, expected.operator_equality(results));
	}

	@Test
	public void getMatchingSubset_2() {
		out1 = MaterialPoints.onePointWithObstacles(new Point(0, 0));
		SystemStateCollection expected = new SystemStateCollection();
		expected.add(
				MaterialPoints.onePointWithOneObstacle(new Point(0, 0), new Rect(new Point(0, 6), new Point(10, 6))));
		expected.add(
				MaterialPoints.onePointWithOneObstacle(new Point(0, 0), new Rect(new Point(10, 6), new Point(10, 0))));
		expected.add(
				MaterialPoints.onePointWithOneObstacle(new Point(0, 0), new Rect(new Point(10, 0), new Point(0, 0))));
		expected.add(
				MaterialPoints.onePointWithOneObstacle(new Point(0, 0), new Rect(new Point(0, 0), new Point(0, 6))));
		expected.add(
				MaterialPoints.onePointWithOneObstacle(new Point(0, 0), new Rect(new Point(4, 5), new Point(4, 3))));
		expected.add(
				MaterialPoints.onePointWithOneObstacle(new Point(0, 0), new Rect(new Point(8, 5), new Point(8, 3))));
		expected.add(
				MaterialPoints.onePointWithOneObstacle(new Point(0, 0), new Rect(new Point(2, 3), new Point(2, 1))));
		expected.add(
				MaterialPoints.onePointWithOneObstacle(new Point(0, 0), new Rect(new Point(6, 3), new Point(6, 1))));
		expected.add(
				MaterialPoints.onePointWithOneObstacle(new Point(0, 0), new Rect(new Point(2, 1), new Point(8, 1))));
		expected.add(
				MaterialPoints.onePointWithOneObstacle(new Point(0, 0), new Rect(new Point(2, 5), new Point(2, 8))));

		SystemStateCollection results = out1.findSubset(MaterialPoints.onePointWithOneObstacle());
		Assert.assertEquals(true, expected.operator_equality(results));
	}

	@Test
	public void getMatchingSubset_3() {
		out1 = MaterialPoints.twoPoints(new Point(0, 0), new Point(1, 1));
		SystemStateCollection expected = new SystemStateCollection();
		expected.add(MaterialPoints.onePoint(new Point(0, 0)));
		expected.add(MaterialPoints.onePoint(new Point(1, 1)));

		SystemStateCollection results = out1.findSubset(MaterialPoints.onePoint(new Point(0, 0)));
		Assert.assertEquals(true, expected.operator_equality(results));
	}

	@Test
	public void applyStateChangingElements_DetectCollisions() {
		out1 = MaterialPoints.onePointWithOneObstacle(new Point(1, 1), new Rect(new Point(0, 0), new Point(2, 2)));
		SystemState expected = MaterialPoints.onePointCollidedWithOneObstacle(new Point(1, 1),
				new Rect(new Point(0, 0), new Point(2, 2)));

		out1.applyAllStateChangingElements(MaterialPoints.movements());
		Assert.assertEquals(true, expected.operator_equality(out1));
	}
}
