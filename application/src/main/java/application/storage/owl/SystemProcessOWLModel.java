package application.storage.owl;

import java.util.UUID;

import org.apache.jena.ontology.DataRange;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.vocabulary.XSD;

import planning.model.SystemProcess;

public class SystemProcessOWLModel implements OWLModel<SystemProcess>, OWLModelCommonPart2 {

	static final String NS = "https://github.com/kinnder/process-engineering/planning/system-process";

	static final String URI_key = NS + "#key";

	static final String URI_isParameterOf = NS + "#isParameterOf";

	static final String URI_hasParameter = NS + "#hasParameter";

	static final String URI_isSystemOperationOf = NS + "#isOperationOf";

	static final String URI_hasSystemOperation = NS + "#hasOperation";

	static final String URI_value = NS + "#value";

	static final String URI_name = NS + "#name";

	static final String URI_Parameter = NS + "#Parameter";

	static final String URI_SystemOperation = NS + "#SystemOperation";

	static final String URI_Process = NS + "#Process";

	private OntModel m;

	@Override
	public void connectOntologyModel(OntModel ontModel) {
		m = ontModel;

		class_SystemOperation = m.getOntClass(URI_SystemOperation);
		class_Parameter = m.getOntClass(URI_Parameter);
		class_SystemProcess = m.getOntClass(URI_Process);

		objectProperty_hasSystemOperation = m.getObjectProperty(URI_hasSystemOperation);
		objectProperty_hasParameter = m.getObjectProperty(URI_hasParameter);
		objectProperty_isSystemOperationOf = m.getObjectProperty(URI_isSystemOperationOf);
		objectProperty_isParameterOf = m.getObjectProperty(URI_isParameterOf);

		datatypeProperty_name = m.getDatatypeProperty(URI_name);
		datatypeProperty_key = m.getDatatypeProperty(URI_key);
		datatypeProperty_value = m.getDatatypeProperty(URI_value);
	}

	@Override
	public OntModel getOntologyModel() {
		return m;
	}

	private OntClass class_SystemProcess;

	@Override
	public OntClass getClass_Process() {
		return class_SystemProcess;
	}

	private OntClass class_SystemOperation;

	@Override
	public OntClass getClass_SystemOperation() {
		return class_SystemOperation;
	}

	private OntClass class_Parameter;

	@Override
	public OntClass getClass_Parameter() {
		return class_Parameter;
	}

	private DatatypeProperty datatypeProperty_name;

	@Override
	public DatatypeProperty getDataProperty_name() {
		return datatypeProperty_name;
	}

	private DatatypeProperty datatypeProperty_value;

	@Override
	public DatatypeProperty getDataProperty_value() {
		return datatypeProperty_value;
	}

	private ObjectProperty objectProperty_hasSystemOperation;

	@Override
	public ObjectProperty getObjectProperty_hasSystemOperation() {
		return objectProperty_hasSystemOperation;
	}

	private ObjectProperty objectProperty_isSystemOperationOf;

	@Override
	public ObjectProperty getObjectProperty_isSystemOperationOf() {
		return objectProperty_isSystemOperationOf;
	}

	private ObjectProperty objectProperty_hasParameter;

	@Override
	public ObjectProperty getObjectProperty_hasParameter() {
		return objectProperty_hasParameter;
	}

	private ObjectProperty objectProperty_isParameterOf;

	@Override
	public ObjectProperty getObjectProperty_isParameterOf() {
		return objectProperty_isParameterOf;
	}

	private void makeDisjoint(OntClass class1, OntClass class2) {
		class1.addDisjointWith(class2);
		class2.addDisjointWith(class1);
	}

	private void makeInverse(ObjectProperty property1, ObjectProperty property2) {
		property1.addInverseOf(property2);
		property2.addInverseOf(property1);
	}

	private DataRange createDataRange(RDFNode... members) {
		DataRange d = m.createOntResource(DataRange.class, m.getProfile().DATARANGE(), null);
		d.addProperty(m.getProfile().UNION_OF(), m.createList(members));
		return d;
	}

