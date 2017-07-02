package biz.picosoft.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Courrier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCourrier")
	long idCourrier;
	@Column(name = "idDocument")
	String idDocument;
	@Column(name = "idProcess")
	String idProcess;
	/*@OneToOne
	private Contacte contacte;
	// long[] listePiéceJointe;
*/
	/*
	 * public Courrier(long idSource, long idDépartement, long idContct, long[]
	 * listePiéceJointe) { super(); this.idSource = idSource; this.idDépartement
	 * = idDépartement; this.idContact = idContct; this.listePiéceJointe =
	 * listePiéceJointe; }
	 */
	public Courrier() {
 
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCourrier ^ (idCourrier >>> 32));
		return result;
	}
	public Courrier(String idDocument, String idProcess) {
		super();
		this.idDocument = idDocument;
		this.idProcess = idProcess;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Courrier other = (Courrier) obj;
		if (idCourrier != other.idCourrier)
			return false;
		return true;
	}
	public long getIdCourrier() {
		return idCourrier;
	}
	public void setIdCourrier(long idCourrier) {
		this.idCourrier = idCourrier;
	}
	public String getIdDocument() {
		return idDocument;
	}
	public void setIdDocument(String idDocument) {
		this.idDocument = idDocument;
	}
	public String getIdProcess() {
		return idProcess;
	}
	public void setIdProcess(String string) {
		this.idProcess = string;
	}
	@Override
	public String toString() {
		return "Courrier [idCourrier=" + idCourrier + ", idDocument=" + idDocument + ", idProcess=" + idProcess + "]";
	}

 
}
