package com.incubyte.sweetshop.service;

import com.incubyte.sweetshop.entity.Sweet;
import com.incubyte.sweetshop.repository.SweetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMockMvc(addFilters = false)
class SweetServiceTest {

    @Autowired
    private SweetService sweetService;

    @Autowired
    private SweetRepository sweetRepository;

    @Test
    void shouldAddSweet() {
        Sweet sweet = new Sweet();
        sweet.setName("Ladoo");
        sweet.setCategory("Indian");
        sweet.setPrice(new BigDecimal("10"));
        sweet.setQuantity(5);

        Sweet saved = sweetService.addSweet(sweet);

        assertNotNull(saved.getId());
    }

    @Test
    void shouldReturnAllSweets() {
        List<Sweet> sweets = sweetService.getAllSweets();
        assertNotNull(sweets);
    }

    @Test
    void shouldUpdateSweet() {
        Sweet sweet = new Sweet();
        sweet.setName("Barfi");
        sweet.setCategory("Indian");
        sweet.setPrice(new BigDecimal("15"));
        sweet.setQuantity(10);

        Sweet saved = sweetService.addSweet(sweet);

        Sweet updated = new Sweet();
        updated.setName("Milk Barfi");
        updated.setCategory("Indian");
        updated.setPrice(new BigDecimal("20"));
        updated.setQuantity(8);

        Sweet result = sweetService.updateSweet(saved.getId(), updated);

        assertEquals("Milk Barfi", result.getName());
        assertEquals(8, result.getQuantity());
    }

    @Test
    void shouldDeleteSweet() {
        Sweet sweet = new Sweet();
        sweet.setName("Jalebi");
        sweet.setQuantity(5);

        Sweet saved = sweetService.addSweet(sweet);
        sweetService.deleteSweet(saved.getId());

        assertFalse(sweetRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldSearchSweetByName() {
        Sweet sweet = new Sweet();
        sweet.setName("Rasgulla");
        sweet.setCategory("Bengali");
        sweet.setPrice(new BigDecimal("25"));
        sweet.setQuantity(10);

        sweetService.addSweet(sweet);

        List<Sweet> result =
                sweetService.searchSweets("rasgulla", null, null, null);

        assertFalse(result.isEmpty());
    }

    @Test
    void shouldPurchaseSweet() {
        Sweet sweet = new Sweet();
        sweet.setName("Kaju Katli");
        sweet.setPrice(new BigDecimal("30"));
        sweet.setQuantity(2);

        Sweet saved = sweetService.addSweet(sweet);
        Sweet purchased = sweetService.purchaseSweet(saved.getId());

        assertEquals(1, purchased.getQuantity());
    }

    @Test
    void shouldFailWhenSweetOutOfStock() {
        Sweet sweet = new Sweet();
        sweet.setName("Peda");
        sweet.setQuantity(0);

        Sweet saved = sweetService.addSweet(sweet);

        assertThrows(RuntimeException.class,
                () -> sweetService.purchaseSweet(saved.getId()));
    }

    @Test
    void shouldRestockSweet() {
        Sweet sweet = new Sweet();
        sweet.setName("Halwa");
        sweet.setQuantity(3);

        Sweet saved = sweetService.addSweet(sweet);
        Sweet restocked = sweetService.restockSweet(saved.getId(), 5);

        assertEquals(8, restocked.getQuantity());
    }

    @Test
    void shouldFailWhenRestockAmountInvalid() {
        Sweet sweet = new Sweet();
        sweet.setName("Soan Papdi");
        sweet.setQuantity(4);

        Sweet saved = sweetService.addSweet(sweet);

        assertThrows(RuntimeException.class,
                () -> sweetService.restockSweet(saved.getId(), 0));
    }
}
