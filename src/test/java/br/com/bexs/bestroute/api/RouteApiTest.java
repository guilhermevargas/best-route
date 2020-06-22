package br.com.bexs.bestroute.api;

import br.com.bexs.bestroute.BestRouteApplication;
import br.com.bexs.bestroute.dto.BestRouteDTO;
import br.com.bexs.bestroute.dto.RouteDTO;
import br.com.bexs.bestroute.model.Route;
import br.com.bexs.bestroute.repository.RouteRepository;
import br.com.bexs.bestroute.service.RouteService;
import br.com.bexs.bestroute.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BestRouteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteApiTest {

  private TestRestTemplate restTemplate = new TestRestTemplate();

  @Autowired
  private RouteRepository routeRepository;

  @Autowired
  private RouteService routeService;

  @Value("${local.server.port}")
  private Integer localPort;

  private String BASE_PATH;

  @Before
  public void setup() {
    BASE_PATH = String.format("http://localhost:%d/routes", localPort);
  }

  @Test
  public void mustCreateANewRoute() throws IOException {
    File file = mockFile();

    RouteDTO routeDTO = new RouteDTO("XXX", "YYY", 100);

    HttpEntity<RouteDTO> request = mountHttpEntity(routeDTO);

    ResponseEntity<Object> response = restTemplate.postForEntity(BASE_PATH, request, Object.class);

    assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

    Set<Route> routes = this.routeRepository.read(file.getPath());
    assertThat(routes, hasSize(1));

    Route newRoute = routes.iterator().next();
    assertThat(newRoute.getFrom(), is(routeDTO.getFrom()));
    assertThat(newRoute.getTo(), is(routeDTO.getTo()));
    assertThat(newRoute.getCost(), is(routeDTO.getCost()));

    file.deleteOnExit();
  }

  @Test
  public void mustNotCreateARouteWithInvalidFields() throws IOException {
    File file = mockFile();

    RouteDTO routeDTO = new RouteDTO(null, "YYY", 100); // without from value

    HttpEntity<RouteDTO> request = mountHttpEntity(routeDTO);

    ResponseEntity<Object> response = restTemplate.postForEntity(BASE_PATH, request, Object.class);

    assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));

    Set<Route> routes = this.routeRepository.read(file.getPath());
    assertThat(routes, hasSize(0));

    file.deleteOnExit();
  }

  @Test
  public void mustGetLowestCostPath() throws IOException {
    File file = mockFileFromClassPath();
    HttpEntity<Object> httpEntity = mountHttpEntity();

    String uri = UriComponentsBuilder.fromHttpUrl(BASE_PATH)
            .queryParam("from", "GRU")
            .queryParam("to", "CDG")
            .toUriString();

    ResponseEntity<BestRouteDTO> response = this.restTemplate.exchange(uri, GET, httpEntity, BestRouteDTO.class);
    BestRouteDTO responseBody = response.getBody();
    List<String> connections = responseBody.getConnections();

    assertThat(responseBody.getLowestCost(), is(40));
    assertThat(connections, hasSize(5));
    assertThat(connections, contains("GRU", "BRC", "SCL", "ORL", "CDG"));

    file.deleteOnExit();
  }

  @Test
  public void mustReturn412WhenRouteNotExists() throws IOException {
    File file = mockFileFromClassPath();
    HttpEntity<Object> httpEntity = mountHttpEntity();

    String uri = UriComponentsBuilder.fromHttpUrl(BASE_PATH)
            .queryParam("from", "YYY")
            .queryParam("to", "XXX")
            .toUriString();

    ResponseEntity<BestRouteDTO> response = this.restTemplate.exchange(uri, GET, httpEntity, BestRouteDTO.class);
    assertThat(response.getStatusCode(), is(HttpStatus.PRECONDITION_FAILED));

    file.deleteOnExit();
  }

  private HttpEntity<Object> mountHttpEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json;charset=UTF-8");
    return new HttpEntity<>(null, headers);
  }

  private HttpEntity<RouteDTO> mountHttpEntity(RouteDTO routeDTO) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json;charset=UTF-8");
    return new HttpEntity<>(routeDTO, headers);
  }

  private File mockFile() throws IOException {
    File file = Files.createTempFile("input-routes", ".csv").toFile();
    ReflectionTestUtils.setField(routeService, "filePath", file.getPath());
    return file;
  }

  private File mockFileFromClassPath() throws IOException {
    File file = FileUtils.createTempFileFromClassPath("input-routes", ".csv");
    ReflectionTestUtils.setField(routeService, "filePath", file.getPath());
    return file;
  }

}
