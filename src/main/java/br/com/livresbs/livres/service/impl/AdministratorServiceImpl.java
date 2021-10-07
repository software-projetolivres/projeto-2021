package br.com.livresbs.livres.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private AdministratorRepository admin;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository admin, BCryptPasswordEncoder passwordEncoder) {
        this.admin = admin;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<AdministratorDTO> listarAdministrator() {
        List<AdministratorDTO> admListDto = new ArrayList<>();
        admin.findAll().forEach(admin -> {
            AdministratorDTO builderDto = AdministratorDTO.builder()
                    .id(admin.getId())
                    .nome(admin.getNome())
                    .sobrenome(admin.getSobrenome())
                    .email(admin.getEmail())
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
    public ResponseEntity<?> cadastraAdministrator(AdministratorDTO adm) {
        Set<Integer> adminRole = new HashSet<>();
        adminRole.add(1);
        if (!admin.existsByEmail(adm.getEmail())){
            Administrator administratorCre = Administrator.builder()
                    .email(adm.getEmail())
                    .nome(adm.getNome())
                    .sobrenome(adm.getSobrenome())
                    .senha(passwordEncoder.encode(adm.getSenha()))
                    .perfis(adminRole)
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
                        .id(adm.getId())
                        .nome(adm.getNome())
                        .email(adm.getEmail())
                        .senha(passwordEncoder.encode(adm.getSenha()))
                        .sobrenome(adm.getSobrenome())
                        .build();

                admin.save(adminEd);
            }
            else{

                Administrator adminEd = Administrator.builder()
                        .id(adm.getId())
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
