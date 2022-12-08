package application.domain;

import java.util.UUID;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.Action;
import planning.model.Attribute;
import planning.model.AttributeTemplate;
import planning.model.LinkTransformation;
import planning.model.LuaScriptActionFunction;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformations;

public class AssemblyLine {

	// TODO (2020-07-30 #30): включить проверку copy-paste
	// CPD-OFF

	public static final String DOMAIN_NAME = "assemblyLine";

	public static final String ID_OBJECT_PICK_AND_PLACE_ROBOT = UUID.randomUUID().toString();

	public static final String ID_OBJECT_PLANE_X_TABLE_1 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_PLANE_X_TABLE_2 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_PLANE_Y_OUTSIDE = UUID.randomUUID().toString();

	public static final String ID_OBJECT_PLANE_Y_INSIDE = UUID.randomUUID().toString();

	public static final String ID_OBJECT_PLANE_Z_TOP = UUID.randomUUID().toString();

	public static final String ID_OBJECT_PLANE_Z_BOTTOM = UUID.randomUUID().toString();

	public static final String ID_OBJECT_PACKAGE_BOX = UUID.randomUUID().toString();

	public static final String ID_OBJECT_SHUTTLE = UUID.randomUUID().toString();

	public static final String ID_OBJECT_TABLE_1 = UUID.randomUUID().toString();

	public static final String ID_OBJECT_TABLE_2 = UUID.randomUUID().toString();

	public static final String ID_TEMPLATE_PLANE_Z_BOTTOM = "ID-PLANE-Z-BOTTOM";

	public static final String ID_TEMPLATE_PLANE_Z_TOP = "ID-PLANE-Z-TOP";

	public static final String ID_TEMPLATE_PACKAGE_BOX_POSITION = "ID-PACKAGE-BOX-POSITION";

	public static final String ID_TEMPLATE_PLANE_Z = "ID-PLANE-Z";

	public static final String ID_TEMPLATE_PLANE_X = "ID-PLANE-X";

	public static final String ID_TEMPLATE_PLANE_Y = "ID-PLANE-Y";

	public static final String ID_TEMPLATE_PACKAGE_BOX = "ID-PACKAGE-BOX";

	public static final String ID_TEMPLATE_PLANE_Y_SOURCE = "ID-PLANE-Y-SOURCE";

	public static final String ID_TEMPLATE_PLANE_Y_TARGET = "ID-PLANE-Y-TARGET";

	public static final String ID_TEMPLATE_PLANE_X_SOURCE = "ID-PLANE-X-SOURCE";

	public static final String ID_TEMPLATE_PLANE_X_TARGET = "ID-PLANE-X-TARGET";

	public static final String ID_TEMPLATE_ROBOT = "ID-ROBOT";

	public static final String ELEMENT_MOVE_WITHOUT_LOAD = "ELEMENT-MOVE-WITHOUT-LOAD";

	public static final String ELEMENT_MOVE_WITH_LOAD = "ELEMENT-MOVE-WITH-LOAD";

	public static final String ELEMENT_LOWER_DOWN = "ELEMENT-LOWER-DOWN";

	public static final String ELEMENT_LIFT_UP = "ELEMENT-LIFT-UP";

	public static final String ELEMENT_CLOSE_GRAB = "ELEMENT-CLOSE-GRAB";

	public static final String ELEMENT_OPEN_GRAB = "ELEMENT-OPEN-GRAB";

	public static final String ELEMENT_TURN_WITH_LOAD = "ELEMENT-TURN-WITH-LOAD";

	public static final String ELEMENT_TURN_WITHOUT_LOAD = "ELEMENT-TURN-WITHOUT-LOAD";

	/** Переместить без нагрузки */
	public static final String OPERATION_MOVE_WITHOUT_LOAD = "OPERATION-MOVE-WITHOUT-LOAD";

	/** Переместить с нагрузкой */
	public static final String OPERATION_MOVE_WITH_LOAD = "OPERATION-MOVE-WITH-LOAD";

