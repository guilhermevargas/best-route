package br.com.bexs.bestroute.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BestRouteDTO {
  private Integer lowestCost;
  private List<String> connections;

  public BestRouteDTO() {
    this.connections = new LinkedList<>();
  }

  public Integer getLowestCost() {
    return lowestCost;
  }

  public void setLowestCost(Integer lowestCost) {
    this.lowestCost = lowestCost;
  }

  public List<String> getConnections() {
    return connections;
  }

  public void addConnection(String connection) {
    this.connections.add(connection);
  }

  @Override
  public String toString() {
    String connectionsString = this.connections.stream()
            .collect(Collectors.joining(" - "));

    return connectionsString + " > $" + this.lowestCost;
  }
}
