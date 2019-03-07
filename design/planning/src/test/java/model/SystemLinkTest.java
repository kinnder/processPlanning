package model;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SystemLinkTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	SystemLink link_1;

	SystemLink link_2;

	SystemLink link_P;

	SystemObject object_1_mock;

	SystemObject object_2_mock;

	@Before
	public void setUp() {
		object_1_mock = context.mock(SystemObject.class, "object-1");
		object_2_mock = context.mock(SystemObject.class, "object-2");

		link_1 = new SystemLink(object_1_mock, object_2_mock, "link1");
		link_2 = new SystemLink(object_1_mock, object_2_mock, "link2");
		link_P = new SystemLink(object_1_mock, object_2_mock, "link1");
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void getLinkInformation() {
		SystemLinkInformation linkInformation = link_1.getLinkInformation();
		Assert.assertEquals(object_1_mock.id, linkInformation.object_1_id);
		Assert.assertEquals(object_2_mock.id, linkInformation.object_2_id);
		Assert.assertEquals(link_1.linkName, linkInformation.name);
	}

	@Test
	public void clone_test() {
		link_2 = (SystemLink) link_1.clone();
		Assert.assertNotEquals(link_1, link_2);
	}

	@Test
	public void matches_self() {
		Assert.assertTrue(link_1.matches(link_1));
	}

	@Test
	public void matches() {
		context.checking(new Expectations() {
			{
				oneOf(object_1_mock).matches(object_1_mock);
				will(returnValue(true));

				oneOf(object_2_mock).matches(object_2_mock);
				will(returnValue(true));
			}
		});

		Assert.assertTrue(link_1.matches(link_P));
	}

	@Test
	public void matches_different_link_names() {
		context.checking(new Expectations() {
			{
				oneOf(object_1_mock).matches(object_1_mock);
				will(returnValue(true));

				oneOf(object_2_mock).matches(object_2_mock);
				will(returnValue(true));
			}
		});

		Assert.assertFalse(link_1.matches(link_2));
	}
}
