package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cppbuilder.objects.MaterialPoint;
import cppbuilder.objects.Point;
import cppbuilder.systems.AssembleLine;
import cppbuilder.systems.MaterialPoints;

public class DesigningTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	Designing out;

	@Before
	public void setUp() {
		out = new Designing();
	}

	private void checkRoute(Route route, String[] expectedNames) {
		Assert.assertEquals(expectedNames.length, route.nodes.size());
		for (int i = 0; i < expectedNames.length; i++) {
			Assert.assertEquals(expectedNames[i], route.nodes.get(i).link.transition.name);
		}
	}

	@Test
	public void solve_ObjectMovement_NoSolution() {
		SystemState begin = MaterialPoints.onePoint(new Point(0, 0));
		SystemState end = MaterialPoints.onePoint(new Point(3, 0));
		ElementBase elements = MaterialPoints.movementLeft();

		Assert.assertEquals(false, out.solve(begin, end, elements));
	}

	@Test
	public void solve_ObjectMovement_Linear() {
		SystemState begin = MaterialPoints.onePoint(new Point(0, 0));
		SystemState end = MaterialPoints.onePoint(new Point(3, 0));
		ElementBase elements = MaterialPoints.movementRight();

		Assert.assertEquals(true, out.solve(begin, end, elements));
		checkRoute(out.getSolution(),
				new String[] { MaterialPoint.MOVE_RIGHT, MaterialPoint.MOVE_RIGHT, MaterialPoint.MOVE_RIGHT });
		Assert.assertEquals(true, out.getSolution().nodes.back().link.end.includes(end));
	}

	@Test
	public void solve_ObjectMovement_Stepwise() {
		SystemState begin = MaterialPoints.onePoint(new Point(1, 1));
		SystemState end = MaterialPoints.onePoint(new Point(4, 4));
		ElementBase elements = MaterialPoints.movements();

		Assert.assertEquals(true, out.solve(begin, end, elements));
		Assert.assertEquals(true, out.getSolution().nodes.back().link.end.includes(end));
	}

	@Test
	public void solve_ObjectMovement_StepwiseWithObstacles() {
//		 y
//		 6 + - - - - - - - - - +
//		 5 | . - - + - - - + . |
//		 4 | b . . | . . . | . |
//		 3 | . | . | . | . | . |
//		 2 | . | . . . | . . e |
//		 1 | . + - - - + - - . |
//		 0 + - - - - - - - - - +
//		 0 0 1 2 3 4 5 6 7 8 9 A x

		SystemState begin = MaterialPoints.onePointWithObstacles(new Point(1, 4));
		SystemState end = MaterialPoints.onePoint(new Point(9, 2));
		ElementBase elements = MaterialPoints.movements();

		Assert.assertEquals(true, out.solve(begin, end, elements));
		checkRoute(out.getSolution(), new String[] { MaterialPoint.MOVE_RIGHT, MaterialPoint.MOVE_RIGHT,
				MaterialPoint.MOVE_DOWN, MaterialPoint.MOVE_DOWN, MaterialPoint.MOVE_RIGHT, MaterialPoint.MOVE_RIGHT,
				MaterialPoint.MOVE_UP, MaterialPoint.MOVE_UP, MaterialPoint.MOVE_RIGHT, MaterialPoint.MOVE_RIGHT,
				MaterialPoint.MOVE_DOWN, MaterialPoint.MOVE_DOWN, MaterialPoint.MOVE_RIGHT, MaterialPoint.MOVE_RIGHT });
		Assert.assertEquals(true, out.getSolution().nodes.back().link.end.includes(end));
	}

	@Test
	public void solve_Assembling_LensesInFrame() {
		SystemState begin = AssembleLine.create_ConfigurationWorkstation();
		AssembleLine.add_LensesInFrameTemplates(begin);
		SystemState end = AssembleLine.create_LensesInFrame();
		ElementBase elements = AssembleLine.assembling();

		Assert.assertEquals(true, out.solve(begin, end, elements));
		Assert.assertEquals(true, out.getSolution().nodes.back().link.end.includes(end));
		Assert.fail("Тест в процессе работы");
	}
}
