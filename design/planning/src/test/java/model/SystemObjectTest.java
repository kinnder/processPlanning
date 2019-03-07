package model;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import utility.Collection;

public class SystemObjectTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	SystemObject object_1;

	SystemObject object_2;

	Collection object_1_attributes_mock;

	Collection object_2_attributes_mock;

	SystemAttribute attribute_1_mock;

	SystemAttribute attribute_2_mock;

	Collection attributes = new Collection();

	@Before
	public void setUp() {
		object_1_attributes_mock = context.mock(Collection.class, "object-1-attributes");
		object_2_attributes_mock = context.mock(Collection.class, "object-2-attributes");

		object_1 = new SystemObject();
		object_1.attributes = object_1_attributes_mock;

		object_2 = new SystemObject();
		object_2.attributes = object_2_attributes_mock;

		attribute_1_mock = context.mock(SystemAttribute.class, "attribute-1");
		attribute_1_mock.name = "attribute-1";
		attribute_1_mock.value = "1";
		attribute_2_mock = context.mock(SystemAttribute.class, "attribute-2");
		attribute_2_mock.name = "attribute-2";
		attribute_2_mock.value = "0";
		attributes = new Collection();
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				allowing(attribute_1_mock).getInteger();
				will(returnValue(1));

				allowing(attribute_2_mock).getInteger();
				will(returnValue(0));

				allowing(object_1_attributes_mock).clone();
				will(returnValue(object_1_attributes_mock));

				allowing(object_1_attributes_mock).compareTo(object_2_attributes_mock);
				will(returnValue(1));

				allowing(object_1_attributes_mock).matches(object_2_attributes_mock);
				will(returnValue(true));

				allowing(object_2_attributes_mock).compareTo(object_1_attributes_mock);
				will(returnValue(-1));

				allowing(object_2_attributes_mock).matches(object_1_attributes_mock);
				will(returnValue(false));
			}
		});
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void matches_self() {
		Assert.assertTrue(object_1.matches(object_1));
	}

	@Test
	public void matches() {
		Assert.assertTrue(object_1.matches(object_2));
	}

	@Test
	public void matches_no_matching_attributes() {
		Assert.assertFalse(object_2.matches(object_1));
	}

	@Test
	public void clone_test() {
		object_2 = (SystemObject) object_1.clone();

		Assert.assertNotEquals(object_1, object_2);
	}

	@Test
	public void compareTo_follows() {
		Assert.assertEquals(1, object_1.compareTo(object_2));
	}

	@Test
	public void compareTo_preceds() {
		Assert.assertEquals(-1, object_2.compareTo(object_1));
	}

	@Test
	public void compareTo_self() {
		Assert.assertEquals(0, object_1.compareTo(object_1));
	}

	@Test
	public void modify() {
		final SystemAttribute modification_1_mock = context.mock(SystemAttribute.class, "modification-1");
		final SystemAttribute modification_2_mock = context.mock(SystemAttribute.class, "modification-2");
		final Collection modifications = new Collection();
		modifications.add(modification_1_mock);
		modifications.add(modification_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).matches(modification_1_mock);
				will(returnValue(true));

				oneOf(attribute_1_mock).modify(modification_1_mock);

				oneOf(attribute_2_mock).matches(modification_1_mock);
				will(returnValue(false));

				oneOf(attribute_1_mock).matches(modification_2_mock);
				will(returnValue(false));

				oneOf(attribute_2_mock).matches(modification_2_mock);
				will(returnValue(true));

				oneOf(attribute_2_mock).modify(modification_2_mock);
			}
		});

		object_1.attributes = attributes;

		object_1.modify(modifications);
	}

	@Test
	public void getAttributeByName() {
		object_1.attributes = attributes;

		Assert.assertEquals(attribute_1_mock, object_1.getAttributeByName("attribute-1"));
		Assert.assertEquals(attribute_2_mock, object_1.getAttributeByName("attribute-2"));
	}

	@Test
	public void getAttributeByName_no_attribute() {
		object_1.attributes = attributes;

		Assert.assertNull(object_1.getAttributeByName("wrong name"));
	}

	@Test
	public void hasFreeLink() {
		object_1.attributes = attributes;

		Assert.assertTrue(object_1.hasAvailableLink("attribute-1"));
	}

	@Test
	public void hasFreeLink_no_free_links() {
		object_1.attributes = attributes;

		Assert.assertFalse(object_1.hasAvailableLink("attribute-2"));
	}

	@Test
	public void hasFreeLink_null() {
		object_1.attributes = attributes;

		Assert.assertTrue(object_1.hasAvailableLink(null));
	}

	@Test
	public void addAttribute() {
		context.checking(new Expectations() {
			{
				oneOf(object_1_attributes_mock).add(attribute_1_mock);
			}
		});

		object_1.addAttribute(attribute_1_mock);
	}

	@Test
	public void registerLink() {
		final SystemLink systemLink_mock = context.mock(SystemLink.class);
		systemLink_mock.linkName = "attribute-1";
		final SystemLinkInformation systemLinkInformation_mock = context.mock(SystemLinkInformation.class);

		context.checking(new Expectations() {
			{
				oneOf(systemLink_mock).getLinkInformation();
				will(returnValue(systemLinkInformation_mock));

				oneOf(attribute_1_mock).setInteger(0);
			}
		});

		object_1.attributes = attributes;

		object_1.registerLink(systemLink_mock);
		Assert.assertEquals(1, object_1.linkInformations.size());
	}
}
