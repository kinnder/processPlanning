package model;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import utility.Collection;

public class SystemTransitionTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	SystemTransition transition;

	SystemModification operation1_mock;

	SystemModification operation2_mock;

	SystemObject operation1_target_mock;

	SystemObject operation2_target_mock;

	Collection operation1_modification_mock;

	Collection operation2_modification_mock;

	@Before
	public void setUp() {
		operation1_mock = context.mock(SystemModification.class, "operation1");
		operation2_mock = context.mock(SystemModification.class, "operation2");
		operation1_target_mock = context.mock(SystemObject.class, "operation1_target");
		operation2_target_mock = context.mock(SystemObject.class, "operation2_target");
		operation1_modification_mock = context.mock(Collection.class, "operation1_modification");
		operation2_modification_mock = context.mock(Collection.class, "operation2_modification");

		operation1_mock.target = operation1_target_mock;
		operation1_mock.modifications = operation1_modification_mock;

		operation2_mock.target = operation2_target_mock;
		operation2_mock.modifications = operation2_modification_mock;

		transition = new SystemTransition();
		transition.modifications.add(operation1_mock);
		transition.modifications.add(operation2_mock);
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void getModifiedObjects() {
		final SystemObject modifiingObject1_mock = context.mock(SystemObject.class, "modifiingObject1");
		final SystemObject modifiingObject2_mock = context.mock(SystemObject.class, "modifiingObject2");
		final Collection modifiingObjects = new Collection();
		modifiingObjects.add(modifiingObject1_mock);
		modifiingObjects.add(modifiingObject2_mock);
		final SystemObject modifiedObject1_mock = context.mock(SystemObject.class, "modifiiedObject1");
		final SystemObject modifiedObject2_mock = context.mock(SystemObject.class, "modifiiedObject2");

		context.checking(new Expectations() {
			{
				oneOf(modifiingObject1_mock).clone();
				will(returnValue(modifiingObject1_mock));

				oneOf(modifiingObject2_mock).clone();
				will(returnValue(modifiingObject2_mock));

				oneOf(modifiingObject1_mock).matches(operation1_target_mock);
				will(returnValue(true));

				oneOf(modifiingObject1_mock).getModifiedObject(operation1_modification_mock);
				will(returnValue(modifiedObject1_mock));

				oneOf(modifiingObject2_mock).matches(operation2_target_mock);
				will(returnValue(true));

				oneOf(modifiingObject2_mock).getModifiedObject(operation2_modification_mock);
				will(returnValue(modifiedObject2_mock));
			}
		});

		Collection modifiedObjects = transition.getModifiedObjects(modifiingObjects);
		Assert.assertEquals(2, modifiedObjects.size());
		Assert.assertEquals(modifiedObject1_mock, modifiedObjects.get(0));
		Assert.assertEquals(modifiedObject2_mock, modifiedObjects.get(1));
	}
}
