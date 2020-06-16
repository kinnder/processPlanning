package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.LinkTransformation;

public class LinkTransformationXMLSchemaTest {

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

	LinkTransformationXMLSchema testable;

	@BeforeEach
	public void setup() {
		testable = new LinkTransformationXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");

		context.checking(new Expectations() {
			{
				oneOf(root_mock).getChildText("objectId");
				will(returnValue("objectId"));

				oneOf(root_mock).getChildText("name");
				will(returnValue("name"));

				oneOf(root_mock).getChildText("oldValue");
				will(returnValue("oldValue"));

				oneOf(root_mock).getChildText("newValue");
				will(returnValue("newValue"));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof LinkTransformation);
	}

	@Test
	public void combine() {
		final LinkTransformation linkTransformation_mock = context.mock(LinkTransformation.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTransformation_mock).getObjectId();
				will(returnValue("id"));

				oneOf(linkTransformation_mock).getLinkName();
				will(returnValue("name"));

				oneOf(linkTransformation_mock).getLinkObject1Old();
				will(returnValue("old-link-value"));

				oneOf(linkTransformation_mock).getLinkObjectId1New();
				will(returnValue("new-link-value"));
			}
		});

		Element element = testable.combine(linkTransformation_mock);
		assertEquals("id", element.getChildText("objectId"));
		assertEquals("name", element.getChildText("name"));
		assertEquals("old-link-value", element.getChildText("oldValue"));
		assertEquals("new-link-value", element.getChildText("newValue"));
	}

	@Test
	public void combine_empty_new_link() {
		final LinkTransformation linkTransformation_mock = context.mock(LinkTransformation.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTransformation_mock).getObjectId();
				will(returnValue("id"));

				oneOf(linkTransformation_mock).getLinkName();
				will(returnValue("name"));

				oneOf(linkTransformation_mock).getLinkObject1Old();
				will(returnValue("old-link-value"));

				oneOf(linkTransformation_mock).getLinkObjectId1New();
				will(returnValue(null));
			}
		});

		Element element = testable.combine(linkTransformation_mock);
		assertEquals("id", element.getChildText("objectId"));
		assertEquals("name", element.getChildText("name"));
		assertEquals("old-link-value", element.getChildText("oldValue"));
		assertNull(element.getChild("newValue"));
	}

	@Test
	public void combine_empty_old_link() {
		final LinkTransformation linkTransformation_mock = context.mock(LinkTransformation.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTransformation_mock).getObjectId();
				will(returnValue("id"));

				oneOf(linkTransformation_mock).getLinkName();
				will(returnValue("name"));

				oneOf(linkTransformation_mock).getLinkObject1Old();
				will(returnValue(null));

				oneOf(linkTransformation_mock).getLinkObjectId1New();
				will(returnValue("new-link-value"));
			}
		});

		Element element = testable.combine(linkTransformation_mock);
		assertEquals("id", element.getChildText("objectId"));
		assertEquals("name", element.getChildText("name"));
		assertNull(element.getChild("oldValue"));
		assertEquals("new-link-value", element.getChildText("newValue"));
	}
}
