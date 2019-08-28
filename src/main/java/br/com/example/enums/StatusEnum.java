package br.com.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public @Getter @AllArgsConstructor enum StatusEnum {

  APROVADO("Aprovado"), PENDENTE("Pendente"), RECUSADO("Recusado");

  private String descricao;

}
