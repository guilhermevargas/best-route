package br.com.bexs.bestroute.service;

import br.com.bexs.bestroute.dto.BestRouteDTO;
import br.com.bexs.bestroute.dto.RouteDTO;
import br.com.bexs.bestroute.exception.BestRouteNotFoundException;
import br.com.bexs.bestroute.exception.RouteNotExistsException;
import br.com.bexs.bestroute.model.Graph;
import br.com.bexs.bestroute.model.LowestCostPath;
import br.com.bexs.bestroute.model.Route;
import br.com.bexs.bestroute.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.binarySearch;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.util.Assert.notNull;

@Service
public class RouteService {
  private RouteRepository routeRepository;
  private LowestCostPathService lowestCostPathService;

  @Value("${input.routes.file.path}")
  private String filePath;

  public RouteService(RouteRepository routeRepository, LowestCostPathService lowestCostPathService) {
    this.routeRepository = routeRepository;
    this.lowestCostPathService = lowestCostPathService;
  }

  public void create(RouteDTO routeDTO) {
    notNull(routeDTO, "route can't be null");

    Route route = new Route(routeDTO.getFrom(), routeDTO.getTo(), routeDTO.getCost());
    routeRepository.write(route, filePath);
  }

  public BestRouteDTO getBestRoute(String from, String to) {
    notNull(from, "from can't be null");
    notNull(to, "to can't be null");

    Set<Route> routes = routeRepository.read(filePath);
    List<String> vertices = getVertices(routes);
    Graph graph = getGraph(routes, vertices);

    Integer fromIndex = binarySearch(vertices, from);
    Integer toIndex = binarySearch(vertices, to);

    if (fromIndex < 0 || toIndex < 0) {
      throw new RouteNotExistsException(PRECONDITION_FAILED, "Route doesn't exists");
    }

    LowestCostPath lowestCostPath = lowestCostPathService.find(graph);
    int lowestCost = lowestCostPath.getCost()[fromIndex][toIndex];
    int[][] path = lowestCostPath.getPath();

    if (path[fromIndex][toIndex] == -1) {
      throw new BestRouteNotFoundException(NOT_FOUND, "Best Route Not Found");
    }

    BestRouteDTO bestRoute = new BestRouteDTO();
    bestRoute.setLowestCost(lowestCost);
    bestRoute.addConnection(vertices.get(fromIndex));
    addPath(path, fromIndex, toIndex, bestRoute, vertices);
    bestRoute.addConnection(vertices.get(toIndex));

    return bestRoute;
  }

  private void addPath(int[][] path, int from, int to, BestRouteDTO bestRouteDTO, List<String> vertex) {
    if (path[from][to] == from)
      return;

    addPath(path, from, path[from][to], bestRouteDTO, vertex);
    bestRouteDTO.addConnection(vertex.get(path[from][to]));
  }

  private Graph getGraph(Set<Route> routes, List<String> vertices) {
    Graph graph = new Graph(vertices.size());

    for (Route route : routes) {
      int i = binarySearch(vertices, route.getFrom());
      int j = binarySearch(vertices, route.getTo());
      graph.addEdge(i, j, route.getCost());
    }
    return graph;
  }

  private List<String> getVertices(Set<Route> routes) {
    Stream<String> fromStream = routes.stream().map(Route::getFrom);
    Stream<String> toStream = routes.stream().map(Route::getTo);

    return Stream.concat(fromStream, toStream).distinct()
            .sorted()
            .collect(Collectors.toList());
  }
}
