package design.planning.gui;

import design.planning.Planning;

public class Application {
	public static void main(String args[]){
		Planning planning = new Planning();

		System.out.print("hello it's GUI\n");
		System.out.print(planning.plan());
	}

}
