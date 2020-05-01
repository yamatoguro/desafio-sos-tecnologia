package com.sos.desafio.main.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.desafio.main.model.Patrimonio;

public interface PatrimonioRepository extends JpaRepository<Patrimonio, Long> {
    
}