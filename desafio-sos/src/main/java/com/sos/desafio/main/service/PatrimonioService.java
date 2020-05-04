package com.sos.desafio.main.service;

import java.util.ArrayList;
import java.util.List;

import com.sos.desafio.main.model.Patrimonio;
import com.sos.desafio.main.model.dto.PatrimonioDTO;
import com.sos.desafio.main.repository.PatrimonioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatrimonioService {

    @Autowired(required = true)
    private PatrimonioRepository patrimonioRepository;

    @Autowired
    private MarcaService marcaService;

    public String save(Patrimonio patrimonio) {
        if(patrimonio != null){
            patrimonioRepository.save(patrimonio);
            return "201 - Sucesso";
        }
        else
            return "Patrimônio não recebido corretamente.";
    }

    public PatrimonioDTO getPatrimonio(Long id) {
        return toDTO(patrimonioRepository.findById(id).get());
	}

	public PatrimonioDTO[] getAll() {
		return toDTOArray(patrimonioRepository.getListaPatrimonios());
	}

	public String update(Long id, Patrimonio patrimonio) {
		if(patrimonio != null && id != null && id > 0){
            patrimonio.setId(id);
            patrimonioRepository.save(patrimonio);
            return "201 - Sucesso";
        }
        return "Patrimônio ou id não recebido corretamente.";
	}

	public String delete(Long id) {
		if(id != null && id > 0){{}
            patrimonioRepository.deleteById(id);
            return "201 - Sucesso";
        }
        return "Id não recebido corretamente.";
	}

    @Transactional
	public String cadastra(Patrimonio p) {
        patrimonioRepository.save(p);
        return p.toJson();
    }
    
    public PatrimonioDTO toDTO(Patrimonio p){
        PatrimonioDTO patrimonio =  new PatrimonioDTO(p);
        patrimonio.setMarca(marcaService.getMarca(patrimonio.getMarcaId()).getNome());
        return patrimonio;
    }

    public PatrimonioDTO[] toDTOArray(Patrimonio[] p){
        List<PatrimonioDTO> patrimonioDTOs = new ArrayList<PatrimonioDTO>();
        for (int i = 0; i < p.length; i++) {
            patrimonioDTOs.add(toDTO(p[i]));
        }
        return (PatrimonioDTO[]) patrimonioDTOs.toArray();
    }
}