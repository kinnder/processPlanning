package planning.model;

import java.util.Map;

public interface ParameterUpdater {

	void invoke(SystemVariant systemVariant, Map<String, String> parameters);
}
