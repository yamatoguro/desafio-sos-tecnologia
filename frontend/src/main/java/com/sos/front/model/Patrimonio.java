package com.sos.front.model;

import java.io.Serializable;

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

    private Long id;
    private Long marcaId;
    private String marca;
    private String nome;
    private String descricao;
    private String n_tombo;
    private String pdf;
    public String toJson() 
    { 
        return "[{" +
        "\"id\":" + "\"" + id + "\"" +
        ",\"marcaId\":" + "\"" + marcaId + "\"" +
        ",\"marca\":" + "\"" + marca + "\"" +
        ",\"nome\":" + "\"" + nome + "\"" +
        ",\"descricao\":" + "\"" + descricao + "\"" +
        ",\"n_tombo\":" + "\"" + n_tombo + "\"" + 
        ",\"pdf\":" + "\"" + pdf + "\"" + "}]"; 
    } 
}

