package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.System;

public class TaskDescriptionXMLModel implements XMLModel {

	@Override
	public void parse(Element element) throws DataConversionException {
		// TODO Auto-generated method stub
	}

	@Override
	public Element combine() {
		// TODO Auto-generated method stub
		return null;
	}

	private System initialSystem = new System();

	public System getInitialSystem() {
		return this.initialSystem;
	}

	public void setInitialSystem(System system) {
		this.initialSystem = system;
	}

	private System finalSystem = new System();

	public System getFinalSystem() {
		return this.finalSystem;
	}

	public void setFinalSystem(System system) {
		this.finalSystem = system;
	}
}
