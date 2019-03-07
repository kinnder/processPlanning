package asm;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import asm.elements.RotateToTransportLine;
import model.Element;
import model.SystemState;
import utility.Collection;

public class ASMAcceptanceTest {

	@Test
	@Ignore
	public void ASMElement() {
		Element element = RotateToTransportLine.getElement();
		SystemState stateBeforeTransition = RotateToTransportLine.getStateBeforeTransition();
		SystemState stateAfterTransition = RotateToTransportLine.getStateAfterTransition();
		Assert.assertNotEquals(stateAfterTransition, stateBeforeTransition);

		Collection states = element.getStatesAfterTransitionFromState(stateBeforeTransition);
		Assert.assertEquals(1, states.size());
		Assert.assertEquals(0, states.get(0).compareTo(stateAfterTransition));

		Assert.fail("WIP");
	}
}
