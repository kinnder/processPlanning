package domain.assemblyLine;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.AttributeTemplate;
import planning.model.LinkTemplate;
import planning.model.LinkTransformation;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class GenerateSystemTransformations implements AssemblyLine {

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
		action.registerParameterUpdater(
				new LuaScriptActionParameterUpdater(GenerateSystemTransformations.globals, script.toString()));

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
		action.registerParameterUpdater(
				new LuaScriptActionParameterUpdater(GenerateSystemTransformations.globals, script.toString()));

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
		action.registerParameterUpdater(
				new LuaScriptActionParameterUpdater(GenerateSystemTransformations.globals, script.toString()));

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
		action.registerParameterUpdater(
				new LuaScriptActionParameterUpdater(GenerateSystemTransformations.globals, script.toString()));

		return new SystemTransformation(ELEMENT_MOVE_WITHOUT_LOAD, action, template, transformations);
	}

	// TODO : пересмотреть положение globals
	static Globals globals = JsePlatform.standardGlobals();

	public static void main(String args[]) {
		SystemTransformations assemblyLineTransformations = new SystemTransformations();
		assemblyLineTransformations.addElement(turnWithoutLoad());
		assemblyLineTransformations.addElement(turnWithLoad());
		assemblyLineTransformations.addElement(openGrab());
		assemblyLineTransformations.addElement(closeGrab());
		assemblyLineTransformations.addElement(liftUp());
		assemblyLineTransformations.addElement(lowerDown());
		assemblyLineTransformations.addElement(moveWithLoad());
		assemblyLineTransformations.addElement(moveWithoutLoad());
	}
}
