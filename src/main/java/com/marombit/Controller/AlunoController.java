package com.marombit.Controller;

import com.marombit.Exception.CpfJaCadastradoException;
import com.marombit.Model.Aluno;
import com.marombit.Repository.AlunoRepository;
import jakarta.validation.Valid;
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
    public ResponseEntity<Aluno> criarAluno(@Valid @RequestBody Aluno aluno) {
        if(repository.existsByCpf((aluno.getCpf()))) {
            throw new CpfJaCadastradoException(aluno.getCpf());
        }

        // var - Serve para tipagem, quando não sabemos o tipo de valor retornado
        var salvo = repository.save(aluno);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@Valid @PathVariable Long id, @RequestBody Aluno aluno) {
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

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarAlunoPorId(@PathVariable Long id) {
        Optional<Aluno> aluno = repository.findById(id);

        if (aluno.isPresent()) {
            return ResponseEntity.ok(aluno.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> statusMatricula(@PathVariable Long id) {
        Optional<Aluno> aluno = repository.findById(id);

        if (aluno.isPresent()) {
            if (aluno.get().getMatriculaAtiva() == true) {
                return ResponseEntity.ok("Matricula Ativa!");
            }
            return ResponseEntity.ok("Matricula Inativa!");
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
