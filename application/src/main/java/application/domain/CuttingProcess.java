package application.domain;

import java.util.UUID;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.Action;
import planning.model.Attribute;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.LuaScriptActionFunction;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class CuttingProcess {

	// TODO (2020-07-30 #30): включить проверку copy-paste
	// CPD-OFF

	public static final String DOMAIN_NAME = "cuttingProcess";

	public static final String ID_OBJECT_WORKPIECE = UUID.randomUUID().toString();

	public static final String ID_OBJECT_CYLINDER_SURFACE = UUID.randomUUID().toString();

	public static final String ID_OBJECT_REQUIREMENT_SURFACE_A = UUID.randomUUID().toString();

	public static final String ID_OBJECT_REQUIREMENT_SURFACE_B = UUID.randomUUID().toString();

	public static final String ID_OBJECT_REQUIREMENT_SURFACE_C = UUID.randomUUID().toString();

	/** Заготовка */
	public static final String OBJECT_WORKPIECE = "OBJECT-WORKPIECE";

	/** Цилиндрическая поверхность */
	public static final String OBJECT_CYLINDER_SURFACE = "OBJECT-CYLINDER-SURFACE";

	/** Требование к поверхности a */
	public static final String OBJECT_REQUIREMENT_SURFACE_A = "OBJECT-REQUIREMENT-SURFACE-A";

	/** Требование к поверхности b */
	public static final String OBJECT_REQUIREMENT_SURFACE_B = "OBJECT-REQUIREMENT-SURFACE-B";

	/** Требование к поверхности c */
	public static final String OBJECT_REQUIREMENT_SURFACE_C = "OBJECT-REQUIREMENT-SURFACE-C";

	/** Является частью */
	public static final String LINK_IS_PART_OF = "LINK-IS-PART-OF";

	/** Является требованием */
	public static final String LINK_IS_REQUIREMENT_OF = "LINK-IS-REQUIREMENT-OF";

	/** Является требованием диаметра */
	public static final String LINK_IS_DIAMETER_REQUIREMENT = "LINK-IS-DIAMETER-REQUIREMENT";

	/** Является требованием длины */
	public static final String LINK_IS_LENGTH_REQUIREMENT = "LINK-IS-LENGTH-REQUIREMENT";

	/** Сторона поверхности справа */
	public static final String LINK_SURFACE_SIDE_RIGHT = "LINK-SURFACE-SIDE-RIGHT";

	/** Сторона поверхности слева */
	public static final String LINK_SURFACE_SIDE_LEFT = "LINK-SURFACE-SIDE-LEFT";

	/** Диаметр */
	public static final String ATTRIBUTE_DIAMETER = "ATTRIBUTE-DIAMETER";

	/** Требование диаметра */
	public static final String ATTRIBUTE_DIAMETER_REQUIREMENT = "ATTRIBUTE-DIAMETER-REQUIREMENT";

	/** Состояние требования диаметра */
	public static final String ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS = "ATTRIBUTE-DIAMETER-REQUIREMENT-STATUS";

	/** Есть требование диаметра */
	public static final String ATTRIBUTE_HAS_DIAMETER_REQUIREMENT = "ATTRIBUTE-HAS-DIAMETER-REQUIREMENT";

	/** Требование длины */
	public static final String ATTRIBUTE_LENGTH_REQUIREMENT = "ATTRIBUTE-LENGTH-REQUIREMENT";

	/** Состояние требования длины */
	public static final String ATTRIBUTE_LENGTH_REQUIREMENT_STATUS = "ATTRIBUTE-LENGTH-REQUIREMENT-STATUS";

	/** Есть требование длины */
	public static final String ATTRIBUTE_HAS_LENGTH_REQUIREMENT = "ATTRIBUTE-HAS-LENGTH-REQUIREMENT";

	/** Длина */
	public static final String ATTRIBUTE_LENGTH = "ATTRIBUTE-LENGTH";

	/** Заготовка */
	public static final String ATTRIBUTE_WORKPIECE = "ATTRIBUTE-WORKPIECE";

	/** Цилиндрическая поверхность */
	public static final String ATTRIBUTE_CYLINDER_SURFACE = "ATTRIBUTE-CYLINDER-SURFACE";

	/** Требование */
	public static final String ATTRIBUTE_REQUIREMENT = "ATTRIBUTE-REQUIREMENT";

	/** Точить цилиндрическую поверхность */
	public static final String OPERATION_CUT_CYLINDER_SURFACE = "OPERATION-CUT-CYLINDER-SURFACE";

	/** Разделить цилиндрическую поверхность */
	public static final String OPERATION_SPLIT_CYLINDER_SURFACE = "OPERATION-SPLIT-CYLINDER-SURFACE";

	/** Подрезать цилиндрическую поверхность */
	public static final String OPERATION_TRIM_CYLINDER_SURFACE = "OPERATION-TRIM-CYLINDER-SURFACE";

	/** Разница диаметров */
	public static final String PARAMETER_DIAMETER_DELTA = "PARAMETER-DIAMETER-DELTA";

	/** Разница длин */
	public static final String PARAMETER_LENGTH_DELTA = "PARAMETER-LENGTH-DELTA";

	public static final String ELEMENT_CUT_CYLINDER_SURFACE = "ELEMENT-CUT-CYLINDER-SURFACE";

	public static final String ELEMENT_TRIM_CYLINDER_SURFACE = "ELEMENT-TRIM-CYLINDER-SURFACE";

	public static final String ELEMENT_SPLIT_CYLINDER_SURFACE = "ELEMENT-SPLIT-CYLINDER-SURFACE";

	public static final String ID_WORKPIECE = "ID-WORKPIECE";

	public static final String ID_CYLINDER_SURFACE = "ID-CYLINDER-SURFACE";

	public static final String ID_REQUIREMENT = "ID-REQUIREMENT";

	public static final String ID_REQUIREMENT_L = "ID-REQUIREMENT-L";

	public static final String ID_REQUIREMENT_R = "ID-REQUIREMENT-R";

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
		action.registerPreConditionChecker(new LuaScriptActionFunction(globals, script.toString()));

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

		action.registerParameterUpdater(new LuaScriptActionFunction(globals, script.toString()));

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
		action.registerPreConditionChecker(new LuaScriptActionFunction(globals, script.toString()));

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
		action.registerPreConditionChecker(new LuaScriptActionFunction(globals, script.toString()));

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
		script.append("local system = systemVariant:getSystem()");
		script.append("\n");
		script.append(
				"system:addLink('" + LINK_IS_PART_OF + "', cylinderSurface_new:getId(), workpiece_actual:getId())");
		script.append("\n");
		script.append(
				"system:addLink('" + LINK_IS_PART_OF + "', workpiece_actual:getId(), cylinderSurface_new:getId())");
		script.append("\n");
		script.append("system:addLink('" + LINK_IS_DIAMETER_REQUIREMENT
				+ "', cylinderSurface_new:getId(), requirement_r_actual:getId())");
		script.append("\n");
		script.append("system:getLink('" + LINK_IS_DIAMETER_REQUIREMENT
				+ "', requirement_r_actual:getId(), nil):setId2(cylinderSurface_new:getId())");
		script.append("\n");
		script.append("system:addLink('" + LINK_IS_LENGTH_REQUIREMENT + "', cylinderSurface_new:getId(), nil)");
		script.append("\n");
		script.append("system:addLink('" + LINK_SURFACE_SIDE_LEFT
				+ "', cylinderSurface_new:getId(), cylinderSurface_actual:getId())");
		script.append("\n");
		script.append("system:addLink('" + LINK_SURFACE_SIDE_LEFT
				+ "', cylinderSurface_actual:getId(), cylinderSurface_new:getId())");
		script.append("\n");
		script.append("system:addLink('" + LINK_SURFACE_SIDE_RIGHT + "', cylinderSurface_new:getId(), nil)");
		script.append("\n");
		script.append("cylinderSurface_actual:getAttribute('" + ATTRIBUTE_LENGTH + "'):setValue(lengthRequired)");
		script.append("\n");

		action.registerParameterUpdater(new LuaScriptActionFunction(globals, script.toString()));

		return new SystemTransformation(ELEMENT_SPLIT_CYLINDER_SURFACE, action, systemTemplate, transformations);
	}

	// TODO (2022-12-07 #73): пересмотреть положение globals
	static Globals globals = JsePlatform.standardGlobals();

	public static SystemTransformations getSystemTransformations() {
		SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(cutCylinderSurface());
		systemTransformations.add(trimCylinderSurface());
		systemTransformations.add(splitCylinderSurface());
		return systemTransformations;
	}

	public static System initialSystem() {
		final System system = new System();

		final SystemObject workpiece = new SystemObject(OBJECT_WORKPIECE, ID_OBJECT_WORKPIECE);
		workpiece.addAttribute(new Attribute(ATTRIBUTE_WORKPIECE, true));
		system.addObject(workpiece);

		final SystemObject cylinderSurface = new SystemObject(OBJECT_CYLINDER_SURFACE, ID_OBJECT_CYLINDER_SURFACE);
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, Integer.valueOf(22)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_LENGTH, Integer.valueOf(90)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, false));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_LENGTH_REQUIREMENT, false));
		system.addObject(cylinderSurface);

		final SystemObject requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A,
				ID_OBJECT_REQUIREMENT_SURFACE_A);
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(20)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(45)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		system.addObject(requirement_a);

		final SystemObject requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B,
				ID_OBJECT_REQUIREMENT_SURFACE_B);
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(16)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(30)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		system.addObject(requirement_b);

		final SystemObject requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C,
				ID_OBJECT_REQUIREMENT_SURFACE_C);
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(12)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(15)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		system.addObject(requirement_c);

		system.addLink(workpiece, LINK_IS_PART_OF, cylinderSurface);
		system.addLink(workpiece, LINK_IS_REQUIREMENT_OF, requirement_a);
		system.addLink(workpiece, LINK_IS_REQUIREMENT_OF, requirement_b);
		system.addLink(workpiece, LINK_IS_REQUIREMENT_OF, requirement_c);

		system.addLink(cylinderSurface, LINK_IS_DIAMETER_REQUIREMENT, null);
		system.addLink(cylinderSurface, LINK_IS_LENGTH_REQUIREMENT, null);

		system.addLink(requirement_a, LINK_IS_DIAMETER_REQUIREMENT, null);
		system.addLink(requirement_a, LINK_IS_LENGTH_REQUIREMENT, null);
		system.addLink(requirement_a, LINK_SURFACE_SIDE_LEFT, null);
		system.addLink(requirement_a, LINK_SURFACE_SIDE_RIGHT, LINK_SURFACE_SIDE_LEFT, requirement_b);

		system.addLink(requirement_b, LINK_IS_DIAMETER_REQUIREMENT, null);
		system.addLink(requirement_b, LINK_IS_LENGTH_REQUIREMENT, null);
		system.addLink(requirement_b, LINK_SURFACE_SIDE_RIGHT, LINK_SURFACE_SIDE_LEFT, requirement_c);

		system.addLink(requirement_c, LINK_IS_DIAMETER_REQUIREMENT, null);
		system.addLink(requirement_c, LINK_IS_LENGTH_REQUIREMENT, null);
		system.addLink(requirement_c, LINK_SURFACE_SIDE_RIGHT, null);

		return system;
	}

	public static System finalSystem() {
		final System system = new System();

		final SystemObject workpiece = new SystemObject(OBJECT_WORKPIECE, ID_OBJECT_WORKPIECE);
		workpiece.addAttribute(new Attribute(ATTRIBUTE_WORKPIECE, true));
		system.addObject(workpiece);

		final SystemObject requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A,
				ID_OBJECT_REQUIREMENT_SURFACE_A);
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		system.addObject(requirement_a);

		final SystemObject requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B,
				ID_OBJECT_REQUIREMENT_SURFACE_B);
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		system.addObject(requirement_b);

		final SystemObject requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C,
				ID_OBJECT_REQUIREMENT_SURFACE_C);
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		system.addObject(requirement_c);

		system.addLink(requirement_a, LINK_IS_REQUIREMENT_OF, workpiece);
		system.addLink(requirement_a, LINK_SURFACE_SIDE_LEFT, null);
		system.addLink(requirement_a, LINK_SURFACE_SIDE_RIGHT, LINK_SURFACE_SIDE_LEFT, requirement_b);

		system.addLink(requirement_b, LINK_IS_REQUIREMENT_OF, workpiece);
		system.addLink(requirement_b, LINK_SURFACE_SIDE_RIGHT, LINK_SURFACE_SIDE_LEFT, requirement_c);

		system.addLink(requirement_c, LINK_IS_REQUIREMENT_OF, workpiece);
		system.addLink(requirement_c, LINK_SURFACE_SIDE_RIGHT, null);

		return system;
	}

	public static TaskDescription getTaskDescription() {
		TaskDescription taskDescription = new TaskDescription();
		taskDescription.setInitialSystem(initialSystem());
		taskDescription.setFinalSystem(finalSystem());
		return taskDescription;
	}

	// TODO (2020-07-30 #30): включить проверку copy-paste
	// CPD-ON
}
