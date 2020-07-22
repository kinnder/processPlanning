package application.command;

import utility.Matcher;

public class NewSystemTransformationsCommandDataMatcher extends Matcher<NewSystemTransformationsCommandData> {

	public NewSystemTransformationsCommandDataMatcher expectSystemTransformationsFile(
			String systemTransformationsFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(NewSystemTransformationsCommandData arg) throws MatcherException {
				compare("systemTransformationsFile", systemTransformationsFile, arg.systemTransformationsFile);
			}
		});
		return this;
	}
}
