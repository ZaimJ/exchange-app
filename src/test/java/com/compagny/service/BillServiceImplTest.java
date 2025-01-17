package com.compagny.service;

import com.compagny.dto.BillRequest;
import com.compagny.dto.BillResponse;
import com.compagny.model.LineItem;
import com.compagny.model.UserExchange;
import com.compagny.model.type.ItemType;
import com.compagny.model.type.UserType;
import com.compagny.service.impl.BillServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class BillServiceImplTest {

    @Mock
    private DiscountService discountService;

    @Mock
    private ExchangeRateService exchangeService;

    @InjectMocks
    private BillServiceImpl billService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateBill_shouldCalculateCorrectly() {
        // Prepare test data
        List<LineItem> items = Arrays.asList(
                LineItem.builder().name("Bread").category(ItemType.GROCERY).amount(50.0).build(),
                LineItem.builder().name("Samsung A51").category(ItemType.ELECTRONICS).amount(100.0).build()
        );

        UserExchange user = UserExchange.builder()
                .id("Gabriel")
                .type(UserType.EMPLOYEE)
                .registrationDate(LocalDateTime.now().minusYears(3))
                .build();

        BillRequest request = BillRequest.builder()
                .items(items)
                .originalCurrency("USD")
                .targetCurrency("EUR")
                .user(user)
                .build();

        // Mock service behaviors
        Mockito.when(discountService.calculateDiscount(items, user)).thenReturn(45.0);
        Mockito.when(exchangeService.getExchangeRate("USD", "EUR")).thenReturn(0.92);

        // Execute method
        BillResponse response = billService.calculateBill(request);

        // Assertions
        Assertions.assertEquals(150.0, response.getOriginalAmount());
        Assertions.assertEquals(105.0, response.getDiscountedAmount());
        Assertions.assertEquals(96.6, response.getConvertedAmount(), 0.1);
        Assertions.assertEquals("EUR", response.getTargetCurrency());
    }
}
