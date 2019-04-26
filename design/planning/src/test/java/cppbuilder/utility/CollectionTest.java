package cppbuilder.utility;

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

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	private class CollectionItem extends LogicOperators<CollectionItem> {
	}

	Collection<CollectionItem> out1;
	Collection<CollectionItem> out2;

	CollectionItem item;

	@Before
	public void setUp() {
		out1 = new Collection<CollectionItem>();
		out2 = new Collection<CollectionItem>();

		item = context.mock(CollectionItem.class);
	}

	@Test
	public void addItem() {
		out1.add(item);
		out1.add(item);
		Assert.assertEquals(2, out1.size());
		Assert.assertEquals(true, item == out1.get(0));
		Assert.assertEquals(true, item == out1.get(1));
	}

	@Test
	public void removeItem() {
		out1.add(item);
		out1.remove(item);
		Assert.assertEquals(0, out1.size());
	}

	@Test
	public void itemExists() {
		Assert.assertEquals(false, out1.exists(item));

		out1.add(item);
		Assert.assertEquals(true, out1.exists(item));
	}

	@Test
	public void frontBackIterators() {
		out1.add(item);
		Assert.assertEquals(true, item == out1.back());
	}

	@Test
	public void clearing() {
		out1.add(item);
		Assert.assertEquals(1, out1.size());

		out1.clear();
		Assert.assertEquals(0, out1.size());
	}

	@Test
	public void toXMLString() {
		Assert.assertEquals(false, out2.toXMLString().isEmpty());
	}
}
