package com.marombit.Service;

import com.marombit.Exception.AlunoNotFoundException;
import com.marombit.Exception.CpfJaCadastradoException;
import com.marombit.Model.Aluno;
import com.marombit.Repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno criarAluno(Aluno aluno) {
        if (alunoRepository.existsByCpf(aluno.getCpf())) {
            throw new CpfJaCadastradoException(aluno.getCpf());
        }

        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(Long id, Aluno aluno) {
        Aluno alunoExist = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException(id));

        alunoExist.setNome(aluno.getNome());
        alunoExist.setCpf(aluno.getCpf());
        alunoExist.setDtNascimento(aluno.getDtNascimento());
        alunoExist.setPlano(aluno.getPlano());
        alunoExist.setMatriculaAtiva(aluno.getMatriculaAtiva());

        return alunoRepository.save(alunoExist);
    }

    public Aluno buscarId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException(id));
    }

    public void deletarAluno(Long id) {
        alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException(id));

        alunoRepository.deleteById(id);
    }
}
