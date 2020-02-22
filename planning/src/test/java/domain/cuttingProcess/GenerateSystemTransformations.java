package domain.cuttingProcess;

import java.io.IOException;
import java.net.URISyntaxException;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.LinkTemplate;
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
		final SystemObjectTemplate workpiece = new SystemObjectTemplate(ID_WORKPIECE);
		final SystemObjectTemplate cylinderSurface = new SystemObjectTemplate(ID_CYLINDER_SURFACE);
		final SystemObjectTemplate requirement = new SystemObjectTemplate(ID_REQUIREMENT);

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(workpiece);
		template.addObjectTemplate(cylinderSurface);
		template.addObjectTemplate(requirement);

		workpiece.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, ID_CYLINDER_SURFACE));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, ID_REQUIREMENT));

		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, false));
		cylinderSurface.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, ID_WORKPIECE));
		cylinderSurface.addLinkTemplate(new LinkTemplate(LINK_IS_DIAMETER_REQUIREMENT, null));

		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_REQUIREMENT, true));
		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT));
		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, ID_WORKPIECE));
		requirement.addLinkTemplate(new LinkTemplate(LINK_IS_DIAMETER_REQUIREMENT, null));

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
		script.append("local diameter = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_DIAMETER
				+ "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT + "')");
		script.append("\n");
		script.append("local diameterRequired = requirement_actual:getAttribute('" + ATTRIBUTE_DIAMETER_REQUIREMENT
				+ "'):getValueAsInteger()");
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
		script.append("local diameter = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_DIAMETER
				+ "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT + "')");
		script.append("\n");
		script.append("local diameterRequired = requirement_actual:getAttribute('" + ATTRIBUTE_DIAMETER_REQUIREMENT
				+ "'):getValueAsInteger()");
		script.append("\n");
		script.append("local diameterDelta = diameter - diameterRequired");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_DIAMETER_DELTA + "', diameterDelta)");
		script.append("\n");
		script.append("cylinderSurface_actual:getAttribute('" + ATTRIBUTE_DIAMETER + "'):setValue(diameterRequired)");
		script.append("\n");

		action.registerActionParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(ELEMENT_CUT_CYLINDER_SURFACE, action, template, transformations);
	}

	public static SystemTransformation trimCylinderSurface() {
		final SystemObjectTemplate workpiece = new SystemObjectTemplate(ID_WORKPIECE);
		final SystemObjectTemplate cylinderSurface = new SystemObjectTemplate(ID_CYLINDER_SURFACE);
		final SystemObjectTemplate requirement = new SystemObjectTemplate(ID_REQUIREMENT);

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(workpiece);
		template.addObjectTemplate(cylinderSurface);
		template.addObjectTemplate(requirement);

		workpiece.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, ID_CYLINDER_SURFACE));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, ID_REQUIREMENT));

		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_HAS_LENGTH_REQUIREMENT, false));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, true));
		cylinderSurface.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, ID_WORKPIECE));
		cylinderSurface.addLinkTemplate(new LinkTemplate(LINK_IS_LENGTH_REQUIREMENT, null));

		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_REQUIREMENT, true));
		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH_REQUIREMENT));
		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, ID_WORKPIECE));
		requirement.addLinkTemplate(new LinkTemplate(LINK_IS_LENGTH_REQUIREMENT, null));

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
		script.append(
				"local length = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_LENGTH + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT + "')");
		script.append("\n");
		script.append("local lengthRequired = requirement_actual:getAttribute('" + ATTRIBUTE_LENGTH_REQUIREMENT
				+ "'):getValueAsInteger()");
		script.append("\n");
		script.append("return length == lengthRequired");
		script.append("\n");

		final Action action = new Action(OPERATION_TRIM_CYLINDER_SURFACE);
		action.registerActionPreConditionChecker(new LuaScriptActionPreConditionChecker(globals, script.toString()));

		return new SystemTransformation(ELEMENT_TRIM_CYLINDER_SURFACE, action, template, transformations);
	}

	public static SystemTransformation splitCylinderSurface() {
		final SystemObjectTemplate workpiece = new SystemObjectTemplate(ID_WORKPIECE);
		final SystemObjectTemplate cylinderSurface = new SystemObjectTemplate(ID_CYLINDER_SURFACE);
		final SystemObjectTemplate requirement_l = new SystemObjectTemplate(ID_REQUIREMENT_L);
		final SystemObjectTemplate requirement_r = new SystemObjectTemplate(ID_REQUIREMENT_R);

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(workpiece);
		template.addObjectTemplate(cylinderSurface);
		template.addObjectTemplate(requirement_l);
		template.addObjectTemplate(requirement_r);

		workpiece.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, ID_CYLINDER_SURFACE));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, ID_REQUIREMENT_L));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, ID_REQUIREMENT_R));

		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_HAS_LENGTH_REQUIREMENT, false));
		cylinderSurface.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, ID_WORKPIECE));
		cylinderSurface.addLinkTemplate(new LinkTemplate(LINK_IS_LENGTH_REQUIREMENT, null));

		requirement_l.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_REQUIREMENT, true));
		requirement_l.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH_REQUIREMENT));
		requirement_l.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_l.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, ID_WORKPIECE));
		requirement_l.addLinkTemplate(new LinkTemplate(LINK_SURFACE_SIDE_RIGHT, ID_REQUIREMENT_R));
		requirement_l.addLinkTemplate(new LinkTemplate(LINK_IS_LENGTH_REQUIREMENT, null));

		requirement_r.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_REQUIREMENT, true));
		requirement_r.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT));
		requirement_r.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_r.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, ID_WORKPIECE));
		requirement_r.addLinkTemplate(new LinkTemplate(LINK_SURFACE_SIDE_LEFT, ID_REQUIREMENT_L));
		requirement_r.addLinkTemplate(new LinkTemplate(LINK_IS_DIAMETER_REQUIREMENT, null));

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
		script.append("local diameter = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_DIAMETER
				+ "'):getValueAsInteger()");
		script.append("\n");
		script.append(
				"local length = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_LENGTH + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_l_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT_L + "')");
		script.append("\n");
		script.append("local lengthRequired = requirement_l_actual:getAttribute('" + ATTRIBUTE_LENGTH_REQUIREMENT
				+ "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_r_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT_R + "')");
		script.append("\n");
		script.append("local diameterRequired = requirement_r_actual:getAttribute('" + ATTRIBUTE_DIAMETER_REQUIREMENT
				+ "'):getValueAsInteger()");
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
		script.append("local diameter = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_DIAMETER
				+ "'):getValueAsInteger()");
		script.append("\n");
		script.append(
				"local length = cylinderSurface_actual:getAttribute('" + ATTRIBUTE_LENGTH + "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_l_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT_L + "')");
		script.append("\n");
		script.append("local lengthRequired = requirement_l_actual:getAttribute('" + ATTRIBUTE_LENGTH_REQUIREMENT
				+ "'):getValueAsInteger()");
		script.append("\n");
		script.append("local requirement_r_actual = systemVariant:getObjectByIdMatch('" + ID_REQUIREMENT_R + "')");
		script.append("\n");
		script.append("local diameterRequired = requirement_r_actual:getAttribute('" + ATTRIBUTE_DIAMETER_REQUIREMENT
				+ "'):getValueAsInteger()");
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
		script.append("local cylinderSurface_new = systemVariant:getSystem():addNewObject('" + OBJECT_CYLINDER_SURFACE
				+ "')");
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
		script.append("cylinderSurface_new:addLink('" + LINK_IS_PART_OF + "', workpiece_actual:getId())");
		script.append("\n");
		script.append(
				"cylinderSurface_new:addLink('" + LINK_IS_DIAMETER_REQUIREMENT + "', requirement_r_actual:getId())");
		script.append("\n");
		script.append("cylinderSurface_new:addLink('" + LINK_IS_LENGTH_REQUIREMENT + "', nil)");
		script.append("\n");
		script.append("cylinderSurface_new:addLink('" + LINK_SURFACE_SIDE_LEFT + "', cylinderSurface_actual:getId())");
		script.append("\n");
		script.append("cylinderSurface_actual:getAttribute('" + ATTRIBUTE_LENGTH + "'):setValue(lengthRequired)");
		script.append("\n");
		script.append("cylinderSurface_new:addLink('" + LINK_SURFACE_SIDE_RIGHT + "', cylinderSurface_new:getId())");
		script.append("\n");
		script.append("workpiece_actual:addLink('" + LINK_IS_PART_OF + "', cylinderSurface_new:getId())");
		script.append("\n");
		script.append("requirement_r_actual:getLink('" + LINK_IS_DIAMETER_REQUIREMENT
				+ "', nil):setObjectId(cylinderSurface_new:getId())");
		script.append("\n");

		action.registerActionParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(ELEMENT_SPLIT_CYLINDER_SURFACE, action, template, transformations);
	}

	// TODO : пересмотреть положение globals
	static Globals globals = JsePlatform.standardGlobals();

	public static void main(String args[]) {
		SystemTransformations cuttingProcessTransformations = new SystemTransformations();
		cuttingProcessTransformations.add(cutCylinderSurface());
		cuttingProcessTransformations.add(trimCylinderSurface());
		cuttingProcessTransformations.add(splitCylinderSurface());

		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.setSystemTransformations(cuttingProcessTransformations);
		try {
			xmlFile.save(GenerateSystemTransformations.class.getResource("/cuttingProcess/systemTransformations.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
