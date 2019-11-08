package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class LinkTransformationTest {

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

	LinkTransformation testable;

	@BeforeEach
	public void setup() {
		testable = new LinkTransformation("id-template", "link-name", "link-old-value", "link-new-value");
	}

	@Test
	public void applyTo() {
		final SystemObject object_mock = context.mock(SystemObject.class);
		final Link link_mock = context.mock(Link.class);
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);

		context.checking(new Expectations() {
			{
				oneOf(systemVariant_mock).getObjectByIdMatch("id-template");
				will(returnValue(object_mock));

				oneOf(systemVariant_mock).getObjectIdByIdMatch("link-new-value");
				will(returnValue("link-new-value-actual"));

				oneOf(systemVariant_mock).getObjectIdByIdMatch("link-old-value");
				will(returnValue("link-old-value-actual"));

				oneOf(object_mock).getLink("link-name", "link-old-value-actual");
				will(returnValue(link_mock));

				oneOf(link_mock).setObjectId("link-new-value-actual");
			}
		});

		testable.applyTo(systemVariant_mock);
	}

	@Test
	public void getLinkName() {
		assertEquals("link-name", testable.getLinkName());
	}

	@Test
	public void getLinkNewValue() {
		assertEquals("link-new-value", testable.getLinkNewValue());
	}

	@Test
	public void getLinkOldValue() {
		assertEquals("link-old-value", testable.getLinkOldValue());
	}
}