	@Override
	public void createOntologyModel() {
		m = createOntologyModelBase();

		m.createOntology(NS);

		class_SystemProcess = m.createClass(URI_Process);
		class_SystemProcess.addLabel("Process", "en");
		class_SystemProcess.addLabel("Процесс", "ru");

		class_SystemOperation = m.createClass(URI_SystemOperation);
		class_SystemOperation.addLabel("System Operation", "en");
		class_SystemOperation.addLabel("Операция системы", "ru");

		class_Parameter = m.createClass(URI_Parameter);
		class_Parameter.addLabel("Parameter", "en");
		class_Parameter.addLabel("Параметр", "ru");

		makeDisjoint(class_SystemOperation, class_Parameter);

		objectProperty_hasSystemOperation = m.createObjectProperty(URI_hasSystemOperation);
		objectProperty_hasSystemOperation.addLabel("has operation", "en");
		objectProperty_hasSystemOperation.addLabel("имеет операцию", "ru");
		objectProperty_hasSystemOperation.addDomain(class_SystemProcess);
		objectProperty_hasSystemOperation.addRange(class_SystemOperation);

		objectProperty_isSystemOperationOf = m.createObjectProperty(URI_isSystemOperationOf);
		objectProperty_isSystemOperationOf.addLabel("is operation of", "en");
		objectProperty_isSystemOperationOf.addLabel("является операция для", "ru");
		objectProperty_isSystemOperationOf.addDomain(class_SystemOperation);
		objectProperty_isSystemOperationOf.addRange(class_SystemProcess);

		makeInverse(objectProperty_hasSystemOperation, objectProperty_isSystemOperationOf);

		objectProperty_hasParameter = m.createObjectProperty(URI_hasParameter);
		objectProperty_hasParameter.addLabel("has parameter", "en");
		objectProperty_hasParameter.addLabel("имеет параметр", "ru");
		objectProperty_hasParameter.addDomain(class_SystemOperation);
		objectProperty_hasParameter.addRange(class_Parameter);

		objectProperty_isParameterOf = m.createObjectProperty(URI_isParameterOf);
		objectProperty_isParameterOf.addLabel("is parameter of", "en");
		objectProperty_isParameterOf.addLabel("является параметром для", "ru");
		objectProperty_isParameterOf.addDomain(class_Parameter);
		objectProperty_isParameterOf.addRange(class_SystemOperation);

		makeInverse(objectProperty_hasParameter, objectProperty_isParameterOf);

		datatypeProperty_name = m.createDatatypeProperty(URI_name);
		datatypeProperty_name.addLabel("name", "en");
		datatypeProperty_name.addLabel("название", "ru");
		datatypeProperty_name.addDomain(class_SystemOperation);
		datatypeProperty_name.addRange(XSD.xstring);

		datatypeProperty_value = m.createDatatypeProperty(URI_value);
		datatypeProperty_value.addLabel("value", "en");
		datatypeProperty_value.addLabel("значение", "ru");
		datatypeProperty_value.addDomain(class_Parameter);
		datatypeProperty_value.addRange(createDataRange(XSD.xstring, XSD.xboolean, XSD.xint));

		datatypeProperty_key = m.createDatatypeProperty(URI_key);
		datatypeProperty_key.addLabel("key", "en");
		datatypeProperty_key.addLabel("ключ", "ru");
		datatypeProperty_key.addDomain(class_Parameter);
		datatypeProperty_key.addRange(XSD.xstring);
	}

	@Override
	public OWLSchema<SystemProcess> getOWLSchema() {
		return new SystemProcessOWLSchema(this);
	}

	@Override
	public OntModel createOntologyModelBase() {
		return ModelFactory.createOntologyModel();
	}

	@Override
	public String getUniqueURI() {
		return NS + "#" + UUID.randomUUID().toString();
	}

	private DatatypeProperty datatypeProperty_key;

	@Override
	public DatatypeProperty getDataProperty_key() {
		return datatypeProperty_key;
	}

	@Override
	public Individual newIndividual_SystemOperation() {
		return class_SystemOperation.createIndividual(getUniqueURI());
	}

	@Override
	public Individual newIndividual_Parameter() {
		return class_Parameter.createIndividual(getUniqueURI());
	}

	@Override
	public Individual newIndividual_Process() {
		return class_SystemProcess.createIndividual(getUniqueURI());
	}
}
