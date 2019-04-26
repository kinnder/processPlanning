package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NodeTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	Node out1;
	Node out2;

	@Before
	public void setUp() {
		out1 = new Node();
		out2 = new Node(new Link());

		prepare_Random(out2);
	}

	public static Node create_Random() {
		Node node = new Node();
		prepare_Random(node);
		return node;
	}

	public static void prepare_Random(Node node) {
		LinkTest.prepare_Random(node.link);
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
