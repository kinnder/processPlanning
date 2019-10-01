package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.method.Planner;
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

	private static final String OPERATION_ROTATE_WITHOUT_LOAD = "Повернуть без нагрузки";

	private static final String OPERATION_TURN_WITH_LOAD = "Повернуть c нагрузкой";

	private static final String OPERATION_OPEN_GRAB = "Открыть захват";

	private static final String OPERATION_CLOSE_GRAB = "Закрыть захват";

	private static final String OPERATION_LIFT_UP = "Поднять вверх";

	private static final String OPERATION_LOWER_DOWN = "Опустить вниз";

	private static final String OPERATION_MOVE_WITH_LOAD = "Переместить с нагрузкой";

	private static final String OPERATION_MOVE_WITHOUT_LOAD = "Переместить без нагрузки";

	private static Globals globals = JsePlatform.standardGlobals();

	public static SystemTransformation turnWithoutLoad() {
		final SystemObjectTemplate robot = new SystemObjectTemplate("#ROBOT");
		final SystemObjectTemplate plane_y_target = new SystemObjectTemplate("#PLANE-Y-TARGET");
		final SystemObjectTemplate plane_y_source = new SystemObjectTemplate("#PLANE-Y-SOURCE");

		final String robot_id = robot.getId();
		final String plane_y_target_id = plane_y_target.getId();
		final String plane_y_source_id = plane_y_source.getId();

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, plane_y_source_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, null));

		plane_y_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y_source.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, robot_id));

		plane_y_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y_target.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, null));

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_y_target);
		template.addObjectTemplate(plane_y_source);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_ROTARY_DRIVE_POSITION, plane_y_source_id, plane_y_target_id),
				new LinkTransformation(plane_y_target_id, LINK_ROTARY_DRIVE_POSITION, null, robot_id),
				new LinkTransformation(plane_y_source_id, LINK_ROTARY_DRIVE_POSITION, robot_id, null) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + plane_y_target_id + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");
		script.append("\n");

		final Action action = new Action(OPERATION_ROTATE_WITHOUT_LOAD);
		action.registerParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(action, template, transformations);
	}

	public static SystemTransformation turnWithLoad() {
		final SystemObjectTemplate robot = new SystemObjectTemplate("#ROBOT");
		final SystemObjectTemplate plane_y_target = new SystemObjectTemplate("#PLANE-Y-TARGET");
		final SystemObjectTemplate plane_y_source = new SystemObjectTemplate("#PLANE-Y-SOURCE");
		final SystemObjectTemplate packageBox = new SystemObjectTemplate("#PACKAGE-BOX");

		final String robot_id = robot.getId();
		final String plane_y_target_id = plane_y_target.getId();
		final String plane_y_source_id = plane_y_source.getId();
		final String packageBox_id = packageBox.getId();

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, plane_y_source_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, packageBox_id));

		plane_y_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y_source.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, robot_id));

		plane_y_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y_target.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, null));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, robot_id));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, null));

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_y_target);
		template.addObjectTemplate(plane_y_source);
		template.addObjectTemplate(packageBox);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_ROTARY_DRIVE_POSITION, plane_y_source_id, plane_y_target_id),
				new LinkTransformation(plane_y_source_id, LINK_ROTARY_DRIVE_POSITION, robot_id, null),
				new LinkTransformation(plane_y_target_id, LINK_ROTARY_DRIVE_POSITION, null, robot_id) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + plane_y_target_id + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_TURN_WITH_LOAD);
		action.registerParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(action, template, transformations);
	}

	public static SystemTransformation openGrab() {
		final SystemTemplate template = new SystemTemplate();
		final SystemObjectTemplate robot = new SystemObjectTemplate("#ROBOT");
		final SystemObjectTemplate plane_y = new SystemObjectTemplate("#PLANE-Y");
		final SystemObjectTemplate plane_x = new SystemObjectTemplate("#PLANE-X");
		final SystemObjectTemplate plane_z = new SystemObjectTemplate("#PLANE-Z");
		final SystemObjectTemplate packageBox = new SystemObjectTemplate("#PACKAGE-BOX");
		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate("#PACKAGE-PLACE");

		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_y);
		template.addObjectTemplate(plane_x);
		template.addObjectTemplate(plane_z);
		template.addObjectTemplate(packageBox);
		template.addObjectTemplate(packageBoxPosition);

		final String robot_id = robot.getId();
		final String plane_y_id = plane_y.getId();
		final String plane_x_id = plane_x.getId();
		final String plane_z_id = plane_z.getId();
		final String packageBox_id = packageBox.getId();
		final String packageBoxPosition_id = packageBoxPosition.getId();

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, packageBox_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, plane_x_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, plane_z_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, plane_y_id));

		plane_x.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, robot_id));

		plane_y.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, robot_id));

		plane_z.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, robot_id));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, robot_id));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, packageBoxPosition_id));

		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_X_POSITION, plane_x_id));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_Y_POSITION, plane_y_id));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_Z_POSITION, plane_z_id));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_GRAB_POSITION, packageBox_id, null),
				new LinkTransformation(packageBox_id, LINK_GRAB_POSITION, robot_id, null) };

		final Action action = new Action(OPERATION_OPEN_GRAB);

		return new SystemTransformation(action, template, transformations);
	}

	public static SystemTransformation closeGrab() {
		final SystemTemplate template = new SystemTemplate();
		final SystemObjectTemplate robot = new SystemObjectTemplate("#ROBOT");
		final SystemObjectTemplate plane_y = new SystemObjectTemplate("#PLANE-Y");
		final SystemObjectTemplate plane_x = new SystemObjectTemplate("#PLANE-X");
		final SystemObjectTemplate plane_z = new SystemObjectTemplate("#PLANE-Z");
		final SystemObjectTemplate packageBox = new SystemObjectTemplate("#PACKAGE-BOX");
		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate("#PACKAGE-PLACE");

		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_y);
		template.addObjectTemplate(plane_x);
		template.addObjectTemplate(plane_z);
		template.addObjectTemplate(packageBox);
		template.addObjectTemplate(packageBoxPosition);

		final String robot_id = robot.getId();
		final String plane_y_id = plane_y.getId();
		final String plane_x_id = plane_x.getId();
		final String plane_z_id = plane_z.getId();
		final String packageBox_id = packageBox.getId();
		final String packageBoxPosition_id = packageBoxPosition.getId();

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, null));
		robot.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, plane_x_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, plane_z_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, plane_y_id));

		plane_x.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, robot_id));

		plane_y.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, robot_id));

		plane_z.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, robot_id));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, null));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, packageBoxPosition_id));

		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_X_POSITION, plane_x_id));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_Y_POSITION, plane_y_id));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_Z_POSITION, plane_z_id));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_GRAB_POSITION, null, packageBox_id),
				new LinkTransformation(packageBox_id, LINK_GRAB_POSITION, null, robot_id) };

		final Action action = new Action(OPERATION_CLOSE_GRAB);

		return new SystemTransformation(action, template, transformations);
	}

	public static SystemTransformation liftUp() {
		final SystemTemplate template = new SystemTemplate();
		final SystemObjectTemplate robot = new SystemObjectTemplate("#ROBOT");
		final SystemObjectTemplate plane_z_top = new SystemObjectTemplate("#PLANE-Z-TOP");
		final SystemObjectTemplate plane_z_bottom = new SystemObjectTemplate("#PLANE-Z-BOTTOM");
		final SystemObjectTemplate packageBox = new SystemObjectTemplate("#PACKAGE-BOX");
		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate("#PACKAGE-PLACE");

		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_z_top);
		template.addObjectTemplate(plane_z_bottom);
		template.addObjectTemplate(packageBox);
		template.addObjectTemplate(packageBoxPosition);

		final String robot_id = robot.getId();
		final String plane_z_top_id = plane_z_top.getId();
		final String plane_z_bottom_id = plane_z_bottom.getId();
		final String packageBox_id = packageBox.getId();
		final String packageBoxPosition_id = packageBoxPosition.getId();

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, packageBox_id));

		plane_z_top.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, null));

		plane_z_bottom.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, robot_id));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, robot_id));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, packageBoxPosition_id));

		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, packageBox_id));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(packageBox_id, LINK_PACKAGE_BOX_POSITION, packageBoxPosition_id, null),
				new LinkTransformation(packageBoxPosition_id, LINK_PACKAGE_BOX_POSITION, packageBox_id, null),
				new LinkTransformation(robot_id, LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom_id, plane_z_top_id),
				new LinkTransformation(plane_z_top_id, LINK_VERTICAL_DRIVE_POSITION, null, robot_id),
				new LinkTransformation(plane_z_bottom_id, LINK_VERTICAL_DRIVE_POSITION, robot_id, null) };

		final Action action = new Action(OPERATION_LIFT_UP);

		return new SystemTransformation(action, template, transformations);
	}

	public static SystemTransformation lowerDown() {
		final SystemTemplate template = new SystemTemplate();
		final SystemObjectTemplate robot = new SystemObjectTemplate("#ROBOT");
		final SystemObjectTemplate plane_y = new SystemObjectTemplate("#PLANE-Y");
		final SystemObjectTemplate plane_x = new SystemObjectTemplate("#PLANE-X");
		final SystemObjectTemplate plane_z_top = new SystemObjectTemplate("#PLANE-Z-TOP");
		final SystemObjectTemplate plane_z_bottom = new SystemObjectTemplate("#PLANE-Z-BOTTOM");
		final SystemObjectTemplate packageBox = new SystemObjectTemplate("#PACKAGE-BOX");
		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate("#PACKAGE-PLACE");

		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_x);
		template.addObjectTemplate(plane_y);
		template.addObjectTemplate(plane_z_top);
		template.addObjectTemplate(plane_z_bottom);
		template.addObjectTemplate(packageBox);
		template.addObjectTemplate(packageBoxPosition);

		final String robot_id = robot.getId();
		final String plane_y_id = plane_y.getId();
		final String plane_x_id = plane_x.getId();
		final String plane_z_top_id = plane_z_top.getId();
		final String plane_z_bottom_id = plane_z_bottom.getId();
		final String packageBox_id = packageBox.getId();
		final String packageBoxPosition_id = packageBoxPosition.getId();

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, plane_x_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, plane_z_top_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, plane_y_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, packageBox_id));

		plane_x.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, robot_id));
		plane_x.addLinkTemplate(new LinkTemplate(LINK_PLANE_X_POSITION, packageBoxPosition_id));

		plane_y.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		plane_y.addLinkTemplate(new LinkTemplate(LINK_ROTARY_DRIVE_POSITION, robot_id));
		plane_y.addLinkTemplate(new LinkTemplate(LINK_PLANE_Y_POSITION, packageBoxPosition_id));

		plane_z_top.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z_top.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, robot_id));

		plane_z_bottom.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		plane_z_bottom.addLinkTemplate(new LinkTemplate(LINK_VERTICAL_DRIVE_POSITION, null));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, robot_id));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, null));

		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_X_POSITION, plane_x_id));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PLANE_Y_POSITION, plane_y_id));
		packageBoxPosition.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, null));

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(packageBox_id, LINK_PACKAGE_BOX_POSITION, null, packageBoxPosition_id),
				new LinkTransformation(packageBoxPosition_id, LINK_PACKAGE_BOX_POSITION, null, packageBox_id),
				new LinkTransformation(robot_id, LINK_VERTICAL_DRIVE_POSITION, plane_z_top_id, plane_z_bottom_id),
				new LinkTransformation(plane_z_top_id, LINK_VERTICAL_DRIVE_POSITION, robot_id, null),
				new LinkTransformation(plane_z_bottom_id, LINK_VERTICAL_DRIVE_POSITION, null, robot_id) };

		final Action action = new Action(OPERATION_LOWER_DOWN);

		return new SystemTransformation(action, template, transformations);
	}

	public static SystemTransformation moveWithLoad() {
		final SystemObjectTemplate robot = new SystemObjectTemplate("#ROBOT");
		final SystemObjectTemplate plane_x_target = new SystemObjectTemplate("#PLANE-X-TARGET");
		final SystemObjectTemplate plane_x_source = new SystemObjectTemplate("#PLANE-X-SOURCE");
		final SystemObjectTemplate packageBox = new SystemObjectTemplate("#PACKAGE-BOX");

		final String robot_id = robot.getId();
		final String plane_x_target_id = plane_x_target.getId();
		final String plane_x_source_id = plane_x_source.getId();
		final String packageBox_id = packageBox.getId();

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, plane_x_source_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, packageBox_id));

		plane_x_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x_target.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, null));

		plane_x_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x_source.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, robot_id));

		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, robot_id));
		packageBox.addLinkTemplate(new LinkTemplate(LINK_PACKAGE_BOX_POSITION, null));

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_x_target);
		template.addObjectTemplate(plane_x_source);
		template.addObjectTemplate(packageBox);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_LINEAR_DRIVE_POSITION, plane_x_source_id, plane_x_target_id),
				new LinkTransformation(plane_x_target_id, LINK_LINEAR_DRIVE_POSITION, null, robot_id),
				new LinkTransformation(plane_x_source_id, LINK_LINEAR_DRIVE_POSITION, robot_id, null) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + plane_x_target_id + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_MOVE_WITH_LOAD);
		action.registerParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(action, template, transformations);
	}

	public static SystemTransformation moveWithoutLoad() {
		final SystemObjectTemplate robot = new SystemObjectTemplate("#ROBOT");
		final SystemObjectTemplate plane_x_target = new SystemObjectTemplate("#PLANE-X-TARGET");
		final SystemObjectTemplate plane_x_source = new SystemObjectTemplate("#PLANE-X-SOURCE");

		final String robot_id = robot.getId();
		final String plane_x_target_id = plane_x_target.getId();
		final String plane_x_source_id = plane_x_source.getId();

		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		robot.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, plane_x_source_id));
		robot.addLinkTemplate(new LinkTemplate(LINK_GRAB_POSITION, null));

		plane_x_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x_target.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, null));

		plane_x_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		plane_x_source.addLinkTemplate(new LinkTemplate(LINK_LINEAR_DRIVE_POSITION, robot_id));

		final SystemTemplate template = new SystemTemplate();
		template.addObjectTemplate(robot);
		template.addObjectTemplate(plane_x_target);
		template.addObjectTemplate(plane_x_source);

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(robot_id, LINK_LINEAR_DRIVE_POSITION, plane_x_source_id, plane_x_target_id),
				new LinkTransformation(plane_x_source_id, LINK_LINEAR_DRIVE_POSITION, robot_id, null),
				new LinkTransformation(plane_x_target_id, LINK_LINEAR_DRIVE_POSITION, null, robot_id) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + plane_x_target_id + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_MOVE_WITHOUT_LOAD);
		action.registerParameterUpdater(new LuaScriptActionParameterUpdater(globals, script.toString()));

		return new SystemTransformation(action, template, transformations);
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

		systemTransformation = turnWithoutLoad();
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_Y_OUTSIDE, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_ROTARY_DRIVE_POSITION, plane_y_outside_id));
		assertNotNull(system.getObjectById(plane_y_outside_id).getLink(LINK_ROTARY_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_y_inside_id).getLink(LINK_ROTARY_DRIVE_POSITION, null));

		systemTransformation = closeGrab();
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_GRAB_POSITION, packageBox_id));
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_GRAB_POSITION, robot_id));

		systemTransformation = liftUp();
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_PACKAGE_BOX_POSITION, null));
		assertNotNull(system.getObjectById(shuttle_id).getLink(LINK_PACKAGE_BOX_POSITION, null));
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_VERTICAL_DRIVE_POSITION, plane_z_top_id));
		assertNotNull(system.getObjectById(plane_z_top_id).getLink(LINK_VERTICAL_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_z_bottom_id).getLink(LINK_VERTICAL_DRIVE_POSITION, null));

		systemTransformation = turnWithLoad();
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_Y_INSIDE, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_ROTARY_DRIVE_POSITION, plane_y_inside_id));
		assertNotNull(system.getObjectById(plane_y_outside_id).getLink(LINK_ROTARY_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(plane_y_inside_id).getLink(LINK_ROTARY_DRIVE_POSITION, robot_id));

		systemTransformation = moveWithLoad();
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);
		assertEquals(OBJECT_PLANE_X_TABLE_1, systemVariants[0].getActionParameter(PARAMETER_TARGET));

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_LINEAR_DRIVE_POSITION, plane_x_table_1_id));
		assertNotNull(system.getObjectById(plane_x_table_1_id).getLink(LINK_LINEAR_DRIVE_POSITION, robot_id));
		assertNotNull(system.getObjectById(plane_x_table_2_id).getLink(LINK_LINEAR_DRIVE_POSITION, null));

		systemTransformation = lowerDown();
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_PACKAGE_BOX_POSITION, table_1_id));
		assertNotNull(system.getObjectById(table_1_id).getLink(LINK_PACKAGE_BOX_POSITION, packageBox_id));
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom_id));
		assertNotNull(system.getObjectById(plane_z_top_id).getLink(LINK_VERTICAL_DRIVE_POSITION, null));
		assertNotNull(system.getObjectById(plane_z_bottom_id).getLink(LINK_VERTICAL_DRIVE_POSITION, robot_id));

		systemTransformation = openGrab();
		systemVariants = systemTransformation.applyTo(system);
		assertEquals(1, systemVariants.length);

		system = systemVariants[0].getSystem();
		assertNotNull(system.getObjectById(robot_id).getLink(LINK_GRAB_POSITION, null));
		assertNotNull(system.getObjectById(packageBox_id).getLink(LINK_GRAB_POSITION, null));

		systemTransformation = moveWithoutLoad();
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
		systemVariants = turnWithoutLoad().applyTo(system);
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

		final SystemTransformation[] systemTransformations = new SystemTransformation[] { turnWithoutLoad(),
				turnWithLoad(), openGrab(), closeGrab(), liftUp(), lowerDown(), moveWithLoad(), moveWithoutLoad() };

		Planner planner = new Planner(system, final_system, systemTransformations);
		planner.plan();

		List<SystemOperation> operations = planner.getShortestPlan();
		assertEquals(8, operations.size());

		SystemOperation operation;
		operation = operations.get(0);
		assertEquals(OPERATION_ROTATE_WITHOUT_LOAD, operation.getName());
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
