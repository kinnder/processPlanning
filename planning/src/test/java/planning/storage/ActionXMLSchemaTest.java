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

	ParameterUpdaterXMLSchema parameterUpdaterXMLSchema_mock;

	PreConditionCheckerXMLSchema preConditionCheckerXMLSchema_mock;

	@BeforeEach
	public void setup() {
		parameterUpdaterXMLSchema_mock = context.mock(ParameterUpdaterXMLSchema.class);
		preConditionCheckerXMLSchema_mock = context.mock(PreConditionCheckerXMLSchema.class);

		testable = new ActionXMLSchema(parameterUpdaterXMLSchema_mock, preConditionCheckerXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new ActionXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> preConditionCheckers = new ArrayList<>();
		final Element preConditionChecker_mock = context.mock(Element.class, "preConditionChecker-element");
		preConditionCheckers.add(preConditionChecker_mock);
		final ActionPreConditionChecker actionPreConditionChecker_mock = context.mock(ActionPreConditionChecker.class);
		final List<Element> parameterUpdaters = new ArrayList<>();
		final Element parameterUpdater_mock = context.mock(Element.class, "parameterUpdater-element");
		parameterUpdaters.add(parameterUpdater_mock);
		final ActionParameterUpdater actionParameterUpdater_mock = context.mock(ActionParameterUpdater.class);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("action-name"));

				oneOf(preConditionCheckerXMLSchema_mock).getSchemaName();
				will(returnValue("preConditionChecker"));

				oneOf(root_mock).getChildren("preConditionChecker");
				will(returnValue(preConditionCheckers));

				oneOf(preConditionCheckerXMLSchema_mock).parse(preConditionChecker_mock);
				will(returnValue(actionPreConditionChecker_mock));

				oneOf(parameterUpdaterXMLSchema_mock).getSchemaName();
				will(returnValue("parameterUpdater"));

				oneOf(root_mock).getChildren("parameterUpdater");
				will(returnValue(parameterUpdaters));

				oneOf(parameterUpdaterXMLSchema_mock).parse(parameterUpdater_mock);
				will(returnValue(actionParameterUpdater_mock));
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
		final Element preConditionChecker = new Element("preConditionChecker");
		final Element parameterUpdater = new Element("parameterUpdater");

		context.checking(new Expectations() {
			{
				oneOf(action_mock).getName();
				will(returnValue("OPERATION"));

				oneOf(action_mock).getPreConditionCheckers();
				will(returnValue(preConditionCheckers));

				oneOf(preConditionCheckerXMLSchema_mock).combine(preConditionChecker_mock);
				will(returnValue(preConditionChecker));

				oneOf(action_mock).getParameterUpdaters();
				will(returnValue(parameterUpdaters));

				oneOf(parameterUpdaterXMLSchema_mock).combine(parameterUpdater_mock);
				will(returnValue(parameterUpdater));
			}
		});

		Element element = testable.combine(action_mock);
		assertEquals("OPERATION", element.getChildText("name"));
		assertNotNull(element.getChild("preConditionChecker"));
		assertNotNull(element.getChild("parameterUpdater"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("action", testable.getSchemaName());
	}
}
