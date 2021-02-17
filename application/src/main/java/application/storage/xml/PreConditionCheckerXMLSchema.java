package application.storage.xml;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.ActionPreConditionChecker;
import planning.model.LuaScriptActionPreConditionChecker;
import planning.model.LuaScriptLine;

// TODO : rename schema to match generic class
public class PreConditionCheckerXMLSchema implements XMLSchema<ActionPreConditionChecker> {

	final private static String TAG_preConditionChecker = "preConditionChecker";

	// TODO : пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	private LuaScriptLineXMLSchema luaScriptLineXMLSchema = new LuaScriptLineXMLSchema();

	@Override
	public ActionPreConditionChecker parse(Element root) throws DataConversionException {
		List<Element> elements = root.getChildren(luaScriptLineXMLSchema.getSchemaName());
		Map<Integer, LuaScriptLine> scriptLines = new TreeMap<Integer, LuaScriptLine>();
		for (Element element : elements) {
			LuaScriptLine scriptLine = luaScriptLineXMLSchema.parse(element);
			scriptLines.put(scriptLine.getNumber(), scriptLine);
		}
		return new LuaScriptActionPreConditionChecker(globals, scriptLines.values());
	}

	@Override
	public Element combine(ActionPreConditionChecker preConditionChecker) {
		LuaScriptActionPreConditionChecker luaPreConditionChecker = (LuaScriptActionPreConditionChecker) preConditionChecker;
		Element root = new Element(TAG_preConditionChecker);
		Collection<LuaScriptLine> scriptLines = luaPreConditionChecker.getScriptLines();
		for (LuaScriptLine scriptLine : scriptLines) {
			Element element = luaScriptLineXMLSchema.combine(scriptLine);
			root.addContent(element);
		}
		return root;
	}

	@Override
	public String getSchemaName() {
		return TAG_preConditionChecker;
	}

}
