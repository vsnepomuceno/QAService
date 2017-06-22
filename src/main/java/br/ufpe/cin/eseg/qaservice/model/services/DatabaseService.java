package br.ufpe.cin.eseg.qaservice.model.services;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.ufpe.cin.eseg.qaservice.model.entities.Paper;
import br.ufpe.cin.eseg.qaservice.util.LoggerQAS;

@Path("service")
public class DatabaseService implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;

	public DatabaseService() {
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();

		this.em = ((EntityManagerFactory) ac.getBean("entityManagerFactory")).
							createEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/papers")
	public String getPapers() {
		String json = "";
		try {
		Query q = em.createQuery("from Paper");
		List<Paper> papers = q.getResultList();

		Gson gson = new GsonBuilder()
			    .excludeFieldsWithoutExposeAnnotation()
			    .create();
		json = gson.toJson(papers);
		} catch(Exception ex) {
			ex.printStackTrace();
			LoggerQAS.getLoggerInstance().logError(ex.getMessage());
		}
		return json;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/papers/title")
	public String getPapersByTitle(@QueryParam(value = "title") String title) {
		String json = "";
		try {
		Query q = em.createQuery("from Paper p where p.name like  '%"+title+"%'");
		List<Paper> papers = q.getResultList();

		Gson gson = new GsonBuilder()
			    .excludeFieldsWithoutExposeAnnotation()
			    .create();
		json = gson.toJson(papers);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return json;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/papers/authors")
	public String getPapersByAuthors(@QueryParam(value = "authors") String authors) {
		String json = "";
		try {
		Query q = em.createQuery("from Paper p where p.authors like '%"+authors+"%'");
		List<Paper> papers = q.getResultList();

		Gson gson = new GsonBuilder()
			    .excludeFieldsWithoutExposeAnnotation()
			    .create();
		json = gson.toJson(papers);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return json;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/papers/year")
	public String getPapersByYear(@QueryParam(value = "year") String year) {
		String json = "";
		try {
		Query q = em.createQuery("from Paper p where p.year = "+year);
		List<Paper> papers = q.getResultList();

		Gson gson = new GsonBuilder()
			    .excludeFieldsWithoutExposeAnnotation()
			    .create();
		json = gson.toJson(papers);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return json;
	}
	
	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Path("/help")
	public String getPlain() {		
		String html = "<?xml version='1.0' encoding='UTF-8' ?>" + 
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
					"<html> <head> <style> table { font-family: arial, sans-serif; border-collapse: collapse; width: 100%; }" +
					"td, th { border: 1px solid #dddddd; padding: 8px;} " +
					"tr:nth-child(even) { background-color: #dddddd;}" + 
					"</style><title>QAService ESEG</title> </head>" +					
					"<body> " +
					"<table><tr><th><h3>Service Name</h3></th> <th><h3>URL</h3></th> <th><h3>Description</h3></th> </tr>" +

					"<tr> "+
				    "<td>Get All Papers</td>"+
				    "<td>vsnepomuceno.com.br/qaservice/resources/service/papers</td>"+
				    "<td>This Service retrieves a JSON file with information about all papers stored on database.</td>"+
				    "</tr>"+
				    
					"<tr> "+
					"<td>Get Papers By Title</td>"+
					"<td>vsnepomuceno.com.br/qaservice/resources/service/papers/title?title=value</td>"+
					"<td>This Service retrieves a JSON file with information about papers with the <b>title</b> parameter value.</td>"+
					"</tr>"+
					
					"<tr> "+
					"<td>Get Papers By Authors</td>"+
					"<td>vsnepomuceno.com.br/qaservice/resources/service/papers/authors?authors=value</td>"+
					"<td>This Service retrieves a JSON file with information about papers with the <b>authors</b> parameter value.</td>"+
					"</tr>"+
										
					"<tr> "+
					"<td>Get Papers By Year</td>"+
					"<td>vsnepomuceno.com.br/qaservice/resources/service/papers/year?year=value</td>"+
					"<td>This Service retrieves a JSON file with information about papers with the <b>year</b> parameter value.</td>"+
					"</tr>"+
				    
					"</body> </html>";
		return html;
	}
}
