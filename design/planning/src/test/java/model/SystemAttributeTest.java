package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SystemAttributeTest {

	SystemAttribute attribute1;

	SystemAttribute attribute2;

	SystemAttribute attributeP;

	@Before
	public void setUp() {
		attribute1 = new SystemAttribute("name", "attribute1");
		attribute2 = new SystemAttribute("name", "attribute2");
		attributeP = new SystemAttribute("name", "");
	}

	@Test
	public void compareTo_follows() {
		Assert.assertEquals(1, attribute2.compareTo(attribute1));
		Assert.assertEquals(10, attribute1.compareTo(attributeP));
	}

	@Test
	public void compareTo_preceds() {
		Assert.assertEquals(-1, attribute1.compareTo(attribute2));
	}

	@Test
	public void compareTo() {
		Assert.assertEquals(0, attribute1.compareTo((SystemAttribute) attribute1.clone()));
	}

	@Test
	public void compareTo_self() {
		Assert.assertEquals(0, attribute1.compareTo(attribute1));
	}

	@Test
	public void clone_test() {
		attribute2 = (SystemAttribute) attribute1.clone();
		Assert.assertNotEquals(attribute1, attribute2);
	}

	@Test
	public void matches_self() {
		Assert.assertTrue(attribute1.matches(attribute1));
	}

	@Test
	public void matches() {
		// TODO: неправильное поведение, если все поля заполнены matches должно вести
		// себя как equals
		Assert.assertTrue(attribute1.matches(attribute2));
	}

	@Test
	public void modify_float() {
		attribute1.setFloat(1.0f);
		attribute2.setFloat(2.0f);

		attribute1.modify(attribute2);
		Assert.assertEquals(3.0f, attribute1.getFloat(), 0.0);
	}

	@Test
	public void getFloat() {
		float value = 104.f;
		attribute1.setFloat(value);
		Assert.assertEquals(value, attribute1.getFloat(), 0.0);
	}

	@Test
	public void getInt() {
		int value = 10;
		attribute1.setInteger(value);
		Assert.assertEquals(value, attribute1.getInteger());
	}
}
