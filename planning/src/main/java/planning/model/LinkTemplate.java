package planning.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LinkTemplate implements Cloneable {

	private String id1;

	private String id2;

	private String name;

	public LinkTemplate(String name, String id1, String id2) {
		this.name = name;
		this.id1 = id1;
		this.id2 = id2;
	}

	public String getId1() {
		return id1;
	}

	public String getId2() {
		return id2;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof LinkTemplate) {
			LinkTemplate linkTemplate = (LinkTemplate) obj;
			return Objects.equals(name, linkTemplate.name) && Objects.equals(id1, linkTemplate.id1)
					&& Objects.equals(id2, linkTemplate.id2);
		}
		return false;
	}

	public boolean matches(Link link, IdsMatching matching) {
		return link.getName().equals(name) && Objects.equals(matching.get(id1), link.getId1())
				&& Objects.equals(matching.get(id2), link.getId2());
	}

	@Override
	public LinkTemplate clone() throws CloneNotSupportedException {
		LinkTemplate clone = (LinkTemplate) super.clone();
		clone.name = name;
		clone.id1 = id1;
		clone.id2 = id2;
		return clone;
	}

	public Set<String> getIds() {
		Set<String> ids = new HashSet<>();
		if (id1 != null) {
			ids.add(id1);
		}
		if (id2 != null) {
			ids.add(id2);
		}
		return ids;
	}
}
