package br.ufpe.cin.eseg.qaservice.model.entities;

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
import javax.persistence.Table;

@Entity
@Table(name = "quality_assessment")
public class QualityAssessment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Integer codigo;

	@ManyToOne
	@JoinColumn(name = "paper_id", updatable = false)
	private Paper paper;

	@Column(name = "methodName", nullable = false, length = 100)
	private String methodName;

	@Column(name = "methodReference", nullable = false, length = 100)
	private String methodReference;

	@Column(name = "methodScore")
	private Integer methodScore;

	@Column(name = "observations", length = 2000)
	private String observations;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] pdfFile;

	@Column(name = "positiveqa")
	private Integer positiveQA;

	@Column(name = "negativeqa")
	private Integer negativeQA;

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
}
