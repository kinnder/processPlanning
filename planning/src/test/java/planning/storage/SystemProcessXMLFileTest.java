package planning.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.SystemProcess;

public class SystemProcessXMLFileTest {

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

	SystemProcessXMLFile testable;

	@BeforeEach
	public void setup() {
		testable = new SystemProcessXMLFile();
	}

	@Test
	public void setSystemProcess() {
		final SystemProcess systemProcess_mock = context.mock(SystemProcess.class);

		testable.setSystemProcess(systemProcess_mock);
		assertEquals(systemProcess_mock, testable.getSystemProcess());
	}

	@Test
	public void getSystemProcess() {
		assertNotNull(testable.getSystemProcess());
	}

	@Test
	public void getXMLSchema() {
		assertTrue(testable.getXMLSchema() instanceof SystemProcessXMLSchema);
	}
}
