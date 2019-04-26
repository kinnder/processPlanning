package cppbuilder;

import cppbuilder.utility.RandomGenerator;

public class NodeCollectionTest {

	public static void prepare_Random(NodeCollection nodes) {
		int nodeAmount = RandomGenerator.getInteger(1, 3);
		for (int i = 0; i < nodeAmount; i++) {
			nodes.add(NodeTest.create_Random());
		}
	}
}
