package utility;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CollectionTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	Collection collection_1;
	Collection collection_2;

	CollectionItem item_A1_mock;
	CollectionItem item_A2_mock;
	CollectionItem item_A3_mock;
	CollectionItem item_AP_mock;
	CollectionItem item_B1_mock;
	CollectionItem item_B2_mock;
	CollectionItem item_BP_mock;
	CollectionItem item_C1_mock;
	CollectionItem item_CP_mock;

	@Before
	public void setUp() {
		item_A1_mock = context.mock(CollectionItem.class, "item-A1");
		item_A2_mock = context.mock(CollectionItem.class, "item-A2");
		item_A3_mock = context.mock(CollectionItem.class, "item-A3");
		item_AP_mock = context.mock(CollectionItem.class, "item-AP");
		item_B1_mock = context.mock(CollectionItem.class, "item-B1");
		item_B2_mock = context.mock(CollectionItem.class, "item-B2");
		item_BP_mock = context.mock(CollectionItem.class, "item-BP");
		item_C1_mock = context.mock(CollectionItem.class, "item-C1");
		item_CP_mock = context.mock(CollectionItem.class, "item-CP");

		collection_1 = new Collection();
		collection_1.add(item_A1_mock);

		collection_2 = new Collection();
		collection_2.add(item_AP_mock);
		collection_2.add(item_BP_mock);

		context.checking(new Expectations() {
			{
				allowing(item_A1_mock).clone();
				will(returnValue(item_A1_mock));

				allowing(item_A1_mock).compareTo(item_AP_mock);
				will(returnValue(1));

				allowing(item_A1_mock).compareTo(item_A1_mock);
				will(returnValue(0));

				allowing(item_A1_mock).compareTo(item_A2_mock);
				will(returnValue(-1));

				allowing(item_A1_mock).compareTo(item_A3_mock);
				will(returnValue(-1));

				allowing(item_A1_mock).compareTo(item_B1_mock);
				will(returnValue(-1));

				allowing(item_A1_mock).matches(item_AP_mock);
				will(returnValue(true));

				allowing(item_A1_mock).matches(item_BP_mock);
				will(returnValue(false));

				allowing(item_A2_mock).compareTo(item_A1_mock);
				will(returnValue(1));

				allowing(item_A2_mock).compareTo(item_A2_mock);
				will(returnValue(0));

				allowing(item_A2_mock).compareTo(item_A3_mock);
				will(returnValue(-1));

				allowing(item_A2_mock).compareTo(item_B1_mock);
				will(returnValue(-1));

				allowing(item_A2_mock).matches(item_AP_mock);
				will(returnValue(true));

				allowing(item_A2_mock).matches(item_BP_mock);
				will(returnValue(false));

				allowing(item_A3_mock).compareTo(item_A1_mock);
				will(returnValue(1));

				allowing(item_A3_mock).compareTo(item_A2_mock);
				will(returnValue(1));

				allowing(item_A3_mock).compareTo(item_A3_mock);
				will(returnValue(0));

				allowing(item_A3_mock).compareTo(item_B1_mock);
				will(returnValue(-1));

				allowing(item_A3_mock).matches(item_AP_mock);
				will(returnValue(true));

				allowing(item_A3_mock).matches(item_BP_mock);
				will(returnValue(false));

				allowing(item_AP_mock).clone();
				will(returnValue(item_AP_mock));

				allowing(item_AP_mock).compareTo(item_A1_mock);
				will(returnValue(-1));

				allowing(item_B1_mock).compareTo(item_A1_mock);
				will(returnValue(1));

				allowing(item_B1_mock).compareTo(item_A2_mock);
				will(returnValue(1));

				allowing(item_B1_mock).compareTo(item_A3_mock);
				will(returnValue(1));

				allowing(item_B1_mock).compareTo(item_B1_mock);
				will(returnValue(0));

				allowing(item_B1_mock).matches(item_AP_mock);
				will(returnValue(false));

				allowing(item_B1_mock).matches(item_BP_mock);
				will(returnValue(true));

				allowing(item_B1_mock).matches(item_CP_mock);
				will(returnValue(false));

				allowing(item_B2_mock).compareTo(item_A1_mock);
				will(returnValue(1));

				allowing(item_B2_mock).compareTo(item_A2_mock);
				will(returnValue(1));

				allowing(item_B2_mock).compareTo(item_B2_mock);
				will(returnValue(0));

				allowing(item_B2_mock).matches(item_AP_mock);
				will(returnValue(false));

				allowing(item_B2_mock).matches(item_BP_mock);
				will(returnValue(true));

				allowing(item_BP_mock).clone();
				will(returnValue(item_BP_mock));

				allowing(item_BP_mock).compareTo(item_AP_mock);
				will(returnValue(1));

				allowing(item_C1_mock).matches(item_AP_mock);
				will(returnValue(false));

				allowing(item_C1_mock).matches(item_BP_mock);
				will(returnValue(false));

				allowing(item_CP_mock).clone();
				will(returnValue(item_CP_mock));
			}
		});
	}

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	@Test
	public void matches() {
		collection_1.add(item_B1_mock);

		Assert.assertTrue(collection_1.matches(collection_2));
	}

	@Test
	public void matches_self() {
		Assert.assertTrue(collection_1.matches(collection_1));
	}

	@Test
	public void matches_pattern_doesnot_match() {
		collection_1.add(item_B1_mock);
		collection_1.add(item_B1_mock);
		collection_2.add(item_CP_mock);

		Assert.assertFalse(collection_1.matches(collection_2));
	}

	@Test
	public void matches_pattern_has_more_items() {
		Assert.assertFalse(collection_1.matches(collection_2));
	}

	@Test
	public void clone_test() {
		collection_2 = (Collection) collection_1.clone();

		Assert.assertNotEquals(collection_1, collection_2);
	}

	@Test
	public void compareTo() {
		Assert.assertEquals(0, collection_1.compareTo((CollectionItem) collection_1.clone()));
	}

	@Test
	public void compareTo_self() {
		Assert.assertEquals(0, collection_1.compareTo(collection_1));
	}

	@Test
	public void compareTo_follows() {
		Assert.assertEquals(1, collection_1.compareTo(collection_2));
	}

	@Test
	public void compareTo_preceds() {
		Assert.assertEquals(-1, collection_2.compareTo(collection_1));
	}

	@Test
	public void intersect() {
		collection_1.add(item_B1_mock);
		collection_1.add(item_C1_mock);

		Collection intersection = collection_1.intersect(collection_2);
		Assert.assertEquals(2, intersection.size());
		Assert.assertTrue(intersection.contains(item_A1_mock));
		Assert.assertTrue(intersection.contains(item_B1_mock));
	}

	@Test
	public void complement() {
		collection_1.add(item_B1_mock);
		collection_1.add(item_C1_mock);

		Collection complemention = collection_1.complement(collection_2);
		Assert.assertEquals(1, complemention.size());
		Assert.assertTrue(complemention.contains(item_C1_mock));
	}

	@Test
	public void subsets_case_1() {
		// set : {A1, B1, A2, B2}
		// pattern : {AP, BP}
		// expected : {A1, B1}, {A1, B2}, {A2, B1}, {A2, B2}

		collection_1.add(item_B1_mock);
		collection_1.add(item_A2_mock);
		collection_1.add(item_B2_mock);

		Collection[] expected = new Collection[4];
		expected[0] = new Collection();
		expected[0].add(item_A1_mock);
		expected[0].add(item_B1_mock);
		expected[1] = new Collection();
		expected[1].add(item_A1_mock);
		expected[1].add(item_B2_mock);
		expected[2] = new Collection();
		expected[2].add(item_A2_mock);
		expected[2].add(item_B1_mock);
		expected[3] = new Collection();
		expected[3].add(item_A2_mock);
		expected[3].add(item_B2_mock);

		Collection[] subsets = collection_1.subsets(collection_2);
		Assert.assertEquals(expected.length, subsets.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertEquals(0, expected[i].compareTo(subsets[i]));
		}
	}

	@Test
	public void subsets_case_2() {
		// set : {A1, A2, B1, A3}
		// pattern : {AP, BP, AP}
		// expected : {A1, B1, A2}, {A1, B1, A3}, {A2, B1, A1}, {A2, B1, A3},
		// {A3, B1, A1}, {A3, B1, A2}

		collection_1.add(item_A2_mock);
		collection_1.add(item_B1_mock);
		collection_1.add(item_A3_mock);
		collection_2.add(item_AP_mock);

		Collection[] expected = new Collection[6];
		expected[0] = new Collection();
		expected[0].add(item_A1_mock);
		expected[0].add(item_B1_mock);
		expected[0].add(item_A2_mock);
		expected[1] = new Collection();
		expected[1].add(item_A1_mock);
		expected[1].add(item_B1_mock);
		expected[1].add(item_A3_mock);
		expected[2] = new Collection();
		expected[2].add(item_A2_mock);
		expected[2].add(item_B1_mock);
		expected[2].add(item_A1_mock);
		expected[3] = new Collection();
		expected[3].add(item_A2_mock);
		expected[3].add(item_B1_mock);
		expected[3].add(item_A3_mock);
		expected[4] = new Collection();
		expected[4].add(item_A3_mock);
		expected[4].add(item_B1_mock);
		expected[4].add(item_A1_mock);
		expected[5] = new Collection();
		expected[5].add(item_A3_mock);
		expected[5].add(item_B1_mock);
		expected[5].add(item_A2_mock);

		Collection[] subsets = collection_1.subsets(collection_2);
		Assert.assertEquals(expected.length, subsets.length);
		for (int i = 0; i < expected.length; i++) {
			Assert.assertEquals(0, expected[i].compareTo(subsets[i]));
		}
	}

	@Test
	public void find() {
		Assert.assertEquals(item_A1_mock, collection_1.find((x) -> x.equals(item_A1_mock)));
	}
}
