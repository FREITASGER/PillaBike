package com.svalero.pillaBike.exception;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private int code;
    private  String message;
    private Map<String, String> errors; //mapa de errores

    //constructor para tener solo codigo y mensaje
    public ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
        errors = new HashMap<>(); //para que no devuelva nada en vez de un NULL
    }
}