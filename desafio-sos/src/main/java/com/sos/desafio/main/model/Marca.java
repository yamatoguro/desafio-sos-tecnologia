package com.sos.desafio.main.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
@Table(name = "marca")
public class Marca implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marca_id")
    private Long marcaId;

    @Column(name = "nome")
    private String nome;

    @Column(name = "pdf")
    private String pdf;

    public String toJson() 
    { 
        return "{" +
        ",\"marcaId\"" + "\"" + marcaId + "\"" +
        ",\"nome\"" + "\"" + nome + "\"" + 
        ",\"pdf\"" + "\"" + pdf + "\"" +"}";  
    } 
}
