package com.senai.pousadabackend.domain.imagem.configuracao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemConfiguracaoRepository extends JpaRepository<ImagemConfiguracao, Long>  {


    @Query("SELECT i FROM ImagemConfiguracao i WHERE i.tema.id = :idTema")
    List<ImagemConfiguracao> listarPor(Long idTema);

}
