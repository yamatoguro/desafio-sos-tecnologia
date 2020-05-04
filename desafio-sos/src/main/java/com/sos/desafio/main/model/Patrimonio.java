package com.sos.desafio.main.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
@Table(name = "patrimonio")
public class Patrimonio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "marcaid")
    private Long marcaId;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "n_tombo")
    private String n_tombo;

    public String toJson() 
    { 
        return "{" +
        "\"id\":" + id +
        "\"marcaId\":" + marcaId +
        "\"nome\":" + nome +
        "\"descricao\":" + descricao +
        "\"n_tombo\":" + n_tombo + "}"; 
    } 
}
