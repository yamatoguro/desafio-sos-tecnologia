package com.sos.front.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Marca implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long marcaId;

    private String nome;

    public String toJson() 
    { 
        return "{" +
        "\"marcaId\":" + marcaId +
        "\"nome\":" + nome + "}"; 
    } 
}

