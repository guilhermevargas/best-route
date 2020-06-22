package br.com.bexs.bestroute.api;

import br.com.bexs.bestroute.dto.BestRouteDTO;
import br.com.bexs.bestroute.dto.RouteDTO;
import br.com.bexs.bestroute.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/routes")
public class RouteApi {
  private RouteService routeService;

  public RouteApi(RouteService routeService) {
    this.routeService = routeService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody @Valid RouteDTO routeDTO) {
    routeService.create(routeDTO);
  }

  @GetMapping
  public BestRouteDTO getBestRoute(@RequestParam String from, @RequestParam String to) {
    return routeService.getBestRoute(from, to);
  }
}

