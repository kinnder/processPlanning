package application.command;

import java.io.IOException;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import application.event.CommandStatusEvent;
import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.AttributeTemplate;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.LuaScriptActionPreConditionChecker;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;
import planning.storage.SystemTransformationsXMLFile;

public class NewSystemTransformationsCommand extends Command {

	public static final String NAME = "new_st";

	SystemTransformationsXMLFile transformationsXMLFile = new SystemTransformationsXMLFile();

	@Override
	public void execute(CommandData data) throws Exception {
		execute((NewSystemTransformationsCommandData) data);
	}

	private void execute(NewSystemTransformationsCommandData data) throws IOException {
		notifyCommandStatus(new CommandStatusEvent("executing command: \"new system transformation\"..."));

		SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(connectAlphaAndBeta());

		transformationsXMLFile.setObject(systemTransformations);
		transformationsXMLFile.save(data.systemTransformationsFile);

		notifyCommandStatus(new CommandStatusEvent("done"));
	}

	// TODO (2020-07-22 #28): пересмотреть положение globals
	static Globals globals = JsePlatform.standardGlobals();

	// TODO (2020-07-22 #28): использовать в качестве примеров сценарии из domain
	public static SystemTransformation connectAlphaAndBeta() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate alpha = new SystemObjectTemplate("#ALPHA");
		alpha.addAttributeTemplate(new AttributeTemplate("IS-APLHA", true));
		systemTemplate.addObjectTemplate(alpha);

		final SystemObjectTemplate beta = new SystemObjectTemplate("#BETA");
		beta.addAttributeTemplate(new AttributeTemplate("IS-BETA", true));
		systemTemplate.addObjectTemplate(beta);

		final Transformation transformations[] = new Transformation[] {};

		StringBuilder script_1 = new StringBuilder();
		script_1.append("local systemVariant = ...");
		script_1.append("\n");
		script_1.append("return true");
		script_1.append("\n");

		StringBuilder script_2 = new StringBuilder();
		script_2.append("local systemVariant = ...");
		script_2.append("\n");

		final Action action = new Action("OPERATION-CONNECT-ALPHA-AND-BETA");
		action.registerActionPreConditionChecker(new LuaScriptActionPreConditionChecker(globals, script_1.toString()));
		action.registerActionParameterUpdater(new LuaScriptActionParameterUpdater(globals, script_2.toString()));

		return new SystemTransformation("ELEMENT-CONNECT-ALPHA-AND-BETA", action, systemTemplate, transformations);
	}
}
