package cppbuilder;

/** Тип перехода */
public enum TransitionType {
	/** неизвестно */
	Unknown,

	/** разница атрибутов */
	DeltaAttributes,

	/** замена атрибутов */
	ChangeAttributes;

	public static TransitionType valueOf(int value) {
		switch (value) {
		case 1:
			return TransitionType.DeltaAttributes;
		case 2:
			return TransitionType.ChangeAttributes;
		default:
			return TransitionType.Unknown;
		}
	}
}
