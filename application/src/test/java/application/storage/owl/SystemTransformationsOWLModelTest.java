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

public class SystemTransformationsOWLModelTest {

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

	SystemTransformationsOWLModel testable;

	@BeforeEach
	public void setup() {
		testable = new SystemTransformationsOWLModel();
	}

	@Test
	public void getOWLSchema() {
		assertTrue(testable.getOWLSchema() instanceof SystemTransformationsOWLSchema);
	}

	@Test
	public void createOntologyModel() {
		testable.createOntologyModel();

		assertEquals(SystemTransformationsOWLModel.URI_Action, testable.getClass_Action().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_AttributeTemplate, testable.getClass_AttributeTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_AttributeTransformation, testable.getClass_AttributeTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_Line, testable.getClass_Line().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_LinkTemplate, testable.getClass_LinkTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_LinkTransformation, testable.getClass_LinkTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_ObjectTemplate, testable.getClass_ObjectTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_ActionFunction, testable.getClass_ActionFunction().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_SystemTemplate, testable.getClass_SystemTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_SystemTransformation, testable.getClass_SystemTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_SystemTransformations, testable.getClass_SystemTransformations().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_Transformation, testable.getClass_Transformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_Transformations, testable.getClass_Transformations().getURI());

		assertEquals(SystemTransformationsOWLModel.URI_name, testable.getDataProperty_name().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_newValue, testable.getDataProperty_newValue().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_number, testable.getDataProperty_number().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_objectId, testable.getDataProperty_objectId().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_objectId1, testable.getDataProperty_objectId1().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_objectId2, testable.getDataProperty_objectId2().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_oldValue, testable.getDataProperty_oldValue().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_text, testable.getDataProperty_text().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_value, testable.getDataProperty_value().getURI());

		assertEquals(SystemTransformationsOWLModel.URI_areTransformationsOf, testable.getObjectProperty_areTransformationsOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasAction, testable.getObjectProperty_hasAction().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasAttributeTemplate, testable.getObjectProperty_hasAttributeTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasAttributeTransformation, testable.getObjectProperty_hasAttributeTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasLine, testable.getObjectProperty_hasLine().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasLinkTemplate, testable.getObjectProperty_hasLinkTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasLinkTransformation, testable.getObjectProperty_hasLinkTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasObjectTemplate, testable.getObjectProperty_hasObjectTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasParameterUpdater, testable.getObjectProperty_hasParameterUpdater().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasPreConditionChecker, testable.getObjectProperty_hasPreConditionChecker().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasSystemTemplate, testable.getObjectProperty_hasSystemTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasSystemTransformation, testable.getObjectProperty_hasSystemTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasTransformation, testable.getObjectProperty_hasTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasTransformations, testable.getObjectProperty_hasTransformations().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isActionOf, testable.getObjectProperty_isActionOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isAttributeTemplateOf, testable.getObjectProperty_isAttributeTemplateOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isAttributeTransformationOf, testable.getObjectProperty_isAttributeTransformationOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isLineOf, testable.getObjectProperty_isLineOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isLinkTemplateOf, testable.getObjectProperty_isLinkTemplateOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isLinkTransformationOf, testable.getObjectProperty_isLinkTransformationOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isObjectTemplateOf, testable.getObjectProperty_isObjectTemplateOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isParameterUpdaterOf, testable.getObjectProperty_isParameterUpdaterOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isPreConditionCheckerOf, testable.getObjectProperty_isPreConditionCheckerOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isSystemTemplateOf, testable.getObjectProperty_isSystemTemplateOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isSystemTransformationOf, testable.getObjectProperty_isSystemTransformationOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isTransformationOf, testable.getObjectProperty_isTransformationOf().getURI());
	}

	@Test
	public void connectOntologyModel() {
		final SystemTransformationsOWLModel newOwlModel = new SystemTransformationsOWLModel();
		newOwlModel.createOntologyModel();

		testable.connectOntologyModel(newOwlModel.getOntologyModel());

		assertEquals(SystemTransformationsOWLModel.URI_Action, testable.getClass_Action().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_AttributeTemplate, testable.getClass_AttributeTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_AttributeTransformation, testable.getClass_AttributeTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_Line, testable.getClass_Line().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_LinkTemplate, testable.getClass_LinkTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_LinkTransformation, testable.getClass_LinkTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_ObjectTemplate, testable.getClass_ObjectTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_ActionFunction, testable.getClass_ActionFunction().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_SystemTemplate, testable.getClass_SystemTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_SystemTransformation, testable.getClass_SystemTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_SystemTransformations, testable.getClass_SystemTransformations().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_Transformation, testable.getClass_Transformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_Transformations, testable.getClass_Transformations().getURI());

		assertEquals(SystemTransformationsOWLModel.URI_name, testable.getDataProperty_name().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_newValue, testable.getDataProperty_newValue().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_number, testable.getDataProperty_number().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_objectId, testable.getDataProperty_objectId().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_objectId1, testable.getDataProperty_objectId1().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_objectId2, testable.getDataProperty_objectId2().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_oldValue, testable.getDataProperty_oldValue().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_text, testable.getDataProperty_text().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_value, testable.getDataProperty_value().getURI());

		assertEquals(SystemTransformationsOWLModel.URI_areTransformationsOf, testable.getObjectProperty_areTransformationsOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasAction, testable.getObjectProperty_hasAction().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasAttributeTemplate, testable.getObjectProperty_hasAttributeTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasAttributeTransformation, testable.getObjectProperty_hasAttributeTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasLine, testable.getObjectProperty_hasLine().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasLinkTemplate, testable.getObjectProperty_hasLinkTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasLinkTransformation, testable.getObjectProperty_hasLinkTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasObjectTemplate, testable.getObjectProperty_hasObjectTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasParameterUpdater, testable.getObjectProperty_hasParameterUpdater().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasPreConditionChecker, testable.getObjectProperty_hasPreConditionChecker().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasSystemTemplate, testable.getObjectProperty_hasSystemTemplate().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasSystemTransformation, testable.getObjectProperty_hasSystemTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasTransformation, testable.getObjectProperty_hasTransformation().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_hasTransformations, testable.getObjectProperty_hasTransformations().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isActionOf, testable.getObjectProperty_isActionOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isAttributeTemplateOf, testable.getObjectProperty_isAttributeTemplateOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isAttributeTransformationOf, testable.getObjectProperty_isAttributeTransformationOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isLineOf, testable.getObjectProperty_isLineOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isLinkTemplateOf, testable.getObjectProperty_isLinkTemplateOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isLinkTransformationOf, testable.getObjectProperty_isLinkTransformationOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isObjectTemplateOf, testable.getObjectProperty_isObjectTemplateOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isParameterUpdaterOf, testable.getObjectProperty_isParameterUpdaterOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isPreConditionCheckerOf, testable.getObjectProperty_isPreConditionCheckerOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isSystemTemplateOf, testable.getObjectProperty_isSystemTemplateOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isSystemTransformationOf, testable.getObjectProperty_isSystemTransformationOf().getURI());
		assertEquals(SystemTransformationsOWLModel.URI_isTransformationOf, testable.getObjectProperty_isTransformationOf().getURI());
	}

