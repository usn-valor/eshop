package ru.home.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.home.controller.repr.ProductRepr;
import ru.home.service.model.LineItem;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void testEmptyCart() {
        Assertions.assertNotNull(cartService.getLineItems());
        Assertions.assertEquals(0, cartService.getLineItems().size());
        Assertions.assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testAddProduct() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setPrice(new BigDecimal(123));
        expectedProduct.setName("Product Name");

        cartService.addProductQty(expectedProduct, "color", "material", 1);

        List<LineItem> lineItems = cartService.getLineItems();
        Assertions.assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        Assertions.assertEquals("color", lineItem.getColor());
        Assertions.assertEquals("material", lineItem.getMaterial());
        Assertions.assertEquals(1, lineItem.getQty());
    }

    @Test
    public void testDeleteProduct() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setPrice(new BigDecimal(123));
        expectedProduct.setName("Product Name");

        cartService.addProductQty(expectedProduct, "color", "material", 1);
        int size = cartService.getLineItems().size();
        cartService.removeProductQty(expectedProduct, "color", "material", 1);

        Assertions.assertEquals(size - 1, cartService.getLineItems().size());
    }
}
