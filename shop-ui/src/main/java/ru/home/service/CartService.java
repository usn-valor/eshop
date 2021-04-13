package ru.home.service;

import ru.home.controller.repr.ProductRepr;
import ru.home.service.model.LineItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    void addProductQty(ProductRepr productRepr, String color, String material, int qty);

    void removeProductQty(ProductRepr productRepr, String color, String material, int qty);

    List<LineItem> getLineItems();

    BigDecimal getTotal();
}
