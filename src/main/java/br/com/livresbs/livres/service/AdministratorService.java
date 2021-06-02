package br.com.livresbs.livres.service;

import br.com.livresbs.livres.dto.AdministratorDTO;
import br.com.livresbs.livres.dto.ConsumidorDTO;
import br.com.livresbs.livres.model.Administrator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AdministratorService {
	
    List<AdministratorDTO> listarAdministrator();
    Administrator listaAdministratorUnico(@PathVariable(value = "id") String id);
    ResponseEntity cadastraAdministrator(@RequestBody AdministratorDTO adm);
    void deletarAdministrator(@PathVariable(value = "id") String id);
    ResponseEntity<String> editaAdministrator(AdministratorDTO adm);
}
