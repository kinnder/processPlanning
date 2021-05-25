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
import planning.model.LuaScriptActionFunction;

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

	LuaScriptActionFunctionXMLSchema luaSciptActionFunctionXMLSchema_mock;

	@BeforeEach
	public void setup() {
		luaSciptActionFunctionXMLSchema_mock = context.mock(LuaScriptActionFunctionXMLSchema.class);

		testable = new ActionXMLSchema(luaSciptActionFunctionXMLSchema_mock);
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
		final LuaScriptActionFunction lua_preConditionChecker_mock = context.mock(LuaScriptActionFunction.class, "lua-preConditionChecker");
		final LuaScriptActionFunction lua_parameterUpdater_mock = context.mock(LuaScriptActionFunction.class, "lua-parameterUpdater");
		final LuaScriptActionFunction lua_unknownFunction_mock = context.mock(LuaScriptActionFunction.class, "lua-unknownFunction");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("name");
				will(returnValue("action-name"));

				oneOf(luaSciptActionFunctionXMLSchema_mock).getSchemaName();
				will(returnValue("luaScriptActionFunction"));

				oneOf(root_mock).getChildren("luaScriptActionFunction");
				will(returnValue(e_actionFunctions));

				oneOf(luaSciptActionFunctionXMLSchema_mock).parse(e_preConditionChecker_mock);
				will(returnValue(lua_preConditionChecker_mock));

				oneOf(e_preConditionChecker_mock).getAttributeValue("functionType");
				will(returnValue("preConditionChecker"));

				oneOf(luaSciptActionFunctionXMLSchema_mock).parse(e_parameterUpdater_mock);
				will(returnValue(lua_parameterUpdater_mock));

				oneOf(e_parameterUpdater_mock).getAttributeValue("functionType");
				will(returnValue("parameterUpdater"));

				oneOf(luaSciptActionFunctionXMLSchema_mock).parse(e_unknownFunction_mock);
				will(returnValue(lua_unknownFunction_mock));

				oneOf(e_unknownFunction_mock).getAttributeValue("functionType");
				will(returnValue("unknownFunction"));
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
		final LuaScriptActionFunction lua_preConditionChecker_mock = context.mock(LuaScriptActionFunction.class, "lua-preConditionChecker");
		final LuaScriptActionFunction lua_parameterUpdater_mock = context.mock(LuaScriptActionFunction.class, "lua-parameterUpdater");
		final List<LuaScriptActionFunction> preConditionCheckers = new ArrayList<>();
		preConditionCheckers.add(lua_preConditionChecker_mock);
		final List<LuaScriptActionFunction> parameterUpdaters = new ArrayList<>();
		parameterUpdaters.add(lua_parameterUpdater_mock);
		final Element preConditionChecker = new Element("preConditionChecker");
		final Element parameterUpdater = new Element("parameterUpdater");

		context.checking(new Expectations() {
			{
				oneOf(action_mock).getName();
				will(returnValue("OPERATION"));

				oneOf(action_mock).getPreConditionCheckers();
				will(returnValue(preConditionCheckers));

				oneOf(luaSciptActionFunctionXMLSchema_mock).combine(lua_preConditionChecker_mock);
				will(returnValue(preConditionChecker));

				oneOf(action_mock).getParameterUpdaters();
				will(returnValue(parameterUpdaters));

				oneOf(luaSciptActionFunctionXMLSchema_mock).combine(lua_parameterUpdater_mock);
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
