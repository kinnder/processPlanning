package planning.model;

import java.util.Map;

public interface ParameterUpdater {

	void invoke(System system, IdsMatching idsMatching, Map<String, String> parameters);
}
