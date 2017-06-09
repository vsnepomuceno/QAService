package br.ufpe.cin.eseg.qaservice.model.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufpe.cin.eseg.qaservice.model.entities.Paper;

public interface PaperRepository extends JpaRepository<Paper, Integer> {

	
	 @Query("select p from Paper p where p.name like %?1%")
	 public List<Paper> findByPartialNameAndSort(String name, Sort sort);
	 
	 @Query("select p from Paper p where p.authors like %?1%")
	 public List<Paper> findByPartialAuthors(String authors);
	 
	 public List<Paper> findByYear(Integer year);
	 
	 public Paper findByName(String name);
	 
	 public Paper findByCodigo(Integer codigo);
}
