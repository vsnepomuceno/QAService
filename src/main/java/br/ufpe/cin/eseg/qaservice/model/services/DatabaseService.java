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

@Path("service")
public class DatabaseService implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager em;

	public DatabaseService() {
		ApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();

		this.em = ((EntityManagerFactory) ac.getBean("entityManagerFactory")).
							createEntityManager();
	}

	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/hello")
	public String getPlain() {
		return "Hello World!!!";
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
			System.out.println(ex.getMessage());
			ex.printStackTrace();
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
}
