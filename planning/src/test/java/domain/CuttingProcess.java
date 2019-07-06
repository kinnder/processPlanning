package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import planning.model.Action;
import planning.model.Attribute;
import planning.model.AttributeTemplate;
import planning.model.AttributeTransformation;
import planning.model.ConditionChecker;
import planning.model.Element;
import planning.model.IdsMatching;
import planning.model.Link;
import planning.model.LinkTemplate;
import planning.model.ParameterUpdater;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemObjectTemplate;
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

	private static final String LINK_SURFACE_SIDE_RIGHT = "сторона поверхности справа";

	private static final String LINK_SURFACE_SIDE_LEFT = "сторона поверхности слева";

	private static final String ATTRIBUTE_DIAMETER = "диаметр";

	private static final String ATTRIBUTE_DIAMETER_REQUIREMENT = "требование диаметра";

	private static final String ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS = "состояние требования диаметра";

	private static final String ATTRIBUTE_LENGTH_REQUIREMENT = "требование длины";

	private static final String ATTRIBUTE_LENGTH_REQUIREMENT_STATUS = "состояние требования длины";

	private static final String ATTRIBUTE_LENGTH = "длина";

	private static final String ATTRIBUTE_WORKPIECE = "заготовка";

	private static final String ATTRIBUTE_CYLINDER_SURFACE = "цилиндрическая поверхность";

	private static final String ATTRIBUTE_REQUIREMENT = "требование";

	private static final String OPERATION_CUT_CYLINDER_SURFACE = "Точить цилиндрическую поверхность";

	private static final String OPERATION_SPLIT_CYLINDER_SURFACE = "Разделить цилиндрическую поверхность";

	private static final String PARAMETER_DIAMETER_DELTA = "разница диаметров";

	private static final String PARAMETER_LENGTH_DELTA = "разница длин";

	private static final String VALUE_NOT_ACHIEVED = "активно";

	private static final String VALUE_ACHIEVED = "выполнено";

	public static Element cutCylinderSurface() {
		final SystemObjectTemplate workpiece = new SystemObjectTemplate("#WORKPIECE");
		final SystemObjectTemplate cylinderSurface = new SystemObjectTemplate("#CYLINDER-SURFACE");
		final SystemObjectTemplate requirement = new SystemObjectTemplate("#REQUIREMENT");

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(workpiece);
		template.addObjectTemplate(cylinderSurface);
		template.addObjectTemplate(requirement);

		final String workpiece_id = workpiece.getId();
		final String cylinderSurface_id = cylinderSurface.getId();
		final String requirement_id = requirement.getId();

		workpiece.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, cylinderSurface_id));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, requirement_id));

		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER));
		cylinderSurface.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, workpiece_id));

		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_REQUIREMENT, true));
		requirement.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT));
		requirement
				.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, workpiece_id));

		final Transformation transformations[] = new Transformation[] {
				new AttributeTransformation(requirement_id, ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, VALUE_ACHIEVED) };

		final Action action = new Action(OPERATION_CUT_CYLINDER_SURFACE);
		action.registerConditionChecker(new ConditionChecker() {
			@Override
			public boolean invoke(System system, IdsMatching idsMatching, Map<String, String> parameters) {
				// TODO : перенести общий функционал в базовый класс
				String cylinderSurface_id_actual = idsMatching.get(cylinderSurface_id);
				SystemObject cylinderSurface_actual = system.getObjectById(cylinderSurface_id_actual);
				Integer diameter = cylinderSurface_actual.getAttribute(ATTRIBUTE_DIAMETER).getValueAsInteger();

				String requirement_id_actual = idsMatching.get(requirement_id);
				SystemObject requirement_actual = system.getObjectById(requirement_id_actual);
				Integer diameterRequired = requirement_actual.getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT)
						.getValueAsInteger();

				return diameter > diameterRequired;
			}
		});
		action.registerParameterUpdater(new ParameterUpdater() {
			@Override
			public void invoke(System system, IdsMatching idsMatching, Map<String, String> parameters) {
				// TODO : перенести общий функционал в базовый класс
				String cylinderSurface_id_actual = idsMatching.get(cylinderSurface_id);
				SystemObject cylinderSurface_actual = system.getObjectById(cylinderSurface_id_actual);
				Integer diameter = cylinderSurface_actual.getAttribute(ATTRIBUTE_DIAMETER).getValueAsInteger();

				String requirement_id_actual = idsMatching.get(requirement_id);
				SystemObject requirement_actual = system.getObjectById(requirement_id_actual);
				Integer diameterRequired = requirement_actual.getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT)
						.getValueAsInteger();

				Integer diameterDelta = diameter - diameterRequired;
				parameters.put(PARAMETER_DIAMETER_DELTA, Integer.toString(diameterDelta));

				cylinderSurface_actual.getAttribute(ATTRIBUTE_DIAMETER).setValue(diameterRequired);
			}
		});

		return new Element(action, template, transformations);
	}

	public static Element splitCylinderSurface() {
		final SystemObjectTemplate workpiece = new SystemObjectTemplate("#WORKPIECE");
		final SystemObjectTemplate cylinderSurface = new SystemObjectTemplate("#CYLINDER-SURFACE");
		final SystemObjectTemplate requirement_l = new SystemObjectTemplate("#REQUIREMENT-L");
		final SystemObjectTemplate requirement_r = new SystemObjectTemplate("#REQUIREMENT-R");

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(workpiece);
		template.addObjectTemplate(cylinderSurface);
		template.addObjectTemplate(requirement_l);
		template.addObjectTemplate(requirement_r);

		final String workpiece_id = workpiece.getId();
		final String cylinderSurface_id = cylinderSurface.getId();
		final String requirement_l_id = requirement_l.getId();
		final String requirement_r_id = requirement_r.getId();

		workpiece.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, cylinderSurface_id));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, requirement_l_id));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, requirement_r_id));

		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH));
		cylinderSurface.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, workpiece_id));

		requirement_l.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_REQUIREMENT, true));
		requirement_l.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH_REQUIREMENT));
		requirement_l
				.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_l.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_l.addLinkTemplate(new LinkTemplate(LINK_SURFACE_SIDE_RIGHT, requirement_r_id));

		requirement_r.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_REQUIREMENT, true));
		requirement_r.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT));
		requirement_r
				.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_r.addLinkTemplate(new LinkTemplate(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_r.addLinkTemplate(new LinkTemplate(LINK_SURFACE_SIDE_LEFT, requirement_l_id));

		final Transformation transformations[] = new Transformation[] {
				new AttributeTransformation(requirement_l_id, ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, VALUE_ACHIEVED),
				new AttributeTransformation(requirement_r_id, ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, VALUE_ACHIEVED) };

		final Action action = new Action(OPERATION_SPLIT_CYLINDER_SURFACE);
		action.registerConditionChecker(new ConditionChecker() {
			@Override
			public boolean invoke(System system, IdsMatching idsMatching, Map<String, String> parameters) {
				// TODO : перенести общий функционал в базовый класс
				String cylinderSurface_id_actual = idsMatching.get(cylinderSurface_id);
				SystemObject cylinderSurface_actual = system.getObjectById(cylinderSurface_id_actual);
				Integer diameter = cylinderSurface_actual.getAttribute(ATTRIBUTE_DIAMETER).getValueAsInteger();
				Integer length = cylinderSurface_actual.getAttribute(ATTRIBUTE_LENGTH).getValueAsInteger();

				String requirement_l_id_actual = idsMatching.get(requirement_l_id);
				SystemObject requirement_l_actual = system.getObjectById(requirement_l_id_actual);
				Integer lengthRequired = requirement_l_actual.getAttribute(ATTRIBUTE_LENGTH_REQUIREMENT)
						.getValueAsInteger();

				String requirement_r_id_actual = idsMatching.get(requirement_r_id);
				SystemObject requirement_r_actual = system.getObjectById(requirement_r_id_actual);
				Integer diameterRequired = requirement_r_actual.getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT)
						.getValueAsInteger();

				return (diameter > diameterRequired) && (length > lengthRequired);
			}
		});
		action.registerParameterUpdater(new ParameterUpdater() {
			@Override
			public void invoke(System system, IdsMatching idsMatching, Map<String, String> parameters) {
				// TODO : перенести общий функционал в базовый класс
				String cylinderSurface_id_actual = idsMatching.get(cylinderSurface_id);
				SystemObject cylinderSurface_actual = system.getObjectById(cylinderSurface_id_actual);
				Integer diameter = cylinderSurface_actual.getAttribute(ATTRIBUTE_DIAMETER).getValueAsInteger();
				Integer length = cylinderSurface_actual.getAttribute(ATTRIBUTE_LENGTH).getValueAsInteger();

				String requirement_l_id_actual = idsMatching.get(requirement_l_id);
				SystemObject requirement_l_actual = system.getObjectById(requirement_l_id_actual);
				Integer lengthRequired = requirement_l_actual.getAttribute(ATTRIBUTE_LENGTH_REQUIREMENT)
						.getValueAsInteger();

				String requirement_r_id_actual = idsMatching.get(requirement_r_id);
				SystemObject requirement_r_actual = system.getObjectById(requirement_r_id_actual);
				Integer diameterRequired = requirement_r_actual.getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT)
						.getValueAsInteger();

				Integer diameterDelta = diameter - diameterRequired;
				parameters.put(PARAMETER_DIAMETER_DELTA, Integer.toString(diameterDelta));

				Integer lengthDelta = length - lengthRequired;
				parameters.put(PARAMETER_LENGTH_DELTA, Integer.toString(lengthDelta));

				// TODO : добавить трансформацию по добавлению объекта
				SystemObject cylinderSurface_new = new SystemObject(OBJECT_CYLINDER_SURFACE);
				cylinderSurface_new.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
				cylinderSurface_new.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, diameterRequired));
				cylinderSurface_new.addAttribute(new Attribute(ATTRIBUTE_LENGTH, lengthDelta));
				cylinderSurface_new.addLink(new Link(LINK_IS_PART_OF, workpiece_id));
				cylinderSurface_new.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, cylinderSurface_id_actual));
				system.addObject(cylinderSurface_new);

				cylinderSurface_actual.getAttribute(ATTRIBUTE_LENGTH).setValue(lengthRequired);
				cylinderSurface_actual.addLink(new Link(LINK_SURFACE_SIDE_LEFT, cylinderSurface_new.getId()));
			}
		});

		return new Element(action, template, transformations);
	}

	@Test
	public void applicationOfElements() {
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
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, new Integer(22)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_LENGTH, new Integer(90)));
		cylinderSurface.addLink(new Link(LINK_IS_PART_OF, workpiece_id));

		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, new Integer(20)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, new Integer(45)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_a.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_LEFT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_b_id));

		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, new Integer(16)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, new Integer(30)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_b.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_a_id));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_c_id));

		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, new Integer(12)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, new Integer(15)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_c.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_b_id));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, null));

		Element element;
		SystemVariant[] systemVariants;

		element = cutCylinderSurface();
		systemVariants = element.applyTo(system);
		assertEquals(3, systemVariants.length);
		int id;
		for (id = 0; id < 3; id++) {
			if (systemVariants[id].getAction().getParameter(PARAMETER_DIAMETER_DELTA).equals("2")) {
				break;
			}
		}
		assertNotEquals(3, id);
		assertEquals("2", systemVariants[id].getAction().getParameter(PARAMETER_DIAMETER_DELTA));

		system = systemVariants[id].getSystem();
		assertEquals(system.getObjectById(requirement_a_id).getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS)
				.getValueAsString(), VALUE_ACHIEVED);
		assertEquals(system.getObjectById(cylinderSurface_id).getAttribute(ATTRIBUTE_DIAMETER).getValueAsInteger(),
				new Integer(20));

		element = splitCylinderSurface();
		systemVariants = element.applyTo(system);
		assertEquals(2, systemVariants.length);
		for (id = 0; id < 2; id++) {
			if (systemVariants[id].getAction().getParameter(PARAMETER_DIAMETER_DELTA).equals("4")) {
				break;
			}
		}
		assertNotEquals(2, id);
		assertEquals("4", systemVariants[id].getAction().getParameter(PARAMETER_DIAMETER_DELTA));
		assertEquals("45", systemVariants[id].getAction().getParameter(PARAMETER_LENGTH_DELTA));

		system = systemVariants[id].getSystem();
		assertEquals(system.getObjectById(requirement_a_id).getAttribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS)
				.getValueAsString(), VALUE_ACHIEVED);
		assertEquals(system.getObjectById(cylinderSurface_id).getAttribute(ATTRIBUTE_LENGTH).getValueAsInteger(),
				new Integer(45));
		assertEquals(system.getObjectById(requirement_b_id).getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS)
				.getValueAsString(), VALUE_ACHIEVED);

		assertEquals(6, system.getObjects().size());
	}

	@Test
	public void cuttingProcessForCylindricWorkpiece() {
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
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, new Integer(22)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_LENGTH, new Integer(90)));
		cylinderSurface.addLink(new Link(LINK_IS_PART_OF, workpiece_id));

		requirement_a.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, new Integer(20)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, new Integer(45)));
		requirement_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_a.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_LEFT, null));
		requirement_a.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_b_id));

		requirement_b.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, new Integer(16)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, new Integer(30)));
		requirement_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_b.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_b_id));
		requirement_b.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, requirement_c_id));

		requirement_c.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, new Integer(12)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT, new Integer(15)));
		requirement_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));
		requirement_c.addLink(new Link(LINK_IS_REQUIREMENT_OF, workpiece_id));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_LEFT, requirement_b_id));
		requirement_c.addLink(new Link(LINK_SURFACE_SIDE_RIGHT, null));
	}
}
