package br.com.bexs.bestroute.model;

import java.math.BigDecimal;

public class Graph {
  private int adjMatrix[][];
  private int numVertices;

  public Graph(int numVertices) {
    this.numVertices = numVertices;
    adjMatrix = new int[numVertices][numVertices];

    for (int i = 0; i < numVertices; i++) {
      for (int j = 0; j < numVertices; j++) {
        if (i == j) {
          adjMatrix[i][j] = 0;
        } else {
          adjMatrix[i][j] = Integer.MAX_VALUE;
        }
      }
    }
  }

  public int[][] getAdjMatrix() {
    return this.adjMatrix.clone();
  }

  public int getVertices() {
    return this.numVertices;
  }

  public void addEdge(int i, int j, int value) {
    adjMatrix[i][j] = value;
  }

}
