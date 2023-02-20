package planning.model;

public enum AttributeType {
	BOOLEAN("boolean"), INTEGER("integer"), STRING("string");

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
}
