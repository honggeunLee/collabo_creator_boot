package org.example.collabo_creator_boot.creator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class GeocodingService {

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public double[] convertAddressToCoordinates(String address) {
        try {
            String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("query", address)
                    .build()
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoApiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            if (response.getBody() != null && response.getBody().get("documents") != null) {
                List<Map<String, Object>> documents = (List<Map<String, Object>>) response.getBody().get("documents");
                if (!documents.isEmpty()) {
                    Map<String, Object> firstResult = documents.get(0);
                    double latitude = Double.parseDouble(firstResult.get("y").toString());
                    double longitude = Double.parseDouble(firstResult.get("x").toString());
                    return new double[]{latitude, longitude};
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Geocoding failed for address: " + address);
        }

        throw new IllegalArgumentException("No geocoding results found for address: " + address);
    }

}
