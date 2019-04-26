package cppbuilder;

import cppbuilder.utility.RandomGenerator;

public class SystemObjectCollectionTest {

	public static void prepare_Random(SystemObjectCollection systemObjects) {
		int systemObjectAmount = RandomGenerator.getInteger(1, 3);
		for (int i = 0; i < systemObjectAmount; i++) {
			systemObjects.add(SystemObjectTest.create_Random());
		}
	}
}
