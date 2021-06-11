package br.com.livresbs.livres.controller;

import java.util.List;

import br.com.livresbs.livres.service.PreComunidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.livresbs.livres.model.PreComunidade;

@RestController
@RequestMapping(value = "precomunidade")
public class PreComunidadeController {
    @Autowired
    private PreComunidadeService pcr;

    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<PreComunidade> listaPreComunidades() {
        return pcr.listaPreComunidades();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public PreComunidade listaPreComunidadeUnica(@PathVariable(value = "id") long id) {
        return pcr.listaPreComunidadeUnica(id);
    }

    @CrossOrigin
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PreComunidade cadastraPreComunidade(@RequestBody PreComunidade pc) {
        return pcr.cadastraPreComunidade(pc);
    }
    
    @CrossOrigin
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PreComunidade editaPreComunidade(@RequestBody PreComunidade pc) {
    	return pcr.editaPreComunidade(pc);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> excluiPreComunidade(@PathVariable(value = "id") long id) { return pcr.excluiPreComunidade(id); }
}
