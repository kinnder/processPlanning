package application.storage.xml;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.ActionParameterUpdater;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.LuaScriptLine;

//TODO : rename schema to match generic class
public class ParameterUpdaterXMLSchema implements XMLSchema<ActionParameterUpdater> {

	final private static String TAG_parameterUpdater = "parameterUpdater";

	@Override
	public String getSchemaName() {
		return TAG_parameterUpdater;
	}

	// TODO : пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	private LuaScriptLineXMLSchema luaScriptLineXMLSchema = new LuaScriptLineXMLSchema();

	@Override
	public ActionParameterUpdater parse(Element root) throws DataConversionException {
		List<Element> elements = root.getChildren(luaScriptLineXMLSchema.getSchemaName());
		Map<Integer, LuaScriptLine> scriptLines = new TreeMap<Integer, LuaScriptLine>();
		for (Element element : elements) {
			LuaScriptLine scriptLine = luaScriptLineXMLSchema.parse(element);
			scriptLines.put(scriptLine.getNumber(), scriptLine);
		}
		return new LuaScriptActionParameterUpdater(globals, scriptLines.values());
	}

	@Override
	public Element combine(ActionParameterUpdater parameterUpdater) {
		LuaScriptActionParameterUpdater luaParameterUpdater = (LuaScriptActionParameterUpdater) parameterUpdater;
		Element root = new Element(TAG_parameterUpdater);
		Collection<LuaScriptLine> scriptLines = luaParameterUpdater.getScriptLines();
		for (LuaScriptLine scriptLine : scriptLines) {
			Element element = luaScriptLineXMLSchema.combine(scriptLine);
			root.addContent(element);
		}
		return root;
	}
}
