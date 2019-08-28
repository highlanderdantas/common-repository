package br.com.example.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

public @Getter @AllArgsConstructor enum OperationEnum {

  EQ("="), LT("<"), GT(">"), LIKE("%"), GTE(">="), LTE("<="), IN("..."), NE("<>");

  private String operator;

  public static OperationEnum fromString(String text) {
    for (OperationEnum b : OperationEnum.values()) {
      if (b.operator.equalsIgnoreCase(text)) {
        return b;
      }
    }
    return null;
  }

}
