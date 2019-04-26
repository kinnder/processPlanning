package cppbuilder;

import java.io.FileNotFoundException;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class RouteTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	Route out1;
	Route out2;

	@Before
	public void setUp() {
		out1 = new Route();
		out2 = new Route();

		prepare_Random(out2);
	}

	public static void prepare_Random(Route route) {
		NodeCollectionTest.prepare_Random(route.nodes);
	}

	@Test
	public void equality() {
		Assert.assertEquals(false, out1.allFieldsAreEqual(out2));

		Assert.assertEquals(true, out1.allFieldsAreEqual(out1));
	}

	@Test
	public void toXMLString() {
		Assert.assertEquals(false, out2.toXMLString().isEmpty());
	}

	@Test
	@Ignore
	public void saveToXMLFile() throws FileNotFoundException {
		out2.saveToXMLFile("routes.xml");
	}
}
