package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.jena.datatypes.xsd.XSDDatatype;
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

import planning.model.LuaScriptLine;

public class LuaScriptLineOWLSchemaTest {

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

	LuaScriptLineOWLSchema testable;

	PlanningOWLModel owlModel_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(PlanningOWLModel.class);

		testable = new LuaScriptLineOWLSchema(owlModel_mock);
	}

	@Test
	public void combine() {
		final LuaScriptLine line = new LuaScriptLine(1, "line-text");
		final Individual i_line_mock = context.mock(Individual.class, "i-line");
		final DatatypeProperty dp_text_mock = context.mock(DatatypeProperty.class, "dp-text");
		final DatatypeProperty dp_number_mock = context.mock(DatatypeProperty.class, "dp-number");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_Line();
				will(returnValue(i_line_mock));

				oneOf(i_line_mock).addLabel("Линия \"1\"", "ru");

				oneOf(i_line_mock).addLabel("Line \"1\"", "en");

				oneOf(owlModel_mock).getDataProperty_text();
				will(returnValue(dp_text_mock));

				oneOf(i_line_mock).addProperty(dp_text_mock, "line-text");

				oneOf(owlModel_mock).getDataProperty_number();
				will(returnValue(dp_number_mock));

				oneOf(i_line_mock).addProperty(dp_number_mock, "1", XSDDatatype.XSDinteger);
			}
		});

		assertEquals(i_line_mock, testable.combine(line));
	}

	@Test
	public void parse() {
		final Individual i_line_mock = context.mock(Individual.class, "i-line");
		final DatatypeProperty dp_text_mock = context.mock(DatatypeProperty.class, "dp-text");
		final DatatypeProperty dp_number_mock = context.mock(DatatypeProperty.class, "dp-number");
		final Statement st_text_mock = context.mock(Statement.class, "st-text");
		final Statement st_number_mock = context.mock(Statement.class, "st-number");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_text();
				will(returnValue(dp_text_mock));

				oneOf(i_line_mock).getProperty(dp_text_mock);
				will(returnValue(st_text_mock));

				oneOf(st_text_mock).getString();
				will(returnValue("line-text"));

				oneOf(owlModel_mock).getDataProperty_number();
				will(returnValue(dp_number_mock));

				oneOf(i_line_mock).getProperty(dp_number_mock);
				will(returnValue(st_number_mock));

				oneOf(st_number_mock).getInt();
				will(returnValue(1));
			}
		});

		LuaScriptLine result = testable.parse(i_line_mock);
		assertEquals(1, result.getNumber());
		assertEquals("line-text", result.getText());
	}
}
