package model;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utility.Collection;

public class TransitionalStateTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	TransitionalState transitionalState;

	Collection modifiingObjects_mock;

	Collection permanentObjects_mock;

	@Before
	public void setUp() {
		modifiingObjects_mock = context.mock(Collection.class, "modifiingObjects");
		permanentObjects_mock = context.mock(Collection.class, "permanentObjects");

		transitionalState = new TransitionalState();
		transitionalState.modifiingObjects = modifiingObjects_mock;
		transitionalState.permanentObjects = permanentObjects_mock;
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void getStateAfterTransition() {
		final SystemTransition transition_mock = context.mock(SystemTransition.class);
		final Collection modifiedObjects = new Collection();
		final Collection nonModifiedObjects = new Collection();

		context.checking(new Expectations() {
			{
				oneOf(transition_mock).getModifiedObjects(modifiingObjects_mock);
				will(returnValue(modifiedObjects));

				oneOf(permanentObjects_mock).clone();
				will(returnValue(nonModifiedObjects));
			}
		});

		transitionalState.getStateAfterTransition(transition_mock);
	}
}
