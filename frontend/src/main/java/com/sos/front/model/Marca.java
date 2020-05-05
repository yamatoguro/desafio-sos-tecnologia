package com.sos.front.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Marca implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long marcaId;
    private String nome;
    private String pdf;
    public String toJson() 
    { 
        return "{" +
        "\"marcaId\":" + marcaId +
        ",\"nome\":" + nome + "}"; 
    } 
}

