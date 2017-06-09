package br.ufpe.cin.eseg.qaservice.model.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "paper")
public class Paper implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Integer codigo;

	@Expose
	@ManyToOne
	@JoinColumn(name = "qauser_id", updatable = false)
	private QAUser qauser;

	@Expose
	@Column(name = "name", nullable = false, length = 300)
	private String name;
	
	@Expose
	@Column(name = "authors", nullable = false, length = 300)
	private String authors;

	@Expose
	@Column(name = "year", nullable = false)
	private Integer year;

	@Expose
	@OneToMany(mappedBy = "paper", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy
	private List<QualityAssessment> qualityAssements;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public QAUser getQauser() {
		return qauser;
	}

	public void setQauser(QAUser user) {
		this.qauser = user;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<QualityAssessment> getQualityAssements() {
		return qualityAssements;
	}

	public void setQualityAssements(List<QualityAssessment> qualityAssements) {
		this.qualityAssements = qualityAssements;
	}
}
