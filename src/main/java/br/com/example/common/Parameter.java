package br.com.example.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;

/**
 * @author Highlander Dantas
 */
public @Data class Parameter {

  private String[] column;
  private OperationEnum operation;
  private Object value;

  /**
   * Transforma os valores passados na assinatura do metodo em uma instancia do {@link Parameter}
   * @param value 
   * @param operation 
   * @param column
   */
  public Parameter(Object value, String operation, String... column) {
    this.column = column;
    this.operation = OperationEnum.fromString(operation);
    this.value = value;
  }

  /**
   * Transforma os valores passados na assinatura do metodo em uma instancia do {@link Parameter}
   * @param value
   * @param operation
   * @param column
   */
  public Parameter(Object value, String operation, String column) {
    this.column = new String[] {column};
    this.operation = OperationEnum.fromString(operation);
    this.value = value;
  }

  /**
   * Retorna o {@link Instanceof} do value do {@link Parameter}
   * @param <Y>
   * @return {@link Instanceof}
   */
  @SuppressWarnings("unchecked")
  public <Y> Y getValueByType() {
    if (value instanceof Integer)
      return (Y) Integer.valueOf(value.toString());
    else if (value instanceof Double)
      return (Y) Double.valueOf(value.toString());
    else if (value instanceof Long)
      return (Y) Long.valueOf(value.toString());
    else if (value instanceof Float)
      return (Y) Float.valueOf(value.toString());
    else if (value instanceof String)
      return (Y) String.valueOf(value.toString());
    else if (value instanceof LocalDateTime) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
      return (Y) LocalDateTime.parse(value.toString(), formatter);
    }
    return (Y) value;
  }

  /**
   * Retorna a primeira coluna
   * @return
   */
  public String getColumn() {
    return this.column[0];
  }

  /**
   * Retorna todas colunas
   * @return
   */
  public String[] getColumns() {
    return this.column;
  }

}
