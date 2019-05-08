package algorithms.permutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeapsAlgorithm implements PermutationAlgorithm {

	@Override
	public List<Object[]> getPermutations(Object[] source, int permutationLength) {
		Object[] variant = Arrays.copyOf(source, source.length);

		int[] ids = new int[permutationLength];
		Arrays.fill(ids, 0);

		List<Object[]> permutations = new ArrayList<Object[]>();
		permutations.add(Arrays.copyOf(variant, variant.length));

		for (int i = 0; i < ids.length;) {
			if (ids[i] < i) {
				swap(variant, i % 2 == 0 ? 0 : ids[i], i);
				permutations.add(Arrays.copyOf(variant, variant.length));
				ids[i]++;
				i = 0;
			} else {
				ids[i] = 0;
				i++;
			}
		}

		return permutations;
	}

	private void swap(Object[] array, int a, int b) {
		Object tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}
}
