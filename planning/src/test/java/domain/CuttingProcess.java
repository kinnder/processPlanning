package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

	private static final String OBJECT_PRODUCT = "изделие";

	private static final String OBJECT_CYLINDER_SURFACE_A = "цилиндрическая поверхность a";

	private static final String OBJECT_CYLINDER_SURFACE_B = "цилиндрическая поверхность b";

	private static final String OBJECT_CYLINDER_SURFACE_C = "цилиндрическая поверхность c";

	private static final String OBJECT_SURFACE_L = "торцевая поверхность слева";

	private static final String OBJECT_SURFACE_R = "торцевая поверхность справа";

	private static final String OBJECT_REQUIREMENT = "требование";

	private static final String LINK_IS_PART_OF = "является частью";

	private static final String LINK_IS_REQUIREMENT_OF = "является требованием";

	private static final String LINK_RIGHT_SIDE_SURFACE = "поверхность справа";

	private static final String LINK_LEFT_SIDE_SURFACE = "поверхность слева";

	private static final String ATTRIBUTE_DIAMETER = "диаметр";

	private static final String ATTRIBUTE_DIAMETER_REQUIREMENT = "требования диаметра";

	private static final String ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS = "состояние требования диаметра";

	private static final String ATTRIBUTE_LENGTH = "длина";

	private static final String ATTRIBUTE_WORKPIECE = "заготовка";

	private static final String ATTRIBUTE_PRODUCT = "изделие";

	private static final String ATTRIBUTE_CYLINDER_SURFACE = "цилиндрическая поверхность";

	private static final String ATTRIBUTE_SIDE_SURFACE = "торцевая поверхность";

	private static final String ATTRIBUTE_REQUIREMENT = "требование";

	private static final String OPERATION_CUT_CYLINDER_SURFACE = "Точить цилиндрическую поверхность";

	private static final String OPERATION_SPLIT_CYLINDER_SURFACE = "Разделить цилиндрическую поверхность";

	private static final String PARAMETER_DIAMETER_DELTA = "разница диаметров";

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
		final SystemObjectTemplate cylinderSurface_l = new SystemObjectTemplate("#CYLINDER_SURFACE_L");
		final SystemObjectTemplate cylinderSurface_r = new SystemObjectTemplate("#CYLINDER_SURFACE_R");

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(workpiece);
		template.addObjectTemplate(cylinderSurface_l);
		template.addObjectTemplate(cylinderSurface_r);

		final String workpiece_id = workpiece.getId();
		final String cylinderSurface_l_id = cylinderSurface_l.getId();
		final String cylinderSurface_r_id = cylinderSurface_r.getId();

		workpiece.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, cylinderSurface_l_id));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, cylinderSurface_r_id));

		// TODO определить шаблоны для поверхностей

		cylinderSurface_l.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface_l.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER));
		cylinderSurface_l.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT));
		cylinderSurface_l.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, workpiece_id));

		cylinderSurface_r.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface_r.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER));
		cylinderSurface_r.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIREMENT));
		cylinderSurface_r.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, workpiece_id));

		// TODO определить трансформации

		final Transformation transformations[] = new Transformation[] {};

		// TODO определить действие

		final Action action = new Action(OPERATION_SPLIT_CYLINDER_SURFACE);

		return new Element(action, template, transformations);
	}

	@Test
	public void applicationOfElements() {
		final SystemObject workpiece = new SystemObject(OBJECT_WORKPIECE);
		final SystemObject cylinderSurface = new SystemObject(OBJECT_CYLINDER_SURFACE_A);
		final SystemObject requirement = new SystemObject(OBJECT_REQUIREMENT);

		System system = new System();
		system.addObject(workpiece);
		system.addObject(cylinderSurface);
		system.addObject(requirement);

		final String workpiece_id = workpiece.getId();
		final String cylinderSurface_id = cylinderSurface.getId();
		final String requirement_id = requirement.getId();

		workpiece.addAttribute(new Attribute(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLink(new Link(LINK_IS_PART_OF, cylinderSurface_id));
		workpiece.addLink(new Link(LINK_IS_REQUIREMENT_OF, requirement_id));

		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, new Integer(22)));
		cylinderSurface.addLink(new Link(LINK_IS_PART_OF, workpiece_id));

		requirement.addAttribute(new Attribute(ATTRIBUTE_REQUIREMENT, true));
		requirement.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT, new Integer(20)));
		requirement.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS, VALUE_NOT_ACHIEVED));

		Element element;
		SystemVariant[] systemVariants;

		element = cutCylinderSurface();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals("2", systemVariants[0].getAction().getParameter(PARAMETER_DIAMETER_DELTA));

		system = systemVariants[0].getSystem();
		assertEquals(system.getObjectById(requirement_id).getAttribute(ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS)
				.getValueAsString(), VALUE_ACHIEVED);

		// TODO добавить проверку элемента
	}

	@Test
	public void cuttingProcessForCylindricWorkpiece() {
		final SystemObject product = new SystemObject(OBJECT_PRODUCT);
		final SystemObject cylinderSurface_a = new SystemObject(OBJECT_CYLINDER_SURFACE_A);
		final SystemObject cylinderSurface_b = new SystemObject(OBJECT_CYLINDER_SURFACE_B);
		final SystemObject cylinderSurface_c = new SystemObject(OBJECT_CYLINDER_SURFACE_C);

		System system = new System();
		system.addObject(product);
		system.addObject(cylinderSurface_a);
		system.addObject(cylinderSurface_b);
		system.addObject(cylinderSurface_c);

		final String product_id = product.getId();
		final String cylinderSurface_a_id = cylinderSurface_a.getId();
		final String cylinderSurface_b_id = cylinderSurface_b.getId();
		final String cylinderSurface_c_id = cylinderSurface_c.getId();

		product.addAttribute(new Attribute(ATTRIBUTE_PRODUCT, true));
		product.addLink(new Link(LINK_IS_PART_OF, cylinderSurface_a_id));
		product.addLink(new Link(LINK_IS_PART_OF, cylinderSurface_b_id));
		product.addLink(new Link(LINK_IS_PART_OF, cylinderSurface_c_id));

		cylinderSurface_a.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface_a.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, new Integer(20)));
		cylinderSurface_a.addAttribute(new Attribute(ATTRIBUTE_LENGTH, new Integer(20)));
		cylinderSurface_a.addLink(new Link(LINK_IS_PART_OF, product_id));
		cylinderSurface_a.addLink(new Link(LINK_RIGHT_SIDE_SURFACE, cylinderSurface_b_id));

		cylinderSurface_b.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface_b.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, new Integer(16)));
		cylinderSurface_b.addAttribute(new Attribute(ATTRIBUTE_LENGTH, new Integer(20)));
		cylinderSurface_b.addLink(new Link(LINK_IS_PART_OF, product_id));
		cylinderSurface_b.addLink(new Link(LINK_RIGHT_SIDE_SURFACE, cylinderSurface_c_id));
		cylinderSurface_b.addLink(new Link(LINK_LEFT_SIDE_SURFACE, cylinderSurface_a_id));

		cylinderSurface_c.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface_c.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, new Integer(12)));
		cylinderSurface_c.addAttribute(new Attribute(ATTRIBUTE_LENGTH, new Integer(20)));
		cylinderSurface_c.addLink(new Link(LINK_IS_PART_OF, product_id));
		cylinderSurface_c.addLink(new Link(LINK_LEFT_SIDE_SURFACE, cylinderSurface_b_id));
	}
}
