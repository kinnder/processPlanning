package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.LinkTemplate;
import planning.model.SystemObjectTemplate;
import planning.model.SystemTemplate;

public class SystemTemplateOWLSchemaTest {

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

	SystemTemplateOWLSchema testable;

	PlanningOWLModel owlModel_mock;

	SystemObjectTemplateOWLSchema systemObjectTemplateOWLSchema_mock;

	LinkTemplateOWLSchema linkTemplateOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(PlanningOWLModel.class);
		systemObjectTemplateOWLSchema_mock = context.mock(SystemObjectTemplateOWLSchema.class);
		linkTemplateOWLSchema_mock = context.mock(LinkTemplateOWLSchema.class);

		testable = new SystemTemplateOWLSchema(owlModel_mock, systemObjectTemplateOWLSchema_mock,
				linkTemplateOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemTemplateOWLSchema(new PlanningOWLModel());
	}

	@Test
	public void combine() {
		final SystemObjectTemplate systemObjectTemplate = new SystemObjectTemplate("test-object");
		final LinkTemplate linkTemplate = new LinkTemplate("test-link", null, null);
		final SystemTemplate systemTemplate = new SystemTemplate();
		systemTemplate.addObjectTemplate(systemObjectTemplate);
		systemTemplate.addLinkTemplate(linkTemplate);

		final Individual i_systemTemplate_mock = context.mock(Individual.class, "i-systemTemplate");
		final Individual i_systemObjectTemplate_mock = context.mock(Individual.class, "i-systemObjectTemplate");
		final Individual i_linkTemplate_mock = context.mock(Individual.class, "i-linkTemplate");
		final ObjectProperty op_hasSystemObjectTemplate_mock = context.mock(ObjectProperty.class, "op-hasSystemObjectTemplate");
		final ObjectProperty op_hasLinkTemplate_mock = context.mock(ObjectProperty.class, "op-hasLinkTemplate");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_SystemTemplate();
				will(returnValue(i_systemTemplate_mock));

				oneOf(i_systemTemplate_mock).addLabel("System template", "en");

				oneOf(i_systemTemplate_mock).addLabel("Шаблон системы", "ru");

				oneOf(systemObjectTemplateOWLSchema_mock).combine(systemObjectTemplate);
				will(returnValue(i_systemObjectTemplate_mock));

				oneOf(owlModel_mock).getObjectProperty_hasObjectTemplate();
				will(returnValue(op_hasSystemObjectTemplate_mock));

				oneOf(i_systemTemplate_mock).addProperty(op_hasSystemObjectTemplate_mock, i_systemObjectTemplate_mock);

				oneOf(linkTemplateOWLSchema_mock).combine(linkTemplate);
				will(returnValue(i_linkTemplate_mock));

				oneOf(owlModel_mock).getObjectProperty_hasLinkTemplate();
				will(returnValue(op_hasLinkTemplate_mock));

				oneOf(i_systemTemplate_mock).addProperty(op_hasLinkTemplate_mock, i_linkTemplate_mock);
			}
		});

		assertEquals(i_systemTemplate_mock, testable.combine(systemTemplate));
	}

	@Test
	public void combine_link_with_objects() {
		final SystemObjectTemplate systemObjectTemplate1 = new SystemObjectTemplate("test-object-1-id");
		final SystemObjectTemplate systemObjectTemplate2 = new SystemObjectTemplate("test-object-2-id");
		final LinkTemplate linkTemplate = new LinkTemplate("test-link", "test-object-1-id", "test-object-2-id");
		final SystemTemplate systemTemplate = new SystemTemplate();
		systemTemplate.addObjectTemplate(systemObjectTemplate1);
		systemTemplate.addObjectTemplate(systemObjectTemplate2);
		systemTemplate.addLinkTemplate(linkTemplate);

		final Individual i_systemTemplate_mock = context.mock(Individual.class, "i-systemTemplate");
		final Individual i_systemObjectTemplate1_mock = context.mock(Individual.class, "i-systemObjectTemplate1");
		final Individual i_systemObjectTemplate2_mock = context.mock(Individual.class, "i-systemObjectTemplate2");
		final Individual i_linkTemplate_mock = context.mock(Individual.class, "i-linkTemplate");
		final ObjectProperty op_hasSystemObjectTemplate_mock = context.mock(ObjectProperty.class, "op-hasSystemObjectTemplate");
		final ObjectProperty op_hasSystemObjectTemplate1_mock = context.mock(ObjectProperty.class, "op-hasSystemObjectTemplate1");
		final ObjectProperty op_hasSystemObjectTemplate2_mock = context.mock(ObjectProperty.class, "op-hasSystemObjectTemplate2");
		final ObjectProperty op_hasLinkTemplate_mock = context.mock(ObjectProperty.class, "op-hasLinkTemplate");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_SystemTemplate();
				will(returnValue(i_systemTemplate_mock));

				oneOf(i_systemTemplate_mock).addLabel("System template", "en");

				oneOf(i_systemTemplate_mock).addLabel("Шаблон системы", "ru");

				oneOf(systemObjectTemplateOWLSchema_mock).combine(systemObjectTemplate1);
				will(returnValue(i_systemObjectTemplate1_mock));

				oneOf(owlModel_mock).getObjectProperty_hasObjectTemplate();
				will(returnValue(op_hasSystemObjectTemplate_mock));

				oneOf(i_systemTemplate_mock).addProperty(op_hasSystemObjectTemplate_mock, i_systemObjectTemplate1_mock);

				oneOf(systemObjectTemplateOWLSchema_mock).combine(systemObjectTemplate2);
				will(returnValue(i_systemObjectTemplate2_mock));

				oneOf(owlModel_mock).getObjectProperty_hasObjectTemplate();
				will(returnValue(op_hasSystemObjectTemplate_mock));

				oneOf(i_systemTemplate_mock).addProperty(op_hasSystemObjectTemplate_mock, i_systemObjectTemplate2_mock);

				oneOf(linkTemplateOWLSchema_mock).combine(linkTemplate);
				will(returnValue(i_linkTemplate_mock));

				oneOf(owlModel_mock).getObjectProperty_hasLinkTemplate();
				will(returnValue(op_hasLinkTemplate_mock));

				oneOf(i_systemTemplate_mock).addProperty(op_hasLinkTemplate_mock, i_linkTemplate_mock);

				oneOf(owlModel_mock).getObjectProperty_hasObjectTemplate1();
				will(returnValue(op_hasSystemObjectTemplate1_mock));

				oneOf(i_linkTemplate_mock).addProperty(op_hasSystemObjectTemplate1_mock, i_systemObjectTemplate1_mock);

				oneOf(owlModel_mock).getObjectProperty_hasObjectTemplate2();
				will(returnValue(op_hasSystemObjectTemplate2_mock));

				oneOf(i_linkTemplate_mock).addProperty(op_hasSystemObjectTemplate2_mock, i_systemObjectTemplate2_mock);
			}
		});

		assertEquals(i_systemTemplate_mock, testable.combine(systemTemplate));
	}

	@Test
	public void parse() {
		final Individual i_systemTemplate_mock = context.mock(Individual.class, "i-systemTemplate");
		final OntClass oc_systemObjectTemplate_mock = context.mock(OntClass.class, "oc-systemObjectTemplate");
		final OntClass oc_linkTemplate_mock = context.mock(OntClass.class, "oc-linkTemplate");
		final ObjectProperty op_hasSystemObjectTemplate = context.mock(ObjectProperty.class,
				"op-hasSystemObjectTemplate");
		final ObjectProperty op_hasLinkTemplate = context.mock(ObjectProperty.class, "op-hasLinkTemplate");
		final SystemObjectTemplate systemObjectTemplate = new SystemObjectTemplate("test-object");
		final LinkTemplate linkTemplate = new LinkTemplate("test-link", null, null);

		final Individual i_systemObjectTemplate_mock = context.mock(Individual.class, "i-systemObjectTemplate");
		final ExtendedIterator<Individual> systemObjectTemplateIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemObjectTemplate_mock).iterator());

		final Individual i_linkTemplate_mock = context.mock(Individual.class, "i-linkTemplate");
		final ExtendedIterator<Individual> linkTemplateIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_linkTemplate_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getClass_ObjectTemplate();
				will(returnValue(oc_systemObjectTemplate_mock));

				oneOf(oc_systemObjectTemplate_mock).listInstances();
				will(returnValue(systemObjectTemplateIterator));

				oneOf(owlModel_mock).getObjectProperty_hasObjectTemplate();
				will(returnValue(op_hasSystemObjectTemplate));

				oneOf(i_systemTemplate_mock).hasProperty(op_hasSystemObjectTemplate, i_systemObjectTemplate_mock);
				will(returnValue(true));

				oneOf(i_systemObjectTemplate_mock).asIndividual();
				will(returnValue(i_systemObjectTemplate_mock));

				oneOf(systemObjectTemplateOWLSchema_mock).parse(i_systemObjectTemplate_mock);
				will(returnValue(systemObjectTemplate));

				oneOf(owlModel_mock).getClass_LinkTemplate();
				will(returnValue(oc_linkTemplate_mock));

				oneOf(oc_linkTemplate_mock).listInstances();
				will(returnValue(linkTemplateIterator));

				oneOf(owlModel_mock).getObjectProperty_hasLinkTemplate();
				will(returnValue(op_hasLinkTemplate));

				oneOf(i_systemTemplate_mock).hasProperty(op_hasLinkTemplate, i_linkTemplate_mock);
				will(returnValue(true));

				oneOf(i_linkTemplate_mock).asIndividual();
				will(returnValue(i_linkTemplate_mock));

				oneOf(linkTemplateOWLSchema_mock).parse(i_linkTemplate_mock);
				will(returnValue(linkTemplate));
			}
		});

		SystemTemplate result = testable.parse(i_systemTemplate_mock);
		assertTrue(result.getLinkTemplates().contains(linkTemplate));
		assertTrue(result.getObjectTemplates().contains(systemObjectTemplate));
	}
}
