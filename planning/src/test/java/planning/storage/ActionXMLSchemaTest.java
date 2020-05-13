package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.Action;
import planning.model.ActionParameterUpdater;
import planning.model.ActionPreConditionChecker;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.LuaScriptActionPreConditionChecker;

public class ActionXMLSchemaTest {

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

	ActionXMLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new ActionXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> preConditionCheckers = new ArrayList<>();
		final Element preConditionChecker_mock = context.mock(Element.class, "preConditionChecker");
		preConditionCheckers.add(preConditionChecker_mock);
		final List<Element> parameterUpdaters = new ArrayList<>();
		final Element parameterUpdater_mock = context.mock(Element.class, "parameterUpdater");
		parameterUpdaters.add(parameterUpdater_mock);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("action-name"));

				oneOf(root_mock).getChildren("preConditionChecker");
				will(returnValue(preConditionCheckers));

				// <-- parsePreConditionChecker

				oneOf(preConditionChecker_mock).getChildren("line");

				// parsePreConditionChecker -->

				oneOf(root_mock).getChildren("parameterUpdater");
				will(returnValue(parameterUpdaters));

				// <-- parseParameterUpdater

				oneOf(parameterUpdater_mock).getChildren("line");

				// parseParameterUpdater -->
			}
		});

		assertTrue(testable.parse(root_mock) instanceof Action);
	}

	@Test
	public void combine() {
		final Action action_mock = context.mock(Action.class);
		final LuaScriptActionPreConditionChecker preConditionChecker_mock = context
				.mock(LuaScriptActionPreConditionChecker.class);
		final List<ActionPreConditionChecker> preConditionCheckers = new ArrayList<>();
		preConditionCheckers.add(preConditionChecker_mock);
		final LuaScriptActionParameterUpdater parameterUpdater_mock = context
				.mock(LuaScriptActionParameterUpdater.class);
		final List<ActionParameterUpdater> parameterUpdaters = new ArrayList<>();
		parameterUpdaters.add(parameterUpdater_mock);

		context.checking(new Expectations() {
			{
				oneOf(action_mock).getName();
				will(returnValue("OPERATION"));

				oneOf(action_mock).getPreConditionCheckers();
				will(returnValue(preConditionCheckers));

				// <-- combinePreConditionCheckers

				oneOf(preConditionChecker_mock).getScript();

				// combinePreConditionCheckers -->

				oneOf(action_mock).getParameterUpdaters();
				will(returnValue(parameterUpdaters));

				// <-- combineParameterUpdaters

				oneOf(parameterUpdater_mock).getScript();

				// combineParameterUpdaters -->
			}
		});

		Element element = testable.combine(action_mock);
		assertEquals("OPERATION", element.getChildText("name"));
		assertNotNull(element.getChild("preConditionChecker"));
		assertNotNull(element.getChild("parameterUpdater"));
	}
}
