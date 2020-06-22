package br.com.bexs.bestroute.model;

import org.springframework.util.Assert;

public class LowestCostPath {
  private int[][] cost;
  private int[][] path;

  public LowestCostPath(int[][] cost, int[][] path) {
    Assert.notNull(cost, "cost can't be null");
    Assert.notNull(path, "path can't be null");

    this.cost = cost;
    this.path = path;
  }

  public int[][] getCost() {
    return cost.clone();
  }

  public int[][] getPath() {
    return path.clone();
  }
}