	/** Опустить вниз */
	public static final String OPERATION_LOWER_DOWN = "OPERATION-LOWER-DOWN";

	/** Поднять вверх */
	public static final String OPERATION_LIFT_UP = "OPERATION-LIFT-UP";

	/** Закрыть захват */
	public static final String OPERATION_CLOSE_GRAB = "OPERATION-CLOSE-GRAB";

	/** Открыть захват */
	public static final String OPERATION_OPEN_GRAB = "OPERATION-OPEN-GRAB";

	/** Повернуть c нагрузкой */
	public static final String OPERATION_TURN_WITH_LOAD = "OPERATION-TURN-WITH-LOAD";

	/** Повернуть без нагрузки */
	public static final String OPERATION_TURN_WITHOUT_LOAD = "OPERATION-TURN-WITHOUT-LOAD";

	/** положение в плоскости Z */
	public static final String LINK_PLANE_Z_POSITION = "LINK-PLANE-Z-POSITION";

	/** положение в плоскости Y */
	public static final String LINK_PLANE_Y_POSITION = "LINK-PLANE-Y-POSITION";

	/** положение в плоскости X */
	public static final String LINK_PLANE_X_POSITION = "LINK-PLANE-X-POSITION";

	/** место тары */
	public static final String LINK_PACKAGE_BOX_POSITION = "LINK-PACKAGE-BOX-POSITION";

	/** положение вертикального привода */
	public static final String LINK_VERTICAL_DRIVE_POSITION = "LINK-VERTICAL-DRIVE-POSITION";

	/** положение линейного привода */
	public static final String LINK_LINEAR_DRIVE_POSITION = "LINK-LINEAR-DRIVE-POSITION";

	/** положение поворотного привода */
	public static final String LINK_ROTARY_DRIVE_POSITION = "LINK-ROTARY-DRIVE-POSITION";

	/** положение захвата */
	public static final String LINK_GRAB_POSITION = "LINK-GRAB-POSITION";

	/** цель */
	public static final String PARAMETER_TARGET = "PARAMETER-TARGET";

	/** Плоскость Z */
	public static final String ATTRIBUTE_PLANE_Z = "ATTRIBUTE-PLANE-Z";

	/** Плоскость Y */
	public static final String ATTRIBUTE_PLANE_Y = "ATTRIBUTE-PLANE-Y";

	/** Плоскость X */
	public static final String ATTRIBUTE_PLANE_X = "ATTRIBUTE-PLANE-X";

	/** Место тары */
	public static final String ATTRIBUTE_PACKAGE_BOX_POSITION = "ATTRIBUTE-PACKAGE-BOX-POSITION";

	/** Тара */
	public static final String ATTRIBUTE_PACKAGE = "ATTRIBUTE-PACKAGE";

	/** Робот-перекладчик */
	public static final String ATTRIBUTE_PICK_AND_PLACE_ROBOT = "ATTRIBUTE-PICK-AND-PLACE-ROBOT";

	/** плоскость Z низ */
	public static final String OBJECT_PLANE_Z_BOTTOM = "OBJECT-PLANE-Z-BOTTOM";

	/** плоскость Z вверх */
	public static final String OBJECT_PLANE_Z_TOP = "OBJECT-PLANE-Z-TOP";

	/** плоскоть Y внутри станции */
	public static final String OBJECT_PLANE_Y_INSIDE = "OBJECT-PLANE-Y-INSIDE";

	/** плоскость Y снаружи станции */
	public static final String OBJECT_PLANE_Y_OUTSIDE = "OBJECT-PLANE-Y-OUTSIDE";

	/** плоскость X стол 2 */
	public static final String OBJECT_PLANE_X_TABLE_2 = "OBJECT-PLANE-X-TABLE-2";

	/** плоскость X стол 1 */
	public static final String OBJECT_PLANE_X_TABLE_1 = "OBJECT-PLANE-X-TABLE-1";

	/** Стол 2 */
	public static final String OBJECT_TABLE_2 = "OBJECT-TABLE-2";

	/** Стол 1 */
	public static final String OBJECT_TABLE_1 = "OBJECT-TABLE-1";

