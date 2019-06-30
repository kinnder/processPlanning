package planning.model;

public class AttributeTemplate {

	private String name;

	private Object value;

	private AttributeType type;

	private TemplateType templateType;

	public AttributeTemplate(String name, boolean value) {
		this.name = name;
		this.value = value;
		this.type = AttributeType.Boolean;
		this.templateType = TemplateType.ConcreteValue;
	}

	public AttributeTemplate(String name, String value) {
		this.name = name;
		this.value = value;
		this.type = AttributeType.String;
		this.templateType = TemplateType.ConcreteValue;
	}

	public AttributeTemplate(String name, Object value) {
		this.name = name;
		this.value = value;
		this.type = AttributeType.Object;
		this.templateType = TemplateType.ConcreteValue;
	}

	public AttributeTemplate(String name) {
		this.name = name;
		this.templateType = TemplateType.AnyValue;
	}

	public String getName() {
		return this.name;
	}

	public boolean matches(Attribute object) {
		switch (templateType) {
		case ConcreteValue:
			return matchesName(object) && matchesValue(object);
		default:
			return matchesName(object);
		}
	}

	private boolean matchesName(Attribute object) {
		return name.equals(object.getName());
	}

	private boolean matchesValue(Attribute object) {
		return type.equals(object.getType()) && value.equals(object.getValueAsObject());
	}
}
