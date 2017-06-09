package br.ufpe.cin.eseg.qaservice.model.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "quality_assessment")
public class QualityAssessment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Integer codigo;

	@ManyToOne
	@JoinColumn(name = "paper_id", updatable = false)
	private Paper paper;

	@Expose
	@Column(name = "methodName", nullable = false, length = 100)
	private String methodName;

	@Expose
	@Column(name = "methodReference", nullable = false, length = 100)
	private String methodReference;

	@Expose
	@Column(name = "methodScore")
	private Integer methodScore;

	@Expose
	@Column(name = "observations", length = 2000)
	private String observations;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] pdfFile;

	@Expose
	@Column(name = "positiveqa")
	private Integer positiveQA;

	@Expose
	@Column(name = "negativeqa")
	private Integer negativeQA;
	
	@OneToOne
	@JoinColumn(name = "qauser_id")
	private QAUser qaUser;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Integer getMethodScore() {
		return methodScore;
	}

	public void setMethodScore(Integer methodScore) {
		this.methodScore = methodScore;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public byte[] getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(byte[] pdfFile) {
		this.pdfFile = pdfFile;
	}

	public String getMethodReference() {
		return methodReference;
	}

	public void setMethodReference(String methodReference) {
		this.methodReference = methodReference;
	}

	public Integer getPositiveQA() {
		return positiveQA;
	}

	public void setPositiveQA(Integer positiveQA) {
		this.positiveQA = positiveQA;
	}

	public Integer getNegativeQA() {
		return negativeQA;
	}

	public void setNegativeQA(Integer negativeQA) {
		this.negativeQA = negativeQA;
	}
	
	public QAUser getQaUser() {
		return qaUser;
	}

	public void setQaUser(QAUser qaUser) {
		this.qaUser = qaUser;
	}
}
