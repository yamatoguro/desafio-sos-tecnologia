package com.sos.desafio.main.model.dto;

import com.sos.desafio.main.model.Patrimonio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatrimonioDTO {

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
        "\"marca\":" + marca +
        "\"nome\":" + nome +
        "\"descricao\":" + descricao +
        "\"n_tombo\":" + n_tombo + "}"; 
    }

	public PatrimonioDTO(Patrimonio p) {
        this.id = p.getId();
        this.marcaId = p.getMarcaId();
        this.nome = p.getNome();
        this.descricao = p.getDescricao();
        this.n_tombo = p.getN_tombo();
	}

	public Patrimonio toObj() {
        Patrimonio p = new Patrimonio();
        p.setId(this.id);
        p.setMarcaId(this.marcaId);
        p.setNome(this.nome);
        p.setDescricao(this.descricao);
        p.setN_tombo(this.n_tombo);
        return p;
	} 
}