package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.Attribute;
import planning.model.SystemObject;

public class SystemObjectOWLSchemaTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	SystemObjectOWLSchema testable;

	TaskDescriptionOWLModel owlModel_mock;

	AttributeOWLSchema attributeOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(TaskDescriptionOWLModel.class);
		attributeOWLSchema_mock = context.mock(AttributeOWLSchema.class);

		testable = new SystemObjectOWLSchema(owlModel_mock, attributeOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemObjectOWLSchema(new TaskDescriptionOWLModel());
	}

	@Test
	public void combine() {
		final SystemObject systemObject = new SystemObject("object-name", "object-id");
		final Attribute attribute = new Attribute("attribute-name", "attribute-value");
		systemObject.addAttribute(attribute);
		final Individual i_systemObject_mock = context.mock(Individual.class, "i-systemObject");
		final Individual i_attribute_mock = context.mock(Individual.class, "i-attribute");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final ObjectProperty op_isAttributeOf_mock = context.mock(ObjectProperty.class, "op-isAttributeOf");
		final ObjectProperty op_hasAttribute_mock = context.mock(ObjectProperty.class, "op-hasAttribute");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_SystemObject();
				will(returnValue(i_systemObject_mock));

				oneOf(i_systemObject_mock).addLabel("System Object", "en");

				oneOf(i_systemObject_mock).addLabel("Объект системы", "ru");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_systemObject_mock).addProperty(dp_name_mock, "object-name");

				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_systemObject_mock).addProperty(dp_id_mock, "object-id");

				oneOf(attributeOWLSchema_mock).combine(attribute);
				will(returnValue(i_attribute_mock));

				oneOf(owlModel_mock).getObjectProperty_isAttributeOf();
				will(returnValue(op_isAttributeOf_mock));

				oneOf(i_attribute_mock).addProperty(op_isAttributeOf_mock, i_systemObject_mock);

				oneOf(owlModel_mock).getObjectProperty_hasAttribute();
				will(returnValue(op_hasAttribute_mock));

				oneOf(i_systemObject_mock).addProperty(op_hasAttribute_mock, i_attribute_mock);
			}
		});

		assertEquals(i_systemObject_mock, testable.combine(systemObject));
	}

	@Test
	public void parse() {
		final Individual i_systemObject_mock = context.mock(Individual.class, "i-systemObject");
		final Attribute attribute_mock = context.mock(Attribute.class);
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_id_mock = context.mock(Statement.class, "st-id");
		final OntClass oc_attribute_mock = context.mock(OntClass.class, "oc-attribute");
		final ObjectProperty op_hasAttribute_mock = context.mock(ObjectProperty.class, "op-hasAttribute");

		final Individual i_attribute_mock = context.mock(Individual.class, "i-attribute");
		final ExtendedIterator<Individual> attributeIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_attribute_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_systemObject_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("object-name"));

				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_systemObject_mock).getProperty(dp_id_mock);
				will(returnValue(st_id_mock));

				oneOf(st_id_mock).getString();
				will(returnValue("object-id"));

				oneOf(owlModel_mock).getClass_Attribute();
				will(returnValue(oc_attribute_mock));

				oneOf(oc_attribute_mock).listInstances();
				will(returnValue(attributeIterator));

				oneOf(owlModel_mock).getObjectProperty_hasAttribute();
				will(returnValue(op_hasAttribute_mock));

				oneOf(i_systemObject_mock).hasProperty(op_hasAttribute_mock, i_attribute_mock);
				will(returnValue(true));

				oneOf(i_attribute_mock).asIndividual();
				will(returnValue(i_attribute_mock));

				oneOf(attributeOWLSchema_mock).parse(i_attribute_mock);
				will(returnValue(attribute_mock));

				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});

		SystemObject result = testable.parse(i_systemObject_mock);
		assertEquals("object-name", result.getName());
		assertEquals("object-id", result.getId());
		assertTrue(result.getAttributes().contains(attribute_mock));
	}
}
