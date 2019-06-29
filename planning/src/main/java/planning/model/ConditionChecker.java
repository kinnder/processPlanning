package planning.model;

import java.util.Map;

public interface ConditionChecker {

	boolean invoke(System system, IdsMatching idsMatching, Map<String, String> parameters);
}
