package biz.picosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class CourrierArriv� extends Courrier{
	
	@Column(name = "idD�partement")
	String idD�partement;
	@Column(name = "dateArriv�")
	String dateArriv�;
	@Column(name = "dateCr�ation")
	String dateCr�ation;
	@ManyToOne 
	@JoinColumn(name = "idSoci�t�")
	private Soci�t�  soci�t�;
	 @OneToOne
	private Contacte contacte;
	public CourrierArriv�(String idDocument, String idProcess) {
		super(idDocument, idProcess);
		// TODO Auto-generated constructor stub
	}
	 public CourrierArriv�(){
		 super();
	 }
	public CourrierArriv�(String idDocument, String idProcess, String idD�partement, String dateArriv�, String dateCr�ation,
			biz.picosoft.entity.Soci�t� soci�t�, Contacte contacte) {
		super(idDocument, idProcess);
		this.idD�partement = idD�partement;
		this.dateArriv� = dateArriv�;
		this.dateCr�ation = dateCr�ation;
		this.soci�t� = soci�t�;
		this.contacte = contacte;
	}

	private static final long serialVersionUID = 1L;
	public String getIdD�partement() {
		return idD�partement;
	}

	public void setIdD�partement(String idD�partement) {
		this.idD�partement = idD�partement;
	}

	public String getDateArriv�() {
		return dateArriv�;
	}

	public void setDateArriv�(String dateArriv�) {
		this.dateArriv� = dateArriv�;
	}

	public String getDateCr�ation() {
		return dateCr�ation;
	}

	public void setDateCr�ation(String dateCr�ation) {
		this.dateCr�ation = dateCr�ation;
	}

	public Soci�t� getSoci�t�() {
		return soci�t�;
	}

	public void setSoci�t�(Soci�t� soci�t�) {
		this.soci�t� = soci�t�;
	}

	public Contacte getContacte() {
		return contacte;
	}

	public void setContacte(Contacte contacte) {
		this.contacte = contacte;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
