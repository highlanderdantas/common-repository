package br.com.example.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import br.com.example.enums.StatusEnum;
import lombok.Data;


@Entity
public @Data class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private Boolean possuiCnh;
    private LocalDate dataNascimento;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    
}