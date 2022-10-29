package models;

import java.util.Date;

public class Model {
	private int id;
	private String name;
	private Date created;
	private Date updated;

	public Model() {
	}

	public Model(int id, String name, Date created, Date updated) {
		super();
		this.id = id;
		this.name = name;
		this.created = created;
		this.updated = updated;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "Zone [id=" + id + ", name=" + name + ", created=" + created + ", updated=" + updated + "]";
	}

}
