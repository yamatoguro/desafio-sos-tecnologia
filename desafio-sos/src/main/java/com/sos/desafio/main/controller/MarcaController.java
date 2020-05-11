package com.sos.desafio.main.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sos.desafio.main.model.Marca;
import com.sos.desafio.main.service.MarcaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Marca getMarca(@PathVariable String id) {
        return marcaService.getMarca(Long.parseLong(id));
    }

    @PostMapping
    public String cadastraMarca(@RequestBody String marca) throws UnsupportedEncodingException {
        String decodedQuery = URLDecoder.decode(marca, "UTF-8");
        decodedQuery = decodedQuery.replace("=", "");
        Gson gson = new Gson();
        Type type = new TypeToken<Marca>() {
        }.getType();
        Marca m = gson.fromJson(decodedQuery, type);
        return marcaService.cadastra(m);
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
