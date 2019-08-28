package br.com.example.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;

public @Data class Parameter {

  private String[] column;
  private OperationEnum operation;
  private Object value;

  public Parameter(Object value, String operation, String... column) {
    this.column = column;
    this.operation = OperationEnum.fromString(operation);
    this.value = value;
  }

  public Parameter(Object value, String operation, String column) {
    this.column = new String[] {column};
    this.operation = OperationEnum.fromString(operation);
    this.value = value;
  }

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

  public String getColumn() {
    return this.column[0];
  }

  public String[] getColumns() {
    return this.column;
  }

}