	/** Шаттл */
	public static final String OBJECT_SHUTTLE = "OBJECT-SHUTTLE";

	/** Тара */
	public static final String OBJECT_PACKAGE_BOX = "OBJECT-PACKAGE-BOX";

	/** Робот-перекладчик */
	public static final String OBJECT_PICK_AND_PLACE_ROBOT = "OBJECT-PICK-AND-PLACE-ROBOT";

	public static SystemTransformation turnWithoutLoad() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_TEMPLATE_ROBOT);
		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		systemTemplate.addObjectTemplate(robot);

		final SystemObjectTemplate plane_y_target = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Y_TARGET);
		plane_y_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		systemTemplate.addObjectTemplate(plane_y_target);

		final SystemObjectTemplate plane_y_source = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Y_SOURCE);
		plane_y_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		systemTemplate.addObjectTemplate(plane_y_source);

		systemTemplate.addLinkTemplate(robot, LINK_ROTARY_DRIVE_POSITION, plane_y_source);
		systemTemplate.addLinkTemplate(robot, LINK_GRAB_POSITION, null);

		systemTemplate.addLinkTemplate(plane_y_target, LINK_ROTARY_DRIVE_POSITION, null);

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_ROTARY_DRIVE_POSITION, ID_TEMPLATE_PLANE_Y_SOURCE, ID_TEMPLATE_PLANE_Y_TARGET));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_Y_TARGET, LINK_ROTARY_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_Y_SOURCE, LINK_ROTARY_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null));

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_TEMPLATE_PLANE_Y_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");
		script.append("\n");

		final Action action = new Action(OPERATION_TURN_WITHOUT_LOAD);
		action.registerParameterUpdater(new LuaScriptActionFunction(globals, script.toString()));

		return new SystemTransformation(ELEMENT_TURN_WITHOUT_LOAD, action, systemTemplate, transformations);
	}

	public static SystemTransformation turnWithLoad() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_TEMPLATE_ROBOT);
		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		systemTemplate.addObjectTemplate(robot);

		final SystemObjectTemplate plane_y_target = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Y_TARGET);
		plane_y_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		systemTemplate.addObjectTemplate(plane_y_target);

		final SystemObjectTemplate plane_y_source = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Y_SOURCE);
		plane_y_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		systemTemplate.addObjectTemplate(plane_y_source);

		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_TEMPLATE_PACKAGE_BOX);
		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		systemTemplate.addObjectTemplate(packageBox);

		systemTemplate.addLinkTemplate(robot, LINK_ROTARY_DRIVE_POSITION, plane_y_source);
		systemTemplate.addLinkTemplate(robot, LINK_GRAB_POSITION, packageBox);

		systemTemplate.addLinkTemplate(plane_y_target, LINK_ROTARY_DRIVE_POSITION, null);

		systemTemplate.addLinkTemplate(packageBox, LINK_PACKAGE_BOX_POSITION, null);

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_ROTARY_DRIVE_POSITION, ID_TEMPLATE_PLANE_Y_SOURCE, ID_TEMPLATE_PLANE_Y_TARGET));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_Y_SOURCE, LINK_ROTARY_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_Y_TARGET, LINK_ROTARY_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT));

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_TEMPLATE_PLANE_Y_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_TURN_WITH_LOAD);
		action.registerParameterUpdater(new LuaScriptActionFunction(globals, script.toString()));

		return new SystemTransformation(ELEMENT_TURN_WITH_LOAD, action, systemTemplate, transformations);
	}

	public static SystemTransformation openGrab() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_TEMPLATE_ROBOT);
		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		systemTemplate.addObjectTemplate(robot);

		final SystemObjectTemplate plane_y = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Y);
		plane_y.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		systemTemplate.addObjectTemplate(plane_y);

		final SystemObjectTemplate plane_x = new SystemObjectTemplate(ID_TEMPLATE_PLANE_X);
		plane_x.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		systemTemplate.addObjectTemplate(plane_x);

		final SystemObjectTemplate plane_z = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Z);
		plane_z.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		systemTemplate.addObjectTemplate(plane_z);

		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_TEMPLATE_PACKAGE_BOX);
		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		systemTemplate.addObjectTemplate(packageBox);

		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate(ID_TEMPLATE_PACKAGE_BOX_POSITION);
		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		systemTemplate.addObjectTemplate(packageBoxPosition);

		systemTemplate.addLinkTemplate(robot, LINK_GRAB_POSITION, packageBox);
		systemTemplate.addLinkTemplate(robot, LINK_LINEAR_DRIVE_POSITION, plane_x);
		systemTemplate.addLinkTemplate(robot, LINK_ROTARY_DRIVE_POSITION, plane_y);
		systemTemplate.addLinkTemplate(robot, LINK_VERTICAL_DRIVE_POSITION, plane_z);

		systemTemplate.addLinkTemplate(packageBox, LINK_PACKAGE_BOX_POSITION, packageBoxPosition);

		systemTemplate.addLinkTemplate(packageBoxPosition, LINK_PLANE_X_POSITION, plane_x);
		systemTemplate.addLinkTemplate(packageBoxPosition, LINK_PLANE_Y_POSITION, plane_y);
		systemTemplate.addLinkTemplate(packageBoxPosition, LINK_PLANE_Z_POSITION, plane_z);

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_GRAB_POSITION, ID_TEMPLATE_PACKAGE_BOX, null));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX, LINK_GRAB_POSITION, ID_TEMPLATE_ROBOT, null));

		final Action action = new Action(OPERATION_OPEN_GRAB);

		return new SystemTransformation(ELEMENT_OPEN_GRAB, action, systemTemplate, transformations);
	}

	public static SystemTransformation closeGrab() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_TEMPLATE_ROBOT);
		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		systemTemplate.addObjectTemplate(robot);

		final SystemObjectTemplate plane_y = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Y);
		plane_y.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		systemTemplate.addObjectTemplate(plane_y);

		final SystemObjectTemplate plane_x = new SystemObjectTemplate(ID_TEMPLATE_PLANE_X);
		plane_x.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		systemTemplate.addObjectTemplate(plane_x);

		final SystemObjectTemplate plane_z = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Z);
		plane_z.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		systemTemplate.addObjectTemplate(plane_z);

		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_TEMPLATE_PACKAGE_BOX);
		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		systemTemplate.addObjectTemplate(packageBox);

		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate(ID_TEMPLATE_PACKAGE_BOX_POSITION);
		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		systemTemplate.addObjectTemplate(packageBoxPosition);

		systemTemplate.addLinkTemplate(robot, LINK_GRAB_POSITION, null);
		systemTemplate.addLinkTemplate(robot, LINK_LINEAR_DRIVE_POSITION, plane_x);
		systemTemplate.addLinkTemplate(robot, LINK_ROTARY_DRIVE_POSITION, plane_y);
		systemTemplate.addLinkTemplate(robot, LINK_VERTICAL_DRIVE_POSITION, plane_z);

		systemTemplate.addLinkTemplate(packageBox, LINK_GRAB_POSITION, null);
		systemTemplate.addLinkTemplate(packageBox, LINK_PACKAGE_BOX_POSITION, packageBoxPosition);

		systemTemplate.addLinkTemplate(packageBoxPosition, LINK_PLANE_X_POSITION, plane_x);
		systemTemplate.addLinkTemplate(packageBoxPosition, LINK_PLANE_Y_POSITION, plane_y);
		systemTemplate.addLinkTemplate(packageBoxPosition, LINK_PLANE_Z_POSITION, plane_z);

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_GRAB_POSITION, null, ID_TEMPLATE_PACKAGE_BOX));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX, LINK_GRAB_POSITION, null, ID_TEMPLATE_ROBOT));

		final Action action = new Action(OPERATION_CLOSE_GRAB);

		return new SystemTransformation(ELEMENT_CLOSE_GRAB, action, systemTemplate, transformations);
	}

	public static SystemTransformation liftUp() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_TEMPLATE_ROBOT);
		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		systemTemplate.addObjectTemplate(robot);

		final SystemObjectTemplate plane_z_top = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Z_TOP);
		plane_z_top.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		systemTemplate.addObjectTemplate(plane_z_top);

		final SystemObjectTemplate plane_z_bottom = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Z_BOTTOM);
		plane_z_bottom.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		systemTemplate.addObjectTemplate(plane_z_bottom);

		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_TEMPLATE_PACKAGE_BOX);
		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		systemTemplate.addObjectTemplate(packageBox);

		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate(ID_TEMPLATE_PACKAGE_BOX_POSITION);
		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		systemTemplate.addObjectTemplate(packageBoxPosition);

		systemTemplate.addLinkTemplate(robot, LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom);
		systemTemplate.addLinkTemplate(robot, LINK_GRAB_POSITION, packageBox);

		systemTemplate.addLinkTemplate(plane_z_top, LINK_VERTICAL_DRIVE_POSITION, null);

		systemTemplate.addLinkTemplate(packageBox, LINK_PACKAGE_BOX_POSITION, packageBoxPosition);

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX, LINK_PACKAGE_BOX_POSITION, ID_TEMPLATE_PACKAGE_BOX_POSITION, null));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX_POSITION, LINK_PACKAGE_BOX_POSITION, ID_TEMPLATE_PACKAGE_BOX, null));
		transformations.add(new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_VERTICAL_DRIVE_POSITION, ID_TEMPLATE_PLANE_Z_BOTTOM, ID_TEMPLATE_PLANE_Z_TOP));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_Z_TOP, LINK_VERTICAL_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_Z_BOTTOM, LINK_VERTICAL_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null));

		final Action action = new Action(OPERATION_LIFT_UP);

		return new SystemTransformation(ELEMENT_LIFT_UP, action, systemTemplate, transformations);
	}

	public static SystemTransformation lowerDown() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_TEMPLATE_ROBOT);
		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		systemTemplate.addObjectTemplate(robot);

		final SystemObjectTemplate plane_y = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Y);
		plane_y.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Y, true));
		systemTemplate.addObjectTemplate(plane_y);

		final SystemObjectTemplate plane_x = new SystemObjectTemplate(ID_TEMPLATE_PLANE_X);
		plane_x.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		systemTemplate.addObjectTemplate(plane_x);

		final SystemObjectTemplate plane_z_top = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Z_TOP);
		plane_z_top.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		systemTemplate.addObjectTemplate(plane_z_top);

		final SystemObjectTemplate plane_z_bottom = new SystemObjectTemplate(ID_TEMPLATE_PLANE_Z_BOTTOM);
		plane_z_bottom.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_Z, true));
		systemTemplate.addObjectTemplate(plane_z_bottom);

		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_TEMPLATE_PACKAGE_BOX);
		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		systemTemplate.addObjectTemplate(packageBox);

		final SystemObjectTemplate packageBoxPosition = new SystemObjectTemplate(ID_TEMPLATE_PACKAGE_BOX_POSITION);
		packageBoxPosition.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		systemTemplate.addObjectTemplate(packageBoxPosition);

		systemTemplate.addLinkTemplate(robot, LINK_GRAB_POSITION, packageBox);
		systemTemplate.addLinkTemplate(robot, LINK_LINEAR_DRIVE_POSITION, plane_x);
		systemTemplate.addLinkTemplate(robot, LINK_ROTARY_DRIVE_POSITION, plane_y);
		systemTemplate.addLinkTemplate(robot, LINK_VERTICAL_DRIVE_POSITION, plane_z_top);

		systemTemplate.addLinkTemplate(plane_z_bottom, LINK_VERTICAL_DRIVE_POSITION, null);

		systemTemplate.addLinkTemplate(packageBox, LINK_PACKAGE_BOX_POSITION, null);

		systemTemplate.addLinkTemplate(packageBoxPosition, LINK_PLANE_X_POSITION, plane_x);
		systemTemplate.addLinkTemplate(packageBoxPosition, LINK_PLANE_Y_POSITION, plane_y);
		systemTemplate.addLinkTemplate(packageBoxPosition, LINK_PACKAGE_BOX_POSITION, null);

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX, LINK_PACKAGE_BOX_POSITION, null, ID_TEMPLATE_PACKAGE_BOX_POSITION));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX_POSITION, LINK_PACKAGE_BOX_POSITION, null, ID_TEMPLATE_PACKAGE_BOX));
		transformations.add(new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_VERTICAL_DRIVE_POSITION, ID_TEMPLATE_PLANE_Z_TOP, ID_TEMPLATE_PLANE_Z_BOTTOM));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_Z_TOP, LINK_VERTICAL_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_Z_BOTTOM, LINK_VERTICAL_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT));

		final Action action = new Action(OPERATION_LOWER_DOWN);

		return new SystemTransformation(ELEMENT_LOWER_DOWN, action, systemTemplate, transformations);
	}

	public static SystemTransformation moveWithLoad() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_TEMPLATE_ROBOT);
		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		systemTemplate.addObjectTemplate(robot);

		final SystemObjectTemplate plane_x_target = new SystemObjectTemplate(ID_TEMPLATE_PLANE_X_TARGET);
		plane_x_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		systemTemplate.addObjectTemplate(plane_x_target);

		final SystemObjectTemplate plane_x_source = new SystemObjectTemplate(ID_TEMPLATE_PLANE_X_SOURCE);
		plane_x_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		systemTemplate.addObjectTemplate(plane_x_source);

		final SystemObjectTemplate packageBox = new SystemObjectTemplate(ID_TEMPLATE_PACKAGE_BOX);
		packageBox.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PACKAGE, true));
		systemTemplate.addObjectTemplate(packageBox);

		systemTemplate.addLinkTemplate(robot, LINK_LINEAR_DRIVE_POSITION, plane_x_source);
		systemTemplate.addLinkTemplate(robot, LINK_GRAB_POSITION, packageBox);

		systemTemplate.addLinkTemplate(plane_x_target, LINK_LINEAR_DRIVE_POSITION, null);

		systemTemplate.addLinkTemplate(packageBox, LINK_PACKAGE_BOX_POSITION, null);

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_LINEAR_DRIVE_POSITION, ID_TEMPLATE_PLANE_X_SOURCE, ID_TEMPLATE_PLANE_X_TARGET));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_X_TARGET, LINK_LINEAR_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_X_SOURCE, LINK_LINEAR_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null));

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_TEMPLATE_PLANE_X_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_MOVE_WITH_LOAD);
		action.registerParameterUpdater(new LuaScriptActionFunction(globals, script.toString()));

		return new SystemTransformation(ELEMENT_MOVE_WITH_LOAD, action, systemTemplate, transformations);
	}

	public static SystemTransformation moveWithoutLoad() {
		final SystemTemplate systemTemplate = new SystemTemplate();

		final SystemObjectTemplate robot = new SystemObjectTemplate(ID_TEMPLATE_ROBOT);
		robot.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		systemTemplate.addObjectTemplate(robot);

		final SystemObjectTemplate plane_x_target = new SystemObjectTemplate(ID_TEMPLATE_PLANE_X_TARGET);
		plane_x_target.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		systemTemplate.addObjectTemplate(plane_x_target);

		final SystemObjectTemplate plane_x_source = new SystemObjectTemplate(ID_TEMPLATE_PLANE_X_SOURCE);
		plane_x_source.addAttributeTemplate(new AttributeTemplate(ATTRIBUTE_PLANE_X, true));
		systemTemplate.addObjectTemplate(plane_x_source);

		systemTemplate.addLinkTemplate(robot, LINK_LINEAR_DRIVE_POSITION, plane_x_source);
		systemTemplate.addLinkTemplate(robot, LINK_GRAB_POSITION, null);

		systemTemplate.addLinkTemplate(plane_x_target, LINK_LINEAR_DRIVE_POSITION, null);

		final Transformations transformations = new Transformations();
		transformations.add(new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_LINEAR_DRIVE_POSITION, ID_TEMPLATE_PLANE_X_SOURCE, ID_TEMPLATE_PLANE_X_TARGET));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_X_SOURCE, LINK_LINEAR_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null));
		transformations.add(new LinkTransformation(ID_TEMPLATE_PLANE_X_TARGET, LINK_LINEAR_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT));

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_TEMPLATE_PLANE_X_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_MOVE_WITHOUT_LOAD);
		action.registerParameterUpdater(new LuaScriptActionFunction(globals, script.toString()));

		return new SystemTransformation(ELEMENT_MOVE_WITHOUT_LOAD, action, systemTemplate, transformations);
	}

	// TODO (2022-12-07 #73): пересмотреть положение globals
	static Globals globals = JsePlatform.standardGlobals();

	public static SystemTransformations getSystemTransformations() {
		SystemTransformations systemTransformations = new SystemTransformations();
		systemTransformations.add(turnWithoutLoad());
		systemTransformations.add(turnWithLoad());
		systemTransformations.add(openGrab());
		systemTransformations.add(closeGrab());
		systemTransformations.add(liftUp());
		systemTransformations.add(lowerDown());
		systemTransformations.add(moveWithLoad());
		systemTransformations.add(moveWithoutLoad());
		return systemTransformations;
	}

	public static System initialSystem() {
		final System system = new System();

		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, ID_OBJECT_PICK_AND_PLACE_ROBOT);
		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		system.addObject(robot);

		final SystemObject plane_x_table_1 = new SystemObject(OBJECT_PLANE_X_TABLE_1, ID_OBJECT_PLANE_X_TABLE_1);
		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		system.addObject(plane_x_table_1);

		final SystemObject plane_x_table_2 = new SystemObject(OBJECT_PLANE_X_TABLE_2, ID_OBJECT_PLANE_X_TABLE_2);
		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		system.addObject(plane_x_table_2);

		final SystemObject plane_y_outside = new SystemObject(OBJECT_PLANE_Y_OUTSIDE, ID_OBJECT_PLANE_Y_OUTSIDE);
		plane_y_outside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		system.addObject(plane_y_outside);

		final SystemObject plane_y_inside = new SystemObject(OBJECT_PLANE_Y_INSIDE, ID_OBJECT_PLANE_Y_INSIDE);
		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		system.addObject(plane_y_inside);

		final SystemObject plane_z_top = new SystemObject(OBJECT_PLANE_Z_TOP, ID_OBJECT_PLANE_Z_TOP);
		plane_z_top.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		system.addObject(plane_z_top);

		final SystemObject plane_z_bottom = new SystemObject(OBJECT_PLANE_Z_BOTTOM, ID_OBJECT_PLANE_Z_BOTTOM);
		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		system.addObject(plane_z_bottom);

		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, ID_OBJECT_PACKAGE_BOX);
		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		system.addObject(packageBox);

		final SystemObject shuttle = new SystemObject(OBJECT_SHUTTLE, ID_OBJECT_SHUTTLE);
		shuttle.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		system.addObject(shuttle);

		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1, ID_OBJECT_TABLE_1);
		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		system.addObject(table_1);

		final SystemObject table_2 = new SystemObject(OBJECT_TABLE_2, ID_OBJECT_TABLE_2);
		table_2.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		system.addObject(table_2);

		system.addLink(robot, LINK_LINEAR_DRIVE_POSITION, plane_x_table_2);
		system.addLink(robot, LINK_ROTARY_DRIVE_POSITION, plane_y_inside);
		system.addLink(robot, LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom);
		system.addLink(robot, LINK_GRAB_POSITION, null);

		system.addLink(plane_x_table_1, LINK_LINEAR_DRIVE_POSITION, null);
		system.addLink(plane_x_table_1, LINK_PLANE_X_POSITION, table_1);

		system.addLink(plane_x_table_2, LINK_PLANE_X_POSITION, table_2);
		system.addLink(plane_x_table_2, LINK_PLANE_X_POSITION, shuttle);

		system.addLink(plane_y_inside, LINK_PLANE_Y_POSITION, table_1);
		system.addLink(plane_y_inside, LINK_PLANE_Y_POSITION, table_2);

		system.addLink(plane_y_outside, LINK_ROTARY_DRIVE_POSITION, null);
		system.addLink(plane_y_outside, LINK_PLANE_Y_POSITION, shuttle);

		system.addLink(plane_z_top, LINK_VERTICAL_DRIVE_POSITION, null);

		system.addLink(plane_z_bottom, LINK_PLANE_Z_POSITION, table_1);
		system.addLink(plane_z_bottom, LINK_PLANE_Z_POSITION, table_2);
		system.addLink(plane_z_bottom, LINK_PLANE_Z_POSITION, shuttle);

		system.addLink(packageBox, LINK_GRAB_POSITION, null);
		system.addLink(packageBox, LINK_PACKAGE_BOX_POSITION, shuttle);

		system.addLink(table_1, LINK_PACKAGE_BOX_POSITION, null);

		system.addLink(table_2, LINK_PACKAGE_BOX_POSITION, null);

		return system;
	}

	public static System finalSystem() {
		final System system = new System();

		final SystemObject packageBox = new SystemObject(OBJECT_PACKAGE_BOX, ID_OBJECT_PACKAGE_BOX);
		packageBox.addAttribute(new Attribute(ATTRIBUTE_PACKAGE, true));
		system.addObject(packageBox);

		final SystemObject robot = new SystemObject(OBJECT_PICK_AND_PLACE_ROBOT, ID_OBJECT_PICK_AND_PLACE_ROBOT);
		robot.addAttribute(new Attribute(ATTRIBUTE_PICK_AND_PLACE_ROBOT, true));
		system.addObject(robot);

		final SystemObject table_1 = new SystemObject(OBJECT_TABLE_1, ID_OBJECT_TABLE_1);
		table_1.addAttribute(new Attribute(ATTRIBUTE_PACKAGE_BOX_POSITION, true));
		system.addObject(table_1);

		final SystemObject plane_x_table_1 = new SystemObject(OBJECT_PLANE_X_TABLE_1, ID_OBJECT_PLANE_X_TABLE_1);
		plane_x_table_1.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		system.addObject(plane_x_table_1);

		final SystemObject plane_x_table_2 = new SystemObject(OBJECT_PLANE_X_TABLE_2, ID_OBJECT_PLANE_X_TABLE_2);
		plane_x_table_2.addAttribute(new Attribute(ATTRIBUTE_PLANE_X, true));
		system.addObject(plane_x_table_2);

		final SystemObject plane_y_inside = new SystemObject(OBJECT_PLANE_Y_INSIDE, ID_OBJECT_PLANE_Y_INSIDE);
		plane_y_inside.addAttribute(new Attribute(ATTRIBUTE_PLANE_Y, true));
		system.addObject(plane_y_inside);

		final SystemObject plane_z_bottom = new SystemObject(OBJECT_PLANE_Z_BOTTOM, ID_OBJECT_PLANE_Z_BOTTOM);
		plane_z_bottom.addAttribute(new Attribute(ATTRIBUTE_PLANE_Z, true));
		system.addObject(plane_z_bottom);

		system.addLink(packageBox, LINK_GRAB_POSITION, null);
		system.addLink(packageBox, LINK_PACKAGE_BOX_POSITION, table_1);

		system.addLink(robot, LINK_GRAB_POSITION, null);
		system.addLink(robot, LINK_LINEAR_DRIVE_POSITION, plane_x_table_2);
		system.addLink(robot, LINK_ROTARY_DRIVE_POSITION, plane_y_inside);
		system.addLink(robot, LINK_VERTICAL_DRIVE_POSITION, plane_z_bottom);

		system.addLink(table_1, LINK_PLANE_X_POSITION, plane_x_table_1);
		system.addLink(table_1, LINK_PLANE_Y_POSITION, plane_y_inside);
		system.addLink(table_1, LINK_PLANE_Z_POSITION, plane_z_bottom);

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
