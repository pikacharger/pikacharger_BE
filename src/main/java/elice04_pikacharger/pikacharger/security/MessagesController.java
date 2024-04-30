package elice04_pikacharger.pikacharger.security;


import io.jsonwebtoken.security.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@RestController
public class MessagesController {

    private final WebClient webClient;

    public MessagesController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> messages(){
        return this.webClient.get()
                .uri("http://localhost:8090/messages")
                .attributes(clientRegistrationId("my-oauth2-client"))
                .retrieve()
                .toEntityList(Message.class)
                .block();
    }

    public record Message(String message){
    }
}
