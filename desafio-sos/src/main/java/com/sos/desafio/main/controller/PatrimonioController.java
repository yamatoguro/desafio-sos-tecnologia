package com.sos.desafio.main.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sos.desafio.main.model.dto.PatrimonioCadastroDTO;
import com.sos.desafio.main.model.dto.PatrimonioDTO;
import com.sos.desafio.main.service.PatrimonioService;

import org.apache.tomcat.util.json.ParseException;
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
 * @method GET /patrimonios
 * @return Obter todos os patrimônios
 * 
 * @method GET /patrimonios/{id}
 * @return Obter um patrimônio por ID
 * 
 * @method POST /patrimonios
 * @return Inserir um novo patrimônio
 * 
 * @method PUT /patrimonios/{id}
 * @return Alterar os dados de um patrimônio
 * 
 * @method DELETE /patrimonios/{id}
 * @return Excluir um patrimônio
 */
@RestController
@RequestMapping(path = "/patrimonios", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatrimonioController {

    @Autowired
    private PatrimonioService patrimonioService;

    @GetMapping
    public PatrimonioDTO[] getPatrimonios() {
        return patrimonioService.getAll();
    }

    @GetMapping(path = "/{id}")
    public PatrimonioDTO getPatrimonio(@PathVariable String id) {
        return patrimonioService.getPatrimonio(Long.parseLong(id));
    }

    @PostMapping
    public String cadastraPatrimonio(@RequestBody String patrimonio)
            throws ParseException, UnsupportedEncodingException {
        String decodedQuery = URLDecoder.decode(patrimonio, "UTF-8");
        decodedQuery = decodedQuery.replace("=", "");
        Gson gson = new Gson();
        Type type = new TypeToken<PatrimonioCadastroDTO>() {
        }.getType();
        PatrimonioCadastroDTO p = gson.fromJson(decodedQuery, type);
        return patrimonioService.cadastra(p.toObj());
    }

    @PutMapping(value = "/{id}")
    public String atualizaPatrimonio(@PathVariable Long id, @RequestBody PatrimonioDTO patrimonio) {
        return patrimonioService.update(id, patrimonio.toObj());
    }

    @DeleteMapping(path = "/{id}")
    public String deletePatrimonio(@PathVariable Long id) {
        return patrimonioService.delete(id);
    }
}
