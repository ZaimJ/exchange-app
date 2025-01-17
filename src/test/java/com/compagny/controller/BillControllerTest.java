package com.compagny.controller;

import com.compagny.dto.BillRequest;
import com.compagny.dto.BillResponse;
import com.compagny.model.LineItem;
import com.compagny.model.UserExchange;
import com.compagny.model.type.ItemType;
import com.compagny.model.type.UserType;
import com.compagny.service.BillService;
import com.compagny.v1.BillController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class BillControllerTest {
    @Mock
    private BillService billService;

    @InjectMocks
    private BillController billController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(billController).build();
    }

    @Test
    void calculateBill_validRequest_returnsOkResponse() throws Exception {
        BillRequest request = BillRequest.builder()
                .items(Arrays.asList(
                        LineItem.builder()
                                .name("Pain")
                                .category(ItemType.GROCERY)
                                .amount(105.50)
                                .build(),
                        LineItem.builder()
                                .name("Lait")
                                .category(ItemType.GROCERY)
                                .amount(3.20)
                                .build()
                ))
                .originalCurrency("EUR")
                .targetCurrency("USD")
                .user(UserExchange.builder()
                        .id("EMP123")
                        .type(UserType.EMPLOYEE)
                        .registrationDate(LocalDateTime.now())
                        .build())
                .build();

        BillResponse expectedResponse = BillResponse.builder()
                .convertedAmount(123.456)
                .build();

        Mockito.when(billService.calculateBill(request)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.convertedAmount").value(123.456));
    }
}