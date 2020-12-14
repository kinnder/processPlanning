package application.storage.owl;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;

import planning.method.SystemTransformations;

public class SystemTransformationsOWLSchema implements OWLSchema<SystemTransformations> {

	final private String NS = "https://github.com/kinnder/process-engineering/planning/system-transformations#";

	@Override
	public OntModel combine(SystemTransformations object) {
		// Ontology
		OntModel m = ModelFactory.createOntologyModel();

		OntClass ontClass_systemTransformations = m.createClass(NS + "System Transformations");
		ontClass_systemTransformations.addLabel("System Transformations", "en");
		ontClass_systemTransformations.addLabel("Трансформации системы", "ru");

		OntClass ontClass_systemTransformation = m.createClass(NS + "System Transformation");
		ontClass_systemTransformation.addLabel("System Transformation", "en");
		ontClass_systemTransformation.addLabel("Трансформация системы", "ru");

		OntClass ontClass_systemTemplate = m.createClass(NS + "System Template");
		ontClass_systemTemplate.addLabel("System Template", "en");
		ontClass_systemTemplate.addLabel("Шаблон системы", "ru");

		OntClass ontClass_transformations = m.createClass(NS + "Transformations");
		ontClass_transformations.addLabel("Transformations", "en");
		ontClass_transformations.addLabel("Трансформации", "ru");

		OntClass ontClass_action = m.createClass(NS + "Action");
		ontClass_action.addLabel("Action", "en");
		ontClass_action.addLabel("Действие", "ru");

		ObjectProperty ontObjectProperty_hasSystemTransformation = m
				.createObjectProperty(NS + "hasSystemTransformation");
		ontObjectProperty_hasSystemTransformation.addLabel("has system transformation", "en");
		ontObjectProperty_hasSystemTransformation.addLabel("имеет трансформацию системы", "ru");
		ontObjectProperty_hasSystemTransformation.addDomain(ontClass_systemTransformations);
		ontObjectProperty_hasSystemTransformation.addRange(ontClass_systemTransformation);

		ObjectProperty ontObjectProperty_isSystemTransformationOf = m
				.createObjectProperty(NS + "isSystemTransformationOf");
		ontObjectProperty_isSystemTransformationOf.addLabel("is system transformation of", "en");
		ontObjectProperty_isSystemTransformationOf.addLabel("является трансформацией системы для", "ru");
		ontObjectProperty_isSystemTransformationOf.addDomain(ontClass_systemTransformation);
		ontObjectProperty_isSystemTransformationOf.addRange(ontClass_systemTransformations);

		ontObjectProperty_hasSystemTransformation.addInverseOf(ontObjectProperty_isSystemTransformationOf);
		ontObjectProperty_isSystemTransformationOf.addInverseOf(ontObjectProperty_hasSystemTransformation);

		ObjectProperty ontObjectProperty_hasSystemTemplate = m.createObjectProperty(NS + "hasSystemTemplate");
		ontObjectProperty_hasSystemTemplate.addLabel("has system template", "en");
		ontObjectProperty_hasSystemTemplate.addLabel("имеет шаблон системы", "ru");
		ontObjectProperty_hasSystemTemplate.addDomain(ontClass_systemTransformation);
		ontObjectProperty_hasSystemTemplate.addRange(ontClass_systemTemplate);

		ObjectProperty ontObjectProperty_isSystemTemplateOf = m.createObjectProperty(NS + "isSystemTemplateOf");
		ontObjectProperty_isSystemTemplateOf.addLabel("is system template of", "en");
		ontObjectProperty_isSystemTemplateOf.addLabel("является шаблоном системы для ", "ru");
		ontObjectProperty_isSystemTemplateOf.addDomain(ontClass_systemTemplate);
		ontObjectProperty_isSystemTemplateOf.addRange(ontClass_systemTransformation);

		ontObjectProperty_hasSystemTemplate.addInverseOf(ontObjectProperty_isSystemTemplateOf);
		ontObjectProperty_isSystemTemplateOf.addInverseOf(ontObjectProperty_hasSystemTemplate);

		ObjectProperty ontObjectProperty_hasTransformations = m.createObjectProperty(NS + "hasTransformations");
		ontObjectProperty_hasTransformations.addLabel("has transformations", "en");
		ontObjectProperty_hasTransformations.addLabel("имеет трансформации", "ru");
		ontObjectProperty_hasTransformations.addDomain(ontClass_systemTransformation);
		ontObjectProperty_hasTransformations.addRange(ontClass_transformations);

		ObjectProperty ontObjectProperty_areTransformationsOf = m.createObjectProperty(NS + "areTransformationsOf");
		ontObjectProperty_areTransformationsOf.addLabel("are transformations of", "en");
		ontObjectProperty_areTransformationsOf.addLabel("является трансформациями для", "ru");
		ontObjectProperty_areTransformationsOf.addDomain(ontClass_transformations);
		ontObjectProperty_areTransformationsOf.addRange(ontClass_systemTransformation);

		ontObjectProperty_hasTransformations.addInverseOf(ontObjectProperty_areTransformationsOf);
		ontObjectProperty_areTransformationsOf.addInverseOf(ontObjectProperty_hasTransformations);

		ObjectProperty ontObjectProperty_hasAction = m.createObjectProperty(NS + "hasAction");
		ontObjectProperty_hasAction.addLabel("has action", "en");
		ontObjectProperty_hasAction.addLabel("имеет действие", "ru");
		ontObjectProperty_hasAction.addDomain(ontClass_systemTransformation);
		ontObjectProperty_hasAction.addRange(ontClass_action);

		ObjectProperty ontObjectProperty_isActionOf = m.createObjectProperty(NS + "isActionOf");
		ontObjectProperty_isActionOf.addLabel("is action of", "en");
		ontObjectProperty_isActionOf.addLabel("является действием для", "ru");
		ontObjectProperty_isActionOf.addDomain(ontClass_action);
		ontObjectProperty_isActionOf.addRange(ontClass_systemTransformation);

		ontObjectProperty_hasAction.addInverseOf(ontObjectProperty_isActionOf);
		ontObjectProperty_isActionOf.addInverseOf(ontObjectProperty_hasAction);

		// systemTemplate

		DatatypeProperty ontDatatypeProperty_name = m.createDatatypeProperty(NS + "name");
		ontDatatypeProperty_name.addLabel("name", "en");
		ontDatatypeProperty_name.addLabel("название", "ru");
		ontDatatypeProperty_name.addDomain(ontClass_systemTransformation);
		ontDatatypeProperty_name.addRange(XSD.xstring);

		// Individuals

		return m;
	}

	@Override
	public SystemTransformations parse(OntModel m) {
		// TODO Auto-generated method stub
		return null;
	}

}
