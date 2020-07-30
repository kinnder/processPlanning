package application.domain.cuttingProcess;

import java.io.IOException;
import java.net.URISyntaxException;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.LuaScriptActionPreConditionChecker;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;
import planning.storage.SystemTransformationsXMLFile;

public class GenerateSystemTransformations implements CuttingProcess {

	public static SystemTransformation cutCylinderSurface() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate workpiece = new SystemObjectTemplate(ID_WORKPIECE);
		workpiece.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_WORKPIECE, true));
		systemTemplate.addObjectTemplate(workpiece);

		final SystemObjectTemplate cylinderSurface = new SystemObjectTemplate(ID_CYLINDER_SURFACE);
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, false));
		systemTemplate.addObjectTemplate(cylinderSurface);

		final SystemObjectTemplate requirement = new SystemObjectTemplate(ID_REQUIREMENT);
		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_REQUIREMENT, true));
		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT));
		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		systemTemplate.addObjectTemplate(requirement);

		systemTemplate.addLinkTemplate(workpiece, LINK_IS_PART_OF, cylinderSurface);
		systemTemplate.addLinkTemplate(workpiece, LINK_IS_REQUIREMENT_OF, requirement);

		systemTemplate.addLinkTemplate(requirement, LINK_IS_DIAMETER_REQUIREMENT, null);

		final Transformation transformations[] = new Transformation[] {
				new AttributeTransformation(ID_REQUIREMENT, ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true),
				new LinkTransformation(ID_REQUIREMENT, LINK_IS_DIAMETER_REQUIREMENT, null, ID_CYLINDER_SURFACE),
				new LinkTransformation(ID_CYLINDER_SURFACE, LINK_IS_DIAMETER_REQUIREMENT, null, ID_REQUIREMENT),
				new AttributeTransformation(ID_CYLINDER_SURFACE, ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, true) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local cylinderSurface_actual = systemVariant:getObjectByIdMatch('" + ID_CYLINDER_SURFACE + "')");
		script.append("\n");
		script.append("local diameter = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_DIAMETER + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT + "')");
		script.append("\n");
		script.append("local diameterRequired = requirement_actual:getAttribute('" + ATTRIBUTE_DIAMETER_REQUIREMENT + "'):getValueAsInteger()");
		script.append("\n");
		script.append("return diameter > diameterRequired");
		script.append("\n");

		final Action action = new Action(OPERATION_CUT_CYLINDER_SURFACE);
		action.registerActionPreConditionChecker(new LuaScriptActionPreConditionChecker(globals, script.toString()));

		script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local cylinderSurface_actual = systemVariant:getObjectByIdMatch('" + ID_CYLINDER_SURFACE + "')");
		script.append("\n");
		script.append("local diameter = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_DIAMETER + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT + "')");
		script.append("\n");
		script.append("local diameterRequired = requirement_actual:getAttribute('" + ATTRIBUTE_DIAMETER_REQUIREMENT + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local diameterDelta = diameter - diameterRequired");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_DIAMETER_DELTA + "', diameterDelta)");
		script.append("\n");
		script.append("cylinderSurface_actual:getAttribute('" + ATTRIBUTE_DIAMETER + "'):setValue(diameterRequired)");
		script.append("\n");

		action.registerActionParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(ELEMENT_CUT_CYLINDER_SURFACE, action, systemTemplate, transformations);
	}

	public static SystemTransformation trimCylinderSurface() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate workpiece = new SystemObjectTemplate(ID_WORKPIECE);
		workpiece.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_WORKPIECE, true));
		systemTemplate.addObjectTemplate(workpiece);

		final SystemObjectTemplate cylinderSurface = new SystemObjectTemplate(ID_CYLINDER_SURFACE);
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_HAS_LENGTH_REQUIREMENT, false));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, true));
		systemTemplate.addObjectTemplate(cylinderSurface);

		final SystemObjectTemplate requirement = new SystemObjectTemplate(ID_REQUIREMENT);
		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_REQUIREMENT, true));
		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH_REQUIREMENT));
		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		systemTemplate.addObjectTemplate(requirement);

		systemTemplate.addLinkTemplate(workpiece, LINK_IS_PART_OF, cylinderSurface);
		systemTemplate.addLinkTemplate(workpiece, LINK_IS_REQUIREMENT_OF, requirement);

		systemTemplate.addLinkTemplate(cylinderSurface, LINK_IS_LENGTH_REQUIREMENT, null);

		systemTemplate.addLinkTemplate(requirement, LINK_IS_LENGTH_REQUIREMENT, null);

		final Transformation transformations[] = new Transformation[] {
				new AttributeTransformation(ID_REQUIREMENT, ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true),
				new LinkTransformation(ID_REQUIREMENT, LINK_IS_LENGTH_REQUIREMENT, null, ID_CYLINDER_SURFACE),
				new LinkTransformation(ID_CYLINDER_SURFACE, LINK_IS_LENGTH_REQUIREMENT, null, ID_REQUIREMENT),
				new AttributeTransformation(ID_CYLINDER_SURFACE, ATTRIBUTE_HAS_LENGTH_REQUIREMENT, true) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local cylinderSurface_actual = systemVariant:getObjectByIdMatch('" + ID_CYLINDER_SURFACE + "')");
		script.append("\n");
		script.append("local length = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_LENGTH + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT + "')");
		script.append("\n");
		script.append("local lengthRequired = requirement_actual:getAttribute('" + ATTRIBUTE_LENGTH_REQUIREMENT + "'):getValueAsInteger()");
		script.append("\n");
		script.append("return length == lengthRequired");
		script.append("\n");

		final Action action = new Action(OPERATION_TRIM_CYLINDER_SURFACE);
		action.registerActionPreConditionChecker(new LuaScriptActionPreConditionChecker(globals, script.toString()));

		return new SystemTransformation(ELEMENT_TRIM_CYLINDER_SURFACE, action, systemTemplate, transformations);
	}

	public static SystemTransformation splitCylinderSurface() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate workpiece = new SystemObjectTemplate(ID_WORKPIECE);
		workpiece.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_WORKPIECE, true));
		systemTemplate.addObjectTemplate(workpiece);

		final SystemObjectTemplate cylinderSurface = new SystemObjectTemplate(ID_CYLINDER_SURFACE);
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_HAS_LENGTH_REQUIREMENT, false));
		systemTemplate.addObjectTemplate(cylinderSurface);

		final SystemObjectTemplate requirement_l = new SystemObjectTemplate(ID_REQUIREMENT_L);
		requirement_l.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_REQUIREMENT, true));
		requirement_l.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH_REQUIREMENT));
		requirement_l.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		systemTemplate.addObjectTemplate(requirement_l);

		final SystemObjectTemplate requirement_r = new SystemObjectTemplate(ID_REQUIREMENT_R);
		requirement_r.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_REQUIREMENT, true));
		requirement_r.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT));
		requirement_r.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		systemTemplate.addObjectTemplate(requirement_r);

		systemTemplate.addLinkTemplate(workpiece, LINK_IS_PART_OF, cylinderSurface);
		systemTemplate.addLinkTemplate(workpiece, LINK_IS_REQUIREMENT_OF, requirement_l);
		systemTemplate.addLinkTemplate(workpiece, LINK_IS_REQUIREMENT_OF, requirement_r);

		systemTemplate.addLinkTemplate(cylinderSurface, LINK_IS_LENGTH_REQUIREMENT, null);

		systemTemplate.addLinkTemplate(requirement_l, LINK_SURFACE_SIDE_RIGHT, LINK_SURFACE_SIDE_LEFT, requirement_r);
		systemTemplate.addLinkTemplate(requirement_l, LINK_IS_LENGTH_REQUIREMENT, null);

		systemTemplate.addLinkTemplate(requirement_r, LINK_IS_LENGTH_REQUIREMENT, null);

		final Transformation transformations[] = new Transformation[] {
				new AttributeTransformation(ID_REQUIREMENT_L, ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true),
				new AttributeTransformation(ID_REQUIREMENT_R, ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true),
				new LinkTransformation(ID_CYLINDER_SURFACE, LINK_IS_LENGTH_REQUIREMENT, null, ID_REQUIREMENT_L),
				new LinkTransformation(ID_REQUIREMENT_L, LINK_IS_LENGTH_REQUIREMENT, null, ID_CYLINDER_SURFACE),
				new AttributeTransformation(ID_CYLINDER_SURFACE, ATTRIBUTE_HAS_LENGTH_REQUIREMENT, true) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local cylinderSurface_actual = systemVariant:getObjectByIdMatch('" + ID_CYLINDER_SURFACE + "')");
		script.append("\n");
		script.append("local diameter = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_DIAMETER + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local length = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_LENGTH + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_l_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT_L + "')");
		script.append("\n");
		script.append("local lengthRequired = requirement_l_actual:getAttribute('" + ATTRIBUTE_LENGTH_REQUIREMENT + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_r_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT_R + "')");
		script.append("\n");
		script.append("local diameterRequired = requirement_r_actual:getAttribute('" + ATTRIBUTE_DIAMETER_REQUIREMENT + "'):getValueAsInteger()");
		script.append("\n");
		script.append("return (diameter > diameterRequired) and (length > lengthRequired)");
		script.append("\n");

		final Action action = new Action(OPERATION_SPLIT_CYLINDER_SURFACE);
		action.registerActionPreConditionChecker(new LuaScriptActionPreConditionChecker(globals, script.toString()));

		script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local cylinderSurface_actual = systemVariant:getObjectByIdMatch('" + ID_CYLINDER_SURFACE + "')");
		script.append("\n");
		script.append("local diameter = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_DIAMETER + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local length = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_LENGTH + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_l_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT_L + "')");
		script.append("\n");
		script.append("local lengthRequired = requirement_l_actual:getAttribute('" + ATTRIBUTE_LENGTH_REQUIREMENT + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_r_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT_R + "')");
		script.append("\n");
		script.append("local diameterRequired = requirement_r_actual:getAttribute('" + ATTRIBUTE_DIAMETER_REQUIREMENT + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local workpiece_actual = systemVariant:getObjectByIdMatch('" + ID_WORKPIECE + "')");
		script.append("\n");
		script.append("local diameterDelta = diameter - diameterRequired");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_DIAMETER_DELTA + "', diameterDelta)");
		script.append("\n");
		script.append("local lengthDelta = length - lengthRequired");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_LENGTH_DELTA + "', lengthDelta)");
		script.append("\n");
		script.append("local cylinderSurface_new = systemVariant:getSystem():addNewObject('" + OBJECT_CYLINDER_SURFACE + "')");
		script.append("\n");
		script.append("cylinderSurface_new:addAttribute('" + ATTRIBUTE_CYLINDER_SURFACE + "', true)");
		script.append("\n");
		script.append("cylinderSurface_new:addAttribute('" + ATTRIBUTE_DIAMETER + "', diameterRequired)");
		script.append("\n");
		script.append("cylinderSurface_new:addAttribute('" + ATTRIBUTE_LENGTH + "', lengthDelta)");
		script.append("\n");
		script.append("cylinderSurface_new:addAttribute('" + ATTRIBUTE_HAS_DIAMETER_REQUIREMENT + "', true)");
		script.append("\n");
		script.append("cylinderSurface_new:addAttribute('" + ATTRIBUTE_HAS_LENGTH_REQUIREMENT + "', false)");
		script.append("\n");
		script.append("local system = systemVariant:getSystem()");
		script.append("\n");
		script.append("system:addLink('"+ LINK_IS_PART_OF +"', cylinderSurface_new:getId(), workpiece_actual:getId())");
		script.append("\n");
		script.append("system:addLink('"+ LINK_IS_PART_OF +"', workpiece_actual:getId(), cylinderSurface_new:getId())");
		script.append("\n");
		script.append("system:addLink('"+ LINK_IS_DIAMETER_REQUIREMENT +"', cylinderSurface_new:getId(), requirement_r_actual:getId())");
		script.append("\n");
		script.append("system:getLink('"+ LINK_IS_DIAMETER_REQUIREMENT +"', requirement_r_actual:getId(), nil):setObjectId2(cylinderSurface_new:getId())");
		script.append("\n");
		script.append("system:addLink('"+ LINK_IS_LENGTH_REQUIREMENT +"', cylinderSurface_new:getId(), nil)");
		script.append("\n");
		script.append("system:addLink('"+ LINK_SURFACE_SIDE_LEFT +"', cylinderSurface_new:getId(), cylinderSurface_actual:getId())");
		script.append("\n");
		script.append("system:addLink('"+ LINK_SURFACE_SIDE_LEFT +"', cylinderSurface_actual:getId(), cylinderSurface_new:getId())");
		script.append("\n");
		script.append("system:addLink('"+ LINK_SURFACE_SIDE_RIGHT +"', cylinderSurface_new:getId(), nil)");
		script.append("\n");
		script.append("cylinderSurface_actual:getAttribute('" + ATTRIBUTE_LENGTH + "'):setValue(lengthRequired)");
		script.append("\n");

		action.registerActionParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(ELEMENT_SPLIT_CYLINDER_SURFACE, action, systemTemplate, transformations);
	}

	// TODO : пересмотреть положение globals
	static Globals globals = JsePlatform.standardGlobals();

	public static void main(String args[]) {
		SystemTransformations cuttingProcessTransformations = new SystemTransformations();
		cuttingProcessTransformations.add(cutCylinderSurface());
		cuttingProcessTransformations.add(trimCylinderSurface());
		cuttingProcessTransformations.add(splitCylinderSurface());

		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.setObject(cuttingProcessTransformations);
		try {
			xmlFile.save(GenerateSystemTransformations.class.getResource("/cuttingProcess/systemTransformations.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
