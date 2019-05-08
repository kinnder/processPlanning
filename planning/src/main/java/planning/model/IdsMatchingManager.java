package planning.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithms.permutation.HeapsAlgorithm;
import algorithms.permutation.PermutationAlgorithm;

public class IdsMatchingManager {

	private List<IdsMatching> idsMatchings = new ArrayList<IdsMatching>();

	private PermutationAlgorithm permutationAlgorithm = new HeapsAlgorithm();

	public IdsMatchingManager() {
	}

	public IdsMatchingManager(Set<String> templateIds, Set<String> systemIds) {
		int systemIdsAmount = systemIds.size();
		int templateIdsAmount = templateIds.size();

		if (systemIdsAmount < templateIdsAmount) {
			return;
		}

		Object[] templateIdsArray = Arrays.copyOf(templateIds.toArray(), systemIdsAmount);
		Object[] systemIdsArray = systemIds.toArray();

		int permutationLength = systemIdsAmount;

		List<Object[]> templateIdsPermutations = permutationAlgorithm.getPermutations(templateIdsArray,
				permutationLength);
		List<Object[]> systemIdsPermutations = permutationAlgorithm.getPermutations(systemIdsArray, permutationLength);

		for (Object[] templateIdsPermutation : templateIdsPermutations) {
			for (Object[] systemIdsPermutation : systemIdsPermutations) {
				IdsMatching idsMatching = new IdsMatching();
				for (int j = 0; j < permutationLength; j++) {
					idsMatching.add((String) templateIdsPermutation[j], (String) systemIdsPermutation[j]);
				}
				idsMatchings.add(idsMatching);
			}
		}
	}

	private IdsMatching unchekedIdsMatching = null;

	public IdsMatching getUncheckedMatching() {
		return unchekedIdsMatching;
	}

	public boolean haveUncheckedMatching() {
		if (unchekedIdsMatching != null) {
			unchekedIdsMatching.setChecked(true);

		}
		for (IdsMatching idsMatching : idsMatchings) {
			if (!idsMatching.getChecked()) {
				unchekedIdsMatching = idsMatching;
				return true;
			}
		}
		unchekedIdsMatching = null;
		return false;
	}

	public void removeMatchings(String templateId, String objectId) {
		List<IdsMatching> idsMatchingsToRemove = new ArrayList<IdsMatching>();
		for (IdsMatching idsMatching : idsMatchings) {
			if (idsMatching.get(templateId).equals(objectId)) {
				idsMatchingsToRemove.add(idsMatching);
			}
		}
		idsMatchings.removeAll(idsMatchingsToRemove);
	}

	public void removeMatching(IdsMatching idsMatching) {
		idsMatchings.remove(idsMatching);
	}

	public int getMatchingsAmount() {
		return idsMatchings.size();
	}

	public IdsMatching getMatching(int index) {
		return idsMatchings.get(index);
	}

	private Map<String, List<String>> candidates = new HashMap<String, List<String>>();

	public void prepareMatchingsCandidates(Set<String> templateIds, Set<String> systemIds) {
		candidates.clear();
		for (String templateId : templateIds) {
			candidates.put(templateId, new ArrayList<>(systemIds));
		}
	}

	public void removeMatchingsCandidate(String templateId, String objectId) {
		List<String> systemIds = candidates.get(templateId);
		systemIds.remove(objectId);
	}

	public void generateMatchingsFromCandidates() {
		List<List<String>> objectIdsCombinations = computeCombinations2(candidates.values());
		List<String> templateIdsCombination = new ArrayList<String>(candidates.keySet());
		for (List<String> objectIdsCombination : objectIdsCombinations) {
			Set<String> uniqueObjectIds = new HashSet<String>();
			uniqueObjectIds.addAll(objectIdsCombination);
			if (uniqueObjectIds.size() == objectIdsCombination.size()) {
				IdsMatching idsMatching = new IdsMatching();

				for (int i = 0; i < objectIdsCombination.size(); i++) {
					idsMatching.add(templateIdsCombination.get(i), objectIdsCombination.get(i));
				}
				idsMatchings.add(idsMatching);
			}
		}
	}

	/**
	 * Cartesian n-product
	 */
	public <T> List<List<T>> computeCombinations2(Collection<List<T>> lists) {
		List<List<T>> combinations = Arrays.asList(Arrays.asList());
		for (List<T> list : lists) {
			List<List<T>> extraColumnCombinations = new ArrayList<>();
			for (List<T> combination : combinations) {
				for (T element : list) {
					List<T> newCombination = new ArrayList<>(combination);
					newCombination.add(element);
					extraColumnCombinations.add(newCombination);
				}
			}
			combinations = extraColumnCombinations;
		}
		return combinations;
	}
}
