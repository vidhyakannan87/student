package com.example.Student.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate() {

    RestTemplateBuilder builder = new RestTemplateBuilder();
    builder.messageConverters(new FormHttpMessageConverter(), new MappingJackson2HttpMessageConverter());

    return builder.build();
  }

}
