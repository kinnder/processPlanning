package planning.model;

import java.util.HashMap;
import java.util.Map;

public class IdsMatching {

	private Map<String, String> idsMatching = new HashMap<String, String>();

	public String get(String templateId) {
		return idsMatching.get(templateId);
	}

	public void add(String tempateId, String objectId) {
		idsMatching.put(tempateId, objectId);
	}

	private boolean checked = false;

	public boolean isChecked() {
		return checked;
	}

	public void check() {
		checked = true;
	}

	public boolean areKeysAndValuesTheSame() {
		for (String key : idsMatching.keySet()) {
			String value = idsMatching.get(key);
			if (!key.equals(value)) {
				return false;
			}
		}
		return true;
	}
}
