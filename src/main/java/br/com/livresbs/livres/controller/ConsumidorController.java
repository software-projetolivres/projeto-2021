package br.com.livresbs.livres.controller;

import br.com.livresbs.livres.dto.ConsumidorDTO;
import br.com.livresbs.livres.exception.AuthorizationException;
import br.com.livresbs.livres.model.Consumidor;
import br.com.livresbs.livres.service.ConsumidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "consumidor")
public class ConsumidorController {
    @Autowired
    private ConsumidorService cons;

    @CrossOrigin
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<ConsumidorDTO> listarConsumidor() {
        return cons.listarConsumidor();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Consumidor> listaConsumidorUnico(@PathVariable(value = "id") String id) {
        try {
            Consumidor _cons = cons.findById(id);
            if (_cons != null){
                return ResponseEntity.ok(_cons);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (AuthorizationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<String> editaConsumidor(@RequestBody ConsumidorDTO consumidor){
        return cons.editaConsumidor(consumidor);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> cadastraConsumidor(@RequestBody ConsumidorDTO con) { return cons.cadastraConsumidor(con); }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deletaConsumidor(@PathVariable(value = "id") String id) {
    	cons.deletarConsumidor(id);
    }

    @CrossOrigin
    @GetMapping("/pendentes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<ConsumidorDTO> consumidoresSemPrecomunidade(){
        return cons.consumidoresSemPrecomunidade();
    }
}
