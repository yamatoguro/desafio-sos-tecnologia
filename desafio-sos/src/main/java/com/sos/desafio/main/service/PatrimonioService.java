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

    public PatrimonioDTO getPatrimonio(final Long id) {
        return toDTO(patrimonioRepository.findById(id).get());
    }

    public PatrimonioDTO[] getAll() {
        final List<Patrimonio> patrimonios = patrimonioRepository.findAll();
        final PatrimonioDTO[] patrimoniosArray = new PatrimonioDTO[patrimonios.size()];
        final List<PatrimonioDTO> dto = toDTOArray(patrimonios);
        for (int i = 0; i < patrimoniosArray.length; i++) {
            patrimoniosArray[i] = dto.get(i);
        }
        return patrimoniosArray;
    }

    public String update(final Long id, final Patrimonio patrimonio) {
        if (patrimonio != null && id != null && id > 0) {
            final Patrimonio p = patrimonioRepository.getOne(id);
            p.setDescricao(patrimonio.getDescricao());
            p.setMarcaId(patrimonio.getMarcaId());
            p.setNome(patrimonio.getNome());
            p.setPdf(patrimonio.getPdf());
            patrimonioRepository.save(p);
            return "201 - Sucesso";
        }
        return "Patrimônio ou id não recebido corretamente.";
    }

    public String delete(final Long id) {
        if (id != null && id > 0) {
            {
            }
            patrimonioRepository.deleteById(id);
            return "201 - Sucesso";
        }
        return "Id não recebido corretamente.";
    }

    @Transactional
    public String cadastra(final Patrimonio patrimonio) {
        final Patrimonio p = new Patrimonio();
        p.setMarcaId(patrimonio.getMarcaId());
        p.setNome(patrimonio.getNome());
        p.setDescricao(patrimonio.getDescricao());
        p.setPdf(patrimonio.getPdf());
        p.setN_tombo(geraTomboValido());
        patrimonioRepository.save(p);
        return p.toJson();
    }

    private String geraTomboValido() {
        final String tombo = geraTombo();
        final List<Patrimonio> patrimonios = patrimonioRepository.findAll();
        patrimonios.forEach(p -> {
            if (tombo == p.getN_tombo() || tombo == "") {
                geraTomboValido();
            }
        });
        return tombo;
    }

    private String geraTombo() {
        return Long.toHexString(Double.doubleToLongBits(Math.random() * 12345L));
    }

    public PatrimonioDTO toDTO(final Patrimonio p) {
        final PatrimonioDTO patrimonio = new PatrimonioDTO(p);
        patrimonio.setMarca(marcaService.getMarca(patrimonio.getMarcaId()).getNome());
        return patrimonio;
    }

    public List<PatrimonioDTO> toDTOArray(final List<Patrimonio> p) {
        final List<PatrimonioDTO> patrimonioDTOs = new ArrayList<PatrimonioDTO>();
        p.forEach(patrimonio -> {
            patrimonioDTOs.add(toDTO(patrimonio));
        });
        return patrimonioDTOs;
    }
}