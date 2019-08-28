package br.com.example.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Highlander Dantas
 */
public @Getter @AllArgsConstructor enum OperationEnum {

  EQ("="), LT("<"), GT(">"), LIKE("%"), GTE(">="), LTE("<="), IN("..."), NE("<>");

  private String operator;

  /**
   * Verifica o text se ele representa algum valor do enum
   * @param text string a ser analisada
   * @return {@link OperationEnum}
   */
  public static OperationEnum fromString(String text) {
    for (OperationEnum b : OperationEnum.values()) {
      if (b.operator.equalsIgnoreCase(text)) {
        return b;
      }
    }
    return null;
  }

}
