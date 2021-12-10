package planning.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Link implements Cloneable {

	private String id1;

	private String id2;

	private String name;

	public Link(String name, String id1, String id2) {
		this.name = name;
		this.id1 = id1;
		this.id2 = id2;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof Link) {
			Link link = (Link) obj;
			return Objects.equals(name, link.name) && Objects.equals(id1, link.id1) && Objects.equals(id2, link.id2);
		}
		return false;
	}

	@Override
	public Link clone() throws CloneNotSupportedException {
		Link clone = (Link) super.clone();
		clone.name = name;
		clone.id1 = id1;
		clone.id2 = id2;
		return clone;
	}

	public String getName() {
		return name;
	}

	public String getId1() {
		return id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
	}

	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}

	public LinkTemplate createTemplate() {
		return new LinkTemplate(name, id1, id2);
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
