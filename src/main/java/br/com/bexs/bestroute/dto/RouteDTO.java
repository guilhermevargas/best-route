package br.com.bexs.bestroute.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RouteDTO {
  @NotBlank(message =  "from can't be null")
  private String from;

  @NotBlank(message =  "to can't be null")
  private String to;

  @NotNull(message =  "cost can't be null")
  private Integer cost;

  public RouteDTO(String from, String to, Integer cost) {
    this.from = from;
    this.to = to;
    this.cost = cost;
  }

  public RouteDTO() {}

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public Integer getCost() {
    return cost;
  }

  public void setCost(Integer cost) {
    this.cost = cost;
  }
}
