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

import planning.model.Link;

public class LinkOWLSchemaTest {

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

	LinkOWLSchema testable;

	PlanningOWLModel owlModel_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(PlanningOWLModel.class);

		testable = new LinkOWLSchema(owlModel_mock);
	}

	@Test
	public void combine() {
		final Link link = new Link("link-name", "link-id-1", "link-id-2");
		final Individual i_link_mock = context.mock(Individual.class, "i-link");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_id1_mock = context.mock(DatatypeProperty.class, "dp-id1");
		final DatatypeProperty dp_id2_mock = context.mock(DatatypeProperty.class, "dp-id2");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_Link();
				will(returnValue(i_link_mock));

				oneOf(i_link_mock).addLabel("Link \"link-name\"", "en");

				oneOf(i_link_mock).addLabel("Связь \"link-name\"", "ru");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_link_mock).addProperty(dp_name_mock, "link-name");

				oneOf(owlModel_mock).getDataProperty_id1();
				will(returnValue(dp_id1_mock));

				oneOf(i_link_mock).addProperty(dp_id1_mock, "link-id-1");

				oneOf(owlModel_mock).getDataProperty_id2();
				will(returnValue(dp_id2_mock));

				oneOf(i_link_mock).addProperty(dp_id2_mock, "link-id-2");
			}
		});

		assertEquals(i_link_mock, testable.combine(link));
	}

	@Test
	public void combine_null_ids() {
		final Link link = new Link("link-name", null, null);
		final Individual i_link_mock = context.mock(Individual.class, "i-link");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_Link();
				will(returnValue(i_link_mock));

				oneOf(i_link_mock).addLabel("Link \"link-name\"", "en");

				oneOf(i_link_mock).addLabel("Связь \"link-name\"", "ru");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_link_mock).addProperty(dp_name_mock, "link-name");
			}
		});

		assertEquals(i_link_mock, testable.combine(link));
	}

	@Test
	public void parse() {
		final Individual i_link_mock = context.mock(Individual.class, "i-link");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_id1_mock = context.mock(DatatypeProperty.class, "dp-id1");
		final DatatypeProperty dp_id2_mock = context.mock(DatatypeProperty.class, "dp-id2");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_id1_mock = context.mock(Statement.class, "st-id1");
		final Statement st_id2_mock = context.mock(Statement.class, "st-id2");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_link_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("link-name"));

				oneOf(owlModel_mock).getDataProperty_id1();
				will(returnValue(dp_id1_mock));

				oneOf(i_link_mock).getProperty(dp_id1_mock);
				will(returnValue(st_id1_mock));

				oneOf(st_id1_mock).getString();
				will(returnValue("link-id-1"));

				oneOf(owlModel_mock).getDataProperty_id2();
				will(returnValue(dp_id2_mock));

				oneOf(i_link_mock).getProperty(dp_id2_mock);
				will(returnValue(st_id2_mock));

				oneOf(st_id2_mock).getString();
				will(returnValue("link-id-2"));
			}
		});

		Link result = testable.parse(i_link_mock);
		assertEquals("link-name", result.getName());
		assertEquals("link-id-1", result.getId1());
		assertEquals("link-id-2", result.getId2());
	}

	@Test
	public void parse_null_ids() {
		final Individual i_link_mock = context.mock(Individual.class, "i-link");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_id1_mock = context.mock(DatatypeProperty.class, "dp-id1");
		final DatatypeProperty dp_id2_mock = context.mock(DatatypeProperty.class, "dp-id2");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_link_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("link-name"));

				oneOf(owlModel_mock).getDataProperty_id1();
				will(returnValue(dp_id1_mock));

				oneOf(i_link_mock).getProperty(dp_id1_mock);
				will(returnValue(null));

				oneOf(owlModel_mock).getDataProperty_id2();
				will(returnValue(dp_id2_mock));

				oneOf(i_link_mock).getProperty(dp_id2_mock);
				will(returnValue(null));
			}
		});

		Link result = testable.parse(i_link_mock);
		assertEquals("link-name", result.getName());
		assertNull(result.getId1());
		assertNull(result.getId2());
	}
}
