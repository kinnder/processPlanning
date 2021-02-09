package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import planning.model.AttributeTemplate;
import planning.model.LinkTemplate;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;

public class SystemTemplateOWLSchema implements OWLSchema<SystemTemplate> {

	@Override
	public Individual combine(SystemTemplate systemTemplate) {
		Individual ind_systemTemplate = class_SystemTemplate
				.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
		ind_systemTemplate.addLabel("System Template 1", "en");
		ind_systemTemplate.addLabel("Шаблон системы 1", "ru");
		int i = 0;
		for (SystemObjectTemplate objectTemplate : systemTemplate.getObjectTemplates()) {
			// >> Individual: SystemObjectTemplate
			i++;
			Individual ind_objectTemplate = class_ObjectTemplate
					.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
			ind_objectTemplate.addLabel("Object Template ".concat(Integer.toString(i)), "en");
			ind_objectTemplate.addLabel("Шаблон объекта ".concat(Integer.toString(i)), "ru");
			ind_objectTemplate.addProperty(dataProperty_objectId, objectTemplate.getId());
			int j = 0;
			for (AttributeTemplate attributeTemplate : objectTemplate.getAttributeTemplates()) {
				// >> Individual: AttributeTemplate
				Individual ind_attributeTemplate = class_AttributeTemplate
						.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
				ind_attributeTemplate.addLabel(
						"Attribute Template ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))),
						"en");
				ind_attributeTemplate.addLabel(
						"Шаблон атрибута ".concat(Integer.toString(i).concat(" ").concat(Integer.toString(j))), "ru");
				ind_attributeTemplate.addProperty(dataProperty_name, attributeTemplate.getName());
				Object value = attributeTemplate.getValue();
				if (value != null) {
					// TODO (2021-01-13 #31): поддержка других DataType
					ind_attributeTemplate.addProperty(dataProperty_value, value.toString());
				}
				ind_attributeTemplate.addProperty(objectProperty_isAttributeTemplateOf, ind_objectTemplate);
				ind_objectTemplate.addProperty(objectProperty_hasAttributeTemplate, ind_attributeTemplate);
				// << Individual: AttributeTemplate
			}
			ind_objectTemplate.addProperty(objectProperty_isObjectTemplateOf, ind_systemTemplate);
			ind_systemTemplate.addProperty(objectProperty_hasObjectTemplate, ind_objectTemplate);
			// << Individual: SystemObjectTemplate
		}
		i = 0;
		for (LinkTemplate linkTemplate : systemTemplate.getLinkTemplates()) {
			// >> Individual: LinkTemplate
			Individual ind_linkTemplate = class_LinkTemplate
					.createIndividual(SystemTransformationsOWLModel.NS + UUID.randomUUID().toString());
			ind_linkTemplate.addLabel("Link Template ".concat(Integer.toString(i)), "en");
			ind_linkTemplate.addLabel("Шаблон связи ".concat(Integer.toString(i)), "ru");
			ind_linkTemplate.addProperty(dataProperty_name, linkTemplate.getName());
			String objectId1 = linkTemplate.getObjectId1();
			if (objectId1 != null) {
				ind_linkTemplate.addProperty(dataProperty_objectId1, objectId1);
			}
			String objectId2 = linkTemplate.getObjectId2();
			if (objectId2 != null) {
				ind_linkTemplate.addProperty(dataProperty_objectId2, objectId2);
			}
			ind_linkTemplate.addProperty(objectProperty_isLinkTemplateOf, ind_systemTemplate);
			ind_systemTemplate.addProperty(objectProperty_hasLinkTemplate, ind_linkTemplate);
			// << Individual: LinkTemplate
		}
		return ind_systemTemplate;
	}

	@Override
	public SystemTemplate parse(Individual individual) {
		// TODO Auto-generated method stub
		return null;
	}

	private OntClass class_SystemTemplate;

	private OntClass class_ObjectTemplate;

	private OntClass class_LinkTemplate;

	private OntClass class_AttributeTemplate;

	private ObjectProperty objectProperty_hasObjectTemplate;

	private ObjectProperty objectProperty_isObjectTemplateOf;

	private ObjectProperty objectProperty_hasLinkTemplate;

	private ObjectProperty objectProperty_isLinkTemplateOf;

	private ObjectProperty objectProperty_hasAttributeTemplate;

	private ObjectProperty objectProperty_isAttributeTemplateOf;

	private DatatypeProperty dataProperty_name;

	private DatatypeProperty dataProperty_objectId;

	private DatatypeProperty dataProperty_value;

	private DatatypeProperty dataProperty_objectId1;

	private DatatypeProperty dataProperty_objectId2;

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		class_SystemTemplate = ontModel.getOntClass(SystemTransformationsOWLModel.URI_SystemTemplate);
		class_ObjectTemplate = ontModel.getOntClass(SystemTransformationsOWLModel.URI_ObjectTemplate);
		class_LinkTemplate = ontModel.getOntClass(SystemTransformationsOWLModel.URI_LinkTemplate);
		class_AttributeTemplate = ontModel.getOntClass(SystemTransformationsOWLModel.URI_AttributeTemplate);

		objectProperty_hasObjectTemplate = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_hasObjectTemplate);
		objectProperty_isObjectTemplateOf = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_isObjectTemplateOf);
		objectProperty_hasLinkTemplate = ontModel.getObjectProperty(SystemTransformationsOWLModel.URI_hasLinkTemplate);
		objectProperty_isLinkTemplateOf = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_isLinkTemplateOf);
		objectProperty_hasAttributeTemplate = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_hasAttributeTemplate);
		objectProperty_isAttributeTemplateOf = ontModel
				.getObjectProperty(SystemTransformationsOWLModel.URI_isAttributeTemplateOf);

		dataProperty_name = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_name);
		dataProperty_objectId = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_objectId);
		dataProperty_value = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_value);
		dataProperty_objectId1 = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_objectId1);
		dataProperty_objectId2 = ontModel.getDatatypeProperty(SystemTransformationsOWLModel.URI_objectId2);
	}
}
