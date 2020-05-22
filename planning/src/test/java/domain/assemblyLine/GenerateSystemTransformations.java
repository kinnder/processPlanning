package domain.assemblyLine;

import java.io.IOException;
import java.net.URISyntaxException;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.method.SystemTransformations;
import planning.model.Action;
import planning.model.AttributeTemplate;
import planning.model.LinkTransformation;
import planning.model.LuaScriptActionParameterUpdater;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;
import planning.storage.SystemTransformationsXMLFile;

public class GenerateSystemTransformations implements AssemblyLine {

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

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_ROTARY_DRIVE_POSITION, ID_TEMPLATE_PLANE_Y_SOURCE, ID_TEMPLATE_PLANE_Y_TARGET),
				new LinkTransformation(ID_TEMPLATE_PLANE_Y_TARGET, LINK_ROTARY_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT),
				new LinkTransformation(ID_TEMPLATE_PLANE_Y_SOURCE, LINK_ROTARY_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_TEMPLATE_PLANE_Y_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");
		script.append("\n");

		final Action action = new Action(OPERATION_TURN_WITHOUT_LOAD);
		action.registerActionParameterUpdater(new LuaScriptActionParameterUpdater(GenerateSystemTransformations.globals, script.toString()));

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

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_ROTARY_DRIVE_POSITION, ID_TEMPLATE_PLANE_Y_SOURCE, ID_TEMPLATE_PLANE_Y_TARGET),
				new LinkTransformation(ID_TEMPLATE_PLANE_Y_SOURCE, LINK_ROTARY_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null),
				new LinkTransformation(ID_TEMPLATE_PLANE_Y_TARGET, LINK_ROTARY_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_TEMPLATE_PLANE_Y_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_TURN_WITH_LOAD);
		action.registerActionParameterUpdater(new LuaScriptActionParameterUpdater(GenerateSystemTransformations.globals, script.toString()));

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

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_GRAB_POSITION, ID_TEMPLATE_PACKAGE_BOX, null),
				new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX, LINK_GRAB_POSITION, ID_TEMPLATE_ROBOT, null) };

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

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_GRAB_POSITION, null, ID_TEMPLATE_PACKAGE_BOX),
				new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX, LINK_GRAB_POSITION, null, ID_TEMPLATE_ROBOT) };

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

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX, LINK_PACKAGE_BOX_POSITION, ID_TEMPLATE_PACKAGE_BOX_POSITION, null),
				new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX_POSITION, LINK_PACKAGE_BOX_POSITION, ID_TEMPLATE_PACKAGE_BOX, null),
				new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_VERTICAL_DRIVE_POSITION, ID_TEMPLATE_PLANE_Z_BOTTOM, ID_TEMPLATE_PLANE_Z_TOP),
				new LinkTransformation(ID_TEMPLATE_PLANE_Z_TOP, LINK_VERTICAL_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT),
				new LinkTransformation(ID_TEMPLATE_PLANE_Z_BOTTOM, LINK_VERTICAL_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null) };

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

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX, LINK_PACKAGE_BOX_POSITION, null, ID_TEMPLATE_PACKAGE_BOX_POSITION),
				new LinkTransformation(ID_TEMPLATE_PACKAGE_BOX_POSITION, LINK_PACKAGE_BOX_POSITION, null, ID_TEMPLATE_PACKAGE_BOX),
				new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_VERTICAL_DRIVE_POSITION, ID_TEMPLATE_PLANE_Z_TOP, ID_TEMPLATE_PLANE_Z_BOTTOM),
				new LinkTransformation(ID_TEMPLATE_PLANE_Z_TOP, LINK_VERTICAL_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null),
				new LinkTransformation(ID_TEMPLATE_PLANE_Z_BOTTOM, LINK_VERTICAL_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT) };

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

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_LINEAR_DRIVE_POSITION, ID_TEMPLATE_PLANE_X_SOURCE, ID_TEMPLATE_PLANE_X_TARGET),
				new LinkTransformation(ID_TEMPLATE_PLANE_X_TARGET, LINK_LINEAR_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT),
				new LinkTransformation(ID_TEMPLATE_PLANE_X_SOURCE, LINK_LINEAR_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_TEMPLATE_PLANE_X_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_MOVE_WITH_LOAD);
		action.registerActionParameterUpdater(new LuaScriptActionParameterUpdater(GenerateSystemTransformations.globals, script.toString()));

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

		final Transformation transformations[] = new Transformation[] {
				new LinkTransformation(ID_TEMPLATE_ROBOT, LINK_LINEAR_DRIVE_POSITION, ID_TEMPLATE_PLANE_X_SOURCE, ID_TEMPLATE_PLANE_X_TARGET),
				new LinkTransformation(ID_TEMPLATE_PLANE_X_SOURCE, LINK_LINEAR_DRIVE_POSITION, ID_TEMPLATE_ROBOT, null),
				new LinkTransformation(ID_TEMPLATE_PLANE_X_TARGET, LINK_LINEAR_DRIVE_POSITION, null, ID_TEMPLATE_ROBOT) };

		StringBuilder script = new StringBuilder();
		script.append("local systemVariant = ...");
		script.append("\n");
		script.append("local object = systemVariant:getObjectByIdMatch('" + ID_TEMPLATE_PLANE_X_TARGET + "')");
		script.append("\n");
		script.append("systemVariant:setActionParameter('" + PARAMETER_TARGET + "', object:getName())");

		final Action action = new Action(OPERATION_MOVE_WITHOUT_LOAD);
		action.registerActionParameterUpdater(new LuaScriptActionParameterUpdater(GenerateSystemTransformations.globals, script.toString()));

		return new SystemTransformation(ELEMENT_MOVE_WITHOUT_LOAD, action, systemTemplate, transformations);
	}

	// TODO : пересмотреть положение globals
	static Globals globals = JsePlatform.standardGlobals();

	public static void main(String args[]) {
		SystemTransformations assemblyLineTransformations = new SystemTransformations();
		assemblyLineTransformations.add(turnWithoutLoad());
		assemblyLineTransformations.add(turnWithLoad());
		assemblyLineTransformations.add(openGrab());
		assemblyLineTransformations.add(closeGrab());
		assemblyLineTransformations.add(liftUp());
		assemblyLineTransformations.add(lowerDown());
		assemblyLineTransformations.add(moveWithLoad());
		assemblyLineTransformations.add(moveWithoutLoad());

		SystemTransformationsXMLFile xmlFile = new SystemTransformationsXMLFile();
		xmlFile.setObject(assemblyLineTransformations);
		try {
			xmlFile.save(GenerateSystemTransformations.class.getResource("/assemblyLine/systemTransformations.xml"));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
