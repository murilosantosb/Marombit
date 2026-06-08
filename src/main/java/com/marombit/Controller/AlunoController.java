package com.marombit.Controller;

import com.marombit.Model.Aluno;
import com.marombit.Repository.AlunoRepository;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @GetMapping()
    public List<Aluno> listarTodos() {
        return repository.findAll();
    }

    @PostMapping()
    public ResponseEntity<Aluno> criarAluno(@RequestBody Aluno aluno) {
        // var - Serve para tipagem, quando não sabemos o tipo de valor retornado
        var salvo = repository.save(aluno);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable Long id, @RequestBody Aluno aluno) {
        Optional<Aluno> alunoExist = repository.findById(id);

        if (alunoExist.isPresent()) {
            Aluno alunoAtualizado = alunoExist.get();

            alunoAtualizado.setNome(aluno.getNome());
            alunoAtualizado.setCpf(aluno.getCpf());
            alunoAtualizado.setDtNascimento(aluno.getDtNascimento());
            alunoAtualizado.setPlano(aluno.getPlano());
            alunoAtualizado.setMatriculaAtiva(aluno.getMatriculaAtiva());

            Aluno salvo = repository.save(alunoAtualizado);
            return ResponseEntity.ok(salvo);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
