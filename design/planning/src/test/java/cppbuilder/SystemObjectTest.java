package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cppbuilder.utility.RandomGenerator;

public class SystemObjectTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	SystemObject out1;
	SystemObject out2;

	@Before
	public void setUp() {
		out1 = new SystemObject();
		out2 = new SystemObject();

		prepare_Random(out2);
	}

	public static SystemObject create_Random() {
		SystemObject object = new SystemObject();
		prepare_Random(object);
		return object;
	}

	public static void prepare_Random(SystemObject object) {
		object.name = RandomGenerator.getString(10);
		AttributeCollectionTest.prepare_Random(object.attributes);
	}

	public static void prepare_SimpleObjectWith2Attributes(SystemObject p_object, int p_value1, int p_value2) {
		p_object.name = "Простой объект";
		p_object.attributes.clear();
		p_object.attributes.add(new Attribute("Атрибут №1", p_value1));
		p_object.attributes.add(new Attribute("Атрибут №2", p_value2));
		p_object.attributes.shuffle();
	}

	public static void prepare_SimpleObjectWith3Attributes(SystemObject p_object, int p_value1, int p_value2,
			int p_value3) {
		p_object.name = "Простой объект";
		p_object.attributes.clear();
		p_object.attributes.add(new Attribute("Атрибут №1", p_value1));
		p_object.attributes.add(new Attribute("Атрибут №2", p_value2));
		p_object.attributes.add(new Attribute("Атрибут №3", p_value3));
		p_object.attributes.shuffle();
	}

	@Test
	public void equality() {
		Assert.assertEquals(false, out1.operator_equality(out2));

		prepare_SimpleObjectWith2Attributes(out1, 0, 0);
		prepare_SimpleObjectWith2Attributes(out2, 0, 0);
		Assert.assertEquals(true, out1.operator_equality(out2));
		Assert.assertEquals(true, out1.matches(out2));
		Assert.assertEquals(true, out2.matches(out1));
	}

	@Test
	public void matching_OneSide() {
		prepare_SimpleObjectWith2Attributes(out1, 0, 0);
		prepare_SimpleObjectWith3Attributes(out2, 1, 1, 2);
		Assert.assertEquals(false, out1.operator_equality(out2));
		Assert.assertEquals(true, out1.matches(out2));
		Assert.assertEquals(false, out2.matches(out1));
	}

	@Test
	public void matching_BothSide() {
		prepare_SimpleObjectWith2Attributes(out1, 0, 0);
		prepare_SimpleObjectWith2Attributes(out2, 1, 1);
		Assert.assertEquals(false, out1.operator_equality(out2));
		Assert.assertEquals(true, out1.matches(out2));
		Assert.assertEquals(true, out2.matches(out1));
	}

	@Test
	public void toXMLString() {
		Assert.assertEquals(false, out2.toXMLString().isEmpty());
	}

	@Test
	public void uniqueObjectIds() {
		Assert.assertNotEquals(out1.id, out2.id);
	}
}
