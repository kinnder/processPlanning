package model;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import utility.Collection;

public class ElementTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	Element element;

	SystemState pattern_mock;

	SystemTransition transition_mock;

	@Before
	public void setUp() {
		pattern_mock = context.mock(SystemState.class, "patter");
		transition_mock = context.mock(SystemTransition.class, "transition");

		element = new Element(pattern_mock, transition_mock);
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void getStatesAfterTransitionFromState() {
		final SystemState state_mock = context.mock(SystemState.class, "state");
		final TransitionalState transitionalState_mock = context.mock(TransitionalState.class);
		final Collection transitionalStates = new Collection();
		transitionalStates.add(transitionalState_mock);
		final SystemState transitedState_mock = context.mock(SystemState.class, "transitedState");

		context.checking(new Expectations() {
			{
				oneOf(state_mock).prepareTransitionalStates(pattern_mock);
				will(returnValue(transitionalStates));

				oneOf(transitionalState_mock).getStateAfterTransition(transition_mock);
				will(returnValue(transitedState_mock));
			}
		});

		Collection transitions = element.getStatesAfterTransitionFromState(state_mock);
		Assert.assertEquals(1, transitions.size());
	}

	@Test
	public void getStatePattern() {
		Assert.assertEquals(pattern_mock, element.getStatePattern());
	}

	@Test
	public void getTransition() {
		Assert.assertEquals(transition_mock, element.getTransition());
	}
}
