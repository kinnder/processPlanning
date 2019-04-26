package cppbuilder.utility;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XMLTagBuilderTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	XMLTagBuilder out;

	String name;
	String value;

	@Before
	public void setUp() {
		name = "name";
		value = "value";

		out = new XMLTagBuilder(name);
	}

	@Test
	public void buildTags() {
		Assert.assertEquals("<" + name + ">", out.startTag());
		Assert.assertEquals("</" + name + ">", out.endTag());
	}

	@Test
	public void print() {
		Assert.assertEquals(out.startTag() + value + out.endTag(), out.print(value));
	}
}
