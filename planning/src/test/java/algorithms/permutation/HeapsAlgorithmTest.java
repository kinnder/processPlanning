package algorithms.permutation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class HeapsAlgorithmTest {

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

	HeapsAlgorithm testable;

	@BeforeEach
	public void setup() {
		testable = new HeapsAlgorithm();
	}

	@Test
	public void getPermutations() {
		String[] ids = { "t-1", "t-2", "t-3", null };

		List<Object[]> permutations = testable.getPermutations(ids, ids.length);
		assertEquals(24, permutations.size());
	}
}
