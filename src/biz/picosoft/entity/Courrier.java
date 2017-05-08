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
	@Column(name = "idSource")
	long idSource;
	@Column(name = "idDépartement")
	long idDépartement;
	@OneToOne
	private Contacte contacte;
	// long[] listePiéceJointe;

	/*
	 * public Courrier(long idSource, long idDépartement, long idContct, long[]
	 * listePiéceJointe) { super(); this.idSource = idSource; this.idDépartement
	 * = idDépartement; this.idContact = idContct; this.listePiéceJointe =
	 * listePiéceJointe; }
	 */

	public long getIdSource() {
		return idSource;
	}

	public Courrier(long idSource, long idDépartement, Contacte contacte) {
		super();
		this.idSource = idSource;
		this.idDépartement = idDépartement;
		this.contacte = contacte;
	}

	 

	public void setIdSource(long idSource) {
		this.idSource = idSource;
	}

	public long getIdDépartement() {
		return idDépartement;
	}

	public void setIdDépartement(long idDépartement) {
		this.idDépartement = idDépartement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCourrier ^ (idCourrier >>> 32));
		return result;
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

	@Override
	public String toString() {
		return "Courrier [idCourrier=" + idCourrier + ", idSource=" + idSource + ", idDépartement=" + idDépartement
				+ "]";
	}

}
