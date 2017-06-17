package br.ufpe.cin.eseg.qaservice.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpe.cin.eseg.qaservice.model.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
