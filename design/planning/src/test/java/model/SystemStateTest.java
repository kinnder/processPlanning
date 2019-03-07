package model;

import java.util.Iterator;
import java.util.function.Predicate;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import utility.Collection;
import utility.CollectionItem;

public class SystemStateTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	SystemState state_A1;

	SystemState state_AP;

	Collection objects_A1_mock;

	Collection objects_AP_mock;

	Collection links_A1_mock;

	Collection links_AP_mock;

	Collection objectsPossibleToChange_mock;

	Collection objectsNotPossibleToChange_mock;

	Collection objectsIncludedInChangeCombination_mock;

	Collection objectsNotIncludedInChangeCombination_mock;

	Collection[] modifiingObjectsCombinations;

	SystemObject object_A1_mock;

	SystemObject object_A2_mock;

	SystemObject object_AP_mock;

	Iterator<CollectionItem> iterator_mock;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		objects_A1_mock = context.mock(Collection.class, "objects-A1");
		objects_AP_mock = context.mock(Collection.class, "objects-AP");
		links_A1_mock = context.mock(Collection.class, "links-A1");
		links_AP_mock = context.mock(Collection.class, "links-AP");
		objectsPossibleToChange_mock = context.mock(Collection.class, "objects-possible-to-change");
		objectsNotPossibleToChange_mock = context.mock(Collection.class, "objects-not-possible-to-change");
		objectsIncludedInChangeCombination_mock = context.mock(Collection.class,
				"objects-included-in-change-combination");
		objectsNotIncludedInChangeCombination_mock = context.mock(Collection.class,
				"objects-not-included-in-change-combination");
		modifiingObjectsCombinations = new Collection[1];
		modifiingObjectsCombinations[0] = objectsIncludedInChangeCombination_mock;
		object_A1_mock = context.mock(SystemObject.class, "object-A1");
		object_A2_mock = context.mock(SystemObject.class, "object-A2");
		object_AP_mock = context.mock(SystemObject.class, "object-AP");
		iterator_mock = context.mock(Iterator.class);

		state_A1 = new SystemState();
		state_A1.objects = objects_A1_mock;
		state_A1.links = links_A1_mock;

		state_AP = new SystemState();
		state_AP.objects = objects_AP_mock;
		state_AP.links = links_AP_mock;

		context.checking(new Expectations() {
			{
				allowing(objects_A1_mock).clone();
				will(returnValue(objects_A1_mock));

				allowing(objects_A1_mock).compareTo(objects_AP_mock);
				will(returnValue(1));

				allowing(objects_A1_mock).complement(objects_AP_mock);
				will(returnValue(objectsNotPossibleToChange_mock));

				allowing(objects_A1_mock).contains(object_A1_mock);
				will(returnValue(true));

				allowing(objects_A1_mock).contains(object_A2_mock);
				will(returnValue(true));

				allowing(objects_A1_mock).contains(object_AP_mock);
				will(returnValue(false));

				allowing(objects_A1_mock).intersect(objects_AP_mock);
				will(returnValue(objectsPossibleToChange_mock));

				allowing(objects_A1_mock).matches(objects_AP_mock);
				will(returnValue(true));

				allowing(objects_AP_mock).compareTo(objects_A1_mock);
				will(returnValue(-1));

				allowing(objects_AP_mock).contains(object_AP_mock);
				will(returnValue(true));

				allowing(objects_AP_mock).matches(objects_A1_mock);
				will(returnValue(false));

				allowing(objectsPossibleToChange_mock).complement(objectsIncludedInChangeCombination_mock);
				will(returnValue(objectsNotIncludedInChangeCombination_mock));

				allowing(objectsPossibleToChange_mock).subsets(objects_AP_mock);
				will(returnValue(modifiingObjectsCombinations));

				allowing(objectsIncludedInChangeCombination_mock).clone();
				will(returnValue(objectsIncludedInChangeCombination_mock));

				allowing(objectsNotIncludedInChangeCombination_mock).clone();
				will(returnValue(objectsNotIncludedInChangeCombination_mock));

				allowing(objectsNotPossibleToChange_mock).clone();
				will(returnValue(objectsNotPossibleToChange_mock));

				allowing(object_A1_mock).hasAvailableLink(null);
				will(returnValue(true));

				allowing(object_A1_mock).hasAvailableLink("l-group");
				will(returnValue(false));

				allowing(object_A2_mock).hasAvailableLink(null);
				will(returnValue(true));

				allowing(object_AP_mock).hasAvailableLink(null);
				will(returnValue(true));

				allowing(links_A1_mock).clone();
				will(returnValue(links_A1_mock));

				allowing(links_A1_mock).iterator();
				will(returnValue(iterator_mock));

				allowing(links_A1_mock).matches(links_AP_mock);
				will(returnValue(true));

				allowing(iterator_mock).hasNext();
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
		Assert.assertTrue(state_A1.matches(state_A1));
	}

	@Test
	public void matches() {
		Assert.assertTrue(state_A1.matches(state_AP));
	}

	@Test
	public void matches_no_matching_objects() {
		Assert.assertFalse(state_AP.matches(state_A1));
	}

	@Test
	public void matches_links() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(links_A1_mock).add(with(any(SystemLink.class)));

				oneOf(object_A1_mock).registerLink(with(any(SystemLink.class)));

				oneOf(object_A2_mock).registerLink(with(any(SystemLink.class)));

				oneOf(links_AP_mock).add(with(any(SystemLink.class)));

				oneOf(object_AP_mock).registerLink(with(any(SystemLink.class)));

				oneOf(object_AP_mock).registerLink(with(any(SystemLink.class)));
			}
		});

		state_A1.createLink(object_A1_mock, object_A2_mock, null);
		state_AP.createLink(object_AP_mock, object_AP_mock, null);

		Assert.assertTrue(state_A1.matches(state_AP));
	}

	@Test
	public void prepareTransitionalStates() {
		context.checking(new Expectations() {
			{
				oneOf(objectsNotIncludedInChangeCombination_mock).addRange(objectsNotPossibleToChange_mock);
			}
		});

		Collection states = state_A1.prepareTransitionalStates(state_AP);
		Assert.assertEquals(modifiingObjectsCombinations.length, states.size());
	}

	@Test
	@Ignore
	public void prepareTransitionalStates_patternWithLinks() {

//	state  :   {A1, B1, A2, B2; A1-B1, A2-B2}
//	pattern:   {AP, BP; AP-BP}
//	result :   { {A1, B1; A1-B1}, {A2, B2; A2-B2} }, { {A2, B2; A2-B2}, {A1, B1; A1-B1} }

		Assert.fail("WIP");
	}

	@Test
	@Ignore
	public void subset() {
//	state    :   {A1, B1, A2, B2}
//	pattern  :   {AP, BP}
//	results  :   {A1, B1}, {A1, B2}, {A2, B1}, {A2, B2}

		Assert.fail("WIP");
	}

	@Test
	@Ignore
	public void subset_patternWithLinks() {
//	state    :   {A1, B1, A2, B2, A3, B3; A1-B1, A2-B2}
//	pattern  :   {AP, BP, AP-BP}
//	results  :   {A1, B1; A1-B1}, {A2, B2; A2-B2}

		Assert.fail("WIP");
	}

	@Test
	public void compareTo_self() {
		Assert.assertEquals(0, state_A1.compareTo(state_A1));
	}

	@Test
	public void compareTo_follows() {
		Assert.assertEquals(1, state_A1.compareTo(state_AP));
	}

	@Test
	public void compareTo_preceds() {
		Assert.assertEquals(-1, state_AP.compareTo(state_A1));
	}

	@Test
	public void clone_test() {
		state_AP = (SystemState) state_A1.clone();

		Assert.assertNotEquals(state_A1, state_AP);
	}

	@Test
	public void createLink() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(links_A1_mock).add(with(any(SystemLink.class)));

				oneOf(object_A1_mock).registerLink(with(any(SystemLink.class)));

				oneOf(object_A2_mock).registerLink(with(any(SystemLink.class)));
			}
		});

		state_A1.createLink(object_A1_mock, object_A2_mock, null);
	}

	@Test(expected = Exception.class)
	public void createLink_differentSystems() throws Exception {
		state_A1.createLink(object_A1_mock, object_AP_mock, null);
	}

	@Test(expected = Exception.class)
	public void createLink_no_available_links() throws Exception {
		state_A1.createLink(object_A1_mock, object_A2_mock, "l-group");
	}

	@Test
	public void addObject() {
		context.checking(new Expectations() {
			{
				oneOf(objects_A1_mock).add(object_A1_mock);
			}
		});
		state_A1.addObject(object_A1_mock);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getObjectById() {
		context.checking(new Expectations() {
			{
				oneOf(objects_A1_mock).find(with(any(Predicate.class)));
				will(returnValue(object_A1_mock));
			}
		});

		Assert.assertEquals(object_A1_mock, state_A1.getObjectById(object_A1_mock.id));
	}
}
