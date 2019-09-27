package com.sinopec.springbootdemo.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HttpService {

    public String client(String url, HttpMethod method, Map<String, String> params) {
        RestTemplate template = new RestTemplate();
        final String[] finalUrl = {url + "?"};
        params.forEach((key, value) -> {
            finalUrl[0] = finalUrl[0] + key + "=" + value + "&";
        });
        System.out.println(finalUrl[0]);
        ResponseEntity<String> response = template.getForEntity(finalUrl[0], String.class);
        return response.getBody();
    }
}
