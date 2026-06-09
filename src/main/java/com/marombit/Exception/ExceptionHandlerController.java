package com.marombit.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> errosDeValidacao(MethodArgumentNotValidException error) {
        Map<String, String> erros = new HashMap<>();

        error.getBindingResult().getAllErrors().forEach((err) -> {
            String campoQueDeuErro = ((FieldError) err).getField();
            String mensagemDeErro = err.getDefaultMessage();

            erros.put(campoQueDeuErro, mensagemDeErro);
        });

        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CpfJaCadastradoException.class)
    public ResponseEntity<Map<String, String>> handleConflito(CpfJaCadastradoException erro) {
        Map<String, String> error = new HashMap<>();

        error.put("erro", erro.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
