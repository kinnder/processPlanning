package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.Attribute;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.SystemTransformation;
import planning.model.Link;
import planning.model.LinkTemplate;
import planning.model.LinkTransformation;
import planning.model.LuaScriptActionPreConditionChecker;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemObjectTemplate;
import planning.model.SystemOperation;
import planning.model.SystemTemplate;
import planning.model.SystemVariant;
import planning.model.Transformation;

public class CuttingProcess {

	private static final String OBJECT_WORKPIECE = "заготовка";

	private static final String OBJECT_CYLINDER_SURFACE = "цилиндрическая поверхность";

	private static final String OBJECT_REQUIREMENT_SURFACE_A = "требование к поверхности a";

	private static final String OBJECT_REQUIREMENT_SURFACE_B = "требование к поверхности b";

	private static final String OBJECT_REQUIREMENT_SURFACE_C = "требование к поверхности c";

	private static final String LINK_IS_PART_OF = "является частью";

	private static final String LINK_IS_REQUIREMENT_OF = "является требованием";

	private static final String LINK_IS_DIAMETER_REQUIREMENT = "является требованием диаметра";

	private static final String LINK_IS_LENGTH_REQUIREMENT = "является требованием длины";

	private static final String LINK_SURFACE_SIDE_RIGHT = "сторона поверхности справа";

	private static final String LINK_SURFACE_SIDE_LEFT = "сторона поверхности слева";

	private static final String ATTRIBUTE_DIAMETER = "диаметр";

	private static final String ATTRIBUTE_DIAMETER_REQUIREMENT = "требование диаметра";

	private static final String ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS = "состояние требования диаметра";

	private static final String ATTRIBUTE_HAS_DIAMETER_REQUIREMENT = "есть требование диаметра";

	private static final String ATTRIBUTE_LENGTH_REQUIREMENT = "требование длины";

	private static final String ATTRIBUTE_LENGTH_REQUIREMENT_STATUS = "состояние требования длины";

	private static final String ATTRIBUTE_HAS_LENGTH_REQUIREMENT = "есть требование длины";

	private static final String ATTRIBUTE_LENGTH = "длина";

	private static final String ATTRIBUTE_WORKPIECE = "заготовка";

	private static final String ATTRIBUTE_CYLINDER_SURFACE = "цилиндрическая поверхность";

	private static final String ATTRIBUTE_REQUIREMENT = "требование";

	private static final String OPERATION_CUT_CYLINDER_SURFACE = "Точить цилиндрическую поверхность";

	private static final String OPERATION_SPLIT_CYLINDER_SURFACE = "Разделить цилиндрическую поверхность";

	private static final String OPERATION_TRIM_CYLINDER_SURFACE = "Подрезать цилиндрическую поверхность";

	private static final String PARAMETER_DIAMETER_DELTA = "разница диаметров";

	private static final String PARAMETER_LENGTH_DELTA = "разница длин";

	private static final String ELEMENT_CUT_CYLINDER_SURFACE = "cutCylinderSurface";

	private static final String ELEMENT_TRIM_CYLINDER_SURFACE = "trimCylinderSurface";

	private static final String ELEMENT_SPLIT_CYLINDER_SURFACE = "splitCylinderSurface";

	private static final String ID_WORKPIECE = "#WORKPIECE";

	private static final String ID_CYLINDER_SURFACE = "#CYLINDER-SURFACE";

	private static final String ID_REQUIREMENT = "#REQUIREMENT";

	private static final String ID_REQUIREMENT_L = "#REQUIREMENT-L";

	private static final String ID_REQUIREMENT_R = "#REQUIREMENT-R";

	private static Globals globals = JsePlatform.standardGlobals();

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
		action.registerPreConditionChecker(new LuaScriptActionPreConditionChecker(globals, script.toString()));

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

		action.registerParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

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
		action.registerPreConditionChecker(new LuaScriptActionPreConditionChecker(globals, script.toString()));

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
		action.registerPreConditionChecker(new LuaScriptActionPreConditionChecker(globals, script.toString()));

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
		// TODO : добавить трансформацию по добавлению объекта
		script.append("local cylinderSurface_new = luajava.newInstance('planning.model.SystemObject', '"
				+ OBJECT_CYLINDER_SURFACE + "')");
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
		script.append("systemVariant:getSystem():addObject(cylinderSurface_new)");
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

