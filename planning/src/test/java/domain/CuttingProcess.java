package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;

import org.junit.jupiter.api.Test;

import planning.model.Action;
import planning.model.Attribute;
import planning.model.AttributeTemplate;
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

	private static final String OBJECT_SURFACE_L = "торцевая поверхность слева";

	private static final String OBJECT_SURFACE_R = "торцевая поверхность справа";

	private static final String LINK_IS_PART_OF = "является частью";

	private static final String ATTRIBUTE_DIAMETER = "диаметр";

	private static final String ATTRIBUTE_DIAMETER_REQUIRED = "требуемый диаметр";

	private static final String ATTRIBUTE_WORKPIECE = "деталь";

	private static final String ATTRIBUTE_CYLINDER_SURFACE = "цилиндрическая поверхность";

	private static final String ATTRIBUTE_SIDE_SURFACE = "торцевая поверхность";

	private static final String OPERATION_TRIM_CYLINDER_SURFACE = "Точить цилиндрическую поверхность";

	private static final String PARAMETER_DIAMETER_DELTA = "разница диаметров";

	public static Element cutCylinderSurface() {
		final SystemObjectTemplate workpiece = new SystemObjectTemplate("#WORKPIECE");
		final SystemObjectTemplate cylinderSurface = new SystemObjectTemplate("#CYLINDER_SURFACE");

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(workpiece);
		template.addObjectTemplate(cylinderSurface);

		final String workpiece_id = workpiece.getId();
		final String cylinderSurface_id = cylinderSurface.getId();

		workpiece.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, cylinderSurface_id));

		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER));
		cylinderSurface.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_DIAMETER_REQUIRED));
		cylinderSurface.addLinkTemplate(new LinkTemplate(LINK_IS_PART_OF, workpiece_id));

		final Transformation transformations[] = new Transformation[] {};

		final Action action = new Action(OPERATION_TRIM_CYLINDER_SURFACE);
		action.registerParameterUpdater(new ParameterUpdater() {
			@Override
			public void invoke(System system, IdsMatching idsMatching, Map<String, String> parameters) {
				String objectId = idsMatching.get(cylinderSurface_id);
				SystemObject object = system.getObjectById(objectId);
				Integer diameter = object.getAttribute(ATTRIBUTE_DIAMETER).getValueAsInteger();
				Integer diameterRequired = object.getAttribute(ATTRIBUTE_DIAMETER_REQUIRED).getValueAsInteger();
				Integer diameterDelta = diameter - diameterRequired;
				parameters.put(PARAMETER_DIAMETER_DELTA, Integer.toString(diameterDelta));
				object.getAttribute(ATTRIBUTE_DIAMETER).setValue(diameterDelta);
			}
		});
		action.registerConditionChecker(new ConditionChecker() {
			@Override
			public boolean invoke(System system, IdsMatching idsMatching, Map<String, String> parameters) {
				String objectId = idsMatching.get(cylinderSurface_id);
				SystemObject object = system.getObjectById(objectId);
				Integer diameter = object.getAttribute(ATTRIBUTE_DIAMETER).getValueAsInteger();
				Integer diameterRequired = object.getAttribute(ATTRIBUTE_DIAMETER_REQUIRED).getValueAsInteger();
				return diameter > diameterRequired;
			}
		});

		return new Element(action, template, transformations);
	}

	@Test
	public void applicationOfElements() {
		final SystemObject workpiece = new SystemObject(OBJECT_WORKPIECE);
		final SystemObject cylinderSurface = new SystemObject(OBJECT_CYLINDER_SURFACE);
		final SystemObject sideSurface_l = new SystemObject(OBJECT_SURFACE_L);
		final SystemObject sideSurface_r = new SystemObject(OBJECT_SURFACE_R);

		System system = new System();
		system.addObject(workpiece);
		system.addObject(cylinderSurface);
		system.addObject(sideSurface_l);
		system.addObject(sideSurface_r);

		final String workpiece_id = workpiece.getId();
		final String cylinderSurface_id = cylinderSurface.getId();
		final String sideSurface_l_id = sideSurface_l.getId();
		final String sideSurface_r_id = sideSurface_r.getId();

		workpiece.addAttribute(new Attribute(ATTRIBUTE_WORKPIECE, true));
		workpiece.addLink(new Link(LINK_IS_PART_OF, cylinderSurface_id));
		workpiece.addLink(new Link(LINK_IS_PART_OF, sideSurface_l_id));
		workpiece.addLink(new Link(LINK_IS_PART_OF, sideSurface_r_id));

		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_CYLINDER_SURFACE, true));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER, new Integer(22)));
		cylinderSurface.addAttribute(new Attribute(ATTRIBUTE_DIAMETER_REQUIRED, new Integer(20)));
		cylinderSurface.addLink(new Link(LINK_IS_PART_OF, workpiece_id));

		sideSurface_l.addAttribute(new Attribute(ATTRIBUTE_SIDE_SURFACE, true));
		sideSurface_l.addLink(new Link(LINK_IS_PART_OF, workpiece_id));

		sideSurface_r.addAttribute(new Attribute(ATTRIBUTE_SIDE_SURFACE, true));
		sideSurface_r.addLink(new Link(LINK_IS_PART_OF, workpiece_id));

		Element element;
		SystemVariant[] systemVariants;

		element = cutCylinderSurface();
		systemVariants = element.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals("2", systemVariants[0].getAction().getParameter(PARAMETER_DIAMETER_DELTA));

		system = systemVariants[0].getSystem();
		// no transformations
	}
}
