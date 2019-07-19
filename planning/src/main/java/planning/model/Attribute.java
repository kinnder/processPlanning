package planning.model;

public class Attribute implements Cloneable {

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

	public Attribute(String name, Integer value) {
		this.name = name;
		this.value = value;
		this.type = AttributeType.Integer;
	}

	@Override
	public Attribute clone() throws CloneNotSupportedException {
		Attribute clone = (Attribute) super.clone();
		clone.name = name;
		clone.value = value;
		clone.type = type;
		return clone;
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

	public Integer getValueAsInteger() {
		return (Integer) value;
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

	public void setValue(Object value) {
		this.value = value;
	}

	public AttributeTemplate createTemplate() {
		switch (type) {
		case Boolean:
			return new AttributeTemplate(name, getValueAsBoolean());
		case String:
			return new AttributeTemplate(name, getValueAsString());
		case Integer:
			return new AttributeTemplate(name, getValueAsInteger());
		default:
			return new AttributeTemplate(name, getValueAsObject());
		}
	}

	public AttributeType getType() {
		return type;
	}
}
