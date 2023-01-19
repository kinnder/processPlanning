package planning.model;

import java.util.HashMap;
import java.util.Map;

public class IdsMatching {

	private Map<String, String> matchings = new HashMap<>();

	public String get(String templateId) {
		return matchings.get(templateId);
	}

	public void add(String tempateId, String objectId) {
		matchings.put(tempateId, objectId);
	}

	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void check() {
		checked = true;
	}

	public boolean areKeysAndValuesTheSame() {
		for (String key : matchings.keySet()) {
			final String value = matchings.get(key);
			if (!key.equals(value)) {
				return false;
			}
		}
		return true;
	}
}
