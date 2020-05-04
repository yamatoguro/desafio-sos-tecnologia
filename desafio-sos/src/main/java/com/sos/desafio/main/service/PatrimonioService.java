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

    public PatrimonioDTO getPatrimonio(Long id) {
        return toDTO(patrimonioRepository.findById(id).get());
	}

	public PatrimonioDTO[] getAll() {
		return toDTOArray(patrimonioRepository.getListaPatrimonios());
	}

	public String update(Long id, Patrimonio patrimonio) {
		if(patrimonio != null && id != null && id > 0){
            Patrimonio p = patrimonioRepository.getOne(id);
            p.setDescricao(patrimonio.getDescricao());
            p.setMarcaId(patrimonio.getMarcaId());
            p.setNome(patrimonio.getNome());
            patrimonioRepository.save(p);
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
        p.setN_tombo(geraTomboValido());
        patrimonioRepository.save(p);
        return p.toJson();
    }
    
    private String geraTomboValido() {
        String tombo = "";
        geraTombo(tombo);
        List<Patrimonio> patrimonios = patrimonioRepository.findAll();
        patrimonios.forEach(p -> {
            if(tombo == p.getN_tombo()){
                geraTombo(tombo);
            }
        });
        return tombo;
    }

    private void geraTombo(String tombo) {
        tombo = Long.toHexString(Double.doubleToLongBits(Math.random()));
    }

    public PatrimonioDTO toDTO(Patrimonio p) {
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