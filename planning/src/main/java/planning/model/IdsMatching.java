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

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
