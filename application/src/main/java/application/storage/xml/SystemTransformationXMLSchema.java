package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Action;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformations;

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
		final String name = root.getChildText(TAG_name);
		final Action action = actionXMLSchema.parse(root.getChild(actionXMLSchema.getSchemaName()));
		final SystemTemplate systemTemplate = systemTemplateXMLSchema.parse(root.getChild(systemTemplateXMLSchema.getSchemaName()));
		final Transformations transformations = transformationsXMLSchema.parse(root.getChild(transformationsXMLSchema.getSchemaName()));
		return new SystemTransformation(name, action, systemTemplate, transformations);
	}

	@Override
	public Element combine(SystemTransformation systemTransformation) {
		final Element name = new Element(TAG_name);
		name.setText(systemTransformation.getName());
		final Element action = actionXMLSchema.combine(systemTransformation.getAction());
		final Element systemTemplate = systemTemplateXMLSchema.combine(systemTransformation.getSystemTemplate());
		final Element transformations = transformationsXMLSchema.combine(systemTransformation.getTransformations());
		final Element root = new Element(TAG_systemTransformation);
		root.addContent(name);
		root.addContent(systemTemplate);
		root.addContent(transformations);
		root.addContent(action);
		return root;
	}
}
