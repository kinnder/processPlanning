package application.command;

import utility.Matcher;

public class PlanCommandDataMatcher extends Matcher<PlanCommandData> {

	public PlanCommandDataMatcher expectProcessFile(String processFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(PlanCommandData arg) throws MatcherException {
				compare("processFile", processFile, arg.processFile);
			}
		});
		return this;
	}

	public PlanCommandDataMatcher expectTaskDescriptionFile(String taskDescriptionFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(PlanCommandData arg) throws MatcherException {
				compare("taskDescriptionFile", taskDescriptionFile, arg.taskDescriptionFile);
			}
		});
		return this;
	}

	public PlanCommandDataMatcher expectSystemTransformationsFile(String systemTransformationsFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(PlanCommandData arg) throws MatcherException {
				compare("systemTransformationsFile", systemTransformationsFile, arg.systemTransformationsFile);
			}
		});
		return this;
	}
}
