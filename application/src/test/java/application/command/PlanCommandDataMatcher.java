package application.command;

import testtools.Matcher;

public class PlanCommandDataMatcher extends Matcher<PlanCommandData> {

	public PlanCommandDataMatcher expectProcessFile(String processFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(PlanCommandData arg) {
				compare("processFile", processFile, arg.processFile);
			}
		});
		return this;
	}

	public PlanCommandDataMatcher expectTaskDescriptionFile(String taskDescriptionFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(PlanCommandData arg) {
				compare("taskDescriptionFile", taskDescriptionFile, arg.taskDescriptionFile);
			}
		});
		return this;
	}

	public PlanCommandDataMatcher expectSystemTransformationsFile(String systemTransformationsFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(PlanCommandData arg) {
				compare("systemTransformationsFile", systemTransformationsFile, arg.systemTransformationsFile);
			}
		});
		return this;
	}

	public PlanCommandDataMatcher expectNodeNetworkFile(String nodeNetworkFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(PlanCommandData arg) {
				compare("nodeNetworkFile", nodeNetworkFile, arg.nodeNetworkFile);
			}
		});
		return this;
	}
}
