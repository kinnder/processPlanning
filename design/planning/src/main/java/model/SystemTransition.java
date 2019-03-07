package model;

import java.util.ArrayList;
import java.util.List;

import utility.Collection;
import utility.CollectionItem;

public class SystemTransition {

	public String name;

	public List<SystemModification> modifications;

	public SystemTransition() {
		modifications = new ArrayList<SystemModification>();
	}

	public Collection getModifiedObjects(Collection modifiingObjects) {
		Collection modifiedObjects = new Collection();

		Collection objectsToModify = (Collection) modifiingObjects.clone();
		for (SystemModification operation : modifications) {
			// TODO : перенести этот код в SystemModification
			for (CollectionItem ci : objectsToModify) {
				SystemObject objectToModify = (SystemObject) ci;
				if (objectToModify.matches(operation.target)) {
					SystemObject modifiedObject = objectToModify.getModifiedObject(operation.modifications);
					modifiedObjects.add(modifiedObject);
					objectsToModify.remove(objectToModify);
					break;
				}
			}
		}

		return modifiedObjects;
	}
}
