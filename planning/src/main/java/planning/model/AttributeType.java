package planning.model;

public enum AttributeType {
	Boolean, String, Object, Integer;

	public static AttributeType getTypeOf(Object value) {
		if (value instanceof Boolean) {
			return Boolean;
		}
		if (value instanceof String) {
			return String;
		}
		if (value instanceof Integer) {
			return Integer;
		}
		return Object;
	}
}
