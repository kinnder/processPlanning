package planning.model;

import java.util.UUID;

public class AttributeTemplate {

	private String name;

	private Object value;

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

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		// TODO (2022-12-02 #73) : добавить установку AnyValue и ConcreteValue
		this.value = value;
	}
}
