package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class IdsMatchingManagerTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	IdsMatchingManager testable;

	@BeforeEach
	public void setup() {
		testable = new IdsMatchingManager();
	}

	@Test
	public void getMatchingsAmount() {
		assertEquals(0, testable.getMatchingsAmount());
	}

	@Test
	public void haveUncheckedMatching() {
		Set<String> templateIds = new HashSet<String>();
		templateIds.add("T-1");
		templateIds.add("T-2");
		Set<String> objectIds = new HashSet<String>();
		objectIds.add("ID-1");
		objectIds.add("ID-2");

		testable.prepareMatchingsCandidates(templateIds, objectIds);
		testable.generateMatchingsFromCandidates();

		assertEquals(2, testable.getMatchingsAmount());

		assertTrue(testable.haveUncheckedMatching());
		assertEquals(testable.getMatching(0), testable.getUncheckedMatching());

		assertTrue(testable.haveUncheckedMatching());
		assertEquals(testable.getMatching(1), testable.getUncheckedMatching());

		assertFalse(testable.haveUncheckedMatching());
		assertNull(testable.getUncheckedMatching());
	}

	@Test
	public void generateMatchingsFromCandidates() {
		Set<String> templateIds = new HashSet<String>();
		templateIds.add("T-1");
		templateIds.add("T-2");
		templateIds.add("T-3");
		Set<String> systemIds = new HashSet<String>();
		systemIds.add("ID-1");
		systemIds.add("ID-2");
		systemIds.add("ID-3");
		systemIds.add("ID-4");
		systemIds.add("ID-5");

		testable.prepareMatchingsCandidates(templateIds, systemIds);

		testable.removeMatchingsCandidate("T-2", "ID-1");
		testable.removeMatchingsCandidate("T-2", "ID-3");
		testable.removeMatchingsCandidate("T-2", "ID-4");
		testable.removeMatchingsCandidate("T-2", "ID-5");
		testable.removeMatchingsCandidate("T-3", "ID-1");
		testable.removeMatchingsCandidate("T-3", "ID-2");

		testable.generateMatchingsFromCandidates();
		assertEquals(9, testable.getMatchingsAmount());

		IdsMatching idsMatching = testable.getMatching(8);
		assertEquals("ID-1", idsMatching.get("T-1"));
		assertEquals("ID-2", idsMatching.get("T-2"));
		assertEquals("ID-3", idsMatching.get("T-3"));

		testable.removeMatching(idsMatching);
		assertEquals(8, testable.getMatchingsAmount());
	}

	@Test
	public void getIdsMatchings() {
		assertEquals(0, testable.getIdsMatchings().length);
	}
}
