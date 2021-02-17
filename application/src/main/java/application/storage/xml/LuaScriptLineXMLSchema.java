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
		int number = element.getAttribute(TAG_n).getIntValue();
		String text = element.getText();
		LuaScriptLine scriptLine = new LuaScriptLine(number, text);
		return scriptLine;
	}

	@Override
	public Element combine(LuaScriptLine scriptLine) {
		Element element = new Element(TAG_line);
		element.setText(scriptLine.getText());
		element.setAttribute(TAG_n, scriptLine.getNumber().toString());
		return element;
	}
}
