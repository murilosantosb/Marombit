package com.marombit.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório!")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório!")
    @Column(unique = true)
    private String cpf;

    @NotNull(message = "A data de nacimento é obrigatória")
    private LocalDate dtNascimento;

    @NotNull(message = "A matricula ativa é obrigatória!")
    private Boolean matriculaAtiva;

    @NotNull(message = "O plano é obrigatório!")
    @Enumerated(EnumType.STRING)
    private Plano plano;


}
