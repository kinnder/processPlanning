package application.storage.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import planning.model.ActionFunction;

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

	ActionFunctionXMLSchema actionFunctionXMLSchema_mock;

	@BeforeEach
	public void setup() {
		actionFunctionXMLSchema_mock = context.mock(ActionFunctionXMLSchema.class);

		testable = new ActionXMLSchema(actionFunctionXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new ActionXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Element e_preConditionChecker_mock = context.mock(Element.class, "e-preConditionChecker");
		final Element e_parameterUpdater_mock = context.mock(Element.class, "e-parameterUpdater");
		final Element e_unknownFunction_mock = context.mock(Element.class, "e-unknownFunction");
		final List<Element> e_actionFunctions = new ArrayList<>();
		e_actionFunctions.add(e_preConditionChecker_mock);
		e_actionFunctions.add(e_parameterUpdater_mock);
		e_actionFunctions.add(e_unknownFunction_mock);
		final ActionFunction preConditionChecker_mock = context.mock(ActionFunction.class, "preConditionChecker");
		final ActionFunction parameterUpdater_mock = context.mock(ActionFunction.class, "parameterUpdater");
		final ActionFunction unknownFunction_mock = context.mock(ActionFunction.class, "unknownFunction");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("action-name"));

				oneOf(actionFunctionXMLSchema_mock).getSchemaName();
				will(returnValue("actionFunction"));

				oneOf(root_mock).getChildren("actionFunction");
				will(returnValue(e_actionFunctions));

				oneOf(actionFunctionXMLSchema_mock).parse(e_preConditionChecker_mock);
				will(returnValue(preConditionChecker_mock));

				oneOf(e_preConditionChecker_mock).getAttributeValue("functionType");
				will(returnValue("preConditionChecker"));

				oneOf(preConditionChecker_mock).setType(ActionFunction.TYPE_PRECONDITION_CHECKER);

				oneOf(actionFunctionXMLSchema_mock).parse(e_parameterUpdater_mock);
				will(returnValue(parameterUpdater_mock));

				oneOf(e_parameterUpdater_mock).getAttributeValue("functionType");
				will(returnValue("parameterUpdater"));

				oneOf(parameterUpdater_mock).setType(ActionFunction.TYPE_PARAMETER_UPDATER);

				oneOf(actionFunctionXMLSchema_mock).parse(e_unknownFunction_mock);
				will(returnValue(unknownFunction_mock));

				oneOf(e_unknownFunction_mock).getAttributeValue("functionType");
				will(returnValue("unknownFunction"));
			}
		});

		context.checking(new Expectations() {
			{
				allowing(preConditionChecker_mock).getType();
				will(returnValue(ActionFunction.TYPE_PRECONDITION_CHECKER));

				allowing(parameterUpdater_mock).getType();
				will(returnValue(ActionFunction.TYPE_PARAMETER_UPDATER));
			}
		});

		Action result = testable.parse(root_mock);
		assertTrue(result instanceof Action);
		assertEquals(1, result.getParameterUpdaters().size());
		assertEquals(1, result.getPreConditionCheckers().size());
	}

	@Test
	public void combine() {
		final Action action_mock = context.mock(Action.class);
		final ActionFunction preConditionChecker_mock = context.mock(ActionFunction.class, "preConditionChecker");
		final ActionFunction parameterUpdater_mock = context.mock(ActionFunction.class, "parameterUpdater");
		final List<ActionFunction> preConditionCheckers = new ArrayList<>();
		preConditionCheckers.add(preConditionChecker_mock);
		final List<ActionFunction> parameterUpdaters = new ArrayList<>();
		parameterUpdaters.add(parameterUpdater_mock);
		final Element preConditionChecker = new Element("preConditionChecker");
		final Element parameterUpdater = new Element("parameterUpdater");

		context.checking(new Expectations() {
			{
				oneOf(action_mock).getName();
				will(returnValue("OPERATION"));

				oneOf(action_mock).getPreConditionCheckers();
				will(returnValue(preConditionCheckers));

				oneOf(actionFunctionXMLSchema_mock).combine(preConditionChecker_mock);
				will(returnValue(preConditionChecker));

				oneOf(action_mock).getParameterUpdaters();
				will(returnValue(parameterUpdaters));

				oneOf(actionFunctionXMLSchema_mock).combine(parameterUpdater_mock);
				will(returnValue(parameterUpdater));
			}
		});

		Element element = testable.combine(action_mock);
		assertEquals("OPERATION", element.getChildText("name"));
		assertEquals("preConditionChecker", element.getChild("preConditionChecker").getAttributeValue("functionType"));
		assertEquals("parameterUpdater", element.getChild("parameterUpdater").getAttributeValue("functionType"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("action", testable.getSchemaName());
	}
}
