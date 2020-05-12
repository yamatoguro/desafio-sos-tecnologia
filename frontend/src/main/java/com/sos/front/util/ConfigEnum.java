package com.sos.front.util;

import java.nio.file.Paths;

public enum ConfigEnum {
    
    API_URL("http://localhost:12333");
 
    private final String descricao;
 
    ConfigEnum(String descricao) {
        this.descricao = descricao;
    }
 
    public String getDescricao() {
        return descricao;
    }
}
