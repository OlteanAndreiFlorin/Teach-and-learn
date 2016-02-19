package ro.sci.group2.domain;

public class Course extends AbstractModel {

	private String name;
	private int level;

	public Course(long id) {
		setId(id);
	}

	public Course() {
		this(0);
	}

	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {

		this.name = name;

	}

	public int getLevel() {
		return level;
	}

	/**
	 * 
	 * @param level
	 */
	public void setLevel(int level) {

		this.level = level;

	}



	@Override
	public String toString() {
		return "Course [name=" + name + ", level=" + level + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + level;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (level != other.level)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.toLowerCase().equals(other.name.toLowerCase()))
			return false;
		return true;
	}
}
