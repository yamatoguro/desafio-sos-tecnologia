package com.sos.desafio.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sos.desafio.main.model.Patrimonio;

@Repository
public interface PatrimonioRepository extends JpaRepository<Patrimonio, Long> {
    
    @Query("select p from Patrimonio p")
    public Patrimonio[] getListaPatrimonios();
}