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
	public void newInstance() {
		testable = new LinkTransformation();
		assertEquals("object-id", testable.getId1());
		assertEquals("link-name", testable.getLinkName());
		assertEquals("id-2-old", testable.getId2Old());
		assertEquals("id-2-new", testable.getId2New());
	}

	@Test
	public void applyTo() {
		final SystemObject object_mock = context.mock(SystemObject.class);
		final Link link_in_system_mock = context.mock(Link.class, "link-in-system");
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final System system_mock = context.mock(System.class);

		context.checking(new Expectations() {
			{
				oneOf(systemVariant_mock).getObjectByIdMatch("id-template");
				will(returnValue(object_mock));

				oneOf(object_mock).getId();
				will(returnValue("id-actual"));

				oneOf(systemVariant_mock).getObjectIdByIdMatch("link-new-value");
				will(returnValue("link-new-value-actual"));

				oneOf(systemVariant_mock).getObjectIdByIdMatch("link-old-value");
				will(returnValue("link-old-value-actual"));

				oneOf(systemVariant_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(system_mock).getLink("link-name", "id-actual", "link-old-value-actual");
				will(returnValue(link_in_system_mock));

				oneOf(link_in_system_mock).setId2("link-new-value-actual");
			}
		});

		testable.applyTo(systemVariant_mock);
	}

	@Test
	public void getLinkName() {
		assertEquals("link-name", testable.getLinkName());
	}

	@Test
	public void setLinkName() {
		testable.setLinkName("new-link-name");
		assertEquals("new-link-name", testable.getLinkName());
	}

	@Test
	public void getId1() {
		assertEquals("id-template", testable.getId1());
	}

	@Test
	public void getId2New() {
		assertEquals("link-new-value", testable.getId2New());
	}

	@Test
	public void setId2New() {
		testable.setId2New("new-link-new-value");
		assertEquals("new-link-new-value", testable.getId2New());
	}

	@Test
	public void getId2Old() {
		assertEquals("link-old-value", testable.getId2Old());
	}

	@Test
	public void setId2Old() {
		testable.setId2Old("new-link-old-value");
		assertEquals("new-link-old-value", testable.getId2Old());
	}

	@Test
	public void toString_test() {
		assertEquals("link-transformation", testable.toString());
	}
}
