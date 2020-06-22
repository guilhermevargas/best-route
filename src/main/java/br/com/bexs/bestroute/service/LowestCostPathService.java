package br.com.bexs.bestroute.service;

import br.com.bexs.bestroute.model.Graph;
import br.com.bexs.bestroute.model.LowestCostPath;
import org.springframework.stereotype.Service;

@Service
public class LowestCostPathService {
  public LowestCostPath find(Graph graph) {
    int N = graph.getVertices();
    int[][] adjMatrix = graph.getAdjMatrix();

    int[][] cost = new int[N][N];
    int[][] path = new int[N][N];

    for (int v = 0; v < N; v++) {
      for (int u = 0; u < N; u++) {
        cost[v][u] = adjMatrix[v][u];

        if (v == u)
          path[v][u] = 0;
        else if (cost[v][u] != Integer.MAX_VALUE)
          path[v][u] = v;
        else
          path[v][u] = -1;
      }
    }

    for (int k = 0; k < N; k++) {
      for (int v = 0; v < N; v++) {
        for (int u = 0; u < N; u++) {

          if (cost[v][k] != Integer.MAX_VALUE
                  && cost[k][u] != Integer.MAX_VALUE
                  && (cost[v][k] + cost[k][u] < cost[v][u])) {
            cost[v][u] = cost[v][k] + cost[k][u];
            path[v][u] = path[k][u];
          }
        }
      }
    }

    return new LowestCostPath(cost, path);
  }
}
