package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.jena.ontology.Individual;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class TaskDescriptionOWLModelTest {

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

	TaskDescriptionOWLModel testable;

	@BeforeEach
	public void setup() {
		testable = new TaskDescriptionOWLModel();
	}

	@Test
	public void getOWLSchema() {
		assertTrue(testable.getOWLSchema() instanceof TaskDescriptionOWLSchema);
	}

	@Test
	public void createOntologyModel() {
		testable.createOntologyModel();

		assertEquals(TaskDescriptionOWLModel.URI_Attribute, testable.getClass_Attribute().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_FinalSystem, testable.getClass_FinalSystem().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_InitialSystem, testable.getClass_InitialSystem().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_Link, testable.getClass_Link().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_System, testable.getClass_System().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_SystemObject, testable.getClass_SystemObject().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_TaskDescription, testable.getClass_TaskDescription().getURI());

		assertEquals(TaskDescriptionOWLModel.URI_id, testable.getDataProperty_id().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_name, testable.getDataProperty_name().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_objectId1, testable.getDataProperty_objectId1().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_objectId2, testable.getDataProperty_objectId2().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_value, testable.getDataProperty_value().getURI());

		assertEquals(TaskDescriptionOWLModel.URI_hasAttribute, testable.getObjectProperty_hasAttribute().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_hasFinalSystem, testable.getObjectProperty_hasFinalSystem().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_hasInitialSystem, testable.getObjectProperty_hasInitialSystem().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_hasLink, testable.getObjectProperty_hasLink().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_hasSystemObject, testable.getObjectProperty_hasSystemObject().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_isAttributeOf, testable.getObjectProperty_isAttributeOf().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_isFinalSystemOf, testable.getObjectProperty_isFinalSystemOf().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_isInitialSystemOf, testable.getObjectProperty_isInitialSystemOf().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_isLinkOf, testable.getObjectProperty_isLinkOf().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_isSystemObjectOf, testable.getObjectProperty_isSystemObjectOf().getURI());
	}

	@Test
	public void connectOntologyModel() {
		final TaskDescriptionOWLModel newOwlModel = new TaskDescriptionOWLModel();
		newOwlModel.createOntologyModel();

		testable.connectOntologyModel(newOwlModel.getOntologyModel());

		assertEquals(TaskDescriptionOWLModel.URI_Attribute, testable.getClass_Attribute().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_FinalSystem, testable.getClass_FinalSystem().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_InitialSystem, testable.getClass_InitialSystem().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_Link, testable.getClass_Link().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_System, testable.getClass_System().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_SystemObject, testable.getClass_SystemObject().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_TaskDescription, testable.getClass_TaskDescription().getURI());

		assertEquals(TaskDescriptionOWLModel.URI_id, testable.getDataProperty_id().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_name, testable.getDataProperty_name().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_objectId1, testable.getDataProperty_objectId1().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_objectId2, testable.getDataProperty_objectId2().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_value, testable.getDataProperty_value().getURI());

		assertEquals(TaskDescriptionOWLModel.URI_hasAttribute, testable.getObjectProperty_hasAttribute().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_hasFinalSystem, testable.getObjectProperty_hasFinalSystem().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_hasInitialSystem, testable.getObjectProperty_hasInitialSystem().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_hasLink, testable.getObjectProperty_hasLink().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_hasSystemObject, testable.getObjectProperty_hasSystemObject().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_isAttributeOf, testable.getObjectProperty_isAttributeOf().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_isFinalSystemOf, testable.getObjectProperty_isFinalSystemOf().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_isInitialSystemOf, testable.getObjectProperty_isInitialSystemOf().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_isLinkOf, testable.getObjectProperty_isLinkOf().getURI());
		assertEquals(TaskDescriptionOWLModel.URI_isSystemObjectOf, testable.getObjectProperty_isSystemObjectOf().getURI());
	}

	@Test
	public void getUniqueURI() {
		assertNotEquals(testable.getUniqueURI(), testable.getUniqueURI());
	}

	@Test
	public void getUniqueURI_startsWith_NameSpace() {
		assertTrue(testable.getUniqueURI().startsWith(TaskDescriptionOWLModel.NS + "#"));
	}

	@Test
	public void newIndividual() {
		testable.createOntologyModel();

		Individual attribute = testable.newIndividual_Attribute();
		assertEquals(attribute.getOntClass(), testable.getClass_Attribute());
		assertNotEquals(attribute.getURI(), testable.newIndividual_Attribute().getURI());

		Individual link = testable.newIndividual_Link();
		assertEquals(link.getOntClass(), testable.getClass_Link());
		assertNotEquals(link.getURI(), testable.newIndividual_Link().getURI());

		Individual system = testable.newIndividual_System();
		assertEquals(system.getOntClass(), testable.getClass_System());
		assertNotEquals(system.getURI(), testable.newIndividual_System().getURI());

		Individual systemObject = testable.newIndividual_SystemObject();
		assertEquals(systemObject.getOntClass(), testable.getClass_SystemObject());
		assertNotEquals(systemObject.getURI(), testable.newIndividual_SystemObject().getURI());

		Individual taskDescription = testable.newIndividual_TaskDescription();
		assertEquals(taskDescription.getOntClass(), testable.getClass_TaskDescription());
		assertNotEquals(taskDescription.getURI(), testable.newIndividual_TaskDescription().getURI());
	}
}
