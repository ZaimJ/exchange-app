package com.compagny.service;

import com.compagny.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

class ExchangeRateServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exchangeRateService = new ExchangeRateServiceImpl(restTemplate);
    }

    @Test
    void getExchangeRate_shouldReturnCorrectRate() {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Object> rates = new HashMap<>();
        rates.put("EUR", 0.92);
        responseBody.put("rates", rates);

        Mockito.when(restTemplate.getForEntity(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any()
        )).thenReturn(ResponseEntity.ok(responseBody));

        double rate = exchangeRateService.getExchangeRate("USD", "EUR");
        Assertions.assertEquals(0.92, rate);
    }
}