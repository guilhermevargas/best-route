package br.com.bexs.bestroute.repository;

import br.com.bexs.bestroute.BestRouteApplication;
import br.com.bexs.bestroute.model.Route;
import br.com.bexs.bestroute.utils.FileUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BestRouteApplication.class)
public class RouteRepositoryTest {
  @Autowired
  private RouteRepository repository;

  @Test
  public void mustReadAllConnections() throws IOException {
    File file = FileUtils.createTempFileFromClassPath("input-routes", ".csv");
    Set<Route> routes = repository.read(file.getPath());
    file.deleteOnExit();

    Assert.assertThat(routes, Matchers.hasSize(7));
  }

  @Test(expected = IllegalStateException.class)
  public void mustThrowExceptionToIncorrectContentFormat() throws IOException {
    File file = FileUtils.createTempFileFromClassPath("failed-input-routes", ".csv");
    Set<Route> routes = repository.read(file.getPath());
    file.deleteOnExit();

    Assert.assertThat(routes, Matchers.hasSize(0));
  }

  @Test
  public void mustWriteNewConnections() throws IOException {
    File file = FileUtils.createTempFileFromClassPath("input-routes", ".csv");
    Set<Route> routes = repository.read(file.getPath());
    Assert.assertThat(routes, Matchers.hasSize(7));

    Route newRoute = new Route("SLC", "GRU", 100);
    repository.write(newRoute, file.getPath());

    Set<Route> newRoutes = repository.read(file.getPath());
    file.deleteOnExit();

    Assert.assertThat(newRoutes, Matchers.hasSize(8));
  }

}
