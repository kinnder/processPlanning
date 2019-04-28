package domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import planning.model.Attribute;
import planning.model.Element;
import planning.model.Link;
import planning.model.LinkTransformation;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.Transformation;
import planning.model.AttributeTransformation;

public class MaterialPoints {

	public static Element moveRight() {
		System template = new System();

		SystemObject object = new SystemObject("материальная точка", "#ID-1");
		object.addLink(new Link("position", template, "#ID-2"));

		SystemObject point_A = new SystemObject("точка", "#ID-2");
		point_A.addAttribute(new Attribute("occupied", true));
		point_A.addLink(new Link("neighborOnTheRight", template, "#ID-3"));

		SystemObject point_B = new SystemObject("точка", "#ID-3");
		point_A.addAttribute(new Attribute("occupied", false));
		point_B.addLink(new Link("neighborOnTheLeft", template, "#ID-2"));

		template.addObject(object);
		template.addObject(point_A);
		template.addObject(point_B);

		Transformation transformations[] = new Transformation[] { new LinkTransformation("#ID-1", "position", "#ID-3"),
				new AttributeTransformation("#ID-2", "occupied", false),
				new AttributeTransformation("#ID-3", "occupied", true) };

		return new Element("Движение вправо", template, transformations);
	}

	@Test
	@Disabled
	public void applyElement_moveRight() {
		System initialSystem = new System();
		SystemObject initialSystem_object = new SystemObject("материальная точка");
		SystemObject initialSystem_point_A = new SystemObject("точка");
		SystemObject initialSystem_point_B = new SystemObject("точка");

		initialSystem_object.addLink(new Link("position", initialSystem, initialSystem_point_A.getObjectId()));

		initialSystem_point_A.addAttribute(new Attribute("occupied", true));
		initialSystem_point_A
				.addLink(new Link("neighborOnTheRight", initialSystem, initialSystem_point_B.getObjectId()));

		initialSystem_point_A.addAttribute(new Attribute("occupied", false));
		initialSystem_point_A
				.addLink(new Link("neighborOnTheLeft", initialSystem, initialSystem_point_A.getObjectId()));

		initialSystem.addObject(initialSystem_object);
		initialSystem.addObject(initialSystem_point_A);
		initialSystem.addObject(initialSystem_point_B);

		System expectedSystem = new System();
		SystemObject expectedSystem_object = new SystemObject("материальная точка");
		SystemObject expectedSystem_point_A = new SystemObject("точка");
		SystemObject expectedSystem_point_B = new SystemObject("точка");

		expectedSystem_object.addLink(new Link("position", expectedSystem, expectedSystem_point_B.getObjectId()));

		expectedSystem_point_A.addAttribute(new Attribute("occupied", false));
		expectedSystem_point_A
				.addLink(new Link("neighborOnTheRight", expectedSystem, expectedSystem_point_B.getObjectId()));

		expectedSystem_point_A.addAttribute(new Attribute("occupied", true));
		expectedSystem_point_A
				.addLink(new Link("neighborOnTheLeft", expectedSystem, expectedSystem_point_A.getObjectId()));

		expectedSystem.addObject(expectedSystem_object);
		expectedSystem.addObject(expectedSystem_point_A);
		expectedSystem.addObject(expectedSystem_point_B);

		System actualSystem = moveRight().applyTo(initialSystem);

		assertTrue(expectedSystem.equals(actualSystem));
	}
}
