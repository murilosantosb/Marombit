package com.marombit.Controller;

import com.marombit.Model.Aluno;
import com.marombit.Service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Aluno> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Aluno> criarAluno(@Valid @RequestBody Aluno aluno) {
        Aluno salvo = service.criarAluno(aluno);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable Long id, @Valid @RequestBody Aluno aluno) {
        Aluno atualizado = service.atualizar(id, aluno);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarAlunoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarId(id));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> statusMatricula(@PathVariable Long id) {
        Aluno aluno = service.buscarId(id);

        if (aluno.getMatriculaAtiva()) {
            return ResponseEntity.ok("Matricula Ativa");
        }

        return ResponseEntity.ok("Matricula inativa");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        service.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }
}
