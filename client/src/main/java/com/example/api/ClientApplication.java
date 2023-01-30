package api;

import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@SpringBootApplication
@RestController
public class ClientApplication {

    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    public ClientApplication(WebClient.Builder webClientBuilder,
                           ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.loadBalancedWebClientBuilder = webClientBuilder;
        this.lbFunction = lbFunction;
    }

    private Mono<String> handleResponse(ClientResponse r) {
        if (r.statusCode().is2xxSuccessful()) {
            return r.bodyToMono(String.class);
        }

        return r.bodyToMono(String.class)
                .switchIfEmpty(Mono.error(new IllegalStateException("Failed: " + r.statusCode())))
                .flatMap(response -> Mono.error(new IllegalStateException("Failed: " + r.statusCode() + ", " + response)));
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @RequestMapping("/clientTasks")
    @GetMapping("/getallTasks")
    public Mono<String> cgetallTasks() {
        return loadBalancedWebClientBuilder.build().get().uri("http://server/tasks/getallTasks")
                .retrieve().bodyToMono(String.class)
                .map(response -> String.format("%s", response));
    }

    @PostMapping("/enterTask")
    public Mono<String> centerTask(@RequestParam(value = "task") Task task) {
        return loadBalancedWebClientBuilder.build().post().uri("http://server/tasks/enterTask")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("task", task)
                .exchangeToMono(r -> handleResponse(r));
    }

    @GetMapping("/matchTaskByName")
    public Mono<String> cmatchTasksByName(@RequestParam(value = "description") String name) {
        url = String.format("http://server/tasks/matchTaskByName/name?name=%s", name);
        return loadBalancedWebClientBuilder.build().get().uri(url)
                .retrieve().bodyToMono(String.class)
                .map(response -> String.format("%s", response));
    }

    @GetMapping("/matchTaskByDescription")
    public Mono<String> cmatchTaskByDescription(@RequestParam(value = "name") String description) {
        url = String.format("http://server/tasks/matchTaskByDescription/description?description=%s", description);
        return loadBalancedWebClientBuilder.build().get().uri(url")
                .retrieve().bodyToMono(String.class)
                .map(response -> String.format("%s", response));
    }


    @GetMapping("/matchTasksTime")
    public Mono<String> cmatchTasksTime(@RequestParam(value = "time") Integer time) {
        url = String.format("http://server/tasks/matchTasksTime/time?time=%d", time);
        return loadBalancedWebClientBuilder.build().get().uri(url")
                .retrieve().bodyToMono(String.class)
                .map(response -> String.format("%s", response));
    }
}