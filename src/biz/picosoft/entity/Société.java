package biz.picosoft.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Société implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSociété")
	int idSociété;
	@Column(name = "nom")
	String nom;
	@Column(name = "email")
	String email;
	@Column(name = "télèphone")
	String télèphone;
	@Column(name = "adress")
	String adress;
	@OneToMany (fetch = FetchType.EAGER,mappedBy = "société", cascade = CascadeType.ALL)
	private List<Contacte> contacts;
	
	 public Société(String nom, String email, String télèphone, String adress) {
		super();
		this.nom = nom;
		this.email = email;
		this.télèphone = télèphone;
		this.adress = adress;
	}

	 

	public Société(int idSociété, String nom, String email, String télèphone, String adress) {
		super();
		this.idSociété = idSociété;
		this.nom = nom;
		this.email = email;
		this.télèphone = télèphone;
		this.adress = adress;
		this.contacts = contacts;
	}



	public Société() {
		super();
	}



	public int getIdSociété() {
		return idSociété;
	}

	public void setIdSociété(int idSociété) {
		this.idSociété = idSociété;
	}

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

	public String getTélèphone() {
		return télèphone;
	}

	public void setTélèphone(String télèphone) {
		this.télèphone = télèphone;
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
		result = prime * result + idSociété;
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
		Société other = (Société) obj;
		if (idSociété != other.idSociété)
			return false;
		return true;
	} 

}
