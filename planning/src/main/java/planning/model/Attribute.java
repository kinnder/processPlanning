package planning.model;

public class Attribute implements Cloneable {

	private String name;

	private Object value;

	public Attribute(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public Attribute clone() throws CloneNotSupportedException {
		Attribute clone = (Attribute) super.clone();
		clone.name = name;
		clone.value = value;
		return clone;
	}

	public String getValueAsString() {
		return (String) value;
	}

	public boolean getValueAsBoolean() {
		return (boolean) (value);
	}

	public Object getValue() {
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
		return name.equals(attribute.name) && value.equals(attribute.value);
	}

	public String getName() {
		return this.name;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public AttributeTemplate createTemplate() {
		return new AttributeTemplate(name, value);
	}
}
