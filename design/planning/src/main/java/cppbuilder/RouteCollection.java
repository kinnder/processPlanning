package cppbuilder;

import cppbuilder.utility.Collection;

/** Коллекция путей */
public class RouteCollection extends Collection<Route> {

	/** Получить кратчайший путь */
	public Route getShortestRoute() {
		Route result = items.get(0);
		for (Route route : items) {
			if (result.nodes.size() > route.nodes.size()) {
				result = route;
			}
		}
		return result;
	}
}
