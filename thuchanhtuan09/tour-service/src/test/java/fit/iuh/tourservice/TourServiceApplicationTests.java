package fit.iuh.tourservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TourServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TourServiceApplicationTests {

    @LocalServerPort
    int port;

    @Test
    void getAllTours() throws Exception {
        var response = send("/tours");
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("\"id\":1"));
    }

    @Test
    void getTourById() throws Exception {
        var response = send("/tours/1");
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Ha Long Bay"));
    }

    @Test
    void getTourByIdNotFound() throws Exception {
        assertEquals(404, send("/tours/999").statusCode());
    }

    private HttpResponse<String> send(String path) throws Exception {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).GET().build();
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

}
