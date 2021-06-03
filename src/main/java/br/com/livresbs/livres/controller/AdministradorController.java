package br.com.livresbs.livres.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.livresbs.livres.dto.AdministratorDTO;
import br.com.livresbs.livres.model.Administrator;
import br.com.livresbs.livres.service.AdministratorService;

@RestController
@RequestMapping(value = "admin")
public class AdministradorController {
    @Autowired
    private AdministratorService admin;

    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<AdministratorDTO> listarAdministrator() {
        return admin.listarAdministrator();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Administrator listaAdministradorUnico(@PathVariable(value = "id") String id) {
        return admin.listaAdministratorUnico(id);
    }

    @CrossOrigin
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> editaAdministrator(@RequestBody AdministratorDTO administrator){
        return admin.editaAdministrator(administrator);
    }

    @CrossOrigin
    @PostMapping
    //@PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> cadastraAdministrator(@RequestBody AdministratorDTO administrator) { return admin.cadastraAdministrator(administrator); }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deletarAdministrator(@PathVariable(value = "id") String id) {
    	admin.deletarAdministrator(id);
    }
}
