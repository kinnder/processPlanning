package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Action;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class SystemTransformationXMLSchema implements XMLSchema<SystemTransformation> {

	final private static String TAG_systemTransformation = "systemTransformation";

	final private static String TAG_name = "name";

	@Override
	public String getSchemaName() {
		return TAG_systemTransformation;
	}

	public SystemTransformationXMLSchema() {
		this(new ActionXMLSchema(), new TransformationsXMLSchema(), new SystemTemplateXMLSchema());
	}

	SystemTransformationXMLSchema(ActionXMLSchema actionXMLSchema, TransformationsXMLSchema transformationsXMLSchema, SystemTemplateXMLSchema systemTemplateXMLSchema) {
		this.actionXMLSchema = actionXMLSchema;
		this.systemTemplateXMLSchema = systemTemplateXMLSchema;
		this.transformationsXMLSchema = transformationsXMLSchema;
	}

	private ActionXMLSchema actionXMLSchema;

	private TransformationsXMLSchema transformationsXMLSchema;

	private SystemTemplateXMLSchema systemTemplateXMLSchema;

	@Override
	public SystemTransformation parse(Element root) throws DataConversionException {
		String name = root.getChildText(TAG_name);
		Action action = actionXMLSchema.parse(root.getChild(actionXMLSchema.getSchemaName()));
		SystemTemplate systemTemplate = systemTemplateXMLSchema.parse(root.getChild(systemTemplateXMLSchema.getSchemaName()));
		Transformation[] transformations = transformationsXMLSchema.parse(root.getChild(transformationsXMLSchema.getSchemaName()));
		return new SystemTransformation(name, action, systemTemplate, transformations);
	}

	@Override
	public Element combine(SystemTransformation systemTransformation) {
		Element name = new Element(TAG_name);
		name.setText(systemTransformation.getName());
		Element action = actionXMLSchema.combine(systemTransformation.getAction());
		Element systemTemplate = systemTemplateXMLSchema.combine(systemTransformation.getSystemTemplate());
		Element transformations = transformationsXMLSchema.combine(systemTransformation.getTransformations());
		Element root = new Element(TAG_systemTransformation);
		root.addContent(name);
		root.addContent(systemTemplate);
		root.addContent(transformations);
		root.addContent(action);
		return root;
	}
}
