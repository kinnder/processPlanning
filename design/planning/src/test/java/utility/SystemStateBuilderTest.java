package utility;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.SystemObject;
import model.SystemState;

public class SystemStateBuilderTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	SystemStateBuilder builder;

	SystemState systemState_mock;

	SystemObject systemObject_1_mock;

	SystemObject systemObject_2_mock;

	String linkName = "link";

	@Before
	public void setUp() {
		systemState_mock = context.mock(SystemState.class);
		systemObject_1_mock = context.mock(SystemObject.class, "object-1");
		systemObject_2_mock = context.mock(SystemObject.class, "object-2");

		builder = new SystemStateBuilder(systemState_mock);
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void build() {
		Assert.assertEquals(systemState_mock, builder.build());
	}

	@Test
	public void addObject() {
		context.checking(new Expectations() {
			{
				oneOf(systemState_mock).addObject(systemObject_1_mock);
			}
		});

		Assert.assertEquals(builder, builder.addObject(systemObject_1_mock));
	}

	@Test
	public void linkObjects() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(systemState_mock).createLink(systemObject_1_mock, systemObject_2_mock, linkName);
			}
		});
		Assert.assertEquals(builder, builder.linkObjects(systemObject_1_mock, systemObject_2_mock, linkName));
	}
}
