package planning.model;

import java.util.HashMap;
import java.util.Map;

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
		testable = new LinkTransformation("id-template", "link-name", "link-value");
	}

	@Test
	public void applyTo() {
		final System system_mock = context.mock(System.class);
		final Map<String, String> idsMatching = new HashMap<String, String>();
		idsMatching.put("id-template", "id");
		idsMatching.put("link-value", "link-id");
		final SystemObject object_mock = context.mock(SystemObject.class);
		final Link link_mock = context.mock(Link.class);

		context.checking(new Expectations() {
			{
				oneOf(system_mock).getObjectById("id");
				will(returnValue(object_mock));

				oneOf(object_mock).getLink("link-name");
				will(returnValue(link_mock));

				oneOf(link_mock).setObjectId("link-id");
			}
		});

		testable.applyTo(system_mock, idsMatching);
	}
}
