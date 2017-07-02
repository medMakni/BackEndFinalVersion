package biz.picosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Entity
public class CourrierSortie extends Courrier{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "idDépartement")
	String idDépartement;
	@Column(name = "dateCréation")
	String dateCréation;
	@ManyToOne 
	@JoinColumn(name = "idSociété")
	private Société  société;
	 @OneToOne
	private Contacte contacte;
	 
	 public CourrierSortie(){
		 super();
	 }
		public CourrierSortie(String idDocument, String idProcess, String idDépartement, String dateCréation, Société société,
				Contacte contacte) {
			super(idDocument, idProcess);
			this.idDépartement = idDépartement;
			this.dateCréation = dateCréation;
			this.société = société;
			this.contacte = contacte;
		}
		public String getIdDépartement() {
			return idDépartement;
		}
		public void setIdDépartement(String idDépartement) {
			this.idDépartement = idDépartement;
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
