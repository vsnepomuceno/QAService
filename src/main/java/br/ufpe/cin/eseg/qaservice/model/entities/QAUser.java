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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "qauser")
public class QAUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Integer codigo;

	@Column(name = "username", nullable = false, length = 100)
	private String username;
	@Column(name = "email", nullable = false, length = 50)
	private String email;
	@Column(name = "password", nullable = false, length = 64)
	private String password;

	@Column(name = "superUser", nullable = false)
	private boolean superUser;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "institution_id")
	private Institution institution;

	@OneToMany(mappedBy = "qauser", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy ("codigo DESC")
	private List<Paper> papersEvaluated;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public List<Paper> getPapersEvaluated() {
		return papersEvaluated;
	}

	public void setPapersEvaluated(List<Paper> papersEvaluated) {
		this.papersEvaluated = papersEvaluated;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public boolean isSuperUser() {
		return superUser;
	}

	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}
}
