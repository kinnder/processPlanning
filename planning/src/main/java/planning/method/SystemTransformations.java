package planning.method;

import java.util.ArrayList;
import planning.model.SystemTransformation;

// TODO (2022-07-09 #72): убрать слово System из названия класса и связанных классов
public class SystemTransformations extends ArrayList<SystemTransformation> {

	private static final long serialVersionUID = -6023577435712637199L;

	public SystemTransformation get(String name) {
		for (SystemTransformation element : this) {
			if (element.getName().equals(name)) {
				return element;
			}
		}
		return null;
	}

	public void addAll(SystemTransformation[] elements) {
		for (SystemTransformation element : elements) {
			this.add(element);
		}
	}
}
