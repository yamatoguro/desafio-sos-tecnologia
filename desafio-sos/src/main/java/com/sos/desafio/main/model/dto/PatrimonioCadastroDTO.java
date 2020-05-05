package com.sos.desafio.main.model.dto;

import com.sos.desafio.main.model.Patrimonio;

public class PatrimonioCadastroDTO {
    private Long marcaId;
    private String nome;
    private String descricao;

    public Patrimonio toObj() {
        Patrimonio p = new Patrimonio();
        p.setMarcaId(this.marcaId);
        p.setNome(this.nome);
        p.setDescricao(this.descricao);
        return p;
	} 
}