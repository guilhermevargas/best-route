package br.com.bexs.bestroute.repository;

import br.com.bexs.bestroute.exception.FailedToReadConnectionException;
import br.com.bexs.bestroute.exception.FailedToWriteConnectionException;
import br.com.bexs.bestroute.model.Route;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RouteRepository {
  private static final int AIRPORT_FROM = 0;
  private static final int AIRPORT_TO = 1;
  private static final int COST = 2;

  public Set<Route> read(String path) {
    Assert.hasText(path, "path can't be null");

    try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {

      return fileReader.lines()
              .filter(line -> line != null && !line.isEmpty())
              .map(this::toRoute).collect(Collectors.toSet());

    } catch (IOException e) {
      throw new FailedToReadConnectionException(
              "Failed while try to read csv from path: " + path, e
      );
    }
  }

  public void write(Route route, String path) {
    Assert.notNull(route, "route can't be null");
    Assert.hasText(path, "path can't be null");

    try (FileWriter fileWriter = new FileWriter(path, true)) {
      fileWriter.append(route.toString());
    } catch (Exception e) {
      throw new FailedToWriteConnectionException(
              "Failed while try to write csv from path: " + path, e
      );
    }
  }

  public Route toRoute(String line) {
    String[] data = line.split(",");

    if (data.length != 3)
      throw new IllegalStateException(
              String.format("CSV contains route in a incorrect format %s. Expected: GRU,CDG,75", line));

    return new Route((data[AIRPORT_FROM]), (data[AIRPORT_TO]), Integer.valueOf(data[COST]));
  }

}
