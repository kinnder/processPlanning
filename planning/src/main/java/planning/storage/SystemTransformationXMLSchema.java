package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Action;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;

public class SystemTransformationXMLSchema implements XMLSchema<SystemTransformation> {

	private ActionXMLSchema actionSchema = new ActionXMLSchema();

	private TransformationsXMLSchema transformationsSchema = new TransformationsXMLSchema();

	private SystemTemplateXMLSchema systemTemplateSchema = new SystemTemplateXMLSchema();

	@Override
	public SystemTransformation parse(Element root) throws DataConversionException {
		String name = root.getChildText("name");
		Action action = actionSchema.parse(root.getChild("action"));
		SystemTemplate systemTemplate = systemTemplateSchema.parse(root.getChild("systemTemplate"));
		Transformation[] transformations = transformationsSchema.parse(root.getChild("transformations"));
		return new SystemTransformation(name, action, systemTemplate, transformations);
	}

	@Override
	public Element combine(SystemTransformation systemTransformation) {
		Element name = new Element("name");
		name.setText(systemTransformation.getName());
		Element action = actionSchema.combine(systemTransformation.getAction());
		Element systemTemplate = systemTemplateSchema.combine(systemTransformation.getSystemTemplate());
		Element transformations = transformationsSchema.combine(systemTransformation.getTransformations());
		Element root = new Element("systemTransformation");
		root.addContent(name);
		root.addContent(systemTemplate);
		root.addContent(transformations);
		root.addContent(action);
		return root;
	}
}
