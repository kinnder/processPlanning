package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cppbuilder.utility.RandomGenerator;

public class AttributeCollectionTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	AttributeCollection out1;
	AttributeCollection out2;

	@Before
	public void setUp() {
		out1 = new AttributeCollection();
		out2 = new AttributeCollection();

		prepare_Random(out2);
	}

	public static void prepare_Random(AttributeCollection attributes) {
		int attributesAmount = RandomGenerator.getInteger(1, 3);
		for (int i = 0; i < attributesAmount; i++) {
			attributes.add(AttributeTest.create_Random());
		}
	}

	public static void prepare_Coordinates_3D(AttributeCollection attributes) {
		attributes.clear();
		attributes.add(new Attribute(Attribute.ATTRIBUTE_X));
		attributes.add(new Attribute(Attribute.ATTRIBUTE_Y));
		attributes.add(new Attribute(Attribute.ATTRIBUTE_Z));
		attributes.shuffle();
	}

	@Test
	public void find() {
		prepare_Coordinates_3D(out1);
		Assert.assertEquals(true, out1.find(Attribute.ATTRIBUTE_UNKNOWN) == null);
		Assert.assertEquals(Attribute.ATTRIBUTE_X, out1.find(Attribute.ATTRIBUTE_X).name);
		Assert.assertEquals(Attribute.ATTRIBUTE_Y, out1.find(Attribute.ATTRIBUTE_Y).name);
		Assert.assertEquals(Attribute.ATTRIBUTE_Z, out1.find(Attribute.ATTRIBUTE_Z).name);
	}
}
