package br.ufpe.cin.eseg.qaservice.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufpe.cin.eseg.qaservice.model.entities.Paper;
import br.ufpe.cin.eseg.qaservice.model.entities.QAUser;
import br.ufpe.cin.eseg.qaservice.model.entities.QualityAssessment;
import br.ufpe.cin.eseg.qaservice.model.repositories.PaperRepository;
import br.ufpe.cin.eseg.qaservice.model.repositories.QAUserRepository;
import br.ufpe.cin.eseg.qaservice.util.LoggerQAS;

@Component
@ManagedBean(name = "paperModel", eager = true)
@SessionScoped
public class PaperModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PaperRepository paperRepository;
	
	@Autowired
	private QAUserRepository qaUserRepository;
	
	public String registerPaper(String title, String authors, String year) {
		String ret = "";
		try {
			
			if (paperRepository.findByName(title) != null) {
				throw new Exception("Paper already registered!");
			}
			
			Paper paper = new Paper();
			paper.setName(title);
			paper.setAuthors(authors);
			paper.setYear(Integer.parseInt(year));
			paper.setQauser(this.getQAUserLogged());
			
			paper = paperRepository.saveAndFlush(paper);
			
			this.setQAUserLogged(qaUserRepository.findByEmail(paper.getQauser().getEmail()));
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, 
							"Paper Registered with success!", ""));
			
			ret = "/restrict/index.xhtml?faces-redirect=true";
		}catch (NumberFormatException nfex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Not valid year!", ""));
		}catch (Exception ex) {
			LoggerQAS.getLoggerInstance().logError(ex.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							ex.getMessage(), ""));
		}
		
		return ret;
	}
	
	public List<Paper> getAllPapers() {
		
		List<Paper> papers = paperRepository.findAll();
				
		return papers;
	}
	
	public String listAssessments(Integer codigo) {
		
		Paper paper = paperRepository.findByCodigo(codigo);
		
		this.setSelectedPaper(paper);
		
		return "/restrict/assessment/list.xhtml?faces-redirect=true";
	}
	
	public String registerAssessmentRedirect() {
		return "/restrict/assessment/register.xhtml?faces-redirect=true";
	}
	
	public String registerAssessmentRedirect(String methodName, String 
			methodReference, Integer methodScore, String observations) {
		String ret = "";
		try {
			QualityAssessment qa = new QualityAssessment();
			qa.setMethodName(methodName);
			qa.setMethodReference(methodReference);
			qa.setMethodScore(methodScore);
			qa.setObservations(observations);
			
			
			Paper paper = this.getSelectedPaper();
			paper.getQualityAssements().add(qa);
			paper = paperRepository.saveAndFlush(paper);
			this.setSelectedPaper(paper);
			ret = "/restrict/assessment/list.xhtml?faces-redirect=true";
		}catch (Exception ex) {
			LoggerQAS.getLoggerInstance().logError(ex.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							ex.getMessage(), ""));
		}
		
		return ret;
	}
	
	public QAUser getQAUserLogged() {
		return (QAUser) FacesContext.getCurrentInstance().getExternalContext().
				getSessionMap().get("qauserLogged");
	}
	
	public void setQAUserLogged(QAUser qaUser) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
		put("qauserLogged", qaUser);
	}
	
	public Paper getSelectedPaper() {
		return (Paper) FacesContext.getCurrentInstance().getExternalContext().
				getSessionMap().get("selectedPaper");
	}
	
	public void setSelectedPaper(Paper paper) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
		put("selectedPaper", paper);
	}
}
