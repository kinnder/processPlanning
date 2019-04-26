package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cppbuilder.utility.RandomGenerator;

public class AttributeTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	Attribute out1;
	Attribute out2;

	@Before
	public void setUp() {
		out1 = new Attribute();
		out2 = new Attribute();

		prepare_Random(out2);
	}

	public static void prepare_Random(Attribute attribute) {
		attribute.name = RandomGenerator.getString(10);
		attribute.value = String.valueOf(RandomGenerator.getInteger(-10, 10));
	}

	public static Attribute create_Random() {
		Attribute attribute = new Attribute();
		prepare_Random(attribute);
		return attribute;
	}

	@Test
	public void equality() {
		Assert.assertEquals(false, out2.operator_equality(out1));
		Assert.assertEquals(true, out2.operator_equality(out2));

		out1 = out2;
		Assert.assertEquals(true, out2.operator_equality(out1));
		Assert.assertEquals(true, out1.matches(out2));
		Assert.assertEquals(true, out2.matches(out1));
	}

	@Test
	public void matching_both_sides() {
		out1.name = out2.name;
		out1.value = String.valueOf(Integer.parseInt(out2.value) + 1);
		Assert.assertEquals(false, out1.operator_equality(out2));
		Assert.assertEquals(true, out1.matches(out2));
		Assert.assertEquals(true, out2.matches(out1));
	}

	@Test
	public void includes() {
		out1.name = Attribute.ATTRIBUTE_X;
		out2.name = Attribute.ATTRIBUTE_X;
		out1.value = "9";
		out2.minValue = "9";
		out2.maxValue = "11";
		Assert.assertEquals(true, out2.haveEqualValues(out1));
	}

	@Test
	public void toXMLString() {
		Assert.assertEquals(false, out2.toXMLString().isEmpty());
	}
}
