package biz.picosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class CourrierArrivé extends Courrier{
	
	@Column(name = "idDépartement")
	String idDépartement;
	@Column(name = "dateArrivé")
	String dateArrivé;
	@Column(name = "dateCréation")
	String dateCréation;
	@ManyToOne 
	@JoinColumn(name = "idSociété")
	private Société  société;
	 @OneToOne
	private Contacte contacte;
	public CourrierArrivé(String idDocument, String idProcess) {
		super(idDocument, idProcess);
		// TODO Auto-generated constructor stub
	}
	 public CourrierArrivé(){
		 super();
	 }
	public CourrierArrivé(String idDocument, String idProcess, String idDépartement, String dateArrivé, String dateCréation,
			biz.picosoft.entity.Société société, Contacte contacte) {
		super(idDocument, idProcess);
		this.idDépartement = idDépartement;
		this.dateArrivé = dateArrivé;
		this.dateCréation = dateCréation;
		this.société = société;
		this.contacte = contacte;
	}

	private static final long serialVersionUID = 1L;
	public String getIdDépartement() {
		return idDépartement;
	}

	public void setIdDépartement(String idDépartement) {
		this.idDépartement = idDépartement;
	}

	public String getDateArrivé() {
		return dateArrivé;
	}

	public void setDateArrivé(String dateArrivé) {
		this.dateArrivé = dateArrivé;
	}

	public String getDateCréation() {
		return dateCréation;
	}

	public void setDateCréation(String dateCréation) {
		this.dateCréation = dateCréation;
	}

	public Société getSociété() {
		return société;
	}

	public void setSociété(Société société) {
		this.société = société;
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
