package design.planning.cli;

import design.planning.Planning;

public class Application {
	public static void main(String[] p_args) {
		Planning planning = new Planning();

		System.out.print("hello it's CLI\n");
		System.out.print(planning.plan());
	}
}
