package planning.model;

import java.util.UUID;

public class AttributeTemplate {

	private TemplateType templateType;

	public AttributeTemplate() {
		this("attributeTemplate-" + UUID.randomUUID().toString());
	}

	public AttributeTemplate(String name) {
		this.name = name;
		this.templateType = TemplateType.AnyValue;
	}

	public AttributeTemplate(String name, Object value) {
		this.name = name;
		this.value = value;
		this.templateType = TemplateType.ConcreteValue;
	}

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
		return value.equals(object.getValue());
	}

	private Object value;

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		// TODO (2022-12-02 #73) : добавить установку AnyValue и ConcreteValue
		this.value = value;
	}

	public AttributeType getType() {
		return AttributeType.fromValue(value);
	}

	public void setType(AttributeType type) {
		if (value == null) {
			return;
		}
		switch (type) {
		case BOOLEAN:
			value = Boolean.valueOf(value.toString());
			break;
		case INTEGER:
			value = Integer.valueOf(value.toString());
			break;
		case STRING:
			value = value.toString();
			break;
		case NULL:
			value = null;
			break;
		case OBJECT:
		default:
			break;
		}
	}
}
