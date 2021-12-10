package application.storage.xml;

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
				oneOf(root_mock).getChildText("id");
				will(returnValue("l-id"));

				oneOf(root_mock).getChildText("name");
				will(returnValue("l-name"));

				oneOf(root_mock).getChildText("id2Old");
				will(returnValue("oldValue"));

				oneOf(root_mock).getChildText("id2New");
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
				oneOf(linkTransformation_mock).getId();
				will(returnValue("l-id"));

				oneOf(linkTransformation_mock).getLinkName();
				will(returnValue("l-name"));

				oneOf(linkTransformation_mock).getId2Old();
				will(returnValue("old-link-value"));

				oneOf(linkTransformation_mock).getId2New();
				will(returnValue("new-link-value"));
			}
		});

		Element element = testable.combine(linkTransformation_mock);
		assertEquals("l-id", element.getChildText("id"));
		assertEquals("l-name", element.getChildText("name"));
		assertEquals("old-link-value", element.getChildText("id2Old"));
		assertEquals("new-link-value", element.getChildText("id2New"));
	}

	@Test
	public void combine_empty_new_link() {
		final LinkTransformation linkTransformation_mock = context.mock(LinkTransformation.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTransformation_mock).getId();
				will(returnValue("l-id"));

				oneOf(linkTransformation_mock).getLinkName();
				will(returnValue("l-name"));

				oneOf(linkTransformation_mock).getId2Old();
				will(returnValue("old-link-value"));

				oneOf(linkTransformation_mock).getId2New();
				will(returnValue(null));
			}
		});

		Element element = testable.combine(linkTransformation_mock);
		assertEquals("l-id", element.getChildText("id"));
		assertEquals("l-name", element.getChildText("name"));
		assertEquals("old-link-value", element.getChildText("id2Old"));
		assertNull(element.getChild("id2New"));
	}

	@Test
	public void combine_empty_old_link() {
		final LinkTransformation linkTransformation_mock = context.mock(LinkTransformation.class);

		context.checking(new Expectations() {
			{
				oneOf(linkTransformation_mock).getId();
				will(returnValue("l-id"));

				oneOf(linkTransformation_mock).getLinkName();
				will(returnValue("l-name"));

				oneOf(linkTransformation_mock).getId2Old();
				will(returnValue(null));

				oneOf(linkTransformation_mock).getId2New();
				will(returnValue("new-link-value"));
			}
		});

		Element element = testable.combine(linkTransformation_mock);
		assertEquals("l-id", element.getChildText("id"));
		assertEquals("l-name", element.getChildText("name"));
		assertNull(element.getChild("id2Old"));
		assertEquals("new-link-value", element.getChildText("id2New"));
	}

	@Test
	public void getSchemaName() {
		assertEquals("linkTransformation", testable.getSchemaName());
	}
}
