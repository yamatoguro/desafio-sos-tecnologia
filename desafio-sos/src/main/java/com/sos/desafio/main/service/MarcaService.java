package com.sos.desafio.main.service;

import com.sos.desafio.main.model.Marca;
import com.sos.desafio.main.repository.MarcaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarcaService {

    @Autowired(required = true)
    private MarcaRepository marcaRepository;

    public String save(Marca marca) {
        if(marca != null){
            marcaRepository.save(marca);
            return "201 - Sucesso";
        }
        else
            return "Marca não recebido corretamente.";
    }

    public Marca getMarca(Long id) {
        return marcaRepository.findById(id).get();
	}

	public Marca[] getAll() {
        Marca[] marcas = new Marca[Integer.parseInt(marcaRepository.count()+"")];
		return marcaRepository.findAll().toArray(marcas);
	}

	public String update(Long id, Marca marca) {
		if(marca != null && id != null && id > 0){
            marca.setMarcaId(id);
            marcaRepository.save(marca);
            return "201 - Sucesso";
        }
        return "Marca ou id não recebido corretamente.";
	}

	public String delete(Long id) {
		if(id != null && id > 0){{}
            marcaRepository.deleteById(id);
            return "201 - Sucesso";
        }
        return "Id não recebido corretamente.";
	}

    @Transactional
	public String cadastra(Marca m) {
        marcaRepository.save(m);
		return m.toJson();
	}
}