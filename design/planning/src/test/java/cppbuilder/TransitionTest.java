package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cppbuilder.utility.RandomGenerator;

public class TransitionTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	Transition out1;
	Transition out2;

	@Before
	public void setUp() {
		out1 = new Transition();
		out2 = new Transition();

		prepare_Random(out2);
	}

	public static void prepare_Random(Transition transition) {
		transition.name = RandomGenerator.getString(10);

		int value = RandomGenerator.getIntegerFrom(new int[] { TransitionType.Unknown.ordinal(),
				TransitionType.DeltaAttributes.ordinal(), TransitionType.ChangeAttributes.ordinal() });
		transition.type = TransitionType.valueOf(value);
	}

	@Test
	public void equality() {
		Assert.assertEquals(false, out2.operator_equality(out1));

		Assert.assertEquals(true, out2.operator_equality(out2));
	}

	@Test
	public void toXMLString() {
		Assert.assertEquals(false, out2.toXMLString().isEmpty());
	}
}
