package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.method.Planner;
import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.Attribute;
import planning.model.AttributeTemplate;
import planning.model.SystemTransformation;
import planning.model.Link;
import planning.model.LinkTemplate;
import planning.model.LinkTransformation;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemObjectTemplate;
import planning.model.SystemOperation;
import planning.model.SystemTemplate;
import planning.model.SystemVariant;
import planning.model.Transformation;
import planning.storage.SystemTransformationsXMLFile;

public class AssemblyLine {

	private static final String OBJECT_PICK_AND_PLACE_ROBOT = "Робот-перекладчик";

	private static final String OBJECT_PACKAGE_BOX = "Тара";

	private static final String OBJECT_SHUTTLE = "Шаттл";

	private static final String OBJECT_TABLE_1 = "Стол 1";

	private static final String OBJECT_TABLE_2 = "Стол 2";

	private static final String OBJECT_PLANE_X_TABLE_1 = "плоскость X стол 1";

	private static final String OBJECT_PLANE_X_TABLE_2 = "плоскость X стол 2";

	private static final String OBJECT_PLANE_Y_OUTSIDE = "плоскость Y снаружи станции";

	private static final String OBJECT_PLANE_Y_INSIDE = "плоскоть Y внутри станции";

	private static final String OBJECT_PLANE_Z_TOP = "плоскость Z вверх";

	private static final String OBJECT_PLANE_Z_BOTTOM = "плоскость Z низ";

	private static final String ATTRIBUTE_PICK_AND_PLACE_ROBOT = "Робот-перекладчик";

	private static final String ATTRIBUTE_PACKAGE = "Тара";

	private static final String ATTRIBUTE_PACKAGE_BOX_POSITION = "Место тары";

	private static final String ATTRIBUTE_PLANE_X = "Плоскость X";

	private static final String ATTRIBUTE_PLANE_Y = "Плоскость Y";

	private static final String ATTRIBUTE_PLANE_Z = "Плоскость Z";

	private static final String PARAMETER_TARGET = "цель";

	private static final String LINK_GRAB_POSITION = "положение захвата";

	private static final String LINK_ROTARY_DRIVE_POSITION = "положение поворотного привода";

	private static final String LINK_LINEAR_DRIVE_POSITION = "положение линейного привода";

	private static final String LINK_VERTICAL_DRIVE_POSITION = "положение вертикального привода";

	private static final String LINK_PACKAGE_BOX_POSITION = "место тары";

	private static final String LINK_PLANE_X_POSITION = "положение в плоскости X";

	private static final String LINK_PLANE_Y_POSITION = "положение в плоскости Y";

	private static final String LINK_PLANE_Z_POSITION = "положение в плоскости Z";

	private static final String OPERATION_TURN_WITHOUT_LOAD = "Повернуть без нагрузки";

	private static final String OPERATION_TURN_WITH_LOAD = "Повернуть c нагрузкой";

	private static final String OPERATION_OPEN_GRAB = "Открыть захват";

	private static final String OPERATION_CLOSE_GRAB = "Закрыть захват";

	private static final String OPERATION_LIFT_UP = "Поднять вверх";

	private static final String OPERATION_LOWER_DOWN = "Опустить вниз";

	private static final String OPERATION_MOVE_WITH_LOAD = "Переместить с нагрузкой";

	private static final String OPERATION_MOVE_WITHOUT_LOAD = "Переместить без нагрузки";

	private static final String ELEMENT_TURN_WITHOUT_LOAD = "turnWithoutLoad";

	private static final String ELEMENT_TURN_WITH_LOAD = "turnWithLoad";

	private static final String ELEMENT_OPEN_GRAB = "openGrab";

	private static final String ELEMENT_CLOSE_GRAB = "closeGrab";

	private static final String ELEMENT_LIFT_UP = "liftUp";

	private static final String ELEMENT_LOWER_DOWN = "lowerDown";

	private static final String ELEMENT_MOVE_WITH_LOAD = "moveWithLoad";

