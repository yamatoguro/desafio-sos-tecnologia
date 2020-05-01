package com.sos.desafio.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sos.desafio.main.model.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long>{
    
}