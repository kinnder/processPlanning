package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class PlanningOWLModelTest {

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

	PlanningOWLModel testable;

	@BeforeEach
	public void setup() {
		testable = new PlanningOWLModel();
	}

	@Test
	public void getUniqueURI() {
		assertNotEquals(testable.getUniqueURI(), testable.getUniqueURI());
	}

	@Test
	public void getUniqueURI_startsWith_NameSpace() {
		assertTrue(testable.getUniqueURI().startsWith(PlanningOWLModel.NS + "#"));
	}

	@Test
	public void getOntologyModel() {
		assertNull(testable.getOntologyModel());
	}

	@Test
	public void createOntologyModelBase() {
		OntModel model = testable.createOntologyModelBase();
		assertNotNull(model);
		assertEquals(19, model.listObjects().toList().size());
		assertEquals(106, model.listStatements().toList().size());
	}

	@Test
	public void createOntologyModel() {
		testable.createOntologyModel();

		assertEquals(PlanningOWLModel.URI_Action, testable.getClass_Action().getURI());
		assertEquals(PlanningOWLModel.URI_ActionFunction, testable.getClass_ActionFunction().getURI());
		assertEquals(PlanningOWLModel.URI_Attribute, testable.getClass_Attribute().getURI());
		assertEquals(PlanningOWLModel.URI_AttributeTemplate, testable.getClass_AttributeTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_AttributeTransformation, testable.getClass_AttributeTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_Edge, testable.getClass_Edge().getURI());
		assertEquals(PlanningOWLModel.URI_FinalSystem, testable.getClass_FinalSystem().getURI());
		assertEquals(PlanningOWLModel.URI_InitialSystem, testable.getClass_InitialSystem().getURI());
		assertEquals(PlanningOWLModel.URI_Line, testable.getClass_Line().getURI());
		assertEquals(PlanningOWLModel.URI_Link, testable.getClass_Link().getURI());
		assertEquals(PlanningOWLModel.URI_LinkTemplate, testable.getClass_LinkTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_LinkTransformation, testable.getClass_LinkTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_Node, testable.getClass_Node().getURI());
		assertEquals(PlanningOWLModel.URI_NodeNetwork, testable.getClass_NodeNetwork().getURI());
		assertEquals(PlanningOWLModel.URI_ObjectTemplate, testable.getClass_ObjectTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_Parameter, testable.getClass_Parameter().getURI());
		assertEquals(PlanningOWLModel.URI_Process, testable.getClass_Process().getURI());
		assertEquals(PlanningOWLModel.URI_System, testable.getClass_System().getURI());
		assertEquals(PlanningOWLModel.URI_SystemObject, testable.getClass_SystemObject().getURI());
		assertEquals(PlanningOWLModel.URI_SystemOperation, testable.getClass_SystemOperation().getURI());
		assertEquals(PlanningOWLModel.URI_SystemTemplate, testable.getClass_SystemTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_SystemTransformation, testable.getClass_SystemTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_SystemTransformations, testable.getClass_SystemTransformations().getURI());
		assertEquals(PlanningOWLModel.URI_TaskDescription, testable.getClass_TaskDescription().getURI());
		assertEquals(PlanningOWLModel.URI_Transformation, testable.getClass_Transformation().getURI());
		assertEquals(PlanningOWLModel.URI_Transformations, testable.getClass_Transformations().getURI());

		assertEquals(PlanningOWLModel.URI_beginNodeId, testable.getDataProperty_beginNodeId().getURI());
		assertEquals(PlanningOWLModel.URI_checked, testable.getDataProperty_checked().getURI());
		assertEquals(PlanningOWLModel.URI_endNodeId, testable.getDataProperty_endNodeId().getURI());
		assertEquals(PlanningOWLModel.URI_id, testable.getDataProperty_id().getURI());
		assertEquals(PlanningOWLModel.URI_key, testable.getDataProperty_key().getURI());
		assertEquals(PlanningOWLModel.URI_name, testable.getDataProperty_name().getURI());
		assertEquals(PlanningOWLModel.URI_id2New, testable.getDataProperty_id2New().getURI());
		assertEquals(PlanningOWLModel.URI_number, testable.getDataProperty_number().getURI());
		assertEquals(PlanningOWLModel.URI_id1, testable.getDataProperty_id1().getURI());
		assertEquals(PlanningOWLModel.URI_id2, testable.getDataProperty_id2().getURI());
		assertEquals(PlanningOWLModel.URI_id2Old, testable.getDataProperty_id2Old().getURI());
		assertEquals(PlanningOWLModel.URI_text, testable.getDataProperty_text().getURI());
		assertEquals(PlanningOWLModel.URI_value, testable.getDataProperty_value().getURI());

		assertEquals(PlanningOWLModel.URI_areTransformationsOf, testable.getObjectProperty_areTransformationsOf().getURI());
		assertEquals(PlanningOWLModel.URI_hasAction, testable.getObjectProperty_hasAction().getURI());
		assertEquals(PlanningOWLModel.URI_hasAttribute, testable.getObjectProperty_hasAttribute().getURI());
		assertEquals(PlanningOWLModel.URI_hasAttributeTemplate, testable.getObjectProperty_hasAttributeTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_hasAttributeTransformation, testable.getObjectProperty_hasAttributeTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_hasBeginNode, testable.getObjectProperty_hasBeginNode().getURI());
		assertEquals(PlanningOWLModel.URI_hasEdge, testable.getObjectProperty_hasEdge().getURI());
		assertEquals(PlanningOWLModel.URI_hasEndNode, testable.getObjectProperty_hasEndNode().getURI());
		assertEquals(PlanningOWLModel.URI_hasFinalSystem, testable.getObjectProperty_hasFinalSystem().getURI());
		assertEquals(PlanningOWLModel.URI_hasInitialSystem, testable.getObjectProperty_hasInitialSystem().getURI());
		assertEquals(PlanningOWLModel.URI_hasLine, testable.getObjectProperty_hasLine().getURI());
		assertEquals(PlanningOWLModel.URI_hasLink, testable.getObjectProperty_hasLink().getURI());
		assertEquals(PlanningOWLModel.URI_hasLinkTemplate, testable.getObjectProperty_hasLinkTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_hasLinkTransformation, testable.getObjectProperty_hasLinkTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_hasNode, testable.getObjectProperty_hasNode().getURI());
		assertEquals(PlanningOWLModel.URI_hasObjectTemplate, testable.getObjectProperty_hasObjectTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_hasObjectTemplate1, testable.getObjectProperty_hasObjectTemplate1().getURI());
		assertEquals(PlanningOWLModel.URI_hasObjectTemplate2, testable.getObjectProperty_hasObjectTemplate2().getURI());
		assertEquals(PlanningOWLModel.URI_hasParameter, testable.getObjectProperty_hasParameter().getURI());
		assertEquals(PlanningOWLModel.URI_hasParameterUpdater, testable.getObjectProperty_hasParameterUpdater().getURI());
		assertEquals(PlanningOWLModel.URI_hasPreConditionChecker, testable.getObjectProperty_hasPreConditionChecker().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystem, testable.getObjectProperty_hasSystem().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemObject, testable.getObjectProperty_hasSystemObject().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemObject1, testable.getObjectProperty_hasSystemObject1().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemObject2, testable.getObjectProperty_hasSystemObject2().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemOperation, testable.getObjectProperty_hasSystemOperation().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemTemplate, testable.getObjectProperty_hasSystemTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemTransformation, testable.getObjectProperty_hasSystemTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_hasTransformation, testable.getObjectProperty_hasTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_hasTransformations, testable.getObjectProperty_hasTransformations().getURI());
		assertEquals(PlanningOWLModel.URI_isActionOf, testable.getObjectProperty_isActionOf().getURI());
		assertEquals(PlanningOWLModel.URI_isActionPerformedBy, testable.getObjectProperty_isActionPerformedBy().getURI());
		assertEquals(PlanningOWLModel.URI_isAttributeOf, testable.getObjectProperty_isAttributeOf().getURI());
		assertEquals(PlanningOWLModel.URI_isAttributeTemplateOf, testable.getObjectProperty_isAttributeTemplateOf().getURI());
		assertEquals(PlanningOWLModel.URI_isAttributeTransformationOf, testable.getObjectProperty_isAttributeTransformationOf().getURI());
		assertEquals(PlanningOWLModel.URI_isBeginNodeOf, testable.getObjectProperty_isBeginNodeOf().getURI());
		assertEquals(PlanningOWLModel.URI_isEdgeOf, testable.getObjectProperty_isEdgeOf().getURI());
		assertEquals(PlanningOWLModel.URI_isEndNodeOf, testable.getObjectProperty_isEndNodeOf().getURI());
		assertEquals(PlanningOWLModel.URI_isFinalSystemOf, testable.getObjectProperty_isFinalSystemOf().getURI());
		assertEquals(PlanningOWLModel.URI_isInitialSystemOf, testable.getObjectProperty_isInitialSystemOf().getURI());
		assertEquals(PlanningOWLModel.URI_isLineOf, testable.getObjectProperty_isLineOf().getURI());
		assertEquals(PlanningOWLModel.URI_isLinkOf, testable.getObjectProperty_isLinkOf().getURI());
		assertEquals(PlanningOWLModel.URI_isLinkTemplateOf, testable.getObjectProperty_isLinkTemplateOf().getURI());
		assertEquals(PlanningOWLModel.URI_isLinkTransformationOf, testable.getObjectProperty_isLinkTransformationOf().getURI());
		assertEquals(PlanningOWLModel.URI_isNodeOf, testable.getObjectProperty_isNodeOf().getURI());
		assertEquals(PlanningOWLModel.URI_isObjectTemplateOf, testable.getObjectProperty_isObjectTemplateOf().getURI());
		assertEquals(PlanningOWLModel.URI_isObjectTemplate1Of, testable.getObjectProperty_isObjectTemplate1Of().getURI());
		assertEquals(PlanningOWLModel.URI_isObjectTemplate2Of, testable.getObjectProperty_isObjectTemplate2Of().getURI());
		assertEquals(PlanningOWLModel.URI_isParameterOf, testable.getObjectProperty_isParameterOf().getURI());
		assertEquals(PlanningOWLModel.URI_isParameterUpdaterOf, testable.getObjectProperty_isParameterUpdaterOf().getURI());
		assertEquals(PlanningOWLModel.URI_isPreConditionCheckerOf, testable.getObjectProperty_isPreConditionCheckerOf().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemObjectOf, testable.getObjectProperty_isSystemObjectOf().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemObject1Of, testable.getObjectProperty_isSystemObject1Of().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemObject2Of, testable.getObjectProperty_isSystemObject2Of().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemOf, testable.getObjectProperty_isSystemOf().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemOperationOf, testable.getObjectProperty_isSystemOperationOf().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemOperationPerformedBy, testable.getObjectProperty_isSystemOperationPerformedBy().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemTemplateOf, testable.getObjectProperty_isSystemTemplateOf().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemTransformationOf, testable.getObjectProperty_isSystemTransformationOf().getURI());
		assertEquals(PlanningOWLModel.URI_isTransformationOf, testable.getObjectProperty_isTransformationOf().getURI());
		assertEquals(PlanningOWLModel.URI_performsAction, testable.getObjectProperty_performsAction().getURI());
		assertEquals(PlanningOWLModel.URI_performsSystemOperation, testable.getObjectProperty_performsSystemOperation().getURI());
	}

	@Test
	public void connectOntologyModel() {
		final PlanningOWLModel newOwlModel = new PlanningOWLModel();
		newOwlModel.createOntologyModel();

		testable.connectOntologyModel(newOwlModel.getOntologyModel());

		assertEquals(PlanningOWLModel.URI_Action, testable.getClass_Action().getURI());
		assertEquals(PlanningOWLModel.URI_ActionFunction, testable.getClass_ActionFunction().getURI());
		assertEquals(PlanningOWLModel.URI_Attribute, testable.getClass_Attribute().getURI());
		assertEquals(PlanningOWLModel.URI_AttributeTemplate, testable.getClass_AttributeTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_AttributeTransformation, testable.getClass_AttributeTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_Edge, testable.getClass_Edge().getURI());
		assertEquals(PlanningOWLModel.URI_FinalSystem, testable.getClass_FinalSystem().getURI());
		assertEquals(PlanningOWLModel.URI_InitialSystem, testable.getClass_InitialSystem().getURI());
		assertEquals(PlanningOWLModel.URI_Line, testable.getClass_Line().getURI());
		assertEquals(PlanningOWLModel.URI_Link, testable.getClass_Link().getURI());
		assertEquals(PlanningOWLModel.URI_LinkTemplate, testable.getClass_LinkTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_LinkTransformation, testable.getClass_LinkTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_Node, testable.getClass_Node().getURI());
		assertEquals(PlanningOWLModel.URI_NodeNetwork, testable.getClass_NodeNetwork().getURI());
		assertEquals(PlanningOWLModel.URI_ObjectTemplate, testable.getClass_ObjectTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_Parameter, testable.getClass_Parameter().getURI());
		assertEquals(PlanningOWLModel.URI_Process, testable.getClass_Process().getURI());
		assertEquals(PlanningOWLModel.URI_System, testable.getClass_System().getURI());
		assertEquals(PlanningOWLModel.URI_SystemObject, testable.getClass_SystemObject().getURI());
		assertEquals(PlanningOWLModel.URI_SystemOperation, testable.getClass_SystemOperation().getURI());
		assertEquals(PlanningOWLModel.URI_SystemTemplate, testable.getClass_SystemTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_SystemTransformation, testable.getClass_SystemTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_SystemTransformations, testable.getClass_SystemTransformations().getURI());
		assertEquals(PlanningOWLModel.URI_TaskDescription, testable.getClass_TaskDescription().getURI());
		assertEquals(PlanningOWLModel.URI_Transformation, testable.getClass_Transformation().getURI());
		assertEquals(PlanningOWLModel.URI_Transformations, testable.getClass_Transformations().getURI());

		assertEquals(PlanningOWLModel.URI_beginNodeId, testable.getDataProperty_beginNodeId().getURI());
		assertEquals(PlanningOWLModel.URI_checked, testable.getDataProperty_checked().getURI());
		assertEquals(PlanningOWLModel.URI_endNodeId, testable.getDataProperty_endNodeId().getURI());
		assertEquals(PlanningOWLModel.URI_id, testable.getDataProperty_id().getURI());
		assertEquals(PlanningOWLModel.URI_key, testable.getDataProperty_key().getURI());
		assertEquals(PlanningOWLModel.URI_name, testable.getDataProperty_name().getURI());
		assertEquals(PlanningOWLModel.URI_id2New, testable.getDataProperty_id2New().getURI());
		assertEquals(PlanningOWLModel.URI_number, testable.getDataProperty_number().getURI());
		assertEquals(PlanningOWLModel.URI_id1, testable.getDataProperty_id1().getURI());
		assertEquals(PlanningOWLModel.URI_id2, testable.getDataProperty_id2().getURI());
		assertEquals(PlanningOWLModel.URI_id2Old, testable.getDataProperty_id2Old().getURI());
		assertEquals(PlanningOWLModel.URI_text, testable.getDataProperty_text().getURI());
		assertEquals(PlanningOWLModel.URI_value, testable.getDataProperty_value().getURI());

		assertEquals(PlanningOWLModel.URI_areTransformationsOf, testable.getObjectProperty_areTransformationsOf().getURI());
		assertEquals(PlanningOWLModel.URI_hasAction, testable.getObjectProperty_hasAction().getURI());
		assertEquals(PlanningOWLModel.URI_hasAttribute, testable.getObjectProperty_hasAttribute().getURI());
		assertEquals(PlanningOWLModel.URI_hasAttributeTemplate, testable.getObjectProperty_hasAttributeTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_hasAttributeTransformation, testable.getObjectProperty_hasAttributeTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_hasBeginNode, testable.getObjectProperty_hasBeginNode().getURI());
		assertEquals(PlanningOWLModel.URI_hasEdge, testable.getObjectProperty_hasEdge().getURI());
		assertEquals(PlanningOWLModel.URI_hasEndNode, testable.getObjectProperty_hasEndNode().getURI());
		assertEquals(PlanningOWLModel.URI_hasFinalSystem, testable.getObjectProperty_hasFinalSystem().getURI());
		assertEquals(PlanningOWLModel.URI_hasInitialSystem, testable.getObjectProperty_hasInitialSystem().getURI());
		assertEquals(PlanningOWLModel.URI_hasLine, testable.getObjectProperty_hasLine().getURI());
		assertEquals(PlanningOWLModel.URI_hasLink, testable.getObjectProperty_hasLink().getURI());
		assertEquals(PlanningOWLModel.URI_hasLinkTemplate, testable.getObjectProperty_hasLinkTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_hasLinkTransformation, testable.getObjectProperty_hasLinkTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_hasNode, testable.getObjectProperty_hasNode().getURI());
		assertEquals(PlanningOWLModel.URI_hasObjectTemplate, testable.getObjectProperty_hasObjectTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_hasObjectTemplate1, testable.getObjectProperty_hasObjectTemplate1().getURI());
		assertEquals(PlanningOWLModel.URI_hasObjectTemplate2, testable.getObjectProperty_hasObjectTemplate2().getURI());
		assertEquals(PlanningOWLModel.URI_hasParameter, testable.getObjectProperty_hasParameter().getURI());
		assertEquals(PlanningOWLModel.URI_hasParameterUpdater, testable.getObjectProperty_hasParameterUpdater().getURI());
		assertEquals(PlanningOWLModel.URI_hasPreConditionChecker, testable.getObjectProperty_hasPreConditionChecker().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystem, testable.getObjectProperty_hasSystem().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemObject, testable.getObjectProperty_hasSystemObject().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemObject1, testable.getObjectProperty_hasSystemObject1().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemObject2, testable.getObjectProperty_hasSystemObject2().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemOperation, testable.getObjectProperty_hasSystemOperation().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemTemplate, testable.getObjectProperty_hasSystemTemplate().getURI());
		assertEquals(PlanningOWLModel.URI_hasSystemTransformation, testable.getObjectProperty_hasSystemTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_hasTransformation, testable.getObjectProperty_hasTransformation().getURI());
		assertEquals(PlanningOWLModel.URI_hasTransformations, testable.getObjectProperty_hasTransformations().getURI());
		assertEquals(PlanningOWLModel.URI_isActionOf, testable.getObjectProperty_isActionOf().getURI());
		assertEquals(PlanningOWLModel.URI_isActionPerformedBy, testable.getObjectProperty_isActionPerformedBy().getURI());
		assertEquals(PlanningOWLModel.URI_isAttributeOf, testable.getObjectProperty_isAttributeOf().getURI());
		assertEquals(PlanningOWLModel.URI_isAttributeTemplateOf, testable.getObjectProperty_isAttributeTemplateOf().getURI());
		assertEquals(PlanningOWLModel.URI_isAttributeTransformationOf, testable.getObjectProperty_isAttributeTransformationOf().getURI());
		assertEquals(PlanningOWLModel.URI_isBeginNodeOf, testable.getObjectProperty_isBeginNodeOf().getURI());
		assertEquals(PlanningOWLModel.URI_isEdgeOf, testable.getObjectProperty_isEdgeOf().getURI());
		assertEquals(PlanningOWLModel.URI_isEndNodeOf, testable.getObjectProperty_isEndNodeOf().getURI());
		assertEquals(PlanningOWLModel.URI_isFinalSystemOf, testable.getObjectProperty_isFinalSystemOf().getURI());
		assertEquals(PlanningOWLModel.URI_isInitialSystemOf, testable.getObjectProperty_isInitialSystemOf().getURI());
		assertEquals(PlanningOWLModel.URI_isLineOf, testable.getObjectProperty_isLineOf().getURI());
		assertEquals(PlanningOWLModel.URI_isLinkOf, testable.getObjectProperty_isLinkOf().getURI());
		assertEquals(PlanningOWLModel.URI_isLinkTemplateOf, testable.getObjectProperty_isLinkTemplateOf().getURI());
		assertEquals(PlanningOWLModel.URI_isLinkTransformationOf, testable.getObjectProperty_isLinkTransformationOf().getURI());
		assertEquals(PlanningOWLModel.URI_isNodeOf, testable.getObjectProperty_isNodeOf().getURI());
		assertEquals(PlanningOWLModel.URI_isObjectTemplateOf, testable.getObjectProperty_isObjectTemplateOf().getURI());
		assertEquals(PlanningOWLModel.URI_isObjectTemplate1Of, testable.getObjectProperty_isObjectTemplate1Of().getURI());
		assertEquals(PlanningOWLModel.URI_isObjectTemplate2Of, testable.getObjectProperty_isObjectTemplate2Of().getURI());
		assertEquals(PlanningOWLModel.URI_isParameterOf, testable.getObjectProperty_isParameterOf().getURI());
		assertEquals(PlanningOWLModel.URI_isParameterUpdaterOf, testable.getObjectProperty_isParameterUpdaterOf().getURI());
		assertEquals(PlanningOWLModel.URI_isPreConditionCheckerOf, testable.getObjectProperty_isPreConditionCheckerOf().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemObjectOf, testable.getObjectProperty_isSystemObjectOf().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemObject1Of, testable.getObjectProperty_isSystemObject1Of().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemObject2Of, testable.getObjectProperty_isSystemObject2Of().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemOf, testable.getObjectProperty_isSystemOf().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemOperationOf, testable.getObjectProperty_isSystemOperationOf().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemOperationPerformedBy, testable.getObjectProperty_isSystemOperationPerformedBy().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemTemplateOf, testable.getObjectProperty_isSystemTemplateOf().getURI());
		assertEquals(PlanningOWLModel.URI_isSystemTransformationOf, testable.getObjectProperty_isSystemTransformationOf().getURI());
		assertEquals(PlanningOWLModel.URI_isTransformationOf, testable.getObjectProperty_isTransformationOf().getURI());
		assertEquals(PlanningOWLModel.URI_performsAction, testable.getObjectProperty_performsAction().getURI());
		assertEquals(PlanningOWLModel.URI_performsSystemOperation, testable.getObjectProperty_performsSystemOperation().getURI());
	}

	@Test
	public void newIndividual() {
		testable.createOntologyModel();

		Individual attribute = testable.newIndividual_Attribute();
		assertEquals(attribute.getOntClass(), testable.getClass_Attribute());
		assertNotEquals(attribute.getURI(), testable.newIndividual_Attribute().getURI());

		Individual edge = testable.newIndividual_Edge();
		assertEquals(edge.getOntClass(), testable.getClass_Edge());
		assertNotEquals(edge.getURI(), testable.newIndividual_Edge().getURI());

		Individual link = testable.newIndividual_Link();
		assertEquals(link.getOntClass(), testable.getClass_Link());
		assertNotEquals(link.getURI(), testable.newIndividual_Link().getURI());

		Individual node = testable.newIndividual_Node();
		assertEquals(node.getOntClass(), testable.getClass_Node());
		assertNotEquals(node.getURI(), testable.newIndividual_Node().getURI());

		Individual nodeNetwork = testable.newIndividual_NodeNetwork();
		assertEquals(nodeNetwork.getOntClass(), testable.getClass_NodeNetwork());
		assertNotEquals(nodeNetwork.getURI(), testable.newIndividual_NodeNetwork().getURI());

		Individual parameter = testable.newIndividual_Parameter();
		assertEquals(parameter.getOntClass(), testable.getClass_Parameter());
		assertNotEquals(parameter.getURI(), testable.newIndividual_Parameter().getURI());

		Individual process = testable.newIndividual_Process();
		assertEquals(process.getOntClass(), testable.getClass_Process());
		assertNotEquals(process.getURI(), testable.newIndividual_Process().getURI());

		Individual system = testable.newIndividual_System();
		assertEquals(system.getOntClass(), testable.getClass_System());
		assertNotEquals(system.getURI(), testable.newIndividual_System().getURI());

		Individual systemObject = testable.newIndividual_SystemObject();
		assertEquals(systemObject.getOntClass(), testable.getClass_SystemObject());
		assertNotEquals(systemObject.getURI(), testable.newIndividual_SystemObject().getURI());

		Individual systemOperation = testable.newIndividual_SystemOperation();
		assertEquals(systemOperation.getOntClass(), testable.getClass_SystemOperation());
		assertNotEquals(systemOperation.getURI(), testable.newIndividual_SystemOperation().getURI());

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

		Individual taskDescription = testable.newIndividual_TaskDescription();
		assertEquals(taskDescription.getOntClass(), testable.getClass_TaskDescription());
		assertNotEquals(taskDescription.getURI(), testable.newIndividual_TaskDescription().getURI());
	}
}
