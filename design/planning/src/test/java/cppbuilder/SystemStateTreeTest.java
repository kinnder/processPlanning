package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cppbuilder.objects.MaterialPoint;
import cppbuilder.objects.Point;
import cppbuilder.objects.Rect;
import cppbuilder.systems.MaterialPoints;

public class SystemStateTreeTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	SystemStateTree out;

	@Before
	public void setUp() {
		out = new SystemStateTree();
	}

	static public void prepareTree_SingleRouteWithoutDeadEnds(SystemStateTree tree) {
		tree.states.add(MaterialPoints.onePoint(new Point(0, 0))); // 1
		tree.states.add(MaterialPoints.onePoint(new Point(1, 0))); // 2
		tree.states.add(MaterialPoints.onePoint(new Point(2, 0))); // 3
		tree.startState = tree.states.get(0);
		tree.states.get(0).link(tree.states.get(1), new Transition("1-2"));
		tree.states.get(1).link(tree.states.get(2), new Transition("2-3"));
		tree.finalState = tree.states.get(2);
	}

	static public void prepareTree_SingleRouteWithDeadEnds(SystemStateTree tree) {
		tree.states.add(MaterialPoints.onePoint(new Point(0, 0))); // 1
		tree.states.add(MaterialPoints.onePoint(new Point(0, 1))); // 2
		tree.states.add(MaterialPoints.onePoint(new Point(1, 0))); // 3
		tree.states.add(MaterialPoints.onePoint(new Point(1, 1))); // 4
		tree.states.add(MaterialPoints.onePoint(new Point(2, 0))); // 5
		tree.startState = tree.states.get(0);
		tree.states.get(0).link(tree.states.get(1), new Transition("1-2"));
		tree.states.get(0).link(tree.states.get(2), new Transition("1-3"));
		tree.states.get(2).link(tree.states.get(3), new Transition("3-4"));
		tree.states.get(2).link(tree.states.get(4), new Transition("3-5"));
		tree.finalState = tree.states.get(4);
	}

	static public void prepareTree_SeveralRoutesWithNoCrossing(SystemStateTree tree) {
		tree.states.add(MaterialPoints.onePoint(new Point(0, 0))); // 1
		tree.states.add(MaterialPoints.onePoint(new Point(1, 1))); // 2
		tree.states.add(MaterialPoints.onePoint(new Point(1, 0))); // 3
		tree.states.add(MaterialPoints.onePoint(new Point(1, -1))); // 4
		tree.states.add(MaterialPoints.onePoint(new Point(2, 0))); // 5
		tree.startState = tree.states.get(0);
		tree.states.get(0).link(tree.states.get(1), new Transition("1-2"));
		tree.states.get(0).link(tree.states.get(2), new Transition("1-3"));
		tree.states.get(0).link(tree.states.get(3), new Transition("1-4"));
		tree.states.get(1).link(tree.states.get(4), new Transition("2-5"));
		tree.states.get(2).link(tree.states.get(4), new Transition("3-5"));
		tree.states.get(3).link(tree.states.get(4), new Transition("4-5"));
		tree.finalState = tree.states.get(4);
	}

	private void checkRoute(Route route, String step1, String step2) {
		Assert.assertEquals(2, route.nodes.size());
		Assert.assertEquals(step1, route.nodes.get(0).link.transition.name);
		Assert.assertEquals(step2, route.nodes.get(1).link.transition.name);
	}

	@Test
	public void prepareRoutes_SingleRouteWithoutDeadEnds() {
		prepareTree_SingleRouteWithoutDeadEnds(out);

		out.prepareRoutes();
		Assert.assertEquals(1, out.routes.size());
		checkRoute(out.routes.get(0), "1-2", "2-3");
	}

	@Test
	public void prepareRoutes_SingleRouteWithDeadEnds() {
		prepareTree_SingleRouteWithDeadEnds(out);

		out.prepareRoutes();
		Assert.assertEquals(1, out.routes.size());
		checkRoute(out.routes.get(0), "1-3", "3-5");
	}

	@Test
	public void prepareRoutes_SeveralRoutesWithNoCrossing() {
		prepareTree_SeveralRoutesWithNoCrossing(out);

		out.prepareRoutes();
		Assert.assertEquals(3, out.routes.size());
		checkRoute(out.routes.get(0), "1-2", "2-5");
		checkRoute(out.routes.get(1), "1-3", "3-5");
		checkRoute(out.routes.get(2), "1-4", "4-5");
	}

	@Test
	public void buildTree_SameStates() {
		Element l_left = MaterialPoint.moveLeft();
		out.states.add(MaterialPoints.onePoint(new Point(0, 0)));
		out.addNewStateToTree(out.states.get(0), l_left);
		Assert.assertEquals(2, out.states.size());
		out.addNewStateToTree(out.states.get(0), l_left);
		Assert.assertEquals(2, out.states.size());
	}

	@Test
	public void buildTree_StateRejectedByRestriction() {
		Element l_element = MaterialPoint.moveLeft();
		out.states.add(MaterialPoints.onePointCollidedWithOneObstacle(new Point(0, 0),
				new Rect(new Point(-1, 1), new Point(-1, -1))));

		out.addNewStateToTree(out.states.get(0), l_element);
		Assert.assertEquals(1, out.states.size());
	}
}
