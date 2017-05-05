package biz.picosoft.entity;

public class Folder {

	int idFolder;
	String name;
	String creationDate;
	String author;
	String path;

	public Folder(int idFolder, String name, String creationDate, String author, String path) {
		super();
		this.idFolder = idFolder;
		this.name = name;
		this.creationDate = creationDate;
		this.author = author;
		this.path = path;
	}

	public int getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(int idFolder) {
		this.idFolder = idFolder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Folder [idFolder=" + idFolder + ", name=" + name + ", creationDate=" + creationDate + ", author="
				+ author + ", path=" + path + "]";
	}

}
