package br.ufpe.cin.eseg.qaservice.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import br.ufpe.cin.eseg.qaservice.model.entities.Comment;
import br.ufpe.cin.eseg.qaservice.model.entities.Paper;
import br.ufpe.cin.eseg.qaservice.model.entities.QAUser;
import br.ufpe.cin.eseg.qaservice.model.entities.QualityAssessment;
import br.ufpe.cin.eseg.qaservice.model.repositories.CommentRepository;
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
	
	@Autowired
	private CommentRepository commentRepository;
	
	private UploadedFile file;	

	private Paper paper;
	
	public PaperModel() {
		this.paper = new Paper();
	}

	
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
			
			this.setQAUserLogged(qaUserRepository.findByEmail(
										paper.getQauser().getEmail()));
			
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
	
	public String updatePaper() {
		String ret = "";
		try {
			Paper found = paperRepository.findByName(paper.getName());
			if ((found != null) && (found.getCodigo() != this.paper.getCodigo())) {
				this.setPaper(new Paper());
				throw new Exception("Paper already registered!");
			}
			paperRepository.saveAndFlush(paper);
			
			
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
			if (this.file == null) {
				throw new Exception("File is not valid!");
			}
			QualityAssessment qa = new QualityAssessment();
			qa.setMethodName(methodName);
			qa.setMethodReference(methodReference);
			qa.setMethodScore(methodScore);
			qa.setObservations(observations);
			qa.setPdfFile(this.file.getContents());
			qa.setQaUser(this.getQAUserLogged());
			
			Paper paper = this.getSelectedPaper();
			paper.getQualityAssements().add(qa);
			qa.setPaper(paper);
			paper = paperRepository.saveAndFlush(paper);
			this.setSelectedPaper(paper);
			ret = "/restrict/assessment/list.xhtml?faces-redirect=true";
		}catch (Exception ex) {
			LoggerQAS.getLoggerInstance().logError(ex.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							ex.getMessage(), ""));
			ex.printStackTrace();
		}
		
		return ret;
	}
	
	public String detailAssessmentRedirect(QualityAssessment qa) {
		String ret= "";
		
		if (qa != null) {
			this.setSelectedQA(qa);
			ret = "/restrict/assessment/detail.xhtml?faces-redirect=true";
		}
		
		return ret;
	}
	
	public String registerComment(String message, QualityAssessment qa) {
		String ret = "";
		
		if (message != null && !message.isEmpty()) {
			try {
				
				Comment com = new Comment();
				com.setMessage(message);
				com.setQualityAssessment(qa);
				com.setSender(this.getQAUserLogged());
				
				List<Comment> coms = qa.getComments();
				coms.add(com);
				qa.setComments(coms);
				
				Paper paper = qa.getPaper();
				paper = paperRepository.saveAndFlush(paper);
				this.setSelectedQA(qa);
				ret = "/restrict/assessment/detail.xhtml?faces-redirect=true";
			}catch (Exception ex) {
				LoggerQAS.getLoggerInstance().logError(ex.getMessage());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								ex.getMessage(), ""));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Message can not be empty!", ""));
		}
		
		return ret;
	}
	
	public String likeQualityAssessment(QualityAssessment quality, boolean like) {
		String ret = "";
		
		if (like) {
			quality.setPositiveQA(quality.getPositiveQA()+1);
		} else {
			quality.setNegativeQA(quality.getNegativeQA()+1);
		}
		
		Paper paper = quality.getPaper();
		paper = paperRepository.saveAndFlush(paper);
		this.setSelectedQA(quality);
		
		return ret;
	}
	
	public String likeComment(Comment comment, boolean like) {
		String ret = "";
		
		if (like) {
			comment.setPositive(comment.getPositive()+1);
		} else {
			comment.setNegative(comment.getNegative()+1);
		}
		
		this.commentRepository.saveAndFlush(comment);
		this.setSelectedQA(comment.getQualityAssessment());
		
		return ret;
	}

	public String searchTitle(String title) {
		String ret= "";
		
		if (title != null) {
			List<Paper> papers = this.paperRepository.findByPartialNameAndSort(title, new Sort(Sort.Direction.ASC, "name"));
			this.setPapers(papers);
			ret = "/restrict/assessment/searchResult.xhtml?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Title can not be empty!", ""));
		}
		
		return ret;
	}
	
	public String searchAuthors(String authors) {
		String ret= "";
		
		if (authors != null) {
			List<Paper> papers = this.paperRepository.findByPartialAuthors(authors);
			this.setPapers(papers);
			ret = "/restrict/assessment/searchResult.xhtml?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Authors can not be empty!", ""));
		}
		
		return ret;
	}
	
	public String searchYear(Integer year) {
		String ret= "";
		
		if (year != null) {
			List<Paper> papers = this.paperRepository.findByYear(year);
			this.setPapers(papers);
			ret = "/restrict/assessment/searchResult.xhtml?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Year can not be empty!", ""));
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
	
	public QualityAssessment getSelectedQA() {
		return (QualityAssessment) FacesContext.getCurrentInstance().getExternalContext().
				getSessionMap().get("qualityAssessment");
	}
	
	public void setSelectedQA(QualityAssessment qa) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
		put("qualityAssessment", qa);
	}
	
	@SuppressWarnings("unchecked")
	public List<Paper> getPapers() {
		return (List<Paper>) FacesContext.getCurrentInstance().getExternalContext().
				getSessionMap().get("papers");
	}
	
	public void setPapers(List<Paper> papers) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
		put("papers", papers);
	}
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	public StreamedContent getPdfFile() {
		InputStream stream = new ByteArrayInputStream(this.getSelectedQA().getPdfFile());
		StreamedContent pdffile = new DefaultStreamedContent(stream, "application/pdf", "attachment.pdf");
        return pdffile;
    }

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}
}
