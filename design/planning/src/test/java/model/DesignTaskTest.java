package model;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DesignTaskTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	DesignTask task;

	ElementsDataBase elements_mock;

	SystemState initialState_mock;

	SystemState targetState_mock;

	@Before
	public void setUp() {
		initialState_mock = context.mock(SystemState.class, "initialState");
		targetState_mock = context.mock(SystemState.class, "targetState");
		elements_mock = context.mock(ElementsDataBase.class);

		task = new DesignTask(initialState_mock, targetState_mock, elements_mock);
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void isValid_noInitialState() {
		task = new DesignTask(null, null, null);

		Assert.assertFalse(task.isValid());
	}

	@Test
	public void isValid_noTargetState() {
		task = new DesignTask(initialState_mock, null, null);

		Assert.assertFalse(task.isValid());
	}

	@Test
	public void isValid_noElements() {
		task = new DesignTask(initialState_mock, targetState_mock, null);

		Assert.assertFalse(task.isValid());
	}

	@Test
	public void isValid_noMatchingElements() {
		context.checking(new Expectations() {
			{
				oneOf(elements_mock).haveElementMatchingState(initialState_mock);
				will(returnValue(false));
			}
		});

		Assert.assertFalse(task.isValid());
	}

	@Test
	public void isValid() {
		context.checking(new Expectations() {
			{
				oneOf(elements_mock).haveElementMatchingState(initialState_mock);
				will(returnValue(true));
			}
		});

		Assert.assertTrue(task.isValid());
	}

	@Test
	public void getInitialState() {
		Assert.assertEquals(initialState_mock, task.getInitialState());
	}

	@Test
	public void getElements() {
		Assert.assertEquals(elements_mock, task.getElements());
	}

	@Test
	public void getTargetState() {
		Assert.assertEquals(targetState_mock, task.getTargetState());
	}

	@Test
	public void createSubTask() {
		final SystemState state_mock = context.mock(SystemState.class, "state");

		DesignTask subTask = task.createSubTask(state_mock);
		Assert.assertEquals(state_mock, subTask.getInitialState());
		Assert.assertEquals(targetState_mock, subTask.getTargetState());
		Assert.assertEquals(elements_mock, subTask.getElements());
	}
}
