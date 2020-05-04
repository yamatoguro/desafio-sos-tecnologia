package com.sos.front.model;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patrimonio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private Long marcaId;
    private String marca;
    private String nome;
    private String descricao;
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

