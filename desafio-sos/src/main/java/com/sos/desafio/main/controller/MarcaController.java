package com.sos.desafio.main.controller;

import com.sos.desafio.main.model.Marca;
import com.sos.desafio.main.service.MarcaService;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @method GET /marcas
 * @return Obter todos os marcas
 * 
 * @method GET /marcas/{id}
 * @return Obter um marca por ID
 * 
 * @method POST /marcas
 * @return Inserir um novo marca
 * 
 * @method PUT /marcas/{id}
 * @return Alterar os dados de um marca
 * 
 * @method DELETE /marcas/{id}
 * @return Excluir um marca
 */
@RestController
@RequestMapping(path = "/marcas", produces = MediaType.APPLICATION_JSON_VALUE)
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public Marca[] getMarcas() {
        return marcaService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Marca getMarca(@RequestParam String id) {
        return marcaService.getMarca(Long.parseLong(id));
    }

    @PostMapping
    public String cadastraMarca(@RequestBody String marca) {
        Marca p;
        try {
            p = (Marca) new JSONParser(marca).parse();
            return marcaService.cadastra(p);
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PutMapping(value="/{id}")
    public String atualizaMarca(@PathVariable Long id, @RequestBody Marca marca) {
        return marcaService.update(id, marca);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteMarca(@PathVariable Long id) {
        return marcaService.delete(id);
    }
}
