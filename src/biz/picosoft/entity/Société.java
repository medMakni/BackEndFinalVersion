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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
public class Soci�t� implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSoci�t�")
	int idSoci�t�;
	@Column(name =  "nom")
	String nom;
	@Column(name = "email")
	String email;
	@Column(name = "t�l�phone")
	String t�l�phone;
	@Column(name = "adress")
	String adress;
	@OneToMany(cascade= CascadeType.REMOVE  ,fetch = FetchType.EAGER,mappedBy ="idContact", orphanRemoval=true) 
	private List<Contacte> contacts;
	@OneToMany(cascade= CascadeType.REMOVE  ,fetch = FetchType.EAGER,mappedBy ="idCourrier", orphanRemoval=true) 
	private List<Courrier> courrier;
	 public Soci�t�(String nom, String email, String t�l�phone, String adress) {
		super();
		this.nom = nom;
		this.email = email;
		this.t�l�phone = t�l�phone;
		this.adress = adress;
	}

	 

	public Soci�t�(int idSoci�t�, String nom, String email, String t�l�phone, String adress) {
		super();
		this.idSoci�t� = idSoci�t�;
		this.nom = nom;
		this.email = email;
		this.t�l�phone = t�l�phone;
		this.adress = adress;
		this.contacts = contacts;
	}



	public Soci�t�() {
		super();
	}



	public int getIdSoci�t�() {
		return idSoci�t�;
	}

	public void setIdSoci�t�(int idSoci�t�) {
		this.idSoci�t� = idSoci�t�;
	}
	@Column(name =  "nom")
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getT�l�phone() {
		return t�l�phone;
	}

	public void setT�l�phone(String t�l�phone) {
		this.t�l�phone = t�l�phone;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public List<Contacte> getContacts() {
		return contacts;
	}



	public void setContacts(List<Contacte> contacts) {
		this.contacts = contacts;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idSoci�t�;
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
		Soci�t� other = (Soci�t�) obj;
		if (idSoci�t� != other.idSoci�t�)
			return false;
		return true;
	} 

}
