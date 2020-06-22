package br.com.bexs.bestroute.service;

import br.com.bexs.bestroute.BestRouteApplication;
import br.com.bexs.bestroute.model.Graph;
import br.com.bexs.bestroute.model.LowestCostPath;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BestRouteApplication.class)
public class LowestCostServiceTest {
  @Autowired
  private LowestCostPathService lowestCostPathService;

  private final int brc = 0;
  private final int cdg = 1;
  private final int orl = 2;
  private final int gru = 3;
  private final int slc = 4;
  private final int vertices = 5;

  @Test
  public void mustFindLowestCostPathFromGRUToCDG() {
    Graph graph = new Graph(vertices);
    graph.addEdge(brc, slc, 5); // connections
    graph.addEdge(orl, cdg, 5);
    graph.addEdge(gru, brc, 10);
    graph.addEdge(gru, cdg, 75);
    graph.addEdge(gru, orl, 56);
    graph.addEdge(gru, slc, 20);
    graph.addEdge(slc, orl, 20);

    LowestCostPath lowestCostPath = lowestCostPathService.find(graph);

    assertThat(lowestCostPath, notNullValue());
    assertThat(lowestCostPath.getCost()[gru][cdg], is(40));

    // path 3 - 0 - 4 - 2 - 1 recursivo
    int[][] path = lowestCostPath.getPath();
    int step1 = path[gru][cdg];
    assertThat(2, is(step1));

    int step2 = path[gru][step1];
    assertThat(4, is(step2));

    int step3 = path[gru][step2];
    assertThat(0, is(step3));
  }
}
