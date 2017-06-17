package br.ufpe.cin.eseg.qaservice.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Integer codigo;
	
	@ManyToOne
	@JoinColumn(name = "quality_assessment_id", updatable = false)
	private QualityAssessment qualityAssessment;
	
	@Column(name = "message", nullable = false, length = 1000)
	private String message;
	
	@OneToOne
	@JoinColumn(name = "qauser_id")
	private QAUser sender;
	
	@Column(name = "positive")
	private Integer positive = 0;

	@Column(name = "negative")
	private Integer negative = 0;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public QualityAssessment getQualityAssessment() {
		return qualityAssessment;
	}

	public void setQualityAssessment(QualityAssessment qualityAssessment) {
		this.qualityAssessment = qualityAssessment;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public QAUser getSender() {
		return sender;
	}

	public void setSender(QAUser sender) {
		this.sender = sender;
	}

	public Integer getPositive() {
		return positive;
	}

	public void setPositive(Integer positive) {
		this.positive = positive;
	}

	public Integer getNegative() {
		return negative;
	}

	public void setNegative(Integer negative) {
		this.negative = negative;
	}	
}
