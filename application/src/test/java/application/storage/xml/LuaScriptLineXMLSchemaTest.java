package application.storage.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.LuaScriptLine;

public class LuaScriptLineXMLSchemaTest {

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

	LuaScriptLineXMLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new LuaScriptLineXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final Attribute n_attribute_mock = context.mock(Attribute.class);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getAttribute("n");
				will(returnValue(n_attribute_mock));

				oneOf(n_attribute_mock).getIntValue();
				will(returnValue(1));

				oneOf(root_mock).getText();
				will(returnValue("local systemVariant = ..."));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof LuaScriptLine);
	}

	@Test
	public void combine() {
		final LuaScriptLine luaScriptLine_mock = context.mock(LuaScriptLine.class);

		context.checking(new Expectations() {
			{
				oneOf(luaScriptLine_mock).getText();
				will(returnValue("local systemVariant = ..."));

				oneOf(luaScriptLine_mock).getNumber();
				will(returnValue(1));
			}
		});

		Element element = testable.combine(luaScriptLine_mock);
		assertEquals("local systemVariant = ...", element.getText());
		assertEquals("1", element.getAttributeValue("n"));
	}

	@Test
	public void getschemaName() {
		assertEquals("line", testable.getSchemaName());
	}
}
