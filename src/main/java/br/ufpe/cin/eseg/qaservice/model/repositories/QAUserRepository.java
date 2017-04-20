package br.ufpe.cin.eseg.qaservice.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpe.cin.eseg.qaservice.model.entities.QAUser;

public interface QAUserRepository extends JpaRepository<QAUser, Integer> {

	public QAUser findByEmail(String email);
	
}
