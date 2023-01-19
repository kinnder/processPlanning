package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.LuaScriptLine;

public class LuaScriptLineXMLSchema implements XMLSchema<LuaScriptLine> {

	private static final String TAG_line = "line";

	private static final String TAG_n = "n";

	@Override
	public String getSchemaName() {
		return TAG_line;
	}

	@Override
	public LuaScriptLine parse(Element element) throws DataConversionException {
		final int number = element.getAttribute(TAG_n).getIntValue();
		final String text = element.getText();
		return new LuaScriptLine(number, text);
	}

	@Override
	public Element combine(LuaScriptLine scriptLine) {
		final Element element = new Element(TAG_line);
		element.setText(scriptLine.getText());
		element.setAttribute(TAG_n, scriptLine.getNumber().toString());
		return element;
	}
}
