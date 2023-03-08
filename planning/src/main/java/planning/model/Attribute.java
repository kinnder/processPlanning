package planning.model;

import java.util.UUID;

public class Attribute implements Cloneable {

	public Attribute() {
		this.name = "attribute-" + UUID.randomUUID().toString();
		this.value = null;
	}

	public Attribute(String name) {
		this.name = name;
		this.value = null;
	}

	public Attribute(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public Attribute clone() throws CloneNotSupportedException {
		return (Attribute) super.clone();
	}

	private Object value;

	public String getValueAsString() {
		return (String) value;
	}

	public boolean getValueAsBoolean() {
		return (boolean) (value);
	}

	public Integer getValueAsInteger() {
		return (Integer) value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Attribute) {
			final Attribute attribute = (Attribute) obj;
			return name.equals(attribute.name) && value.equals(attribute.value);
		}
		return false;
	}

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String aValue) {
		this.name = aValue;
	}

	public AttributeTemplate createTemplate() {
		return new AttributeTemplate(name, value);
	}
}
