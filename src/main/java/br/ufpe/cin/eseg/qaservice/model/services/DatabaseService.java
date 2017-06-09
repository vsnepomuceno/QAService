package br.ufpe.cin.eseg.qaservice.model.services;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
