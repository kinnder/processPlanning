package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinkTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	Link out1;
	Link out2;

	@Before
	public void setUp() {
		out1 = new Link();
		out2 = new Link();

		prepare_Random(out2);
	}

	public static void prepare_Random(Link link) {
		if (link != null) {
			TransitionTest.prepare_Random(link.transition);
		}
	}

	@Test
	public void equality() {
		Assert.assertEquals(false, out2.operator_equality(out1));

		Assert.assertEquals(true, out2.operator_equality(out2));
	}

	@Test
	public void toXMLString() {
		Assert.assertEquals(false, out2.toXMLString().isEmpty());
	}
}
