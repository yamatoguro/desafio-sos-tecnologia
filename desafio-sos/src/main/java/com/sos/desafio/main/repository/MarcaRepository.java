package com.sos.desafio.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sos.desafio.main.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long>{
    
    @Query("select m from Marca m")
    public Marca[] getListaMarcas();
}