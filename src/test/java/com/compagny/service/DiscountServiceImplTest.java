package com.compagny.service;

import com.compagny.model.LineItem;
import com.compagny.model.UserExchange;
import com.compagny.model.type.ItemType;
import com.compagny.model.type.UserType;
import com.compagny.service.impl.DiscountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class DiscountServiceImplTest {

    private DiscountServiceImpl discountService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountServiceImpl();
    }

    @Test
    void calculateDiscount_forEmployee_shouldApplyDiscount() {
        // Prepare test data
        List<LineItem> items = Arrays.asList(
                LineItem.builder().name("Cheese").category(ItemType.GROCERY).amount(50.0).build(),
                LineItem.builder().name("Iphone 11").category(ItemType.ELECTRONICS).amount(100.0).build()
        );

        UserExchange user = UserExchange.builder()
                .id("Patrick")
                .type(UserType.EMPLOYEE)
                .registrationDate(LocalDateTime.now().minusYears(3))
                .build();

        // Execute method
        double discount = discountService.calculateDiscount(items, user);

        Assertions.assertEquals(35.0, discount);
    }

    @Test
    void calculateDiscount_forAffiliate_shouldApplyMediumDiscount() {
        // Prepare test data
        List<LineItem> items = Arrays.asList(
                LineItem.builder().name("Bread").category(ItemType.GROCERY).amount(50.0).build(),
                LineItem.builder().name("Iphone 11").category(ItemType.ELECTRONICS).amount(100.0).build()
        );

        UserExchange user = UserExchange.builder()
                .id("John")
                .type(UserType.AFFILIATE)
                .registrationDate(LocalDateTime.now().minusYears(3))
                .build();

        // Execute method
        double discount = discountService.calculateDiscount(items, user);

        Assertions.assertEquals(15.0, discount);
    }

    @Test
    void calculateDiscount_forLoyalCustomer_shouldApplyDiscount() {
        // Prepare test data
        List<LineItem> items = Arrays.asList(
                LineItem.builder().name("Bread").category(ItemType.GROCERY).amount(50.0).build(),
                LineItem.builder().name("Samsung").category(ItemType.ELECTRONICS).amount(100.0).build()
        );

        UserExchange user = UserExchange.builder()
                .id("Carlos")
                .type(UserType.CUSTOMER)
                .registrationDate(LocalDateTime.now().minusYears(3))
                .build();

        // Execute method
        double discount = discountService.calculateDiscount(items, user);

        Assertions.assertEquals(10.0, discount);
    }

    @Test
    void calculateDiscount_withBulkPurchase_shouldApplyBulkDiscount() {
        // Prepare test data
        List<LineItem> items = Arrays.asList(
                LineItem.builder().name("Samsung A51").category(ItemType.ELECTRONICS).amount(250.0).build(),
                LineItem.builder().name("Ipad 10").category(ItemType.ELECTRONICS).amount(100.0).build()
        );

        UserExchange user = UserExchange.builder()
                .id("David")
                .type(UserType.CUSTOMER)
                .registrationDate(LocalDateTime.now().minusYears(1))
                .build();

        // Execute method
        double discount = discountService.calculateDiscount(items, user);

        Assertions.assertEquals(15.0, discount);
    }
}