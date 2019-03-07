package utility;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.SystemAttribute;
import model.SystemObject;

public class SystemObjectBuilderTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	SystemObjectBuilder builder;

	SystemObject systemObject_mock;

	@Before
	public void setUp() {
		systemObject_mock = context.mock(SystemObject.class);

		builder = new SystemObjectBuilder(systemObject_mock);
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void addAttribute() {
		context.checking(new Expectations() {
			{
				oneOf(systemObject_mock).addAttribute(with(any(SystemAttribute.class)));
			}
		});

		Assert.assertEquals(builder, builder.addAttribute("name", "value"));
	}

	@Test
	public void build() {
		Assert.assertEquals(systemObject_mock, builder.build());
	}
}
