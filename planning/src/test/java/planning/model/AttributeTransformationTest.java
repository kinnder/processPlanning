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

public class AttributeTransformationTest {

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

	AttributeTransformation testable;

	@BeforeEach
	public void setup() {
		testable = new AttributeTransformation("id-template", "attribute-name", "attribute-value");
	}

	@Test
	public void applyTo() {
		final System system_mock = context.mock(System.class);
		final Map<String, String> idsMatching = new HashMap<String, String>();
		idsMatching.put("id-template", "id");
		final SystemObject object_mock = context.mock(SystemObject.class);
		final Attribute attribute_mock = context.mock(Attribute.class);

		context.checking(new Expectations() {
			{
				oneOf(system_mock).getObjectById("id");
				will(returnValue(object_mock));

				oneOf(object_mock).getAttribute("attribute-name");
				will(returnValue(attribute_mock));

				oneOf(attribute_mock).setValue("attribute-value");
			}
		});

		testable.applyTo(system_mock, idsMatching);
	}
}
