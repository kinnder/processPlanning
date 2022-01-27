package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class ApplicationArgumentsTest {

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

	ApplicationArguments testable;

	@BeforeEach
	public void setup() {
		testable = new ApplicationArguments();
	}

	@Test
	public void newInstance() {
		testable = new ApplicationArguments();

		assertFalse(testable.hasArgument_h());
	}

	@Test
	public void hasArgument_h() throws ParseException {
		testable.parseArguments(new String[] { "-h" });

		assertTrue(testable.hasArgument_h());
	}

	@Test
	public void hasArgument_h_longName() throws ParseException {
		testable.parseArguments(new String[] { "-help" });

		assertTrue(testable.hasArgument_h());
	}

	@Test
	public void hasArgument_gui() throws ParseException {
		testable.parseArguments(new String[] { "-gui" });

		assertTrue(testable.hasArgument_gui());
	}

	@Test
	public void hasArgument_plan() throws ParseException {
		testable.parseArguments(new String[] { "-plan" });

		assertTrue(testable.hasArgument_plan());
	}

	@Test
	public void hasArgument_new_st() throws ParseException {
		testable.parseArguments(new String[] { "-new_st" });

		assertTrue(testable.hasArgument_new_st());
	}

	@Test
	public void hasArgument_new_st_lonName() throws ParseException {
		testable.parseArguments(new String[] { "-new-system-transformations" });

		assertTrue(testable.hasArgument_new_st());
	}

	@Test
	public void hasArgument_new_td() throws ParseException {
		testable.parseArguments(new String[] { "-new_td" });

		assertTrue(testable.hasArgument_new_td());
	}

	@Test
	public void hasArgument_new_td_longName() throws ParseException {
		testable.parseArguments(new String[] { "-new-task-description" });

		assertTrue(testable.hasArgument_new_td());
	}

	@Test
	public void hasArgument_verify() throws ParseException {
		testable.parseArguments(new String[] { "-verify" });

		assertTrue(testable.hasArgument_verify());
	}

	@Test
	public void hasArgument_convert() throws ParseException {
		testable.parseArguments(new String[] { "-convert" });

		assertTrue(testable.hasArgument_convert());
	}

	@Test
	public void getArgument_td() throws ParseException {
		testable.parseArguments(new String[] { "-td", "td_file.xml" });

		assertEquals("td_file.xml", testable.getArgument_td("default"));
	}

	@Test
	public void getArgument_td_longName() throws ParseException {
		testable.parseArguments(new String[] { "-task-description=td_file.owl" });

		assertEquals("td_file.owl", testable.getArgument_td("default"));
	}

	@Test
	public void getArgument_td_default() {
		assertEquals("default", testable.getArgument_td("default"));
	}

	@Test
	public void getArgument_st() throws ParseException {
		testable.parseArguments(new String[] { "-st", "st_file.xml" });

		assertEquals("st_file.xml", testable.getArgument_st("default"));
	}

	@Test
	public void getArgument_st_longName() throws ParseException {
		testable.parseArguments(new String[] { "-system-transformations=st_file.owl" });

		assertEquals("st_file.owl", testable.getArgument_st("default"));
	}

	@Test
	public void getArgument_st_default() {
		assertEquals("default", testable.getArgument_st("default"));
	}

	@Test
	public void getArgument_p() throws ParseException {
		testable.parseArguments(new String[] { "-p", "process.xml" });

		assertEquals("process.xml", testable.getArgument_p("default"));
	}

	@Test
	public void getArgument_p_long() throws ParseException {
		testable.parseArguments(new String[] { "-process=process.owl" });

		assertEquals("process.owl", testable.getArgument_p("default"));
	}

	@Test
	public void getArgument_p_default() {
		assertEquals("default", testable.getArgument_p("default"));
	}

	@Test
	public void getArgument_nn() throws ParseException {
		testable.parseArguments(new String[] { "-nn", "nn_file.xml" });

		assertEquals("nn_file.xml", testable.getArgument_nn("default"));
	}

	@Test
	public void getArgument_nn_longName() throws ParseException {
		testable.parseArguments(new String[] { "-node-network=nn_file.owl" });

		assertEquals("nn_file.owl", testable.getArgument_nn("default"));
	}

	@Test
	public void getArgument_nn_default() {
		assertEquals("default", testable.getArgument_nn("default"));
	}

	@Test
	public void getArgument_d() throws ParseException {
		testable.parseArguments(new String[] { "-d", "assemblyLine" });

		assertEquals("assemblyLine", testable.getArgument_d("default"));
	}

	@Test
	public void getArgument_d_longName() throws ParseException {
		testable.parseArguments(new String[] { "-domain=materialPoints" });

		assertEquals("materialPoints", testable.getArgument_d("default"));
	}

	@Test
	public void getArgument_d_default() {
		assertEquals("default", testable.getArgument_d("default"));
	}

	@Test
	public void getOptions() {
		final Options options = testable.getOptions();
		assertEquals(12, options.getOptions().size());
	}
}
