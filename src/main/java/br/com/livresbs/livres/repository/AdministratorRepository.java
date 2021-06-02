package br.com.livresbs.livres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.livresbs.livres.model.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, String> {
    
    Administrator findByEmail(String login);
    Boolean existsByEmail(String email);
}