	private static final String ELEMENT_MOVE_WITHOUT_LOAD = "moveWithoutLoad";

	private static final String ID_ROBOT = "#ROBOT";

	private static final String ID_PLANE_X_TARGET = "#PLANE-X-TARGET";

	private static final String ID_PLANE_X_SOURCE = "#PLANE-X-SOURCE";

	private static final String ID_PLANE_Y_TARGET = "#PLANE-Y-TARGET";

	private static final String ID_PLANE_Y_SOURCE = "#PLANE-Y-SOURCE";

	private static final String ID_PACKAGE_BOX = "#PACKAGE-BOX";

	private static final String ID_PLANE_Y = "#PLANE-Y";

	private static final String ID_PLANE_X = "#PLANE-X";

	private static final String ID_PLANE_Z = "#PLANE-Z";

	private static final String ID_PACKAGE_BOX_POSITION = "#PACKAGE-BOX-POSITION";

	private static final String ID_PLANE_Z_TOP = "#PLANE-Z-TOP";

	private static final String ID_PLANE_Z_BOTTOM = "#PLANE-Z-BOTTOM";

	private static Globals globals = JsePlatform.standardGlobals();

	public static SystemTransformation turnWithoutLoad() {
		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_ROBOT);
		final SystemObjectTemplate plane_y_target = new SystemObjectTemplate(ID_PLANE_Y_TARGET);
		final SystemObjectTemplate plane_y_source = new SystemObjectTemplate(ID_PLANE_Y_SOURCE);

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, ID_PLANE_Y_SOURCE));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, null));

		plane_y_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y_source.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, ID_ROBOT));

		plane_y_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y_target.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, null));

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_y_target);
		template.addObjectTemplate(plane_y_source);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_ROBOT, LINK_ROTARY_DRIVE_POSITION, ID_PLANE_Y_SOURCE, ID_PLANE_Y_TARGET),
				new LinkTransformation(ID_PLANE_Y_TARGET, LINK_ROTARY_DRIVE_POSITION, null, ID_ROBOT),
				new LinkTransformation(ID_PLANE_Y_SOURCE, LINK_ROTARY_DRIVE_POSITION, ID_ROBOT, null) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_PLANE_Y_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");
		script.append("\n");

		final Action action = new Action(OPERATION_TURN_WITHOUT_LOAD);
		action.registerParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(ELEMENT_TURN_WITHOUT_LOAD, action, template, transformations);
	}

	public static SystemTransformation turnWithLoad() {
		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_ROBOT);
		final SystemObjectTemplate plane_y_target = new SystemObjectTemplate(ID_PLANE_Y_TARGET);
		final SystemObjectTemplate plane_y_source = new SystemObjectTemplate(ID_PLANE_Y_SOURCE);
		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_PACKAGE_BOX);

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, ID_PLANE_Y_SOURCE));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, ID_PACKAGE_BOX));

		plane_y_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y_source.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, ID_ROBOT));

		plane_y_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y_target.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, null));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, ID_ROBOT));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, null));

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_y_target);
		template.addObjectTemplate(plane_y_source);
		template.addObjectTemplate(packageBox);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_ROBOT, LINK_ROTARY_DRIVE_POSITION, ID_PLANE_Y_SOURCE, ID_PLANE_Y_TARGET),
				new LinkTransformation(ID_PLANE_Y_SOURCE, LINK_ROTARY_DRIVE_POSITION, ID_ROBOT, null),
				new LinkTransformation(ID_PLANE_Y_TARGET, LINK_ROTARY_DRIVE_POSITION, null, ID_ROBOT) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_PLANE_Y_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_TURN_WITH_LOAD);
		action.registerParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(ELEMENT_TURN_WITH_LOAD, action, template, transformations);
	}

	public static SystemTransformation openGrab() {
		final SystemTemplate template = new SystemTemplate();
		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_ROBOT);
		final SystemObjectTemplate plane_y = new SystemObjectTemplate(ID_PLANE_Y);
		final SystemObjectTemplate plane_x = new SystemObjectTemplate(ID_PLANE_X);
		final SystemObjectTemplate plane_z = new SystemObjectTemplate(ID_PLANE_Z);
		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_PACKAGE_BOX);
		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate(ID_PACKAGE_BOX_POSITION);

		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_y);
		template.addObjectTemplate(plane_x);
		template.addObjectTemplate(plane_z);
		template.addObjectTemplate(packageBox);
		template.addObjectTemplate(packageBoxPosition);

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, ID_PACKAGE_BOX));
		robot.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, ID_PLANE_X));
		robot.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, ID_PLANE_Z));
		robot.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, ID_PLANE_Y));

		plane_x.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, ID_ROBOT));

		plane_y.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, ID_ROBOT));

		plane_z.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, ID_ROBOT));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, ID_ROBOT));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, ID_PACKAGE_BOX_POSITION));

		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_X_POSITION, ID_PLANE_X));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_Y_POSITION, ID_PLANE_Y));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_Z_POSITION, ID_PLANE_Z));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, ID_PACKAGE_BOX));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_ROBOT, LINK_GRAB_POSITION, ID_PACKAGE_BOX, null),
				new LinkTransformation(ID_PACKAGE_BOX, LINK_GRAB_POSITION, ID_ROBOT, null) };

		final Action action = new Action(OPERATION_OPEN_GRAB);

		return new SystemTransformation(ELEMENT_OPEN_GRAB, action, template, transformations);
	}

	public static SystemTransformation closeGrab() {
		final SystemTemplate template = new SystemTemplate();
		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_ROBOT);
		final SystemObjectTemplate plane_y = new SystemObjectTemplate(ID_PLANE_Y);
		final SystemObjectTemplate plane_x = new SystemObjectTemplate(ID_PLANE_X);
		final SystemObjectTemplate plane_z = new SystemObjectTemplate(ID_PLANE_Z);
		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_PACKAGE_BOX);
		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate(ID_PACKAGE_BOX_POSITION);

		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_y);
		template.addObjectTemplate(plane_x);
		template.addObjectTemplate(plane_z);
		template.addObjectTemplate(packageBox);
		template.addObjectTemplate(packageBoxPosition);

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, null));
		robot.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, ID_PLANE_X));
		robot.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, ID_PLANE_Z));
		robot.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, ID_PLANE_Y));

		plane_x.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, ID_ROBOT));

		plane_y.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, ID_ROBOT));

		plane_z.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, ID_ROBOT));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, null));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, ID_PACKAGE_BOX_POSITION));

		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_X_POSITION, ID_PLANE_X));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_Y_POSITION, ID_PLANE_Y));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_Z_POSITION, ID_PLANE_Z));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, ID_PACKAGE_BOX));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_ROBOT, LINK_GRAB_POSITION, null, ID_PACKAGE_BOX),
				new LinkTransformation(ID_PACKAGE_BOX, LINK_GRAB_POSITION, null, ID_ROBOT) };

		final Action action = new Action(OPERATION_CLOSE_GRAB);

		return new SystemTransformation(ELEMENT_CLOSE_GRAB, action, template, transformations);
	}

	public static SystemTransformation liftUp() {
		final SystemTemplate template = new SystemTemplate();
		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_ROBOT);
		final SystemObjectTemplate plane_z_top = new SystemObjectTemplate(ID_PLANE_Z_TOP);
		final SystemObjectTemplate plane_z_bottom = new SystemObjectTemplate(ID_PLANE_Z_BOTTOM);
		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_PACKAGE_BOX);
		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate(ID_PACKAGE_BOX_POSITION);

		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_z_top);
		template.addObjectTemplate(plane_z_bottom);
		template.addObjectTemplate(packageBox);
		template.addObjectTemplate(packageBoxPosition);

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, ID_PLANE_Z_BOTTOM));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, ID_PACKAGE_BOX));

		plane_z_top.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, null));

		plane_z_bottom.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, ID_ROBOT));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, ID_ROBOT));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, ID_PACKAGE_BOX_POSITION));

		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, ID_PACKAGE_BOX));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_PACKAGE_BOX, LINK_PACKAGE_BOX_POSITION, ID_PACKAGE_BOX_POSITION, null),
				new LinkTransformation(ID_PACKAGE_BOX_POSITION, LINK_PACKAGE_BOX_POSITION, ID_PACKAGE_BOX, null),
				new LinkTransformation(ID_ROBOT, LINK_VERTICAL_DRIVE_POSITION, ID_PLANE_Z_BOTTOM, ID_PLANE_Z_TOP),
				new LinkTransformation(ID_PLANE_Z_TOP, LINK_VERTICAL_DRIVE_POSITION, null, ID_ROBOT),
				new LinkTransformation(ID_PLANE_Z_BOTTOM, LINK_VERTICAL_DRIVE_POSITION, ID_ROBOT, null) };

		final Action action = new Action(OPERATION_LIFT_UP);

		return new SystemTransformation(ELEMENT_LIFT_UP, action, template, transformations);
	}

	public static SystemTransformation lowerDown() {
		final SystemTemplate template = new SystemTemplate();
		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_ROBOT);
		final SystemObjectTemplate plane_y = new SystemObjectTemplate(ID_PLANE_Y);
		final SystemObjectTemplate plane_x = new SystemObjectTemplate(ID_PLANE_X);
		final SystemObjectTemplate plane_z_top = new SystemObjectTemplate(ID_PLANE_Z_TOP);
		final SystemObjectTemplate plane_z_bottom = new SystemObjectTemplate(ID_PLANE_Z_BOTTOM);
		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_PACKAGE_BOX);
		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate(ID_PACKAGE_BOX_POSITION);

		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_x);
		template.addObjectTemplate(plane_y);
		template.addObjectTemplate(plane_z_top);
		template.addObjectTemplate(plane_z_bottom);
		template.addObjectTemplate(packageBox);
		template.addObjectTemplate(packageBoxPosition);

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, ID_PLANE_X));
		robot.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, ID_PLANE_Z_TOP));
		robot.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, ID_PLANE_Y));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, ID_PACKAGE_BOX));

		plane_x.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, ID_ROBOT));
		plane_x.addLinkTemplate(new LinkTemplate(LINK_PLANE_X_POSITION, ID_PACKAGE_BOX_POSITION));

		plane_y.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, ID_ROBOT));
		plane_y.addLinkTemplate(new LinkTemplate(LINK_PLANE_Y_POSITION, ID_PACKAGE_BOX_POSITION));

		plane_z_top.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, ID_ROBOT));

		plane_z_bottom.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, null));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, ID_ROBOT));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, null));

		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_X_POSITION, ID_PLANE_X));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_Y_POSITION, ID_PLANE_Y));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, null));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_PACKAGE_BOX, LINK_PACKAGE_BOX_POSITION, null, ID_PACKAGE_BOX_POSITION),
				new LinkTransformation(ID_PACKAGE_BOX_POSITION, LINK_PACKAGE_BOX_POSITION, null, ID_PACKAGE_BOX),
				new LinkTransformation(ID_ROBOT, LINK_VERTICAL_DRIVE_POSITION, ID_PLANE_Z_TOP, ID_PLANE_Z_BOTTOM),
				new LinkTransformation(ID_PLANE_Z_TOP, LINK_VERTICAL_DRIVE_POSITION, ID_ROBOT, null),
				new LinkTransformation(ID_PLANE_Z_BOTTOM, LINK_VERTICAL_DRIVE_POSITION, null, ID_ROBOT) };

		final Action action = new Action(OPERATION_LOWER_DOWN);

		return new SystemTransformation(ELEMENT_LOWER_DOWN, action, template, transformations);
	}

	public static SystemTransformation moveWithLoad() {
		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_ROBOT);
		final SystemObjectTemplate plane_x_target = new SystemObjectTemplate(ID_PLANE_X_TARGET);
		final SystemObjectTemplate plane_x_source = new SystemObjectTemplate(ID_PLANE_X_SOURCE);
		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_PACKAGE_BOX);

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, ID_PLANE_X_SOURCE));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, ID_PACKAGE_BOX));

		plane_x_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x_target.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, null));

		plane_x_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x_source.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, ID_ROBOT));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, ID_ROBOT));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, null));

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_x_target);
		template.addObjectTemplate(plane_x_source);
		template.addObjectTemplate(packageBox);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_ROBOT, LINK_LINEAR_DRIVE_POSITION, ID_PLANE_X_SOURCE, ID_PLANE_X_TARGET),
				new LinkTransformation(ID_PLANE_X_TARGET, LINK_LINEAR_DRIVE_POSITION, null, ID_ROBOT),
				new LinkTransformation(ID_PLANE_X_SOURCE, LINK_LINEAR_DRIVE_POSITION, ID_ROBOT, null) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_PLANE_X_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_MOVE_WITH_LOAD);
		action.registerParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(ELEMENT_MOVE_WITH_LOAD, action, template, transformations);
	}

	public static SystemTransformation moveWithoutLoad() {
		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_ROBOT);
		final SystemObjectTemplate plane_x_target = new SystemObjectTemplate(ID_PLANE_X_TARGET);
		final SystemObjectTemplate plane_x_source = new SystemObjectTemplate(ID_PLANE_X_SOURCE);

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, ID_PLANE_X_SOURCE));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, null));

		plane_x_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x_target.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, null));

		plane_x_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x_source.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, ID_ROBOT));

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_x_target);
		template.addObjectTemplate(plane_x_source);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_ROBOT, LINK_LINEAR_DRIVE_POSITION, ID_PLANE_X_SOURCE, ID_PLANE_X_TARGET),
				new LinkTransformation(ID_PLANE_X_SOURCE, LINK_LINEAR_DRIVE_POSITION, ID_ROBOT, null),
				new LinkTransformation(ID_PLANE_X_TARGET, LINK_LINEAR_DRIVE_POSITION, null, ID_ROBOT) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_PLANE_X_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_MOVE_WITHOUT_LOAD);
		action.registerParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(ELEMENT_MOVE_WITHOUT_LOAD, action, template, transformations);
	}

	private static SystemTransformations assemblyLineTransformations;

	@BeforeAll
	public static void setupAll() throws JDOMException, IOException {
		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.load(AssemblyLine.class.getResource("/assemblyLine/systemTransformations.xml"));

		assemblyLineTransformations = new SystemTransformations();
		// TODO : удалить + удалить затрагиваемый код
		assemblyLineTransformations.addElement(turnWithoutLoad());
		assemblyLineTransformations.addElement(turnWithLoad());
		assemblyLineTransformations.addElement(openGrab());
		assemblyLineTransformations.addElement(closeGrab());
		assemblyLineTransformations.addElement(liftUp());
		assemblyLineTransformations.addElement(lowerDown());
		assemblyLineTransformations.addElement(moveWithLoad());
		assemblyLineTransformations.addElement(moveWithoutLoad());

		// TODO : включить
		// assemblyLineTransformations.addElements(xmlFile.getElements());
	}

	@Test
	public void applicationOfSystemTransformations() throws CloneNotSupportedException {
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT);
		final SystemObject plane_x_table_1 = new SystemObject(OBJECT_PLANE_X_TABLE_1);
		final SystemObject plane_x_table_2 = new SystemObject(OBJECT_PLANE_X_TABLE_2);
		final SystemObject plane_y_outside = new SystemObject(OBJECT_PLANE_Y_OUTSIDE);
		final SystemObject plane_y_inside = new SystemObject(OBJECT_PLANE_Y_INSIDE);
		final SystemObject plane_z_top = new SystemObject(OBJECT_PLANE_Z_TOP);
		final SystemObject plane_z_bottom = new SystemObject(OBJECT_PLANE_Z_BOTTOM);
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX);
		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE);
		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1);
		final SystemObject table_2 = new SystemObject(OBJECT_TABLE_2);

		System system = new System();
		system.addObject(robot);
		system.addObject(plane_x_table_1);
		system.addObject(plane_x_table_2);
		system.addObject(plane_y_outside);
		system.addObject(plane_y_inside);
		system.addObject(plane_z_top);
		system.addObject(plane_z_bottom);
		system.addObject(packageBox);
		system.addObject(shuttle);
		system.addObject(table_1);
		system.addObject(table_2);

		final String robot_id = robot.getId();
		final String plane_x_table_1_id = plane_x_table_1.getId();
		final String plane_x_table_2_id = plane_x_table_2.getId();
		final String plane_y_outside_id = plane_y_outside.getId();
		final String plane_y_inside_id = plane_y_inside.getId();
		final String plane_z_top_id = plane_z_top.getId();
		final String plane_z_bottom_id = plane_z_bottom.getId();
		final String packageBox_id = packageBox.getId();
		final String shuttle_id = shuttle.getId();
		final String table_1_id = table_1.getId();
		final String table_2_id = table_2.getId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, plane_x_table_2_id));
		robot.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, plane_y_inside_id));
		robot.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom_id));
		robot.addLink(new Link(LINK_GRAB_POSITION, null));

		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_1.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, null));
		plane_x_table_1.addLink(new Link(LINK_PLANE_X_POSITION, table_1_id));

		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_2.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, robot_id));
		plane_x_table_2.addLink(new Link(LINK_PLANE_X_POSITION, table_2_id));
		plane_x_table_2.addLink(new Link(LINK_PLANE_X_POSITION, shuttle_id));

		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_inside.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, robot_id));
		plane_y_inside.addLink(new Link(LINK_PLANE_Y_POSITION, table_1_id));
		plane_y_inside.addLink(new Link(LINK_PLANE_Y_POSITION, table_2_id));

		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_outside.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, null));
		plane_y_outside.addLink(new Link(LINK_PLANE_Y_POSITION, shuttle_id));

		plane_z_top.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, null));

		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, robot_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, table_1_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, table_2_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, shuttle_id));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, shuttle_id));

		shuttle.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		shuttle.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		shuttle.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_outside_id));
		shuttle.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		shuttle.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_1.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_1_id));
		table_1.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_1.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		table_2.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_2.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		table_2.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_2.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_2.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		SystemTransformation systemTransformation;
		SystemVariant[] systemVariants;

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_TURN_WITHOUT_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_Y_OUTSIDE, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_ROTARY_DRIVE_POSITION, plane_y_outside_id));
		assertNotNull(system.getObjectById(plane_y_outside_id).getLink(LINK_ROTARY_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_y_inside_id).getLink(LINK_ROTARY_DRIVE_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_CLOSE_GRAB);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_GRAB_POSITION, packageBox_id));
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_GRAB_POSITION, robot_id));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_LIFT_UP);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_PACKAGE_BOX_POSITION, null));
		assertNotNull(system.getObjectById(shuttle_id).getLink(LINK_PACKAGE_BOX_POSITION, null));
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_VERTICAL_DRIVE_POSITION, plane_z_top_id));
		assertNotNull(system.getObjectById(plane_z_top_id).getLink(LINK_VERTICAL_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_z_bottom_id).getLink(LINK_VERTICAL_DRIVE_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_TURN_WITH_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_Y_INSIDE, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_ROTARY_DRIVE_POSITION, plane_y_inside_id));
		assertNotNull(system.getObjectById(plane_y_outside_id).getLink(LINK_ROTARY_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(plane_y_inside_id).getLink(LINK_ROTARY_DRIVE_POSITION, robot_id));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_MOVE_WITH_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_X_TABLE_1, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_LINEAR_DRIVE_POSITION, plane_x_table_1_id));
		assertNotNull(system.getObjectById(plane_x_table_1_id).getLink(LINK_LINEAR_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_x_table_2_id).getLink(LINK_LINEAR_DRIVE_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_LOWER_DOWN);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_PACKAGE_BOX_POSITION, table_1_id));
		assertNotNull(system.getObjectById(table_1_id).getLink(LINK_PACKAGE_BOX_POSITION, packageBox_id));
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom_id));
		assertNotNull(system.getObjectById(plane_z_top_id).getLink(LINK_VERTICAL_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(plane_z_bottom_id).getLink(LINK_VERTICAL_DRIVE_POSITION, robot_id));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_OPEN_GRAB);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_GRAB_POSITION, null));
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_GRAB_POSITION, null));

		systemTransformation = assemblyLineTransformations.getElement(ELEMENT_MOVE_WITHOUT_LOAD);
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_X_TABLE_2, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_LINEAR_DRIVE_POSITION, plane_x_table_2_id));
		assertNotNull(system.getObjectById(plane_x_table_1_id).getLink(LINK_LINEAR_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(plane_x_table_2_id).getLink(LINK_LINEAR_DRIVE_POSITION, robot_id));
	}

	@Test
	public void movePackageBoxToTable1() throws CloneNotSupportedException {
		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT);
		final SystemObject plane_x_table_1 = new SystemObject(OBJECT_PLANE_X_TABLE_1);
		final SystemObject plane_x_table_2 = new SystemObject(OBJECT_PLANE_X_TABLE_2);
		final SystemObject plane_y_outside = new SystemObject(OBJECT_PLANE_Y_OUTSIDE);
		final SystemObject plane_y_inside = new SystemObject(OBJECT_PLANE_Y_INSIDE);
		final SystemObject plane_z_top = new SystemObject(OBJECT_PLANE_Z_TOP);
		final SystemObject plane_z_bottom = new SystemObject(OBJECT_PLANE_Z_BOTTOM);
		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX);
		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE);
		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1);
		final SystemObject table_2 = new SystemObject(OBJECT_TABLE_2);

		System system = new System();
		system.addObject(robot);
		system.addObject(plane_x_table_1);
		system.addObject(plane_x_table_2);
		system.addObject(plane_y_outside);
		system.addObject(plane_y_inside);
		system.addObject(plane_z_top);
		system.addObject(plane_z_bottom);
		system.addObject(packageBox);
		system.addObject(shuttle);
		system.addObject(table_1);
		system.addObject(table_2);

		final String robot_id = robot.getId();
		final String plane_x_table_1_id = plane_x_table_1.getId();
		final String plane_x_table_2_id = plane_x_table_2.getId();
		final String plane_y_outside_id = plane_y_outside.getId();
		final String plane_y_inside_id = plane_y_inside.getId();
		final String plane_z_bottom_id = plane_z_bottom.getId();
		final String packageBox_id = packageBox.getId();
		final String shuttle_id = shuttle.getId();
		final String table_1_id = table_1.getId();
		final String table_2_id = table_2.getId();

		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, plane_x_table_2_id));
		robot.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, plane_y_inside_id));
		robot.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom_id));
		robot.addLink(new Link(LINK_GRAB_POSITION, null));

		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_1.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, null));
		plane_x_table_1.addLink(new Link(LINK_PLANE_X_POSITION, table_1_id));

		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		plane_x_table_2.addLink(new Link(LINK_LINEAR_DRIVE_POSITION, robot_id));
		plane_x_table_2.addLink(new Link(LINK_PLANE_X_POSITION, table_2_id));
		plane_x_table_2.addLink(new Link(LINK_PLANE_X_POSITION, shuttle_id));

		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_inside.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, robot_id));
		plane_y_inside.addLink(new Link(LINK_PLANE_Y_POSITION, table_1_id));
		plane_y_inside.addLink(new Link(LINK_PLANE_Y_POSITION, table_2_id));

		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		plane_y_outside.addLink(new Link(LINK_ROTARY_DRIVE_POSITION, null));
		plane_y_outside.addLink(new Link(LINK_PLANE_Y_POSITION, shuttle_id));

		plane_z_top.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, null));

		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLink(new Link(LINK_VERTICAL_DRIVE_POSITION, robot_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, table_1_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, table_2_id));
		plane_z_bottom.addLink(new Link(LINK_PLANE_Z_POSITION, shuttle_id));

		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, shuttle_id));

		shuttle.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		shuttle.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		shuttle.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_outside_id));
		shuttle.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		shuttle.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_1.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_1_id));
		table_1.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_1.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		table_2.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		table_2.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_2_id));
		table_2.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		table_2.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		table_2.addLink(new Link(LINK_PACKAGE_BOX_POSITION, null));

		SystemVariant[] systemVariants;
		systemVariants = assemblyLineTransformations.getElement(ELEMENT_TURN_WITHOUT_LOAD).applyTo(system);
		assertEquals(1, systemVariants.length);

		final System final_system = new System();
		final SystemObject final_packageBox = new SystemObject(OBJECT_PACKAGE_BOX, packageBox_id);
		final_packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		final_packageBox.addLink(new Link(LINK_GRAB_POSITION, null));
		final_packageBox.addLink(new Link(LINK_PACKAGE_BOX_POSITION, table_1_id));
		final SystemObject final_robot = robot.clone();
		final SystemObject final_table_1 = new SystemObject(OBJECT_TABLE_1, table_1_id);
		final_table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		final_table_1.addLink(new Link(LINK_PLANE_X_POSITION, plane_x_table_1_id));
		final_table_1.addLink(new Link(LINK_PLANE_Y_POSITION, plane_y_inside_id));
		final_table_1.addLink(new Link(LINK_PLANE_Z_POSITION, plane_z_bottom_id));
		final_table_1.addLink(new Link(LINK_PACKAGE_BOX_POSITION, packageBox_id));
		final_system.addObject(final_packageBox);
		final_system.addObject(final_robot);
		final_system.addObject(final_table_1);

		assertFalse(system.equals(final_system));
		assertFalse(system.contains(final_system));

		Planner planner = new Planner(system, final_system, assemblyLineTransformations.getElements());
		planner.plan();

		List<SystemOperation> operations = planner.getShortestPlan();
		assertEquals(8, operations.size());

		SystemOperation operation;
		operation = operations.get(0);
		assertEquals(OPERATION_TURN_WITHOUT_LOAD, operation.getName());
		assertEquals(OBJECT_PLANE_Y_OUTSIDE, operation.getParameter(PARAMETER_TARGET));

		operation = operations.get(1);
		assertEquals(OPERATION_CLOSE_GRAB, operation.getName());

		operation = operations.get(2);
		assertEquals(OPERATION_LIFT_UP, operation.getName());

		operation = operations.get(3);
		assertEquals(OPERATION_TURN_WITH_LOAD, operation.getName());
		assertEquals(OBJECT_PLANE_Y_INSIDE, operation.getParameter(PARAMETER_TARGET));

		operation = operations.get(4);
		assertEquals(OPERATION_MOVE_WITH_LOAD, operation.getName());
		assertEquals(OBJECT_PLANE_X_TABLE_1, operation.getParameter(PARAMETER_TARGET));

		operation = operations.get(5);
		assertEquals(OPERATION_LOWER_DOWN, operation.getName());

		operation = operations.get(6);
		assertEquals(OPERATION_OPEN_GRAB, operation.getName());

		operation = operations.get(7);
		assertEquals(OPERATION_MOVE_WITHOUT_LOAD, operation.getName());
		assertEquals(OBJECT_PLANE_X_TABLE_2, operation.getParameter(PARAMETER_TARGET));
	}
}
