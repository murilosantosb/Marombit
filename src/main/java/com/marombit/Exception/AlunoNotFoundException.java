package com.marombit.Exception;

public class AlunoNotFoundException extends RuntimeException {
    public AlunoNotFoundException(Long id) {
        super("Aluno nao encontrado com id: " + id);
    }
}
