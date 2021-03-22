package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Statement;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.AttributeTemplate;

public class AttributeTemplateOWLSchemaTest {

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

	AttributeTemplateOWLSchema testable;

	SystemTransformationsOWLModel owlModel_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(SystemTransformationsOWLModel.class);

		testable = new AttributeTemplateOWLSchema(owlModel_mock);
	}

	@Test
	public void combine() {
		final AttributeTemplate attributeTemplate = new AttributeTemplate("attribute-name", null);
		final Individual i_attributeTemplate_mock = context.mock(Individual.class, "i-attributeTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_AttributeTemplate();
				will(returnValue(i_attributeTemplate_mock));

				oneOf(i_attributeTemplate_mock).addLabel("Attribute template", "en");

				oneOf(i_attributeTemplate_mock).addLabel("Шаблон атрибута", "ru");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTemplate_mock).addProperty(dp_name_mock, "attribute-name");
			}
		});

		assertEquals(i_attributeTemplate_mock, testable.combine(attributeTemplate));
	}

	@Test
	public void combine_boolean() {
		final AttributeTemplate attributeTemplate = new AttributeTemplate("attribute-name", new Boolean(true));
		final Individual i_attributeTemplate_mock = context.mock(Individual.class, "i-attributeTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_AttributeTemplate();
				will(returnValue(i_attributeTemplate_mock));

				oneOf(i_attributeTemplate_mock).addLabel("Attribute template", "en");

				oneOf(i_attributeTemplate_mock).addLabel("Шаблон атрибута", "ru");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTemplate_mock).addProperty(dp_name_mock, "attribute-name");

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTemplate_mock).addProperty(dp_value_mock, "true", XSDDatatype.XSDboolean);
			}
		});

		assertEquals(i_attributeTemplate_mock, testable.combine(attributeTemplate));
	}

	@Test
	public void combine_integer() {
		final AttributeTemplate attributeTemplate = new AttributeTemplate("attribute-name", new Integer(100));
		final Individual i_attributeTemplate_mock = context.mock(Individual.class, "i-attributeTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_AttributeTemplate();
				will(returnValue(i_attributeTemplate_mock));

				oneOf(i_attributeTemplate_mock).addLabel("Attribute template", "en");

				oneOf(i_attributeTemplate_mock).addLabel("Шаблон атрибута", "ru");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTemplate_mock).addProperty(dp_name_mock, "attribute-name");

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTemplate_mock).addProperty(dp_value_mock, "100", XSDDatatype.XSDinteger);
			}
		});

		assertEquals(i_attributeTemplate_mock, testable.combine(attributeTemplate));
	}

	@Test
	public void combine_string() {
		final AttributeTemplate attributeTemplate = new AttributeTemplate("attribute-name", "attribute-value");
		final Individual i_attributeTemplate_mock = context.mock(Individual.class, "i-attributeTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_AttributeTemplate();
				will(returnValue(i_attributeTemplate_mock));

				oneOf(i_attributeTemplate_mock).addLabel("Attribute template", "en");

				oneOf(i_attributeTemplate_mock).addLabel("Шаблон атрибута", "ru");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTemplate_mock).addProperty(dp_name_mock, "attribute-name");

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTemplate_mock).addProperty(dp_value_mock, "attribute-value", XSDDatatype.XSDstring);
			}
		});

		assertEquals(i_attributeTemplate_mock, testable.combine(attributeTemplate));
	}

	@Test
	public void parse() {
		final Individual i_attributeTemplate_mock = context.mock(Individual.class, "i-attributeTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTemplate_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("attribute-name"));

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTemplate_mock).getProperty(dp_value_mock);
				will(returnValue(null));
			}
		});

		AttributeTemplate result = testable.parse(i_attributeTemplate_mock);
		assertEquals("attribute-name", result.getName());
		assertNull(result.getValue());
	}

	@Test
	public void parse_boolean() {
		final Individual i_attributeTemplate_mock = context.mock(Individual.class, "i-attributeTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_value_mock = context.mock(Statement.class, "st-value");
		final Literal l_value_mock = context.mock(Literal.class, "l-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTemplate_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("attribute-name"));

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTemplate_mock).getProperty(dp_value_mock);
				will(returnValue(st_value_mock));

				oneOf(st_value_mock).getLiteral();
				will(returnValue(l_value_mock));

				oneOf(l_value_mock).getDatatype();
				will(returnValue(XSDDatatype.XSDboolean));

				oneOf(l_value_mock).getBoolean();
				will(returnValue(true));
			}
		});

		AttributeTemplate result = testable.parse(i_attributeTemplate_mock);
		assertEquals("attribute-name", result.getName());
		assertEquals(true, result.getValue());
	}

	@Test
	public void parse_string() {
		final Individual i_attributeTemplate_mock = context.mock(Individual.class, "i-attributeTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_value_mock = context.mock(Statement.class, "st-value");
		final Literal l_value_mock = context.mock(Literal.class, "l-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTemplate_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("attribute-name"));

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTemplate_mock).getProperty(dp_value_mock);
				will(returnValue(st_value_mock));

				oneOf(st_value_mock).getLiteral();
				will(returnValue(l_value_mock));

				oneOf(l_value_mock).getDatatype();
				will(returnValue(XSDDatatype.XSDstring));

				oneOf(l_value_mock).getString();
				will(returnValue("attribute-value"));
			}
		});

		AttributeTemplate result = testable.parse(i_attributeTemplate_mock);
		assertEquals("attribute-name", result.getName());
		assertEquals("attribute-value", result.getValue());
	}

	@Test
	public void parse_integer() {
		final Individual i_attributeTemplate_mock = context.mock(Individual.class, "i-attributeTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_value_mock = context.mock(Statement.class, "st-value");
		final Literal l_value_mock = context.mock(Literal.class, "l-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTemplate_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("attribute-name"));

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTemplate_mock).getProperty(dp_value_mock);
				will(returnValue(st_value_mock));

				oneOf(st_value_mock).getLiteral();
				will(returnValue(l_value_mock));

				oneOf(l_value_mock).getDatatype();
				will(returnValue(XSDDatatype.XSDinteger));

				oneOf(l_value_mock).getInt();
				will(returnValue(100));
			}
		});

		AttributeTemplate result = testable.parse(i_attributeTemplate_mock);
		assertEquals("attribute-name", result.getName());
		assertEquals(100, result.getValue());
	}

	@Test
	public void parse_unsupported_type() {
		final Individual i_attributeTemplate_mock = context.mock(Individual.class, "i-attributeTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_value_mock = context.mock(Statement.class, "st-value");
		final Literal l_value_mock = context.mock(Literal.class, "l-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTemplate_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("attribute-name"));

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTemplate_mock).getProperty(dp_value_mock);
				will(returnValue(st_value_mock));

				oneOf(st_value_mock).getLiteral();
				will(returnValue(l_value_mock));

				oneOf(l_value_mock).getDatatype();
				will(returnValue(XSDDatatype.XSDanyURI));
			}
		});

		AttributeTemplate result = testable.parse(i_attributeTemplate_mock);
		assertEquals("attribute-name", result.getName());
		assertNull(result.getValue());
	}
}
