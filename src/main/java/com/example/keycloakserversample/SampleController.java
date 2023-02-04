package com.example.keycloakserversample;

import com.example.keycloakserversample.api.SamplesApi;
import com.example.keycloakserversample.model.SampleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController implements SamplesApi {

  @Override
  public ResponseEntity<SampleDTO> fetchSamples() {
    return ResponseEntity.ok().body(SampleDTO.builder().fullName("Hello World").build());
  }

  @Override
  public ResponseEntity<SampleDTO> saveSample(SampleDTO sampleDTO) {
    return SamplesApi.super.saveSample(sampleDTO);
  }
}
