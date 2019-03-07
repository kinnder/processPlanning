package model;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ElementsDataBaseTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	ElementsDataBase elements;

	Element element_mock;

	SystemState pattern_mock;

	SystemTransition transition_mock;

	@Before
	public void setUp() {
		element_mock = context.mock(Element.class, "element");
		pattern_mock = context.mock(SystemState.class, "pattern");
		transition_mock = context.mock(SystemTransition.class, "transition");

		elements = new ElementsDataBase();
		elements.elements.add(element_mock);

		context.checking(new Expectations() {
			{
				allowing(element_mock).getStatePattern();
				will(returnValue(pattern_mock));
			}
		});
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void haveElementsMatchingState() {
		final SystemState state_mock = context.mock(SystemState.class, "state");

		context.checking(new Expectations() {
			{
				oneOf(state_mock).matches(pattern_mock);
				will(returnValue(true));
			}
		});

		Assert.assertTrue(elements.haveElementMatchingState(state_mock));
	}

	@Test
	public void haveElementsMatchingState_noMatchingElement() {
		final SystemState state_mock = context.mock(SystemState.class, "state");

		context.checking(new Expectations() {
			{
				oneOf(state_mock).matches(pattern_mock);
				will(returnValue(false));
			}
		});

		Assert.assertFalse(elements.haveElementMatchingState(state_mock));
	}
}
