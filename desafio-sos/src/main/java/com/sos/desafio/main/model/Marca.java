package com.sos.desafio.main.model;

import lombok.Data;
import javax.persistence.*;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Data
@Entity
@Component
@Table(name = "marca")
public class Marca implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marcaid")
    private Long marcaId;

    @Column(name = "nome")
    private String nome;
}
