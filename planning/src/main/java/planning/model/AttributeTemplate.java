package planning.model;

public class AttributeTemplate {

	private String name;

	private Object value;

	private AttributeType type;

	public AttributeTemplate(String name, boolean value) {
		this.name = name;
		this.value = value;
		this.type = AttributeType.Boolean;
	}

	public AttributeTemplate(String name, String value) {
		this.name = name;
		this.value = value;
		this.type = AttributeType.String;
	}

	public AttributeTemplate(String name, Object value) {
		this.name = name;
		this.value = value;
		this.type = AttributeType.Object;
	}

	public String getName() {
		return this.name;
	}

	public boolean matches(Attribute object) {
		return name.equals(object.getName()) && type.equals(object.getType())
				&& value.equals(object.getValueAsObject());
	}
}