	@Test
	public void getUniqueURI() {
		assertNotEquals(testable.getUniqueURI(), testable.getUniqueURI());
	}

	@Test
	public void getUniqueURI_startsWith_NameSpace() {
		assertTrue(testable.getUniqueURI().startsWith(SystemTransformationsOWLModel.NS + "#"));
	}

	@Test
	public void newIndividual() {
		testable.createOntologyModel();

		Individual action = testable.newIndividual_Action();
		assertEquals(action.getOntClass(), testable.getClass_Action());
		assertNotEquals(action.getURI(), testable.newIndividual_Action().getURI());

		Individual attributeTemplate = testable.newIndividual_AttributeTemplate();
		assertEquals(attributeTemplate.getOntClass(), testable.getClass_AttributeTemplate());
		assertNotEquals(attributeTemplate.getURI(), testable.newIndividual_AttributeTemplate().getURI());

		Individual attributeTransformation = testable.newIndividual_AttributeTransformation();
		assertEquals(attributeTransformation.getOntClass(), testable.getClass_AttributeTransformation());
		assertNotEquals(attributeTransformation.getURI(), testable.newIndividual_AttributeTransformation().getURI());

		Individual line = testable.newIndividual_Line();
		assertEquals(line.getOntClass(), testable.getClass_Line());
		assertNotEquals(line.getURI(), testable.newIndividual_Line().getURI());

		Individual linkTemplate = testable.newIndividual_LinkTemplate();
		assertEquals(linkTemplate.getOntClass(), testable.getClass_LinkTemplate());
		assertNotEquals(linkTemplate.getURI(), testable.newIndividual_LinkTemplate().getURI());

		Individual linkTransformation = testable.newIndividual_LinkTransformation();
		assertEquals(linkTransformation.getOntClass(), testable.getClass_LinkTransformation());
		assertNotEquals(linkTransformation.getURI(), testable.newIndividual_LinkTransformation().getURI());

		Individual objectTemplate = testable.newIndividual_ObjectTemplate();
		assertEquals(objectTemplate.getOntClass(), testable.getClass_ObjectTemplate());
		assertNotEquals(objectTemplate.getURI(), testable.newIndividual_ObjectTemplate().getURI());

		Individual actionFunction = testable.newIndividual_ActionFunction();
		assertEquals(actionFunction.getOntClass(), testable.getClass_ActionFunction());
		assertNotEquals(actionFunction.getURI(), testable.newIndividual_ActionFunction().getURI());

		Individual systemTemplate = testable.newIndividual_SystemTemplate();
		assertEquals(systemTemplate.getOntClass(), testable.getClass_SystemTemplate());
		assertNotEquals(systemTemplate.getURI(), testable.newIndividual_SystemTemplate().getURI());

		Individual systemTransformation = testable.newIndividual_SystemTransformation();
		assertEquals(systemTransformation.getOntClass(), testable.getClass_SystemTransformation());
		assertNotEquals(systemTransformation.getURI(), testable.newIndividual_SystemTransformation().getURI());

		Individual systemTransformations = testable.newIndividual_SystemTransformations();
		assertEquals(systemTransformations.getOntClass(), testable.getClass_SystemTransformations());
		assertNotEquals(systemTransformations.getURI(), testable.newIndividual_SystemTransformations().getURI());

		Individual transformation = testable.newIndividual_Transformation();
		assertEquals(transformation.getOntClass(), testable.getClass_Transformation());
		assertNotEquals(transformation.getURI(), testable.newIndividual_Transformation().getURI());

		Individual transformations = testable.newIndividual_Transformations();
		assertEquals(transformations.getOntClass(), testable.getClass_Transformations());
		assertNotEquals(transformations.getURI(), testable.newIndividual_Transformations().getURI());
	}
}
