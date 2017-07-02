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
	@Column(name = "idD�partementSource")
	String idD�partementSource;
	@Column(name = "idD�partementDestination")
	String idD�partementDestination;
	@Column(name = "dateCr�ation")
	String dateCr�ation;
	public CourrierInterne(){
		 super();
	 }
	public CourrierInterne(String idDocument, String idProcess, String idD�partementSource,
			String idD�partementDestination, String dateCr�ation) {
		super(idDocument, idProcess);
		this.idD�partementSource = idD�partementSource;
		this.idD�partementDestination = idD�partementDestination;
		this.dateCr�ation = dateCr�ation;
	}
	public String getIdD�partementSource() {
		return idD�partementSource;
	}
	public void setIdD�partementSource(String idD�partementSource) {
		this.idD�partementSource = idD�partementSource;
	}
	public String getIdD�partementDestination() {
		return idD�partementDestination;
	}
	public void setIdD�partementDestination(String idD�partementDestination) {
		this.idD�partementDestination = idD�partementDestination;
	}
	public String getDateCr�ation() {
		return dateCr�ation;
	}
	public void setDateCr�ation(String dateCr�ation) {
		this.dateCr�ation = dateCr�ation;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
}
