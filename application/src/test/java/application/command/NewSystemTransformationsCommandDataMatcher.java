package application.command;

import utility.Matcher;

public class NewSystemTransformationsCommandDataMatcher extends Matcher<NewSystemTransformationsCommandData> {

	// TODO (2020-07-24 #29): имеется аналогичный метод в классе PlanCommandDataMatcher
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

	public NewSystemTransformationsCommandDataMatcher expectDomain(String domain) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(NewSystemTransformationsCommandData arg) throws MatcherException {
				compare("domain", domain, arg.domain);
			}
		});
		return this;
	}
}
