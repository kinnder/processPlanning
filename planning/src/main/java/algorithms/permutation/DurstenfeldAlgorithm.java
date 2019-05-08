package algorithms.permutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DurstenfeldAlgorithm implements PermutationAlgorithm {

	private static Random random = new Random();

	private static final int AMOUNT_OF_PERMUTATIONS = 24;

	@Override
	public List<Object[]> getPermutations(Object[] source, int permutationLength) {
		Object[] variant = Arrays.copyOf(source, source.length);

		List<Object[]> permutations = new ArrayList<Object[]>();

		for (int p = 0; p < AMOUNT_OF_PERMUTATIONS; p++) {
			for (int i = variant.length - 1; i > 0; i--) {
				int j = random.nextInt(i);
				swap(variant, i, j);
			}
			permutations.add(Arrays.copyOf(variant, permutationLength));
		}

		return permutations;
	}

	private void swap(Object[] array, int a, int b) {
		Object tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}
}
