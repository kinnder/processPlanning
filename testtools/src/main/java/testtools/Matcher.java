package testtools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class Matcher<T> extends TypeSafeMatcher<T> {

	private List<MatcherExpectation> expectations = new ArrayList<MatcherExpectation>();

	protected abstract class MatcherExpectation {
		public abstract void trigger(T arg);

		protected void compare(String fieldName, long valueExpected, long valueActual) {
			if (valueExpected != valueActual) {
				mismatch = true;
				mismatchDescription = String.format("field [%s] - expected: [%d], actual: [%d]", fieldName,
						valueExpected, valueActual);
			}
		}

		protected void compare(String fieldName, String valueExpected, String valueActual) {
			if (valueExpected.compareTo(valueActual) != 0) {
				mismatch = true;
				mismatchDescription = String.format("field [%s] - expected: [%s], actual: [%s]", fieldName,
						valueExpected, valueActual);
			}
		}

		protected void compare(String fieldName, int valueExpected, int valueActual) {
			if (valueExpected != valueActual) {
				mismatch = true;
				mismatchDescription = String.format("field [%s] - expected: [%d], actual: [%d]", fieldName,
						valueExpected, valueActual);
			}
		}

		protected void compareTimestamps(String fieldName, long valueExpected, long valueActual) {
			if (Math.abs(valueExpected - valueActual) > TOLERANCE) {
				mismatch = true;
				mismatchDescription = String.format(
						"field [%s] - expected: [%d], actual: [%d], difference grater than [%d]", fieldName,
						valueExpected, valueActual, TOLERANCE);
			}
		}

		protected void compare(String fieldName, boolean valueExpected, boolean valueActual) {
			if (valueExpected != valueActual) {
				mismatch = true;
				mismatchDescription = String.format("field [%s] - expected: [%b], actual: [%b]", fieldName,
						valueExpected, valueActual);
			}
		}

		protected void compare(String fieldName, Object valueExpected, Object valueActual) {
			if (!Objects.equals(valueExpected, valueActual)) {
				mismatch = true;
				mismatchDescription = String.format("field [%s] - expected: [%s], actual: [%s]", fieldName,
						valueExpected, valueActual);
			}
		}

		final static private int TOLERANCE = 1000;
	}

	private String mismatchDescription;

	private boolean mismatch;

	@Override
	public void describeTo(Description description) {
		if (mismatchDescription != null) {
			description.appendText(mismatchDescription);
		}
	}

	@Override
	protected boolean matchesSafely(T item) {
		mismatchDescription = null;
		mismatch = false;
		for (MatcherExpectation expectation : expectations) {
			expectation.trigger(item);
			if (mismatch) {
				return false;
			}
		}
		return true;
	}

	public void addExpectation(MatcherExpectation expectation) {
		expectations.add(expectation);
	}
}
