package planning.method;

import java.util.ArrayList;
import java.util.List;

import planning.model.SystemTransformation;

public class SystemTransformations {

	private List<SystemTransformation> elements;

	public SystemTransformations() {
		elements = new ArrayList<>();
	}

	public SystemTransformation[] getElements() {
		return elements.toArray(new SystemTransformation[0]);
	}

	public void addElement(SystemTransformation element) {
		elements.add(element);
	}

	public SystemTransformation getElement(String name) {
		for (SystemTransformation element : elements) {
			if (element.getName().equals(name))
				return element;
		}
		return null;
	}
}
