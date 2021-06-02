package br.com.livresbs.livres.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.livresbs.livres.dto.AdministratorDTO;
import br.com.livresbs.livres.model.Administrator;
import br.com.livresbs.livres.repository.AdministratorRepository;
import br.com.livresbs.livres.service.AdministratorService;

@Service
public class AdministratorServiceImpl implements AdministratorService{

    @Autowired
    AdministratorRepository admin;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<AdministratorDTO> listarAdministrator() {
        List<AdministratorDTO> admListDto = new ArrayList<>();
        admin.findAll().forEach(admin -> {
            AdministratorDTO builderDto = AdministratorDTO.builder()
                    .nome(admin.getNome())
                    .senha(admin.getSenha())
                    .sobrenome(admin.getSobrenome())
                    .build();

            admListDto.add(builderDto);
        });
        return admListDto;
    }

    @Override
    public Administrator listaAdministratorUnico(String id) {
        return admin.findById(id).get();
    }

    @Override
    public ResponseEntity cadastraAdministrator(AdministratorDTO adm) {
        if (!admin.existsByEmail(adm.getEmail())){
            Administrator administratorCre = Administrator.builder()
                    .email(adm.getEmail())
                    .nome(adm.getNome())
                    .sobrenome(adm.getSobrenome())
                    .senha(passwordEncoder.encode(adm.getSenha()))
                    .build();

            admin.save(administratorCre);

            return ResponseEntity.status(HttpStatus.OK).body("Cadastrado com Sucesso!");
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
        }
    }

    @Override
    public void deletarAdministrator(String id) {
        admin.deleteById(id);
    }

    @Override
    public ResponseEntity<String> editaAdministrator(AdministratorDTO adm) {
        if(admin.existsByEmail(adm.getEmail())) {

            String senha = adm.getSenha();
            if(senha == "" || senha == null) {
                Administrator adminEd = Administrator.builder()
                        .nome(adm.getNome())
                        .email(adm.getEmail())
                        .senha(passwordEncoder.encode(adm.getSenha()))
                        .sobrenome(adm.getSobrenome())
                        .build();

                admin.save(adminEd);
            }
            else{

                Administrator adminEd = Administrator.builder()
                        .nome(adm.getNome())
                        .email(adm.getEmail())
                        .senha(admin.findById(adm.getId()).get().getSenha())
                        .sobrenome(adm.getSobrenome())
                        .build();

                admin.save(adminEd);
            }

            return ResponseEntity.status(HttpStatus.OK).body("Editado com Sucesso!");
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email não Encontrado!");
        }
    }
    
}
