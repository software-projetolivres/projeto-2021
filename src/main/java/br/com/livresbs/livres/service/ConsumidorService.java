package br.com.livresbs.livres.service;

import br.com.livresbs.livres.dto.ConsumidorDTO;
import br.com.livresbs.livres.exception.AuthorizationException;
import br.com.livresbs.livres.model.Consumidor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.security.auth.message.AuthException;

public interface ConsumidorService {
	
    List<ConsumidorDTO> listarConsumidor();
    Consumidor listaConsumidorUnico(@PathVariable(value = "id") String id);
    ResponseEntity cadastraConsumidor(@RequestBody ConsumidorDTO con);
    ResponseEntity deletarConsumidor(@PathVariable(value = "id") String id);
    ResponseEntity<String> editaConsumidor(ConsumidorDTO consumidor);
    Consumidor findById(String id) throws AuthorizationException;
}
