package algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class CartesianProductTest {

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

	CartesianProduct testable;

	@BeforeEach
	public void setup() {
		testable = new CartesianProduct();
	}

	@Test
	public void computeCombinations2() {
		List<String> t1 = Arrays.asList("ID-1", "ID-2", "ID-3", "ID-4", "ID-5");
		List<String> t2 = Arrays.asList("ID-2");
		List<String> t3 = Arrays.asList("ID-3", "ID-4", "ID-5");

		List<List<String>> combinations = CartesianProduct.compute(Arrays.asList(t1, t2, t3));
		assertEquals(15, combinations.size());
	}
}
