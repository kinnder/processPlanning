package points;

import org.junit.Assert;
import org.junit.Test;

import model.DesignTask;
import model.Element;
import model.SystemState;
import utility.Collection;

public class PointsAcceptanceTest {

	private class PointElementTestCase {

		public SystemState state_after;

		public Element element;

		public PointElementTestCase(Element element, SystemState state_after) {
			this.state_after = state_after;
			this.element = element;
		}
	}

	@Test
	public void PointElement() {
		SystemState state_before = PointStateBuilder.SinglePoint(0, 0, 0);

		PointElementTestCase[] testCases = {
				new PointElementTestCase(PointElementsDataBase.pointElement_noMovement(),
						PointStateBuilder.SinglePoint(0, 0, 0)),
				new PointElementTestCase(PointElementsDataBase.pointElement_movementRight(),
						PointStateBuilder.SinglePoint(1, 0, 0)),
				new PointElementTestCase(PointElementsDataBase.pointElement_movementLeft(),
						PointStateBuilder.SinglePoint(-1, 0, 0)),
				new PointElementTestCase(PointElementsDataBase.pointElement_movementForward(),
						PointStateBuilder.SinglePoint(0, 1, 0)),
				new PointElementTestCase(PointElementsDataBase.pointElement_movementBack(),
						PointStateBuilder.SinglePoint(0, -1, 0)),
				new PointElementTestCase(PointElementsDataBase.pointElement_movementUp(),
						PointStateBuilder.SinglePoint(0, 0, 1)),
				new PointElementTestCase(PointElementsDataBase.pointElement_movementDown(),
						PointStateBuilder.SinglePoint(0, 0, -1)) 
				};

		for (PointElementTestCase testCase : testCases) {
			Collection states = testCase.element.getStatesAfterTransitionFromState(state_before);

			Assert.assertEquals(1, states.size());
			Assert.assertEquals(0, states.get(0).compareTo(testCase.state_after));
		}
	}

	@Test
	public void pathFindingTaskIsValid() {
		SystemState initialState = PointStateBuilder.SinglePoint(0, 0, 0);
		SystemState targetState = PointStateBuilder.SinglePoint(0, 0, 0);
		DesignTask task = new DesignTask(initialState, targetState, new PointElementsDataBase());

		Assert.assertTrue(task.isValid());
	}
}
