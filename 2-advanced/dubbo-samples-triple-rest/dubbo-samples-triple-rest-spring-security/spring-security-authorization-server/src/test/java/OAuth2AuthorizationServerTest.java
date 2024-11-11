import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OAuth2AuthorizationServerTest {

//    @LocalServerPort
    private int port = 9000;

    private final String clientId = "49fd8518-12eb-422b-9264-2bae0ab89f66";
    private final String clientSecret = "H3DTtm2fR3GRAdr4ls1mcg";

    @Test
    public void testClientCredentialsGrantFlow() {
        // build Basic Auth header
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        // build RestClient request
        RestClient restClient = RestClient.builder().build();
        String url = "http://localhost:" + port + "/oauth2/token";

        try {
            // make a post request
            String response = restClient.post()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .body("grant_type=client_credentials&scope=read")
                    .retrieve()
                    .body(String.class);

            System.out.println("Access Token Response: " + response);
        } catch (RestClientResponseException e) {
            // use getStatusCode().value() to get status code
            assertEquals(HttpStatus.UNAUTHORIZED.value(), e.getStatusCode().value(), "请求失败，未授权");
            System.err.println("Error Response: " + e.getResponseBodyAsString());
        }
    }
}
