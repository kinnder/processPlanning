package planning.model;

import java.util.Map;

public interface ConditionChecker {

	boolean invoke(SystemVariant systemVariant, Map<String, String> parameters);
}
