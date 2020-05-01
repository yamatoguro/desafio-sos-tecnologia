package com.sos.desafio.main.controller;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import com.sos.desafio.main.model.Patrimonio;
import com.sos.desafio.main.repository.PatrimonioRepository;


@Scope (value = "session")
@Component (value = "patrimonioList")
@ELBeanName(value = "patrimonioList")
@Join(path = "/", to = "/patrimonio-list.jsf")
public class PatrimonioListController {
    @Autowired
    private PatrimonioRepository patrimonioRepository;

    private List<Patrimonio> patrimonios;

    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        patrimonios = patrimonioRepository.findAll();
    }

    public List<Patrimonio> getPatrimonios() {
        return patrimonios;
    }
}