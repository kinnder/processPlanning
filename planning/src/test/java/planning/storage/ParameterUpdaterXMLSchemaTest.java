package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

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

import planning.model.ActionParameterUpdater;
import planning.model.LuaScriptActionParameterUpdater;

public class ParameterUpdaterXMLSchemaTest {

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

	ParameterUpdaterXMLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new ParameterUpdaterXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> lines = new ArrayList<>();
		final Element line_mock = context.mock(Element.class, "line");
		lines.add(line_mock);
		final Attribute attribute_mock = context.mock(Attribute.class);

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildren("line");
				will(returnValue(lines));

				oneOf(line_mock).getAttribute("n");
				will(returnValue(attribute_mock));

				oneOf(attribute_mock).getIntValue();
				will(returnValue(1));

				oneOf(line_mock).getText();
				will(returnValue("local systemVariant = ..."));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof ActionParameterUpdater);
	}

	@Test
	public void combine() {
		final LuaScriptActionParameterUpdater parameterUpdater_mock = context
				.mock(LuaScriptActionParameterUpdater.class);

		context.checking(new Expectations() {
			{
				oneOf(parameterUpdater_mock).getScript();
				will(returnValue(
						"local systemVariant = ...\nlocal object = systemVariant:getObjectByIdMatch('ID-PLANE-X-TARGET')"));
			}
		});

		Element element = testable.combine(parameterUpdater_mock);
		List<Element> lines = element.getChildren("line");
		assertEquals(2, lines.size());
		assertEquals("local systemVariant = ...", lines.get(0).getText());
		assertEquals("1", lines.get(0).getAttributeValue("n"));
		assertEquals("local object = systemVariant:getObjectByIdMatch('ID-PLANE-X-TARGET')", lines.get(1).getText());
		assertEquals("2", lines.get(1).getAttributeValue("n"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("parameterUpdater", testable.getSchemaName());
	}
}
