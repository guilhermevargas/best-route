package br.com.bexs.bestroute.model;

public class Route {
  private String from;
  private String to;
  private Integer cost;

  public Route(String from, String to, Integer cost) {
    this.from = from;
    this.to = to;
    this.cost = cost;
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public Integer getCost() {
    return cost;
  }

  @Override
  public String toString() {
    return String.format("\n%s,%s,%d", this.getFrom(), this.getTo(), this.cost);
  }
}
