package com.compagny.service.impl;

import com.compagny.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final RestTemplate restTemplate;

    @Value("${exchange.api.url}")
    private String apiUrl;

    @Value("${exchange.api.key}")
    private String apiKey;

    @Override
    @Cacheable(value = "exchangeRates", key = "#from + '-' + #to")
    public double getExchangeRate(String from, String to) {
        try {
            String url = String.format("%s/%s?apikey=%s", apiUrl, from, apiKey);
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> rates = response.getBody() != null ? (Map<String, Object>) response.getBody().get("rates") : new HashMap<>();

            return Optional.ofNullable(rates.get(to))
                    .map(rate -> ((Number) rate).doubleValue())
                    .orElseThrow(() -> new RuntimeException(
                            String.format("Exchange rate not found from %s to %s", from, to)));
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch exchange rate", e);
        }
    }
}
