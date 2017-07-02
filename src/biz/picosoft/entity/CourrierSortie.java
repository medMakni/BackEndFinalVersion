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
	@Column(name = "idD�partement")
	String idD�partement;
	@Column(name = "dateCr�ation")
	String dateCr�ation;
	@ManyToOne 
	@JoinColumn(name = "idSoci�t�")
	private Soci�t�  soci�t�;
	 @OneToOne
	private Contacte contacte;
	 
	 public CourrierSortie(){
		 super();
	 }
		public CourrierSortie(String idDocument, String idProcess, String idD�partement, String dateCr�ation, Soci�t� soci�t�,
				Contacte contacte) {
			super(idDocument, idProcess);
			this.idD�partement = idD�partement;
			this.dateCr�ation = dateCr�ation;
			this.soci�t� = soci�t�;
			this.contacte = contacte;
		}
		public String getIdD�partement() {
			return idD�partement;
		}
		public void setIdD�partement(String idD�partement) {
			this.idD�partement = idD�partement;
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
