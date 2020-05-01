package com.sos.desafio.main.controller;

import com.sos.desafio.main.model.Patrimonio;
import com.sos.desafio.main.repository.PatrimonioRepository;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "patrimonioController")
@ELBeanName(value = "patrimonioController")
@Join(path = "/patrimonio", to = "/patrimonio-form.jsf")
public class PatrimonioController {
    @Autowired
    private PatrimonioRepository PatrimonioRepository;

    private Patrimonio patrimonio = new Patrimonio();

    public String save(Patrimonio patrimonio) {
        PatrimonioRepository.save(patrimonio);
        patrimonio = new Patrimonio();
        return "/patrimonio-list.xhtml?faces-redirect=true";
    }

    public Patrimonio getPatrimonio() {
        return patrimonio;
    }
}
