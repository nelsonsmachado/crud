package com.dbserver.crud.repositories;

import com.dbserver.crud.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    boolean existsByRuaAndNumeroAndComplementoAndBairroAndCidadeAndEstadoAndCep(String rua, int numero, String complemento, String bairro, String cidade, String estado, String cep);

}