		action.registerParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(ELEMENT_SPLIT_CYLINDER_SURFACE, action, template, transformations);
	}

	private static SystemTransformations cuttingProcessTransformations;

	@BeforeAll
	public static void setupAll() {
		cuttingProcessTransformations = new SystemTransformations();
		cuttingProcessTransformations.addElement(cutCylinderSurface());
		cuttingProcessTransformations.addElement(trimCylinderSurface());
		cuttingProcessTransformations.addElement(splitCylinderSurface());
	}

	@Test
	public void applicationOfSystemTransformations() throws CloneNotSupportedException {
		final SystemObject workpiece = new SystemObject(OBJECT_WORKPIECE);
		final SystemObject cylinderSurface = new SystemObject(OBJECT_CYLINDER_SURFACE);
		final SystemObject requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A);
		final SystemObject requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B);
		final SystemObject requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C);

		System system = new System();
		system.addObject(workpiece);
		system.addObject(cylinderSurface);
		system.addObject(requirement_a);
		system.addObject(requirement_b);
		system.addObject(requirement_c);

		final String workpiece_id = workpiece.getId();
		final String cylinderSurface_id = cylinderSurface.getId();
		final String requirement_a_id = requirement_a.getId();
		final String requirement_b_id = requirement_b.getId();
		final String requirement_c_id = requirement_c.getId();

		workpiece.addAttribute(new Attribute(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLink(new Link(LINK_IS_PART_OF, cylinderSurface_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_a_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_b_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_c_id));

		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, Integer.valueOf(22)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_LENGTH, Integer.valueOf(90)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, false));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_LENGTH_REQUIREMENT, false));
		cylinderSurface.addLink(new Link(LINK_IS_PART_OF, workpiece_id));
		cylinderSurface.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		cylinderSurface.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));

		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(20)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(45)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_a.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_a.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_a.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_LEFT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_b_id));

		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(16)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(30)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_b.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_b.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_b.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_a_id));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_c_id));

		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(12)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(15)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_c.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_c.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_c.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_b_id));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, null));

		SystemTransformation systemTransformation;
		SystemVariant[] systemVariants;

		//
		systemTransformation = cuttingProcessTransformations.getElement(ELEMENT_CUT_CYLINDER_SURFACE);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(3, systemVariants.length);
		int id;
		for (id = 0; id < 3; id++) {
			if (systemVariants[id].getActionParameter(PARAMETER_DIAMETER_DELTA).equals("2")) {
				break;
			}
		}
		assertNotEquals(3, id);
		assertEquals("2", systemVariants[id].getActionParameter(PARAMETER_DIAMETER_DELTA));

		system = systemVariants[id].getSystem();
		assertEquals(system.getObjectById(requirement_a_id).getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);
		assertEquals(system.getObjectById(cylinderSurface_id).getAttribute(ATTRIBUTE_DIAMETER).getValueAsInteger(),
				Integer.valueOf(20));
		assertNotNull(system.getObjectById(cylinderSurface_id).getLink(LINK_IS_DIAMETER_REQUIREMENT, requirement_a_id));

		//
		systemTransformation = cuttingProcessTransformations.getElement(ELEMENT_SPLIT_CYLINDER_SURFACE);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(2, systemVariants.length);
		for (id = 0; id < 2; id++) {
			if (systemVariants[id].getActionParameter(PARAMETER_DIAMETER_DELTA).equals("4")) {
				break;
			}
		}
		assertNotEquals(2, id);
		assertEquals("4", systemVariants[id].getActionParameter(PARAMETER_DIAMETER_DELTA));
		assertEquals("45", systemVariants[id].getActionParameter(PARAMETER_LENGTH_DELTA));

		system = systemVariants[id].getSystem();
		assertEquals(system.getObjectById(requirement_a_id).getAttribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);
		assertEquals(system.getObjectById(cylinderSurface_id).getAttribute(ATTRIBUTE_LENGTH).getValueAsInteger(),
				Integer.valueOf(45));
		assertNotNull(system.getObjectById(cylinderSurface_id).getLink(LINK_IS_LENGTH_REQUIREMENT, requirement_a_id));
		assertEquals(system.getObjectById(requirement_b_id).getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);

		assertEquals(6, system.getObjects().size());

		//
		systemTransformation = cuttingProcessTransformations.getElement(ELEMENT_SPLIT_CYLINDER_SURFACE);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals("4", systemVariants[0].getActionParameter(PARAMETER_DIAMETER_DELTA));
		assertEquals("15", systemVariants[0].getActionParameter(PARAMETER_LENGTH_DELTA));

		system = systemVariants[0].getSystem();
		assertEquals(system.getObjectById(requirement_b_id).getAttribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);
		assertEquals(system.getObjectById(requirement_c_id).getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);

		//
		systemTransformation = cuttingProcessTransformations.getElement(ELEMENT_TRIM_CYLINDER_SURFACE);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertEquals(system.getObjectById(requirement_c_id).getAttribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS)
				.getValueAsBoolean(), true);
	}

	@Test
	public void cuttingProcessForCylindricWorkpiece() throws CloneNotSupportedException {
		final SystemObject workpiece = new SystemObject(OBJECT_WORKPIECE);
		final SystemObject cylinderSurface = new SystemObject(OBJECT_CYLINDER_SURFACE);
		final SystemObject requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A);
		final SystemObject requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B);
		final SystemObject requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C);

		System system = new System();
		system.addObject(workpiece);
		system.addObject(cylinderSurface);
		system.addObject(requirement_a);
		system.addObject(requirement_b);
		system.addObject(requirement_c);

		final String workpiece_id = workpiece.getId();
		final String cylinderSurface_id = cylinderSurface.getId();
		final String requirement_a_id = requirement_a.getId();
		final String requirement_b_id = requirement_b.getId();
		final String requirement_c_id = requirement_c.getId();

		workpiece.addAttribute(new Attribute(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLink(new Link(LINK_IS_PART_OF, cylinderSurface_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_a_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_b_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_c_id));

		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, Integer.valueOf(22)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_LENGTH, Integer.valueOf(90)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_DIAMETER_REQUIREMENT, false));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_HAS_LENGTH_REQUIREMENT, false));
		cylinderSurface.addLink(new Link(LINK_IS_PART_OF, workpiece_id));
		cylinderSurface.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		cylinderSurface.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));

		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(20)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(45)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_a.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_a.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_a.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_LEFT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_b_id));

		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(16)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(30)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_b.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_b.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_b.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_a_id));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_c_id));

		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, Integer.valueOf(12)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, false));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, Integer.valueOf(15)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, false));
		requirement_c.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_c.addLink(new Link(LINK_IS_DIAMETER_REQUIREMENT, null));
		requirement_c.addLink(new Link(LINK_IS_LENGTH_REQUIREMENT, null));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_b_id));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, null));

		// TODO : финальный вариант системы необходимо задавать комбинацией Template
		// классов
		final System final_system = new System();
		final SystemObject final_requirement_a = new SystemObject(OBJECT_REQUIREMENT_SURFACE_A, requirement_a_id);
		final_requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		final_requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		final_requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		final_requirement_a.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		final_requirement_a.addLink(new Link(LINK_SURFACE_SIDE_LEFT, null));
		final_requirement_a.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_b_id));
		final SystemObject final_requirement_b = new SystemObject(OBJECT_REQUIREMENT_SURFACE_B, requirement_b_id);
		final_requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		final_requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		final_requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		final_requirement_b.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		final_requirement_b.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_a_id));
		final_requirement_b.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_c_id));
		final SystemObject final_requirement_c = new SystemObject(OBJECT_REQUIREMENT_SURFACE_C, requirement_c_id);
		final_requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		final_requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, true));
		final_requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, true));
		final_requirement_c.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		final_requirement_c.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_b_id));
		final_requirement_c.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, null));
		final_system.addObject(final_requirement_a);
		final_system.addObject(final_requirement_b);
		final_system.addObject(final_requirement_c);

		assertFalse(system.equals(final_system));
		assertFalse(system.contains(final_system));

		Planner planner = new Planner(system, final_system, cuttingProcessTransformations.getElements());
		planner.plan();

		List<SystemOperation> operations = planner.getShortestPlan();
		assertEquals(4, operations.size());

		SystemOperation operation;
		operation = operations.get(0);
		assertEquals(OPERATION_CUT_CYLINDER_SURFACE, operation.getName());
		assertEquals("2", operation.getParameter(PARAMETER_DIAMETER_DELTA));

		operation = operations.get(1);
		assertEquals(OPERATION_SPLIT_CYLINDER_SURFACE, operation.getName());
		assertEquals("4", operation.getParameter(PARAMETER_DIAMETER_DELTA));
		assertEquals("45", operation.getParameter(PARAMETER_LENGTH_DELTA));

		operation = operations.get(2);
		assertEquals(OPERATION_SPLIT_CYLINDER_SURFACE, operation.getName());
		assertEquals("4", operation.getParameter(PARAMETER_DIAMETER_DELTA));
		assertEquals("15", operation.getParameter(PARAMETER_LENGTH_DELTA));

		operation = operations.get(3);
		assertEquals(OPERATION_TRIM_CYLINDER_SURFACE, operation.getName());
	}
}
