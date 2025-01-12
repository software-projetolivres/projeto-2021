package br.com.livresbs.livres.service.impl;

import br.com.livresbs.livres.dto.ConsumidorDTO;
import br.com.livresbs.livres.exception.AuthorizationException;
import br.com.livresbs.livres.functions.ValidaCPF;
import br.com.livresbs.livres.model.Consumidor;
import br.com.livresbs.livres.model.PreComunidade;
import br.com.livresbs.livres.repository.ConsumidorRepository;
import br.com.livresbs.livres.repository.PreComunidadeRepository;
import br.com.livresbs.livres.security.JWTUtil;
import br.com.livresbs.livres.service.ConsumidorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class ConsumidorImpl implements ConsumidorService {
    @Autowired
    ConsumidorRepository cons;

    @Autowired
    PreComunidadeRepository pre;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;


    public List<ConsumidorDTO> listarConsumidor() {
        List<ConsumidorDTO> listConsdto = new ArrayList<>();
        cons.findAll().forEach(consumidor -> {
            ConsumidorDTO.ConsumidorDTOBuilder builderDto = ConsumidorDTO.builder()
                    .id(consumidor.getId())
                    .nome(consumidor.getNome())
                    .email(consumidor.getEmail())
                    .cpf(consumidor.getCpf())
                    .senha(consumidor.getSenha())
                    .sobrenome(consumidor.getSobrenome())
                    .enderecos(consumidor.getEnderecos());

            if(consumidor.getPrecomunidade() != null) {
                builderDto.precomunidade(consumidor.getPrecomunidade().getId());
            }

            listConsdto.add(builderDto.build());
        });
        return listConsdto;
    }

    public Consumidor listaConsumidorUnico(@PathVariable(value = "id") String id) {
        return cons.findById(id).get();
    }

    public ResponseEntity<String> cadastraConsumidor(@RequestBody ConsumidorDTO con)  {

        if(!ValidaCPF.isCPF(con.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido");
        }

        if(cons.existsByCpf(con.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já Cadastrado!");
        }

        if(cons.existsByEmail(con.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já Cadastrado!");
        }


        Set<Integer> conRole = new HashSet<>();
        conRole.add(2);
        Consumidor consumidor = Consumidor.builder()
                .cpf(con.getCpf())
                .email(con.getEmail())
                .nome(con.getNome())
                .sobrenome(con.getSobrenome())
                .senha(passwordEncoder.encode(con.getSenha()))
                .perfis(conRole)
                .enderecos(con.getEnderecos())
                .build();

        cons.save(consumidor);

        return ResponseEntity.status(HttpStatus.OK).body("Cadastrado com Sucesso!");
    }

    @Override
    public ResponseEntity<String> editaConsumidor(ConsumidorDTO consumidor) {
        if(cons.existsByEmail(consumidor.getEmail())) {
            Optional<PreComunidade> oppre = pre.findById(consumidor.getPrecomunidade());
            if (!oppre.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body("Pre Comunidade Não Encontrada!");
            }
            Set<Integer> conRole = new HashSet<>();
            conRole.add(2);
            String senha = consumidor.getSenha();
            if(senha == "" || senha == null) {

                Consumidor con = Consumidor.builder()
                        .id(consumidor.getId())
                        .cpf(consumidor.getCpf())
                        .nome(consumidor.getNome())
                        .email(consumidor.getEmail())
                        .senha(cons.findById(consumidor.getId()).get().getSenha())
                        .sobrenome(consumidor.getSobrenome())
                        .precomunidade(oppre.get())
                        .enderecos(consumidor.getEnderecos())
                        .perfis(conRole)
                        .build();

                cons.save(con);
            }
            else{

                Consumidor con = Consumidor.builder()
                        .id(consumidor.getId())
                        .cpf(consumidor.getCpf())
                        .nome(consumidor.getNome())
                        .sobrenome(consumidor.getSobrenome())
                        .email(consumidor.getEmail())
                        .senha(passwordEncoder.encode(consumidor.getSenha()))
                        .precomunidade(oppre.get())
                        .enderecos(consumidor.getEnderecos())
                        .perfis(conRole)
                        .build();

                cons.save(con);
            }

            return ResponseEntity.status(HttpStatus.OK).body("Editado com Sucesso!");
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Consumidor não Encontrado!");
        }
    }

    @Override
	public ResponseEntity<String> deletarConsumidor(String id) {
		if(cons.existsById(id)) {
			cons.deleteById(id);
			return ResponseEntity.ok().body("Consumidor deletado com sucesso");
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não encontrado");
		
	}

    @Override
    public Consumidor findById(String id) throws AuthorizationException {
        if(!jwtUtil.authorized(id)) {
            throw new AuthorizationException("Acesso negado!");
        }
        Optional<Consumidor> _con = cons.findById(id);
        return _con.orElse(null);
    }

    @Override
    public List<ConsumidorDTO> consumidoresSemPrecomunidade() {
        List<ConsumidorDTO> listConsdto = new ArrayList<>();
        cons.findAll().forEach(consumidor -> {
            if (consumidor.getPrecomunidade() == null){
                ConsumidorDTO builderDto = ConsumidorDTO.builder()
                        .id(consumidor.getId())
                        .nome(consumidor.getNome())
                        .cpf(consumidor.getCpf())
                        .email(consumidor.getEmail())
                        .senha(consumidor.getSenha())
                        .sobrenome(consumidor.getSobrenome())
                        .enderecos(consumidor.getEnderecos())
                        .build();

                listConsdto.add(builderDto);
            } 
        });
        return listConsdto;
    }
}