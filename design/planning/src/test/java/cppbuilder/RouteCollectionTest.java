package cppbuilder;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RouteCollectionTest {

	private final Mockery context = new Mockery() {
		{
			this.setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@After
	public void tearDown() {
		context.assertIsSatisfied();
	}

	RouteCollection out;

	@Before
	public void setUp() {
		out = new RouteCollection();
	}

	@Test
	public void getShortestRoute() {
		Route route_1 = new Route();
		Route route_2 = new Route();
		route_1.nodes.add(new Node());
		route_1.nodes.add(new Node());
		route_2.nodes.add(new Node());
		out.add(route_1);
		out.add(route_2);

		Assert.assertEquals(2, out.size());
		Assert.assertEquals(route_2, out.getShortestRoute());
	}
}
