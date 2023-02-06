package com.example.keycloakserversample;

import com.example.keycloakserversample.api.SamplesApi;
import com.example.keycloakserversample.model.SampleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@Slf4j
public class SampleController implements SamplesApi {

  @Autowired
  @Qualifier("sampleWebClient")
  private WebClient sampleWebClient;

  @Override
  public ResponseEntity<String> helloWorld() {
    sampleWebClient.get()
        .uri("http://localhost:8080/samples/called-by-webclient")
        .retrieve()
        .bodyToMono(String.class)
        .map(string -> "We retrieved the following resource using Client Credentials Grant Type: "
            + string)
        .subscribe(log::info);

    return ResponseEntity.ok().body("Hello World");
  }

  @Override
  public ResponseEntity<SampleDTO> calledByWebclient() {
    return ResponseEntity.ok().body(SampleDTO.builder().fullName("calledByWebClient").build());
  }
}
