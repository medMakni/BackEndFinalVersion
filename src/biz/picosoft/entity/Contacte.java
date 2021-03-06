package biz.picosoft.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Contacte implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idContact")
	int idContact;
	@Column(name = "nom")
	String nom;
	@Column(name = "mail")
	String mail;
	@Column(name = "t�l�phone")
	String t�l�phone;
	@Column(name = "adresse")
	String adresse;
	@ManyToOne 
	@JoinColumn(name = "idSoci�t�")
	private Soci�t� soci�t�;
	@OneToMany(cascade= CascadeType.REMOVE  ,fetch = FetchType.EAGER,mappedBy ="idCourrier", orphanRemoval=true) 
	private List<Courrier> courrier;
	public Contacte() {
		super();
	}

	public long getIdContact() {
		return idContact;
	}

	public Contacte(String nom, String mail, String t�l�phone, String adresse, Soci�t� soci�t�) {
		super();
		this.nom = nom;
		this.mail = mail;
		this.t�l�phone = t�l�phone;
		this.adresse = adresse;
		this.soci�t� = soci�t�;
	}

	public Contacte(int idContact, String nom, String mail, String t�l�phone, String adresse, Soci�t� soci�t�) {
		super();
		this.idContact = idContact;
		this.nom = nom;
		this.mail = mail;
		this.t�l�phone = t�l�phone;
		this.adresse = adresse;
		this.soci�t� = soci�t�;
	}

	public void setIdContact(int idContact) {
		this.idContact = idContact;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getT�l�phone() {
		return t�l�phone;
	}

	public void setT�l�phone(String t�l�phone) {
		this.t�l�phone = t�l�phone;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idContact ^ (idContact >>> 32));
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
		Contacte other = (Contacte) obj;
		if (idContact != other.idContact)
			return false;
		return true;
	}

	public Soci�t� getSoci�t�() {
		return soci�t�;
	}

	public void setSoci�t�(Soci�t� soci�t�) {
		this.soci�t� = soci�t�;
	}

}
