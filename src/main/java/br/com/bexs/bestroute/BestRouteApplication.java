package br.com.bexs.bestroute;

import br.com.bexs.bestroute.dto.BestRouteDTO;
import br.com.bexs.bestroute.service.RouteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Scanner;


@SpringBootApplication
public class BestRouteApplication {

  private final RouteService routeService;

  public BestRouteApplication(RouteService routeService) {
    this.routeService = routeService;
  }

  public static void main(String[] args) {
    if (args.length == 0)
      throw new IllegalStateException("file path is required");

    String filePath = args[0];
    System.setProperty("input.routes.file.path", filePath);

    SpringApplication.run(BestRouteApplication.class, args);
  }

  @Bean
  @Profile("DEV")
  public CommandLineRunner run() {
    return args -> {
      do {
        try {
          System.out.print("please enter the route: ");
          Scanner scanner = new Scanner(System.in);
          String route = scanner.nextLine();
          String[] routes = route.split("-");
          BestRouteDTO bestRoute = routeService.getBestRoute(routes[0], routes[1]);
          System.out.println("best route: " + bestRoute);
        } catch (Exception ex) {
          System.err.println(ex.getMessage());
        }
      } while (true);
    };

  }
}
