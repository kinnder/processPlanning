package application.command;

import testtools.Matcher;

public class ConvertCommandDataMatcher extends Matcher<ConvertCommandData> {

	public ConvertCommandDataMatcher expectProcessFile(String processFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(ConvertCommandData arg) {
				compare("processFile", processFile, arg.processFile);
			}
		});
		return this;
	}

	public ConvertCommandDataMatcher expectTaskDescriptionFile(String taskDescriptionFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(ConvertCommandData arg) {
				compare("taskDescriptionFile", taskDescriptionFile, arg.taskDescriptionFile);
			}
		});
		return this;
	}

	public ConvertCommandDataMatcher expectSystemTransformationsFile(String systemTransformationsFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(ConvertCommandData arg) {
				compare("systemTransformationsFile", systemTransformationsFile, arg.systemTransformationsFile);
			}
		});
		return this;
	}

	public ConvertCommandDataMatcher expectNodeNetworkFile(String nodeNetworkFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(ConvertCommandData arg) {
				compare("nodeNetworkFile", nodeNetworkFile, arg.nodeNetworkFile);
			}
		});
		return this;
	}
}
