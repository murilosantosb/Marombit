package com.marombit.Controller;

import com.marombit.Model.Aluno;
import com.marombit.Service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Alunos", description = "Endpoints de gerenciamento de alunos da academia.")
public class AlunoController {

    @Autowired
    private AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os alunos", description = "Retorna uma lista completa de alunos cadastrados.")
    public List<Aluno> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    @Operation(summary = "Cadastrar alunos", description = "Retornar o cadastro do aluno.")
    @ApiResponse(responseCode = "200", description = "Retorna o aluno salvo")
    @ApiResponse(responseCode = "409", description = "CPF já cadastrado!")
    public ResponseEntity<Aluno> criarAluno(@Valid @RequestBody Aluno aluno) {
        Aluno salvo = service.criarAluno(aluno);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar o cadastro do aluno", description = "Atualiza o cadastro do aluno.")
    @ApiResponse(responseCode = "200", description = "Retorna o aluno")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado!")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable Long id, @Valid @RequestBody Aluno aluno) {
        Aluno atualizado = service.atualizar(id, aluno);
        return ResponseEntity.ok(atualizado);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno pelo ID", description = "Retorna os dados do aluno pelo ID.")
    @ApiResponse(responseCode = "200", description = "Retorna somente os dados do usuário.")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado!")
    public ResponseEntity<Aluno> buscarAlunoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarId(id));
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Verificar se a matricula está ativa", description = "Retorna se a matricula do aluno está ativa.")
    @ApiResponse(responseCode = "200", description = "Retorna Status")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado!")
    public ResponseEntity<String> statusMatricula(@PathVariable Long id) {
        Aluno aluno = service.buscarId(id);

        if (aluno.getMatriculaAtiva()) {
            return ResponseEntity.ok("Matricula Ativa");
        }

        return ResponseEntity.ok("Matricula inativa");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar aluno", description = "Apenas deletar o aluno.")
    @ApiResponse(responseCode = "200", description = "Aluno deletado")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado!")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        service.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }
}
