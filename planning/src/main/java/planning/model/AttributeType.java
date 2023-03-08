package planning.model;

public enum AttributeType {
	BOOLEAN("boolean"), INTEGER("integer"), STRING("string"), OBJECT("object"), NULL("null");

	final private String name;

	AttributeType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static AttributeType fromString(String string) {
		return AttributeType.valueOf(string.toUpperCase());
	}

	public static AttributeType fromValue(Object value) {
		if (value == null) {
			return NULL;
		}
		if (value instanceof String) {
			return STRING;
		}
		if (value instanceof Integer) {
			return INTEGER;
		}
		if (value instanceof Boolean) {
			return BOOLEAN;
		}
		return OBJECT;
	}
}
