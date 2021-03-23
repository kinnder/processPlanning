package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Statement;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.LinkTransformation;

public class LinkTransformationOWLSchemTest {

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

	LinkTransformationOWLSchema testable;

	SystemTransformationsOWLModel owlModel_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(SystemTransformationsOWLModel.class);

		testable = new LinkTransformationOWLSchema(owlModel_mock);
	}

	@Test
	public void combine() {
		final LinkTransformation linkTransformation = new LinkTransformation("link-objectId", "link-name",
				"link-id-old", "link-id-new");
		final Individual i_linkTransformation_mock = context.mock(Individual.class, "i-linkTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");
		final DatatypeProperty dp_oldValue_mock = context.mock(DatatypeProperty.class, "dp-oldValue");
		final DatatypeProperty dp_newValue_mock = context.mock(DatatypeProperty.class, "dp-newValue");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_LinkTransformation();
				will(returnValue(i_linkTransformation_mock));

				oneOf(i_linkTransformation_mock).addLabel("Link transformation", "en");

				oneOf(i_linkTransformation_mock).addLabel("Трансформация связи", "ru");

				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_objectId_mock, "link-objectId");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_name_mock, "link-name");

				oneOf(owlModel_mock).getDataProperty_oldValue();
				will(returnValue(dp_oldValue_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_oldValue_mock, "link-id-old");

				oneOf(owlModel_mock).getDataProperty_newValue();
				will(returnValue(dp_newValue_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_newValue_mock, "link-id-new");
			}
		});

		assertEquals(i_linkTransformation_mock, testable.combine(linkTransformation));
	}

	@Test
	public void combine_null_ids() {
		final LinkTransformation linkTransformation = new LinkTransformation("link-objectId", "link-name", null, null);
		final Individual i_linkTransformation_mock = context.mock(Individual.class, "i-linkTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_LinkTransformation();
				will(returnValue(i_linkTransformation_mock));

				oneOf(i_linkTransformation_mock).addLabel("Link transformation", "en");

				oneOf(i_linkTransformation_mock).addLabel("Трансформация связи", "ru");

				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_objectId_mock, "link-objectId");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_name_mock, "link-name");
			}
		});

		assertEquals(i_linkTransformation_mock, testable.combine(linkTransformation));
	}

	@Test
	public void parse() {
		final Individual i_linkTransformation_mock = context.mock(Individual.class, "i-linkTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");
		final DatatypeProperty dp_oldValue_mock = context.mock(DatatypeProperty.class, "dp-oldValue");
		final DatatypeProperty dp_newValue_mock = context.mock(DatatypeProperty.class, "dp-newValue");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_objectId_mock = context.mock(Statement.class, "st-objectId");
		final Statement st_oldValue_mock = context.mock(Statement.class, "st-oldValue");
		final Statement st_newValue_mock = context.mock(Statement.class, "st-newValue");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_objectId_mock);
				will(returnValue(st_objectId_mock));

				oneOf(st_objectId_mock).getString();
				will(returnValue("link-objectId"));

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("link-name"));

				oneOf(owlModel_mock).getDataProperty_oldValue();
				will(returnValue(dp_oldValue_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_oldValue_mock);
				will(returnValue(st_oldValue_mock));

				oneOf(st_oldValue_mock).getString();
				will(returnValue("link-id-old"));

				oneOf(owlModel_mock).getDataProperty_newValue();
				will(returnValue(dp_newValue_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_newValue_mock);
				will(returnValue(st_newValue_mock));

				oneOf(st_newValue_mock).getString();
				will(returnValue("link-id-new"));
			}
		});

		LinkTransformation result = testable.parse(i_linkTransformation_mock);
		assertEquals("link-name", result.getLinkName());
		assertEquals("link-objectId", result.getObjectId());
		assertEquals("link-id-old", result.getLinkObjectId2Old());
		assertEquals("link-id-new", result.getLinkObjectId2New());
	}

	@Test
	public void parse_null_ids() {
		final Individual i_linkTransformation_mock = context.mock(Individual.class, "i-linkTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");
		final DatatypeProperty dp_oldValue_mock = context.mock(DatatypeProperty.class, "dp-oldValue");
		final DatatypeProperty dp_newValue_mock = context.mock(DatatypeProperty.class, "dp-newValue");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_objectId_mock = context.mock(Statement.class, "st-objectId");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_objectId_mock);
				will(returnValue(st_objectId_mock));

				oneOf(st_objectId_mock).getString();
				will(returnValue("link-objectId"));

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("link-name"));

				oneOf(owlModel_mock).getDataProperty_oldValue();
				will(returnValue(dp_oldValue_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_oldValue_mock);
				will(returnValue(null));

				oneOf(owlModel_mock).getDataProperty_newValue();
				will(returnValue(dp_newValue_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_newValue_mock);
				will(returnValue(null));
			}
		});

		LinkTransformation result = testable.parse(i_linkTransformation_mock);
		assertEquals("link-name", result.getLinkName());
		assertEquals("link-objectId", result.getObjectId());
		assertNull(result.getLinkObjectId2Old());
		assertNull(result.getLinkObjectId2New());
	}
}
