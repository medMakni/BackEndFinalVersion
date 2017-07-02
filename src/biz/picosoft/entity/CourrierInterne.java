package biz.picosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class CourrierInterne extends Courrier {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "idDépartementSource")
	String idDépartementSource;
	@Column(name = "idDépartementDestination")
	String idDépartementDestination;
	@Column(name = "dateCréation")
	String dateCréation;
	public CourrierInterne(){
		 super();
	 }
	public CourrierInterne(String idDocument, String idProcess, String idDépartementSource,
			String idDépartementDestination, String dateCréation) {
		super(idDocument, idProcess);
		this.idDépartementSource = idDépartementSource;
		this.idDépartementDestination = idDépartementDestination;
		this.dateCréation = dateCréation;
	}
	public String getIdDépartementSource() {
		return idDépartementSource;
	}
	public void setIdDépartementSource(String idDépartementSource) {
		this.idDépartementSource = idDépartementSource;
	}
	public String getIdDépartementDestination() {
		return idDépartementDestination;
	}
	public void setIdDépartementDestination(String idDépartementDestination) {
		this.idDépartementDestination = idDépartementDestination;
	}
	public String getDateCréation() {
		return dateCréation;
	}
	public void setDateCréation(String dateCréation) {
		this.dateCréation = dateCréation;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
}
