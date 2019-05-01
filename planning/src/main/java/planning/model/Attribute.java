package planning.model;

public class Attribute {

	private String name;

	private Object value;

	private AttributeType type;

	public Attribute(String name, boolean value) {
		this.name = name;
		this.value = value;
		this.type = AttributeType.Boolean;
	}

	public Attribute(String name, String value) {
		this.name = name;
		this.value = value;
		this.type = AttributeType.String;
	}

	public Attribute(String name, Object value) {
		this.name = name;
		this.value = value;
		this.type = AttributeType.Object;
	}

	@Override
	public Object clone() {
		switch (type) {
		case Boolean:
			return new Attribute(name, getValueAsBoolean());
		case String:
			return new Attribute(name, getValueAsString());
		default:
			return new Attribute(name, getValueAsObject());
		}
	}

	public String getValueAsString() {
		return (String) value;
	}

	public boolean getValueAsBoolean() {
		return (boolean) (value);
	}

	public Object getValueAsObject() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Attribute attribute = (Attribute) obj;
		return name.equals(attribute.name) && type.equals(attribute.type) && value.equals(attribute.value);
	}

	public String getName() {
		return this.name;
	}

	public boolean matches(Attribute template) {
		return name.equals(template.name) && type.equals(template.type) && value.equals(template.value);
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
