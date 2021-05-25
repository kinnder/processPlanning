package application.storage.xml;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.LuaScriptActionFunction;
import planning.model.LuaScriptLine;

public class LuaScriptActionFunctionXMLSchema implements XMLSchema<LuaScriptActionFunction> {

	final private static String TAG_luaScriptActionFunction = "luaScriptActionFunction";

	@Override
	public String getSchemaName() {
		return TAG_luaScriptActionFunction;
	}

	// TODO (2021-05-21 #39): пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	private LuaScriptLineXMLSchema luaScriptLineXMLSchema;

	public LuaScriptActionFunctionXMLSchema() {
		this(new LuaScriptLineXMLSchema());
	}

	LuaScriptActionFunctionXMLSchema(LuaScriptLineXMLSchema luaScriptLineXMLSchema) {
		this.luaScriptLineXMLSchema = luaScriptLineXMLSchema;
	}

	@Override
	public LuaScriptActionFunction parse(Element root) throws DataConversionException {
		List<Element> elements = root.getChildren(luaScriptLineXMLSchema.getSchemaName());
		Map<Integer, LuaScriptLine> scriptLines = new TreeMap<Integer, LuaScriptLine>();
		for (Element element : elements) {
			LuaScriptLine scriptLine = luaScriptLineXMLSchema.parse(element);
			scriptLines.put(scriptLine.getNumber(), scriptLine);
		}
		return new LuaScriptActionFunction(globals, scriptLines.values());
	}

	@Override
	public Element combine(LuaScriptActionFunction actionFunction) {
		Element root = new Element(TAG_luaScriptActionFunction);
		Collection<LuaScriptLine> scriptLines = actionFunction.getScriptLines();
		for (LuaScriptLine scriptLine : scriptLines) {
			Element element = luaScriptLineXMLSchema.combine(scriptLine);
			root.addContent(element);
		}
		return root;
	}
}
