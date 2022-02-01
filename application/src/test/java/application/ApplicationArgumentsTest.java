package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.domain.AssemblyLine;
import application.domain.CuttingProcess;
import application.domain.MaterialPoints;

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
		final Option h_option = new Option("h", "help", false, "prints usage");
		final Option td_option = new Option("td", "task-description", true, "file with description of the task");
		final Option st_option = new Option("st", "system-transformations", true, "file with description of the system transformations");
		final Option p_option = new Option("p", "process", true, "output file with process");
		final Option nn_option = new Option("nn", "node-network", true, "output file with node network");
		final Option plan_option = new Option("plan", "plan process");
		final Option new_st_option = new Option("new_st", "new-system-transformations", false, "create new file with predefined system transformations");
		final Option new_td_option = new Option("new_td", "new-task-description", false, "create new file with predefined task description");
		final Option verify_option = new Option("verify", "verify xml-files with according xml-schemas");
		final Option convert_option = new Option("convert", "convert files between formats: xml to owl and owl to xml");
		final Option gui_option = new Option("gui", "show graphical user interface");
		final Option d_option = new Option("d", "domain", true, String.format("defines domain for predefined data: %s, %s or %s", MaterialPoints.DOMAIN_NAME, CuttingProcess.DOMAIN_NAME, AssemblyLine.DOMAIN_NAME));

		final Options options_expected = new Options();
		options_expected.addOption(h_option);
		options_expected.addOption(td_option);
		options_expected.addOption(st_option);
		options_expected.addOption(p_option);
		options_expected.addOption(nn_option);
		options_expected.addOption(plan_option);
		options_expected.addOption(new_st_option);
		options_expected.addOption(new_td_option);
		options_expected.addOption(verify_option);
		options_expected.addOption(convert_option);
		options_expected.addOption(gui_option);
		options_expected.addOption(d_option);

		final Options options_actual = testable.getOptions();
		assertEquals(12, options_actual.getOptions().size());

		for (Option option_expected : options_expected.getOptions()) {
			Option option_actual = options_actual.getOption(option_expected.getOpt());
			assertEquals(option_expected.getOpt(), option_actual.getOpt());
			assertEquals(option_expected.getLongOpt(), option_actual.getLongOpt());
			assertEquals(option_expected.getDescription(), option_actual.getDescription());
			assertEquals(option_expected.hasArg(), option_actual.hasArg());
		}
	}
}
