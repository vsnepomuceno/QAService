package br.ufpe.cin.eseg.qaservice.model;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufpe.cin.eseg.qaservice.model.entities.Institution;
import br.ufpe.cin.eseg.qaservice.model.entities.QAUser;
import br.ufpe.cin.eseg.qaservice.model.repositories.QAUserRepository;
import br.ufpe.cin.eseg.qaservice.model.util.MD5Hash;

@Component
@ManagedBean(name = "QAUserModel", eager = true)
@SessionScoped
public class QAUserModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private QAUserRepository qaUserRepository;

	// public actions
	public String login(String email, String password) {
		String ret = "";
		QAUser user = this.qaUserRepository.findByEmail(email);

		if (user != null) {
			password = MD5Hash.md5(password);
			if (user.getPassword().equals(password)) {
				this.setQAUserLogged(user);
				ret = "/restrict/index.xhtml?faces-redirect=true";
			} else {
				user = null;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"Login Failed!", "Invalid Password!"));
			}
		} else {
			user = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Login Failed!", "Invalid E-mail!"));
		}

		return ret;
	}

	public String registerUser(String name, String email, String password, 
			String instName, String instCountry) {
		String ret = "login.xhtml";
		if (name != null && email != null && password != null) {
			password = MD5Hash.md5(password);
			try {
				QAUser qaUser = new QAUser();
				qaUser.setUsername(name);
				qaUser.setEmail(email);
				qaUser.setPassword(password);
				Institution inst = new Institution();
				inst.setName(instName);
				inst.setCountry(instCountry);
				qaUser.setInstitution(inst);
				this.qaUserRepository.saveAndFlush(qaUser);
			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								"Register Failed!", ex.getMessage()));
			}
		}
		return ret;
	}
	
	public String updateUser() {
		String ret = "";
		try {
			QAUser qaUser = this.getQAUserLogged();
			this.qaUserRepository.saveAndFlush(qaUser);
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Register Failed!", ex.getMessage()));
		}
		return ret;
	}
	
	public String logout() {

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "/login.xhtml?faces-redirect=true";
	}

	// Session variables
	public void setQAUserLogged(QAUser qaUser) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
		put("qauserLogged", qaUser);
	}

	public QAUser getQAUserLogged() {
		return (QAUser) FacesContext.getCurrentInstance().getExternalContext().
				getSessionMap().get("qauserLogged");
	}

}
